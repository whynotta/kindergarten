package kg.megalab.kindergarten.services.impl;

import jakarta.transaction.Transactional;
import kg.megalab.kindergarten.exception.ConflictException;
import kg.megalab.kindergarten.exception.LogicException;
import kg.megalab.kindergarten.exception.NotFoundException;
import kg.megalab.kindergarten.models.Child;
import kg.megalab.kindergarten.models.Group;
import kg.megalab.kindergarten.models.GroupChildren;
import kg.megalab.kindergarten.models.dto.EnrollChildDto;
import kg.megalab.kindergarten.models.dto.WithdrawChildDto;
import kg.megalab.kindergarten.repositories.ChildRepo;
import kg.megalab.kindergarten.repositories.GroupChildrenRepo;
import kg.megalab.kindergarten.repositories.GroupRepo;
import kg.megalab.kindergarten.response.GlobalResponse;
import kg.megalab.kindergarten.services.GroupChildrenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GroupChildrenServiceImpl implements GroupChildrenService {
    private final GroupRepo groupRepo;
    private final ChildRepo childRepo;
    private final GroupChildrenRepo groupChildrenRepo;

    public GroupChildrenServiceImpl(GroupRepo groupRepo, ChildRepo childRepo, GroupChildrenRepo groupChildrenRepo) {
        this.groupRepo = groupRepo;
        this.childRepo = childRepo;
        this.groupChildrenRepo = groupChildrenRepo;
    }

    @Transactional
    @Override
    public ResponseEntity<GlobalResponse> enrollChild(EnrollChildDto enrollChildDto) {

        Group group = groupRepo.findById(enrollChildDto.getGroupId()).orElseThrow(() ->
                new NotFoundException("Не удалось найти группу с id - " + enrollChildDto.getGroupId()));

        // Категория групп должна быть активной
        if (!group.getGroupCategory().isActive()){
            throw new ConflictException("Категория групп не активна");
        }
        // Проверка на лимит детей в группе
        long activeChildrenCount = groupChildrenRepo.countByGroupIdAndEndDateIsNull(group.getId());
        if (activeChildrenCount >= group.getMaxChildrenCount()){
            throw new ConflictException("Группа заполнена!");
        }
        // Поиск или создание ребенка
        Child child = childRepo.findOrCreate(enrollChildDto.getFirstName(),enrollChildDto.getLastName(),
                enrollChildDto.getPatronymic(),enrollChildDto.getDateOfBirth());

        // Ребенок может быть зачислен только в одну группу
        GroupChildren activeEnrollment = groupChildrenRepo.findByChildIdAndEndDateIsNull(child.getId());
        if(activeEnrollment != null){
            throw new ConflictException("Ребенок уже зачислен в группу: "+ activeEnrollment.getGroup().getName());
        }
        GroupChildren enrollment = new GroupChildren();
        enrollment.setChild(child);
        enrollment.setGroup(group);
        // Минус месяц используется для проверки платежей
        enrollment.setStartDate(LocalDate.now().minusMonths(1));
        enrollment.setPrice(enrollChildDto.getPrice() != null? enrollChildDto.getPrice(): group.getPrice());

        GroupChildren savedEnrollment = groupChildrenRepo.save(enrollment);

        GlobalResponse response = GlobalResponse.created(savedEnrollment);

        return ResponseEntity.status(201).body(response);
    }

    @Transactional
    @Override
    public ResponseEntity<GlobalResponse> withdrawChild(Long id, WithdrawChildDto withdrawChildDto) {
        GroupChildren enrollment = groupChildrenRepo.findById(id).orElseThrow(() ->
                new NotFoundException("запись ребенка в группе не найдена"));

        if (enrollment.getEndDate() != null)
            throw new LogicException("Ребенок уже отчислен из группы");

        LocalDate endDate;
        if (withdrawChildDto.getEndDate() != null) {
            endDate = withdrawChildDto.getEndDate();
        }else
            endDate = LocalDate.now();

        if (endDate.isBefore(enrollment.getStartDate()))
            throw new ConflictException("Дата удаления не  может быть раньше даты зачисления!");
        enrollment.setEndDate(endDate);
        GroupChildren updatedEnrollment = groupChildrenRepo.save(enrollment);

        GlobalResponse response = GlobalResponse.success(updatedEnrollment);
        return ResponseEntity.ok(response);
    }
}

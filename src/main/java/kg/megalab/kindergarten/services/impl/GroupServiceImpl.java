package kg.megalab.kindergarten.services.impl;

import kg.megalab.kindergarten.exception.ConflictException;
import kg.megalab.kindergarten.exception.LogicException;
import kg.megalab.kindergarten.exception.NotFoundException;
import kg.megalab.kindergarten.mappers.GroupMapper;
import kg.megalab.kindergarten.models.Group;
import kg.megalab.kindergarten.models.GroupCategory;
import kg.megalab.kindergarten.models.Teacher;
import kg.megalab.kindergarten.models.dto.GroupCreateDto;
import kg.megalab.kindergarten.models.dto.GroupDto;
import kg.megalab.kindergarten.repositories.GroupCategoryRepo;
import kg.megalab.kindergarten.repositories.GroupRepo;
import kg.megalab.kindergarten.repositories.TeacherRepo;
import kg.megalab.kindergarten.response.GlobalResponse;
import kg.megalab.kindergarten.services.GroupService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupServiceImpl implements GroupService {
    private  final GroupRepo groupRepo;
    private final GroupMapper groupMapper;
    private final GroupCategoryRepo groupCategoryRepo;
    private final TeacherRepo teacherRepo;

    public GroupServiceImpl(GroupRepo groupRepo, GroupCategoryRepo groupCategoryRepo, TeacherRepo teacherRepo) {
        this.groupRepo = groupRepo;
        this.groupCategoryRepo = groupCategoryRepo;
        this.teacherRepo = teacherRepo;
        this.groupMapper = GroupMapper.INSTANCE;
    }

    @Override
    public ResponseEntity<GlobalResponse> createGroup(GroupCreateDto groupCreateDto) {
        if (groupRepo.existsByNameIgnoreCase(groupCreateDto.getName())) {
            throw new ConflictException("Группа с таким названием уже существует!");
        }
        GroupCategory groupCategory = groupCategoryRepo.findById(groupCreateDto.getGroupCategoryId()).
                orElseThrow(() -> new NotFoundException("Категория группы не найдена!"));
        if (!groupCategory.isActive()) {
            throw new LogicException("Категория не активна!");
        }
        Teacher teacher = teacherRepo.findById(groupCreateDto.getTeacherId()).orElseThrow(() ->
                new NotFoundException("Учитель c id - " + groupCreateDto.getTeacherId() + " не найден!"));
        if (!teacher.isActive()) {
            throw new LogicException("Учитель c id - " + groupCreateDto.getTeacherId() + " не активен!");
        }
        if (teacher.getTeacherDegree().ordinal() != 0){
            throw new LogicException("Сотрудник с id - "+ groupCreateDto.getTeacherId() + " не являеется учителем");
        }
        Teacher nanny = teacherRepo.findById(groupCreateDto.getNannyId()).orElseThrow(() ->
                new NotFoundException("Няня с id - " + groupCreateDto.getNannyId() + " не найдена!"));
        if (!nanny.isActive()) {
            throw new LogicException("Няня с id - "+ groupCreateDto.getNannyId()+ " не активна!");
        }
        if (nanny.getTeacherDegree().ordinal() != 1){
            throw new  LogicException("Сотрудник с id - "+ groupCreateDto.getNannyId() + " не является няней!");
        }
        Group group = groupMapper.groupCreateDtoToGroup(groupCreateDto);
        group.setGroupCategory(groupCategory);
        group.setTeacher(teacher);
        group.setNanny(nanny);

        Group savedGroup = groupRepo.save(group);
        GroupDto groupDto = groupMapper.groupToGroupDto(savedGroup);

        GlobalResponse response = GlobalResponse.created(groupDto);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<GlobalResponse> updateGroup(GroupDto groupDto, Long id) {
        Group group = groupRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Группа с id - "+ id + "не найдена"));

        if (groupDto.getName() != null && !groupDto.getName().equalsIgnoreCase(group.getName())) {
            throw new ConflictException("Группа с таким названием уже есть!");
        }

        if (groupDto.getGroupCategory() != null && groupDto.getGroupCategory().getId() != null) {
            GroupCategory groupCategory = groupCategoryRepo.findById(groupDto.getGroupCategory().getId()).orElseThrow(() ->
                    new NotFoundException("Категория групп с id - " + groupDto.getGroupCategory().getId() + " не найдена"));

            if (!groupCategory.isActive()) {
                throw new LogicException("Категория с id - " + groupDto.getGroupCategory().getId() + " не активна!");
            }

            group.setGroupCategory(groupCategory);
        }

        if (groupDto.getTeacher() != null && groupDto.getTeacher().getId() != null) {
            Teacher teacher = teacherRepo.findById(groupDto.getTeacher().getId()).orElseThrow(() ->
                    new NotFoundException("Учитель c id - " + groupDto.getTeacher().getId() + " не найден!"));

            if (!teacher.isActive()) {
                throw new LogicException("Учитель c id - " + groupDto.getTeacher().getId() + " не активен!");
            }


            if (teacher.getTeacherDegree().ordinal() != 0) {
                throw new LogicException("Сотрудник с id - " + groupDto.getTeacher().getId() + " не являеется учителем");
            }

            group.setTeacher(teacher);
        }

        if (groupDto.getNanny() != null && groupDto.getNanny().getId() != null) {
            Teacher nanny = teacherRepo.findById(groupDto.getNanny().getId()).orElseThrow(() ->
                    new NotFoundException("Няня с id - " + groupDto.getNanny().getId() + " не найдена!"));
            if (!nanny.isActive()) {
                throw new LogicException("Няня с id - " + groupDto.getNanny().getId() + " не активна!");
            }

            if (nanny.getTeacherDegree().ordinal() != 1) {
                throw new LogicException("Сотрудник с id - " + groupDto.getNanny().getId() + " не является няней!");
            }

            group.setNanny(nanny);
        }
        group.setMaxChildrenCount(groupDto.getMaxChildrenCount());
        group.setPrice(groupDto.getPrice());

        groupMapper.updateGroupFromGroupDto(groupDto, group);
        Group updatedGroup = groupRepo.save(group);
        GroupDto updatedGroupDto = groupMapper.groupToGroupDto(updatedGroup);

        GlobalResponse response = GlobalResponse.success(updatedGroupDto);
        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<GlobalResponse> deleteGroup(Long id) {
        Group group = groupRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Группа с id - " + id + "не найдена!"));

        groupRepo.delete(group);

        GlobalResponse response = GlobalResponse.success("Группа с id - " + id + "Удалена");
        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<GlobalResponse> findGroupById(Long id) {
        Group group = groupRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Группа с id - " + id + "не найдена!"));
        GroupDto groupDto = groupMapper.groupToGroupDto(group);

        GlobalResponse response = GlobalResponse.success(groupDto);
        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<GlobalResponse> findAllGroups(int pageNo, int pageSize) {
        if(pageNo < 0 || pageSize <= 0){
            throw new LogicException("Ошибка параметром пагинации!");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
        List<Group> groups = groupRepo.findAll(pageable).getContent();
        List<GroupDto> groupDtos = groupMapper.groupsToGroupDtos(groups);

        GlobalResponse response = GlobalResponse.success(groupDtos);
        return ResponseEntity.status(200).body(response);
    }


}

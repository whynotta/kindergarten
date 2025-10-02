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
import kg.megalab.kindergarten.models.dto.PagedResponse;
import kg.megalab.kindergarten.repositories.GroupCategoryRepo;
import kg.megalab.kindergarten.repositories.GroupRepo;
import kg.megalab.kindergarten.repositories.TeacherRepo;
import kg.megalab.kindergarten.response.GlobalResponse;
import kg.megalab.kindergarten.services.GroupService;
import org.springframework.data.domain.Page;
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
        if(group.getPrice() == null)
            group.setPrice(groupCategory.getPrice());
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
                new NotFoundException("Группа с id - " + id + "не найдена"));

        if (groupDto.getName() != null
                && !groupDto.getName().equalsIgnoreCase(group.getName())
                && groupRepo.existsByNameIgnoreCaseAndIdNot(groupDto.getName(), group.getId())) {
            throw new ConflictException("Группа с таким названием уже есть!");
        }
        if (groupDto.getName() != null)
            group.setName(groupDto.getName());

        group.setMaxChildrenCount(groupDto.getMaxChildrenCount());
        group.setPrice(groupDto.getPrice());

        if (groupDto.getGroupCategoryId() != null) {
            GroupCategory groupCategory = groupCategoryRepo.findById(groupDto.getGroupCategoryId()).orElseThrow(() ->
                    new NotFoundException("Категория групп с id - " + groupDto.getGroupCategoryId() + " не найдена"));

            if (!groupCategory.isActive()) {
                throw new LogicException("Категория с id - " + groupDto.getGroupCategoryId() + " не активна!");
            }
            group.setGroupCategory(groupCategory);
        }

            if (groupDto.getTeacherId() != null) {
                Teacher teacher = teacherRepo.findById(groupDto.getTeacherId()).orElseThrow(() ->
                        new NotFoundException("Учитель c id - " + groupDto.getTeacherId() + " не найден!"));

                if (!teacher.isActive()) {
                    throw new LogicException("Учитель c id - " + groupDto.getTeacherId() + " не активен!");
                }

                if (teacher.getTeacherDegree().ordinal() != 0) {
                    throw new LogicException("Сотрудник с id - " + groupDto.getTeacherId() + " не являеется учителем");
                }

                group.setTeacher(teacher);
            }

                if (groupDto.getNannyId() != null) {
                    Teacher nanny = teacherRepo.findById(groupDto.getNannyId()).orElseThrow(() ->
                            new NotFoundException("Няня с id - " + groupDto.getNannyId() + " не найдена!"));
                    if (!nanny.isActive()) {
                        throw new LogicException("Няня с id - " + groupDto.getNannyId() + " не активна!");
                    }

                    if (nanny.getTeacherDegree().ordinal() != 1) {
                        throw new LogicException("Сотрудник с id - " + groupDto.getNannyId() + " не является няней!");
                    }

                    group.setNanny(nanny);
                }

                Group updatedGroup = groupRepo.save(group);
                GroupDto updatedGroupDto = groupMapper.groupToGroupDto(updatedGroup);

                GlobalResponse response = GlobalResponse.success(updatedGroupDto);
                return ResponseEntity.status(200).body(response);
            }

    @Override
    public ResponseEntity<GlobalResponse> deleteGroup(Long id) {
        Group group = groupRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Группа с id - " + id + " не найдена!"));

        groupRepo.delete(group);

        GlobalResponse response = GlobalResponse.success(group);
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

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("groupCategory.name").ascending());
        Page<Group> groupsPage = groupRepo.findAllByOrderByGroupCategoryName(pageable);
        List<GroupDto> groupDtos = groupMapper.groupsToGroupDtos(groupsPage.getContent());

        PagedResponse<GroupDto> pagedResponse = new PagedResponse<>(
                groupDtos,
                groupsPage.getTotalElements(),
                groupsPage.getTotalPages()
        );

        GlobalResponse response = GlobalResponse.success(pagedResponse);
        return ResponseEntity.status(200).body(response);
    }

}

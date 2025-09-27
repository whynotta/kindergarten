package kg.megalab.kindergarten.services.impl;

import kg.megalab.kindergarten.mappers.GroupMapper;
import kg.megalab.kindergarten.models.Group;
import kg.megalab.kindergarten.models.GroupCategory;
import kg.megalab.kindergarten.models.dto.GroupCreateDto;
import kg.megalab.kindergarten.models.dto.GroupDto;
import kg.megalab.kindergarten.repositories.GroupCategoryRepo;
import kg.megalab.kindergarten.repositories.GroupRepo;
import kg.megalab.kindergarten.repositories.TeacherRepo;
import kg.megalab.kindergarten.services.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<GroupDto> createGroup(GroupCreateDto groupCreateDto) {
        if(groupRepo.existsByNameIgnoreCase(groupCreateDto.getName())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Group group = groupMapper.groupCreateDtoToGroup(groupCreateDto);
        group.setGroupCategory(groupCategoryRepo.findById(groupCreateDto.getGroupCategoryId()).orElseThrow());
        group.setTeacher(teacherRepo.findById(groupCreateDto.getTeacherId()).orElseThrow());
        group.setNanny(teacherRepo.findById(groupCreateDto.getNannyId()).orElseThrow());
        groupRepo.save(group);
        GroupDto groupDto = groupMapper.groupToGroupDto(group);
        return new ResponseEntity<>(groupDto, HttpStatus.CREATED);
    }
}

package kg.megalab.kindergarten.services.impl;

import kg.megalab.kindergarten.mappers.GroupCategoryMapper;
import kg.megalab.kindergarten.models.GroupCategory;
import kg.megalab.kindergarten.models.dto.GroupCategoryCreateDto;
import kg.megalab.kindergarten.models.dto.GroupCategoryDto;
import kg.megalab.kindergarten.repositories.GroupCategoryRepo;
import kg.megalab.kindergarten.services.GroupCategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupCategoryServiceImpl implements GroupCategoryService {
    private final GroupCategoryRepo groupCategoryRepo;
    private final GroupCategoryMapper groupCategoryMapper;

    public GroupCategoryServiceImpl(GroupCategoryRepo groupCategoryRepo) {
        this.groupCategoryRepo = groupCategoryRepo;
        this.groupCategoryMapper = GroupCategoryMapper.INSTANCE;
    }

    @Override
    public ResponseEntity<GroupCategoryDto> createGroupCategory(GroupCategoryCreateDto groupCategoryCreateDto) {
        if (groupCategoryRepo.existsByName(groupCategoryCreateDto.getName())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        GroupCategory groupCategory = groupCategoryMapper.groupCategoryCreateDtoToGroupCategory(groupCategoryCreateDto);
        groupCategoryRepo.save(groupCategory);
        GroupCategoryDto groupCategoryDto = groupCategoryMapper.groupCategoryToGroupCategoryDto(groupCategory);
        return new ResponseEntity<>(groupCategoryDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GroupCategoryDto> updateGroupCategory(GroupCategoryDto groupCategoryDto, Long id) {
        if (groupCategoryRepo.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GroupCategory groupCategory = groupCategoryRepo.findById(id).get();
        groupCategoryMapper.updateGroudCategoryFromDto(groupCategoryDto, groupCategory);
        groupCategoryRepo.save(groupCategory);
        GroupCategoryDto updatedGroupCategoryDto = groupCategoryMapper.groupCategoryToGroupCategoryDto(groupCategory);
        return ResponseEntity.ok(updatedGroupCategoryDto);
    }

    @Override
    public ResponseEntity<GroupCategoryDto> deleteGroupCategory(Long id) {
        if (groupCategoryRepo.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GroupCategory groupCategory = groupCategoryRepo.findById(id).get();
        groupCategoryRepo.delete(groupCategory);
        return ResponseEntity.ok(groupCategoryMapper.groupCategoryToGroupCategoryDto(groupCategory));
    }

    @Override
    public ResponseEntity<GroupCategoryDto> findCategoryGroupById(Long id) {
        if(groupCategoryRepo.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GroupCategory groupCategory = groupCategoryRepo.findById(id).get();
        GroupCategoryDto groupCategoryDto = groupCategoryMapper.groupCategoryToGroupCategoryDto(groupCategory);
        return ResponseEntity.ok(groupCategoryDto);
    }

    @Override
    public ResponseEntity<?> findAllCategoryGroup(int pageNo, int pageSize) {
        if (pageNo < 0 && pageSize < 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
        List<GroupCategory> groupCategories = groupCategoryRepo.findAll(pageable).getContent();
        List<GroupCategoryDto> groupCategoryDtos = groupCategoryMapper.groupCategorysToGroupCategoryDtos(groupCategories);
        return ResponseEntity.ok(groupCategoryDtos);
    }
}

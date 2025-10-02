package kg.megalab.kindergarten.services.impl;

import kg.megalab.kindergarten.exception.ConflictException;
import kg.megalab.kindergarten.exception.LogicException;
import kg.megalab.kindergarten.exception.NotFoundException;
import kg.megalab.kindergarten.mappers.GroupCategoryMapper;
import kg.megalab.kindergarten.models.GroupCategory;
import kg.megalab.kindergarten.models.dto.GroupCategoryCreateDto;
import kg.megalab.kindergarten.models.dto.GroupCategoryDto;
import kg.megalab.kindergarten.models.dto.PagedResponse;
import kg.megalab.kindergarten.repositories.GroupCategoryRepo;
import kg.megalab.kindergarten.response.GlobalResponse;
import kg.megalab.kindergarten.services.GroupCategoryService;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<GlobalResponse> createGroupCategory(GroupCategoryCreateDto groupCategoryCreateDto) {
        if (groupCategoryRepo.existsByNameIgnoreCase(groupCategoryCreateDto.getName())){
            throw new ConflictException("Категория с таким именем уже существует!");
        }
        GroupCategory groupCategory = groupCategoryMapper.groupCategoryCreateDtoToGroupCategory(groupCategoryCreateDto);
        GroupCategory createdGroupCategory = groupCategoryRepo.save(groupCategory);
        GroupCategoryDto groupCategoryDto = groupCategoryMapper.groupCategoryToGroupCategoryDto(createdGroupCategory);

        GlobalResponse response = GlobalResponse.created(groupCategoryDto);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<GlobalResponse> updateGroupCategory(GroupCategoryDto groupCategoryDto, Long id) {

        GroupCategory groupCategory = groupCategoryRepo.findById(id).
                orElseThrow(() -> new NotFoundException("Категория не найдена!"));
        if (!groupCategory.getName().equalsIgnoreCase(groupCategoryDto.getName()) &&
                groupCategoryRepo.existsByNameIgnoreCase(groupCategoryDto.getName())){
            throw new ConflictException("Категория с таким именем уже существует");
        }
        groupCategoryMapper.updateGroupCategoryFromDto(groupCategoryDto, groupCategory);
        GroupCategory updatedGroupCategory = groupCategoryRepo.save(groupCategory);
        GroupCategoryDto updatedGroupCategoryDto = groupCategoryMapper.groupCategoryToGroupCategoryDto(updatedGroupCategory);

        GlobalResponse response = GlobalResponse.success(updatedGroupCategoryDto);
        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<GlobalResponse> deleteGroupCategory(Long id) {
        GroupCategory groupCategory = groupCategoryRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Категория не найдена"));
        groupCategoryRepo.delete(groupCategory);

        GlobalResponse response = GlobalResponse.success("Категория групп с id - " + id + " удалена!");
        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<GlobalResponse> findGroupCategoryById(Long id) {
        GroupCategory groupCategory = groupCategoryRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Категория не найдена"));
        GroupCategoryDto groupCategoryDto = groupCategoryMapper.groupCategoryToGroupCategoryDto(groupCategory);

        GlobalResponse response = GlobalResponse.success(groupCategoryDto);
        return ResponseEntity.status(200).body(response);
    }
    @Override
    public ResponseEntity<GlobalResponse> findAllCategoryGroup(int pageNo, int pageSize) {
        if (pageNo < 0 || pageSize <= 0){
            throw new LogicException("Ошибка параметров пагинации");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("name").ascending());
        Page<GroupCategory> groupCategoryPage = groupCategoryRepo.findAll(pageable);
        List<GroupCategoryDto> groupCategoryDtos = groupCategoryMapper.groupCategorysToGroupCategoryDtos(
                groupCategoryPage.getContent());
        PagedResponse<GroupCategoryDto> pagedResponse = new PagedResponse<>(
                groupCategoryDtos,
                groupCategoryPage.getTotalElements(),
                groupCategoryPage.getTotalPages()
        );

        GlobalResponse response = GlobalResponse.success(pagedResponse);
        return ResponseEntity.status(200).body(response);
    }
}

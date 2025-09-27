package kg.megalab.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megalab.kindergarten.models.dto.GroupCategoryCreateDto;
import kg.megalab.kindergarten.models.dto.GroupCategoryDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import kg.megalab.kindergarten.services.GroupCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-category")
@Tag(name = "GroupCategory controller", description = "Управление категориями групп")
public class GroupCategoryController implements CRUDController<GroupCategoryCreateDto, GroupCategoryDto> {
    private final GroupCategoryService groupCategoryService;

    public GroupCategoryController(GroupCategoryService groupCategoryService) {
        this.groupCategoryService = groupCategoryService;
    }

    @PostMapping("")
    @Operation(summary = "Создание категории групп")
    @Override
    public ResponseEntity<GlobalResponse> create(GroupCategoryCreateDto groupCategoryCreateDto) {
        return groupCategoryService.createGroupCategory(groupCategoryCreateDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление категории групп")
    @Override
    public ResponseEntity<GlobalResponse> update(GroupCategoryDto groupCategoryDto, Long id) {
        return groupCategoryService.updateGroupCategory(groupCategoryDto,id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление категории групп")
    @Override
    public ResponseEntity<GlobalResponse> delete(Long id) {

        return groupCategoryService.deleteGroupCategory(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск категории групп по айди")
    @Override
    public ResponseEntity<GlobalResponse> findById(Long id) {

        return groupCategoryService.findCategoryGroupById(id);
    }

    @GetMapping("")
    @Operation(summary = "Вывод всех категорий групп")
    @Override
    public ResponseEntity<GlobalResponse> findAll(int pageNo, int pageSize) {

        return groupCategoryService.findAllCategoryGroup(pageNo,pageSize);
    }
}

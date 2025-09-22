package kg.megalab.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megalab.kindergarten.models.dto.GroupCategoryCreateDto;
import kg.megalab.kindergarten.models.dto.GroupCategoryDto;
import kg.megalab.kindergarten.services.GroupCategoryService;
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
    public ResponseEntity<GroupCategoryDto> create(GroupCategoryCreateDto groupCategoryCreateDto) {
        return null;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление категории групп")
    @Override
    public ResponseEntity<GroupCategoryDto> update(GroupCategoryDto groupCategoryDto, Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление категории групп")
    @Override
    public ResponseEntity<GroupCategoryDto> delete(Long id) {
        return null;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск категории групп по айди")
    @Override
    public ResponseEntity<GroupCategoryDto> findById(Long id) {
        return null;
    }

    @GetMapping("")
    @Operation(summary = "Вывод всех категорий групп")
    @Override
    public ResponseEntity<?> findAll(int pageNo, int pageSize) {
        return null;
    }
}

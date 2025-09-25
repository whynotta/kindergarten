package kg.megalab.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megalab.kindergarten.models.dto.GroupCreateDto;
import kg.megalab.kindergarten.models.dto.GroupDto;
import kg.megalab.kindergarten.services.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@Tag(name = "Category Controller", description = "Управление группами")
public class GroupController implements CRUDController<GroupCreateDto, GroupDto>{
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping()
    @Operation(summary = "Cоздание группы")
    @Override
    public ResponseEntity<GroupDto> create(GroupCreateDto groupCreateDto) {
        return groupService.createGroup(groupCreateDto);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Обновление группы")
    @Override
    public ResponseEntity<GroupDto> update(GroupDto groupDto, Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление группы")
    @Override
    public ResponseEntity<GroupDto> delete(Long id) {
        return null;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск группы по айди")
    @Override
    public ResponseEntity<GroupDto> findById(Long id) {
        return null;
    }

    @GetMapping()
    @Operation(summary = "Вывод всех групп с пагинацией")
    @Override
    public ResponseEntity<?> findAll(int pageNo, int pageSize) {
        return null;
    }
}

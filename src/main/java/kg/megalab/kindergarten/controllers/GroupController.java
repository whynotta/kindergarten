package kg.megalab.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megalab.kindergarten.models.dto.GroupCreateDto;
import kg.megalab.kindergarten.models.dto.GroupDto;
import kg.megalab.kindergarten.response.GlobalResponse;
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
    public ResponseEntity<GlobalResponse> create(GroupCreateDto groupCreateDto) {
        return groupService.createGroup(groupCreateDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление группы")
    @Override
    public ResponseEntity<GlobalResponse> update(GroupDto groupDto, Long id) {
        return groupService.updateGroup(groupDto,id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление группы")
    @Override
    public ResponseEntity<GlobalResponse> delete(Long id) {

        return groupService.deleteGroup(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск группы по айди")
    @Override
    public ResponseEntity<GlobalResponse> findById(Long id) {

        return groupService.findGroupById(id);
    }

    @GetMapping()
    @Operation(summary = "Вывод всех групп с пагинацией")
    @Override
    public ResponseEntity<GlobalResponse> findAll(int pageNo, int pageSize) {

        return groupService.findAllGroups(pageNo,pageSize);
    }
}

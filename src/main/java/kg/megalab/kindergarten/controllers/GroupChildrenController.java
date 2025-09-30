package kg.megalab.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megalab.kindergarten.models.dto.EnrollChildDto;
import kg.megalab.kindergarten.models.dto.WithdrawChildDto;
import kg.megalab.kindergarten.repositories.GroupChildrenRepo;
import kg.megalab.kindergarten.response.GlobalResponse;
import kg.megalab.kindergarten.services.GroupChildrenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-children")
@Tag(name = "Group Children Controller", description = "Управление детьми")
public class GroupChildrenController {
    private final GroupChildrenService groupChildrenService;

    public GroupChildrenController(GroupChildrenService groupChildrenService) {
        this.groupChildrenService = groupChildrenService;
    }


    @PostMapping
    @Operation(summary = "Зачисление ребенка в группу")
    public ResponseEntity<GlobalResponse> enrollChild(@RequestBody EnrollChildDto enrollChildDto){
        return groupChildrenService.enrollChild(enrollChildDto);
    }
    @Operation(summary = "Отчисление ребенка из группы")
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<GlobalResponse> withdrawChild(@PathVariable Long id, @RequestBody WithdrawChildDto withdrawChildDto){
        return groupChildrenService.withdrawChild(id,withdrawChildDto);
    }


}

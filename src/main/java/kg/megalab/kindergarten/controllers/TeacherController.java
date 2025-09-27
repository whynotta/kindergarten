package kg.megalab.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megalab.kindergarten.models.dto.TeacherCreateDto;
import kg.megalab.kindergarten.models.dto.TeacherDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import kg.megalab.kindergarten.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher Controller", description = "Управление сущностями учителей")
public class TeacherController implements CRUDController<TeacherCreateDto, TeacherDto> {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping()
    @Operation(summary = "Создание сущности учителя")
    @Override
    @ApiResponse(responseCode = "201")
    public ResponseEntity<GlobalResponse> create(TeacherCreateDto teacherCreateDto) {
        return teacherService.createTeacher(teacherCreateDto);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновление сущности учителя")
    @Override
    public ResponseEntity<GlobalResponse> update(TeacherDto teacherDto, Long id) {
        return teacherService.updateTeacher(teacherDto,id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление сущности учителя")
    @Override
    public ResponseEntity<GlobalResponse> delete(Long id) {
        return teacherService.deleteTeacher(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск учителя по айди")
    @Override
    public ResponseEntity<GlobalResponse> findById(Long id) {
        return teacherService.findById(id);
    }

    @GetMapping()
    @Operation(summary = "Вывод всех учителей с пагинацией")
    @Override
    public ResponseEntity<GlobalResponse> findAll(int pageNo, int pageSize) {
        return teacherService.findAllTeacher(pageNo,pageSize);
    }
}

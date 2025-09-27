package kg.megalab.kindergarten.services;

import kg.megalab.kindergarten.models.dto.TeacherCreateDto;
import kg.megalab.kindergarten.models.dto.TeacherDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.http.ResponseEntity;

public interface TeacherService {
    ResponseEntity<GlobalResponse> createTeacher(TeacherCreateDto teacherCreateDto);

    ResponseEntity<GlobalResponse> updateTeacher(TeacherDto teacherDto, Long id);

    ResponseEntity<GlobalResponse> deleteTeacher(Long id);

    ResponseEntity<GlobalResponse> findById(Long id);

    ResponseEntity<GlobalResponse> findAllTeacher(int pageNo, int pageSize);
}

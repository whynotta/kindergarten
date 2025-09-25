package kg.megalab.kindergarten.services;

import kg.megalab.kindergarten.models.dto.TeacherCreateDto;
import kg.megalab.kindergarten.models.dto.TeacherDto;
import org.springframework.http.ResponseEntity;

public interface TeacherService {
    ResponseEntity<TeacherDto> createTeacher(TeacherCreateDto teacherCreateDto);

    ResponseEntity<TeacherDto> updateTeacher(TeacherDto teacherDto, Long id);

    ResponseEntity<TeacherDto> deleteTeacher(Long id);

    ResponseEntity<TeacherDto> findById(Long id);

    ResponseEntity<?> findAllTeacher(int pageNo, int pageSize);
}

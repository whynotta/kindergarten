package kg.megalab.kindergarten.services.impl;

import kg.megalab.kindergarten.mappers.TeacherMapper;
import kg.megalab.kindergarten.models.Teacher;
import kg.megalab.kindergarten.models.dto.TeacherCreateDto;
import kg.megalab.kindergarten.models.dto.TeacherDto;
import kg.megalab.kindergarten.repositories.TeacherRepo;
import kg.megalab.kindergarten.services.TeacherService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepo teacherRepo;
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
        this.teacherMapper = TeacherMapper.INSTANCE;
    }

    @Override
    public ResponseEntity<TeacherDto> createTeacher(TeacherCreateDto teacherCreateDto) {
        if(teacherRepo.existsByFirstNameIgnoreCase(teacherCreateDto.getFirstName())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Teacher teacher = teacherMapper.teacherCreateDtoToTeacher(teacherCreateDto);
        teacherRepo.save(teacher);
        TeacherDto teacherDto = teacherMapper.teacherToTeacherDto(teacher);
        return new ResponseEntity<>(teacherDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TeacherDto> updateTeacher(TeacherDto teacherDto, Long id) {
        if(teacherRepo.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Teacher teacher = teacherRepo.findById(id).get();
        teacherMapper.updateTeacherFromDto(teacherDto, teacher);
        teacherRepo.save(teacher);
        TeacherDto updatedTeacherDto = teacherMapper.teacherToTeacherDto(teacher);
        return ResponseEntity.ok(updatedTeacherDto);
    }

    @Override
    public ResponseEntity<TeacherDto> deleteTeacher(Long id) {
        if(teacherRepo.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Teacher teacher = teacherRepo.findById(id).get();
        teacherRepo.delete(teacher);
        return ResponseEntity.ok(teacherMapper.teacherToTeacherDto(teacher));
    }

    @Override
    public ResponseEntity<TeacherDto> findById(Long id) {
        if (teacherRepo.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Teacher teacher = teacherRepo.findById(id).get();
        TeacherDto teacherDto = teacherMapper.teacherToTeacherDto(teacher);
        return ResponseEntity.ok(teacherDto);
    }

    @Override
    public ResponseEntity<?> findAllTeacher(int pageNo, int pageSize) {
        if(pageNo < 0 || pageSize <= 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("teacherDegree").ascending());
        List<Teacher> teachers = teacherRepo.findAll(pageable).getContent();
        List<TeacherDto> teacherDtos = teacherMapper.teachersToTeacherDtos(teachers);
        return ResponseEntity.ok(teacherDtos);
    }

}

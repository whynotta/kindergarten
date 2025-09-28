package kg.megalab.kindergarten.services.impl;

import kg.megalab.kindergarten.exception.ConflictException;
import kg.megalab.kindergarten.exception.LogicException;
import kg.megalab.kindergarten.exception.NotFoundException;
import kg.megalab.kindergarten.mappers.TeacherMapper;
import kg.megalab.kindergarten.models.Teacher;
import kg.megalab.kindergarten.models.dto.TeacherCreateDto;
import kg.megalab.kindergarten.models.dto.TeacherDto;
import kg.megalab.kindergarten.repositories.TeacherRepo;
import kg.megalab.kindergarten.response.GlobalResponse;
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
    public ResponseEntity<GlobalResponse> createTeacher(TeacherCreateDto teacherCreateDto) {
        if (teacherRepo.existsByFirstNameAndLastNameAndPatronymicIgnoreCase(
                teacherCreateDto.getFirstName(),
                teacherCreateDto.getLastName(),
                teacherCreateDto.getPatronymic())){
            throw new ConflictException("Учитель с таким ФИО уже существует");
        }

        Teacher teacher = teacherMapper.teacherCreateDtoToTeacher(teacherCreateDto);
        Teacher savedTeacher = teacherRepo.save(teacher);
        TeacherDto teacherDto = teacherMapper.teacherToTeacherDto(savedTeacher);

        GlobalResponse response = GlobalResponse.created(teacherDto);
        return ResponseEntity.status(201).body(response);
    }
    @Override
    public ResponseEntity<GlobalResponse> updateTeacher(TeacherDto teacherDto, Long id) {
        Teacher teacher = teacherRepo.findById(id).orElseThrow(() ->
                        new NotFoundException("Учитель c id - " + id +" не найден"));
        if (teacherRepo.existsByFirstNameAndLastNameAndPatronymicIgnoreCase(
                teacherDto.getFirstName(),
                teacherDto.getLastName(),
                teacherDto.getPatronymic())){
            throw new ConflictException("Учитель с таким ФИО уже существует");
        }

        teacherMapper.updateTeacherFromDto(teacherDto, teacher);
        Teacher updatedTeacher = teacherRepo.save(teacher);
        TeacherDto updatedTeacherDto = teacherMapper.teacherToTeacherDto(updatedTeacher);

        GlobalResponse response = GlobalResponse.success(updatedTeacherDto);
        return ResponseEntity.status(200).body(response);

    }

    @Override
    public ResponseEntity<GlobalResponse> deleteTeacher(Long id) {
        Teacher teacher = teacherRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Учитель с id - "+ id + " не найден!"));
        if (!teacher.getTeacherGroups().isEmpty() || !teacher.getNannyGroups().isEmpty()){
            throw new LogicException("Нельзя удалить учителя с активными группами!");
        }
        teacherRepo.delete(teacher);

        GlobalResponse response = GlobalResponse.success("Учитель с id - "+ id + " успешно удален");
        return ResponseEntity.status(200).body(response);
    }
    @Override
    public ResponseEntity<GlobalResponse> findById(Long id) {
        Teacher teacher = teacherRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Учитель с id - "+ id + " не найден"));

        TeacherDto teacherDto = teacherMapper.teacherToTeacherDto(teacher);

        GlobalResponse response = GlobalResponse.success(teacherDto);
        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<GlobalResponse> findAllTeacher(int pageNo, int pageSize) {
        if(pageNo < 0 || pageSize <= 0){
            throw new LogicException("Ошибка параметром пагинации!");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("teacherDegree").ascending());
        List<Teacher> teachers = teacherRepo.findAll(pageable).getContent();
        List<TeacherDto> teacherDtos = teacherMapper.teachersToTeacherDtos(teachers);

        GlobalResponse response = GlobalResponse.success(teacherDtos);
        return ResponseEntity.status(200).body(response);
    }

}

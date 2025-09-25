package kg.megalab.kindergarten.mappers;

import kg.megalab.kindergarten.models.Teacher;
import kg.megalab.kindergarten.models.dto.TeacherCreateDto;
import kg.megalab.kindergarten.models.dto.TeacherDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    Teacher teacherCreateDtoToTeacher(TeacherCreateDto teacherCreateDto);

    TeacherDto teacherToTeacherDto(Teacher teacher);

    @Mapping(target = "id", ignore = true)
    void updateTeacherFromDto(TeacherDto teacherDto,@MappingTarget Teacher teacher);

    List<TeacherDto> teachersToTeacherDtos(List<Teacher> teachers);
}

package kg.megalab.kindergarten.models.dto;

import kg.megalab.kindergarten.models.enums.TeacherDegree;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {
    Long id;
    String firstName;
    String lastName;
    String patronymic;
    TeacherDegree teacherDegree;
    Boolean active;
}

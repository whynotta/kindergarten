package kg.megalab.kindergarten.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TeacherCreateDto {
    @NotBlank(message = "Имя обязательно")
    String firstName;
    @NotBlank(message = "Фамилия обязательна")
    String lastName;
    String patronymic;
    @NotBlank(message = "Роль обязательна")
    TeacherDegree teacherDegree;
    @NotNull(message = "Статус обязателен")
    Boolean active;
}

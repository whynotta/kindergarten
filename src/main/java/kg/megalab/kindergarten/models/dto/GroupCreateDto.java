package kg.megalab.kindergarten.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateDto {
    @NotBlank(message = "Имя обязательно!")
    String name;
    @NotNull(message = "Количество обязательно!")
    @Positive(message = "Количество не может быть меньше нуля!")
    int maxChildrenCount;
    @Positive(message = "Цена не может быть меньше нуля!")
    @NotNull(message = "Цена обязательна!")
    double price;
    @NotNull(message = "Няня обязательнна!")
    Long nannyId;
    @NotNull(message = "Категория групп обязательна!")
    Long groupCategoryId;
    @NotNull(message = "Учитель обязателен!")
    Long teacherId;
}

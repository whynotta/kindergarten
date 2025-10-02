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
public class GroupCategoryCreateDto {
    @NotBlank(message = "Название категории обязательно!")
    String name;
    @NotNull(message = "Статус активности обязателен!")
    boolean active;
    @NotNull(message = "Цена обязательна!")
    @Positive(message = "Цена должна быть больше нуля!")
    Integer price;

}

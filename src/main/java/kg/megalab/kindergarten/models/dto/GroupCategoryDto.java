package kg.megalab.kindergarten.models.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCategoryDto {
    @NotBlank(message = "Название категории обязательно!")
    String name;
    @NotNull(message = "Статус активности обязателен!")
    boolean active;
    @NotNull(message = "Цена обязательна!")
    @Positive(message = "Цена должна быть больше нуля!")
    double price;
}

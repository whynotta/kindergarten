package kg.megalab.kindergarten.models.dto;

import kg.megalab.kindergarten.models.GroupChildren;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentCreateDto {
    Long id;
    Integer amount;
    LocalDate paymentDate;
    Long groupChildrenId;
}

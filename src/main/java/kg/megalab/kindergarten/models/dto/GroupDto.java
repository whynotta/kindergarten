package kg.megalab.kindergarten.models.dto;

import kg.megalab.kindergarten.models.GroupCategory;
import kg.megalab.kindergarten.models.Teacher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    Long id;
    String name;
    int maxChildrenCount;
    double price;
    Teacher nanny;
    GroupCategory groupCategory;
    Teacher teacher;
}

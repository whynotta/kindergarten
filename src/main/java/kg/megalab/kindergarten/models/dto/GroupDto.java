package kg.megalab.kindergarten.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    Teacher nanny;
    @JsonIgnore
    GroupCategory groupCategory;
    @JsonIgnore
    Teacher teacher;
}

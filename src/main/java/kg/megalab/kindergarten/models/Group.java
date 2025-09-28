package kg.megalab.kindergarten.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false,name = "max_children_count")
    int maxChildrenCount;
    double price;
    @ManyToOne
    @JoinColumn(name = "nanny_id")
    Teacher nanny;
    @ManyToOne
    @JoinColumn(name = "group_category_id")
    GroupCategory groupCategory;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    List<GroupChildren> groupChildren = new ArrayList<>();

}

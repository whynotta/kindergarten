package kg.megalab.kindergarten.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kg.megalab.kindergarten.models.enums.TeacherDegree;
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
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "first_name", nullable = false)
    String firstName;
    @Column(name = "last_name",nullable = false)
    String lastName;
    String patronymic;
    @Column(nullable = false)
    boolean active;
    @Column(name = "teacher_degree", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    TeacherDegree teacherDegree;
    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    List<Group> teacherGroups = new ArrayList<>();
    @OneToMany(mappedBy = "nanny")
    @JsonIgnore
    List<Group> nannyGroups = new ArrayList<>();


}

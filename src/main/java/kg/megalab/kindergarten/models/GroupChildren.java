package kg.megalab.kindergarten.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_childrens")
public class GroupChildren {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "start_date",nullable = false)
    LocalDate startDate;
    @Column(name = "end_date")
    LocalDate endDate;
    double price;
    @ManyToOne
    @JoinColumn(name = "children_id",nullable = false)
    Child child;
    @ManyToOne
    @JoinColumn(name = "group_id",nullable = false)
    Group group;
    @OneToMany(mappedBy = "groupChildren")
    List<Payment> payments = new ArrayList<>();
}

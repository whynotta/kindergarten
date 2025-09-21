package kg.megalab.kindergarten.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    double amount;
    @Column(nullable = false,name = "payment_date")
    LocalDate paymentDate;
    @ManyToOne
    @JoinColumn(name = "group_children_id",nullable = false)
    GroupChildren groupChildren;

}

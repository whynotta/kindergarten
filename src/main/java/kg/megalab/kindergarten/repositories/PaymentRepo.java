package kg.megalab.kindergarten.repositories;

import kg.megalab.kindergarten.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "WHERE p.groupChildren.id = :groupChildrenId " +
            "AND p.paymentDate BETWEEN :startDate AND :endDate")
    Integer sumPaymentsByGroupChildrenAndPeriod(
            @Param("groupChildrenId") Long groupChildrenId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}

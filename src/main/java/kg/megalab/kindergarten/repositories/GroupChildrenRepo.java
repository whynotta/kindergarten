package kg.megalab.kindergarten.repositories;

import kg.megalab.kindergarten.models.GroupChildren;
import kg.megalab.kindergarten.models.dto.EnrollChildDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface GroupChildrenRepo extends JpaRepository<GroupChildren,Long> {
    GroupChildren findByChildIdAndEndDateIsNull(Long id);

    long countByGroupIdAndEndDateIsNull(Long id);

    @Query("SELECT gc FROM GroupChildren gc WHERE gc.child.id = :childId " +
            "AND gc.startDate <= :endDate " +
            "AND (gc.endDate IS NULL OR gc.endDate >= :startDate)")
    GroupChildren findByChildIdAndPeriodOverlap(
            @Param("childId") Long childId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}

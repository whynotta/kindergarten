package kg.megalab.kindergarten.repositories;

import kg.megalab.kindergarten.models.GroupChildren;
import kg.megalab.kindergarten.models.dto.EnrollChildDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface GroupChildrenRepo extends JpaRepository<GroupChildren,Long> {
    GroupChildren findByChildIdAndEndDateIsNull(Long id);

    long countByGroupIdAndEndDateIsNull(Long id);
}

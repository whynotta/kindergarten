package kg.megalab.kindergarten.repositories;

import jakarta.validation.constraints.NotBlank;
import kg.megalab.kindergarten.models.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    Page<Group> findAllByOrderByGroupCategoryName(Pageable pageable);
}

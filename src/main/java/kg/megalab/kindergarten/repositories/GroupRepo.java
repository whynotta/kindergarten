package kg.megalab.kindergarten.repositories;

import jakarta.validation.constraints.NotBlank;
import kg.megalab.kindergarten.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
    boolean existsByNameIgnoreCase(@NotBlank(message = "Имя обязательно!") String name);
}

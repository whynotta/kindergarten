package kg.megalab.kindergarten.repositories;

import jakarta.validation.constraints.NotBlank;
import kg.megalab.kindergarten.models.GroupCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupCategoryRepo extends JpaRepository<GroupCategory,Long> {
    boolean existsByNameIgnoreCase(@NotBlank(message = "Название категории обязательно!") String name);
}

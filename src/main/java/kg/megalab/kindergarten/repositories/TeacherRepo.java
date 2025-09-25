package kg.megalab.kindergarten.repositories;

import kg.megalab.kindergarten.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher,Long> {
    boolean existsByFirstNameIgnoreCase(String firstName);
}

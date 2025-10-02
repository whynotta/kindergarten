package kg.megalab.kindergarten.repositories;

import jakarta.validation.constraints.NotBlank;
import kg.megalab.kindergarten.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher,Long> {

    boolean existsByFirstNameAndLastNameAndPatronymicIgnoreCaseAndIdNot(String firstName, String lastName, String patronymic,Long id);

    boolean existsByFirstNameAndLastNameAndPatronymicIgnoreCase(String firstName, String lastName, String patronymic);
}

package kg.megalab.kindergarten.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.megalab.kindergarten.models.Child;
import kg.megalab.kindergarten.models.dto.EnrollChildDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ChildRepo extends JpaRepository<Child, Long> {
    Child findByFirstNameAndLastNameAndPatronymicAndBirthDate(@NotBlank(message = "Имя обязательно") String firstName, @NotBlank(message = "Фамилия обязательна") String lastName, String patronymic, @NotNull(message = "Дата рождения обязательна") LocalDate dateOfBirth);

    default Child findOrCreate(String firstName, String lastName, String patronymic, LocalDate dateOfBirth){
        Child child = findByFirstNameAndLastNameAndPatronymicAndBirthDate(firstName, lastName, patronymic, dateOfBirth);
           if (child != null){
            return child;
        }
        Child newChild = new Child();
        newChild.setFirstName(firstName);
        newChild.setLastName(lastName);
        newChild.setPatronymic(patronymic);
        newChild.setBirthDate(dateOfBirth);
        return  save(newChild);
    }
}

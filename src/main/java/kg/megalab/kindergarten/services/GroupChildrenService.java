package kg.megalab.kindergarten.services;

import kg.megalab.kindergarten.models.dto.EnrollChildDto;
import kg.megalab.kindergarten.models.dto.WithdrawChildDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.http.ResponseEntity;

public interface GroupChildrenService {
    ResponseEntity<GlobalResponse> enrollChild(EnrollChildDto enrollChildDto);

    ResponseEntity<GlobalResponse> withdrawChild(Long id, WithdrawChildDto withdrawChildDto);
}

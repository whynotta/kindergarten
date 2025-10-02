package kg.megalab.kindergarten.services;

import kg.megalab.kindergarten.models.Payment;
import kg.megalab.kindergarten.models.dto.PaymentCreateDto;
import kg.megalab.kindergarten.models.dto.PaymentDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<GlobalResponse> createPayment(PaymentCreateDto paymentCreateDto);

    ResponseEntity<GlobalResponse> getPreviousMonth(Long childId);
}

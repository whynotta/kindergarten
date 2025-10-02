package kg.megalab.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megalab.kindergarten.models.Payment;
import kg.megalab.kindergarten.models.dto.PaymentCreateDto;
import kg.megalab.kindergarten.models.dto.PaymentDto;
import kg.megalab.kindergarten.response.GlobalResponse;
import kg.megalab.kindergarten.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payments Controller", description = "Оплата за садик")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation(summary = "Создание платежа")
    public ResponseEntity<GlobalResponse> createPayment(PaymentCreateDto paymentCreateDto) {
        return paymentService.createPayment(paymentCreateDto);
    }
    @GetMapping("/previos-month/{childId}")
    @Operation(summary = "Получение задолженности за прошлый месяц")
    public ResponseEntity<GlobalResponse> getPreviousMonth (@PathVariable Long childId) {
        return paymentService.getPreviousMonth(childId);
    }

}
package kg.megalab.kindergarten.mappers;

import kg.megalab.kindergarten.models.Payment;
import kg.megalab.kindergarten.models.dto.PaymentCreateDto;
import kg.megalab.kindergarten.models.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment paymentCreateDtoToPayment(PaymentCreateDto paymentCreateDto);

    PaymentDto paymentToPaymentDto(Payment payment);
}

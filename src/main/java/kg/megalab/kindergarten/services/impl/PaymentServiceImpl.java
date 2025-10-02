package kg.megalab.kindergarten.services.impl;

import kg.megalab.kindergarten.exception.ConflictException;
import kg.megalab.kindergarten.exception.LogicException;
import kg.megalab.kindergarten.exception.NotFoundException;
import kg.megalab.kindergarten.mappers.PaymentMapper;
import kg.megalab.kindergarten.models.Child;
import kg.megalab.kindergarten.models.GroupChildren;
import kg.megalab.kindergarten.models.Payment;
import kg.megalab.kindergarten.models.dto.PaymentCreateDto;
import kg.megalab.kindergarten.models.dto.PaymentDto;
import kg.megalab.kindergarten.models.dto.PreviousMonthDebtDto;
import kg.megalab.kindergarten.repositories.ChildRepo;
import kg.megalab.kindergarten.repositories.GroupChildrenRepo;
import kg.megalab.kindergarten.repositories.PaymentRepo;
import kg.megalab.kindergarten.response.GlobalResponse;
import kg.megalab.kindergarten.services.PaymentService;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo paymentRepo;
    private final GroupChildrenRepo groupChildrenRepo;
    private final PaymentMapper paymentMapper;
    private final ChildRepo childRepo;

    public PaymentServiceImpl(PaymentRepo paymentRepo, GroupChildrenRepo groupChildrenRepo, ChildRepo childRepo) {
        this.paymentRepo = paymentRepo;
        this.groupChildrenRepo = groupChildrenRepo;
        this.childRepo = childRepo;
        this.paymentMapper = PaymentMapper.INSTANCE;
    }

    @Override
    public ResponseEntity<GlobalResponse> createPayment(PaymentCreateDto paymentCreateDto) {
        GroupChildren groupChildren = groupChildrenRepo.findById(paymentCreateDto.getGroupChildrenId()).orElseThrow(() ->
                new NotFoundException("запись о ребенке в группе не найдена"));

        if(paymentCreateDto.getPaymentDate().isAfter(LocalDate.now()))
            throw new LogicException("Время оплаты не может быть в будущем");
        if(groupChildren.getEndDate() != null)
            throw new ConflictException("Нельзя добавить платеж для отчисленного ребенка");

        Payment payment = paymentMapper.paymentCreateDtoToPayment(paymentCreateDto);
        payment.setGroupChildren(groupChildren);
        payment.setAmount(paymentCreateDto.getAmount());

        paymentRepo.save(payment);

        PaymentDto paymentDto = paymentMapper.paymentToPaymentDto(payment);
        paymentDto.setGroupChildrenId(groupChildren.getId());

        GlobalResponse response = GlobalResponse.created(paymentDto);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<GlobalResponse> getPreviousMonth (Long childId) {
        Child child = childRepo.findById(childId).orElseThrow(() ->
                new NotFoundException("Ребенок с айди -" + childId + "не найден!"));

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfPreviousMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfPreviousMonth = today.minusMonths(1).withDayOfMonth(today.
                minusMonths(1).lengthOfMonth());

        GroupChildren activeEnrollment = groupChildrenRepo.findActiveEnrollmentByChildId(childId);
         if (activeEnrollment == null)
             throw new NotFoundException("У ребенка нет активной группы");

         boolean enrollmentStartedBeforeEnd = activeEnrollment.getStartDate().
                 isBefore(lastDayOfPreviousMonth.plusDays(1));

         boolean enrollmentNotEnderBeforeStart;
         if(activeEnrollment.getEndDate() == null) {
             enrollmentNotEnderBeforeStart = true;
         }else {
             enrollmentNotEnderBeforeStart = activeEnrollment.getEndDate().
                     isAfter(firstDayOfPreviousMonth.minusDays(1));
         }
         boolean isActivePrevMonth = enrollmentStartedBeforeEnd && enrollmentNotEnderBeforeStart;
         if(!isActivePrevMonth)
             throw new NotFoundException("Ребенок не был зачислен в группу в прошлом месяце");

         Integer monthlyPrice;
         if(activeEnrollment.getPrice() != null) {
             monthlyPrice = activeEnrollment.getPrice();
         }else {
             monthlyPrice = activeEnrollment.getGroup().getPrice();
         }
         Integer totalPayment = paymentRepo.sumPaymentsByGroupChildrenAndPeriod(
                 activeEnrollment.getId(), firstDayOfPreviousMonth, lastDayOfPreviousMonth);
         if (totalPayment == null) {
             totalPayment = 0;
         }
         Integer amountDue = Math.max(0, monthlyPrice -  totalPayment);

        PreviousMonthDebtDto previousMonthDebtDto =  new PreviousMonthDebtDto();
        previousMonthDebtDto.setChildId(childId);
        previousMonthDebtDto.setAmountDue(amountDue);

        GlobalResponse response = GlobalResponse.success(previousMonthDebtDto);
        return ResponseEntity.status(200).body(response);
    }
}

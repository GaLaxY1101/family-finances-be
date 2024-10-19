package com.mindspark.family_finances.services;

import com.mindspark.family_finances.dto.CreateRegularPaymentDto;
import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.Card;
import com.mindspark.family_finances.model.Payment;
import com.mindspark.family_finances.model.PaymentDetails;
import com.mindspark.family_finances.model.PaymentHistory;
import com.mindspark.family_finances.model.User;
import com.mindspark.family_finances.repository.PaymentRepository;
import com.mindspark.family_finances.repository.UserRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentService {
    private final TaskScheduler taskScheduler;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final PaymentHistoryService paymentHistoryService;
    private final BankAccountService bankAccountService;
    private final MailSenderService mailSenderService;

    @Transactional
    public void save(Payment payment) {
        paymentRepository.save(payment);
        log.info("{} saved", payment);
    }

    @Transactional
    public Payment createPaymentToChildCard(User sender, Card receiverCard, Double amount) {

        Payment payment = new Payment();


        if (amount <= 0) {
            throw new IllegalArgumentException("Amount can't be less than 0");
        }
        payment.setAmount(amount);
        payment.setRegular(false);
        payment.setReceiverCard(receiverCard);
        payment.setSender(sender);

        save(payment);
        return payment;
    }

    @Transactional
    public PaymentHistory processPayment(Payment payment) {

        if (payment.getReceiverCard() != null) {
            return processToChildCardPayment(payment);
        } else {
            return processToBankAccountPayment(payment);
        }
    }

    private PaymentHistory processToBankAccountPayment(Payment payment) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    public PaymentHistory processToChildCardPayment(Payment payment) {
        //todo validate whether child's card connected to assigner's family bank account
        PaymentHistory paymentHistory = paymentHistoryService.createPaymentHistory(payment);

        BankAccount senderBankAccount = bankAccountService.getFamilyBankAccountByUser(payment.getSender());
        Card childCard = payment.getReceiverCard();

        Double availableBalance = senderBankAccount.getAvailableBalance();
        Double childCardBalance = childCard.getBalance();
        Double amountToSend = payment.getAmount();

        if (availableBalance < amountToSend) {
            paymentHistory.setStatus(PaymentHistory.Status.FAILED);

            String senderName = payment.getSender().getFirstName() + " " + payment.getSender().getLastName();
            String receiverName = payment.getReceiverCard().getUser().getFirstName() + " " + payment.getReceiverCard().getUser().getLastName();
            Double reward = payment.getAmount();


            String mailHeader = "Insufficient Funds for Task Reward Transfer";
            String mailMessage = String.format("""
                    Dear %s,
                    
                    We hope this message finds you well. We wanted to inform you that your child, %s, has successfully completed the task. The reward for this task is %.0f₴.
                    
                    However, we noticed that your current card balance is insufficient to transfer the reward amount.
                    
                    To ensure that your child receives their reward promptly, please consider adding funds to your card or using an alternative payment method.
                    
                    Thank you for your attention to this matter. If you have any questions or need assistance, feel free to reach out to our support team.
                    
                    Best regards,
                    Family finances,
                    MindSpark
                    
                    """, senderName, receiverName, reward );

            mailSenderService.sendMessage(
                    payment.getSender().getEmail(),
                    mailHeader,
                    mailMessage);
        } else {
            senderBankAccount.setAvailableBalance(availableBalance - amountToSend);
            childCard.setBalance(childCardBalance + amountToSend);
            paymentHistory.setStatus(PaymentHistory.Status.COMPLETED);
        }
        return paymentHistory;
    }


    public void createRegularPayment(Authentication authentication, CreateRegularPaymentDto paymentDto) {
        // todo: 1. Check if the regular payment exist, depends on the result process payment or create and after process
        User user = (User) authentication.getPrincipal();
        user = userRepository.findByEmail(user.getEmail()).get();
        PaymentDetails paymentDetails = new PaymentDetails();
//        paymentRepository.save();
//
//        taskScheduler.schedule(() -> processRegularPayment(), paymentDto.firstTimeOfPayment());
    }

    private void processRegularPayment(Long paymentId) {
        Payment payment = paymentRepository.getReferenceById(paymentId);

        taskScheduler.schedule(() -> processRegularPayment(paymentId), calculateNextExecutionDelay(payment));
    }

    private Date calculateNextExecutionDelay(Payment payment) {
        LocalDateTime nextPaymentDate = null;
        LocalDateTime lastPaymentDate = payment.getPaymentDetails().getLastPayment();

        nextPaymentDate = switch (payment.getPaymentDetails().getFrequency()) {
            case DAILY -> lastPaymentDate.plusDays(1);
            case WEEKLY -> lastPaymentDate.plusWeeks(1);
            case MONTHLY -> lastPaymentDate.plusMonths(1);
            case YEARLY -> lastPaymentDate.plusYears(1);
        };
        Instant instant = nextPaymentDate.atZone(ZoneId.systemDefault()).toInstant();
        // Convert Instant to Date
        return Date.from(instant);
    }
}

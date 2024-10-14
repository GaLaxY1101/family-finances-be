package com.mindspark.family_finances.services;

import com.mindspark.family_finances.controllers.CreateBankAccountRequest;
import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.User;
import com.mindspark.family_finances.repository.BankAccountRepository;
import com.mindspark.family_finances.repository.UserRepository;
import com.mindspark.family_finances.services.mailsender.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    public BankAccount createBankAccount(CreateBankAccountRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAvailableBalance(request.getAvailableBalance());
        bankAccount.setTotalBalance(request.getTotalBalance());
        bankAccount.setBlocked(request.isBlocked());
        bankAccount.setCreatedAt(LocalDate.now());

        user.addBankAccount(bankAccount);
        return bankAccountRepository.save(bankAccount);
    }
    public boolean sendJoinRequest(String requesterEmail, String inviterEmail) {
        System.out.println("Requesting to join: " + requesterEmail + " to " + inviterEmail);

        User inviter = userRepository.findByEmail(inviterEmail)
                .orElseThrow(() -> new RuntimeException("Inviter not found"));


        System.out.println("Inviter found: " + inviter.getEmail() + " with bank accounts count: " + inviter.getBankAccounts().size());

        if (inviter.getBankAccounts().isEmpty()) {
            System.out.println("Inviter has no bank accounts.");
            return false;
        }

        StringBuilder accountInfo = new StringBuilder();
        accountInfo.append("Користувач ").append(requesterEmail)
                .append(" хоче долучитися до вашого банківського акаунта.\n")
                .append("Ось інформація про ваші банківські акаунти:\n");

        for (BankAccount account : inviter.getBankAccounts()) {
            accountInfo.append("- ID банківського акаунта: ").append(account.getId())
                    .append(", Доступний баланс: ").append(account.getAvailableBalance())
                    .append(", Загальний баланс: ").append(account.getTotalBalance()).append("\n");
        }

        mailSenderService.requestToJoin(inviterEmail, requesterEmail, accountInfo.toString());
        return true;
    }


    public boolean confirmJoinRequest(Long bankAccountId, String requestEmail) {
        return false; //todo підтвердження додавання користувача
    }


}

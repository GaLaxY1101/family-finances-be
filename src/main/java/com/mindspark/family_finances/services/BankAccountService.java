package com.mindspark.family_finances.services;

import com.mindspark.family_finances.dto.*;
import com.mindspark.family_finances.mapper.BankAccountMapper;
import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.Card;
import com.mindspark.family_finances.model.User;
import com.mindspark.family_finances.repository.BankAccountRepository;
import com.mindspark.family_finances.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    @Value( "${domain}")
    private String domain;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final CardService cardService;
    private final BankAccountMapper bankAccountMapper;
    private final AuthenticationService authenticationService;

    @Transactional
    public CreateBankAccountResponseDto createBankAccount(CreateBankAccountRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAvailableBalance(request.getAvailableBalance());
        bankAccount.setTotalBalance(request.getTotalBalance());
        bankAccount.setBlocked(request.isBlocked());
        bankAccount.setCreatedAt(LocalDate.now());
        bankAccount.setType(BankAccount.Type.FAMILY);

        user.addBankAccount(bankAccount);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        cardService.createDefaultCard(bankAccount);
        return bankAccountMapper.toDto(savedBankAccount);
    }

    @Transactional
    public void sendJoinRequest(Authentication authentication, JoinToBankAccountRequestDto requestDto) {
        String email = requestDto.email();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        User userSender = (User) authentication.getPrincipal();
        if (user.getBankAccounts().isEmpty()) {
            throw new RuntimeException("User with such email don't have bank account");
        }

        StringBuilder mailText = new StringBuilder()
                .append("User with email: ").append(userSender.getEmail())
                .append(" send request to join in your bank account.\n")
                .append("To accept request go to: ")
                .append(domain).append("/bank-account/accept-member/").append(userSender.getId());

        mailSenderService.sendMessage(email, "Request to join bank account", String.valueOf(mailText));
    }

    @Transactional
    public void acceptUser(Authentication authentication, Long userId) {
        User user = (User) authentication.getPrincipal();
        user = userRepository.findByEmail(user.getEmail()).get();
        User requestor = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if (user.getBankAccounts().isEmpty()) {
            throw new RuntimeException("You don't have bank account");
        }
        BankAccount bankAccount = getFamilyBankAccountByUser(user);
        bankAccount.addUser(requestor);
        bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public AddChildResponseDto addChild(Authentication authentication, AddChildRequestDto request) {
        User child = authenticationService.registerChild(request);
        User parent = (User) authentication.getPrincipal();
        parent = userRepository.findByEmail(parent.getEmail()).get();
        BankAccount bankAccount = getFamilyBankAccountByUser(parent);
        bankAccount.addUser(child);
        bankAccountRepository.save(bankAccount);
        Card card = cardService.createChildCard(child, bankAccount);
        return new AddChildResponseDto(card.getId(), child.getId(), request.email(),
                request.firstName(), request.lastName());
    }

    public BankAccount getFamilyBankAccountByUser(User user) {
        return user.getBankAccounts().stream()
                .filter(b -> b.getType() == BankAccount.Type.FAMILY)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("You don't have family bank account"));
    }

}

package com.mindspark.family_finances.controllers;

import com.mindspark.family_finances.dto.*;
import com.mindspark.family_finances.dto.bankaccount.CreateBankAccountRequest;
import com.mindspark.family_finances.dto.bankaccount.CreateBankAccountResponseDto;
import com.mindspark.family_finances.dto.bankaccount.JoinToBankAccountRequestDto;
import com.mindspark.family_finances.model.Goal;
import com.mindspark.family_finances.services.BankAccountService;
import com.mindspark.family_finances.services.GoalService;
import com.mindspark.family_finances.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank-account")
@RequiredArgsConstructor
@Tag(name = "Bank account controller",
        description = "Here we have endpoints for bank account management")
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final PaymentService paymentService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('PARENT')")
    @Operation(summary = "Create bank account",
            description = "Only authenticated used could create bank account")
    public ResponseEntity<CreateBankAccountResponseDto> createBankAccount(
            @RequestBody CreateBankAccountRequest request,
            @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        CreateBankAccountResponseDto createdAccount = bankAccountService.createBankAccount(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PostMapping("/join")
    @PreAuthorize("hasAuthority('PARENT')")
    @Operation(summary = "Join to bank account",
            description = "Join to existing bank account via owner`s e-mail")
    public ResponseEntity<String> joinToBankAccount(Authentication authentication,
                                                    @RequestBody JoinToBankAccountRequestDto requestDto) {
        bankAccountService.sendJoinRequest(authentication, requestDto);
        return ResponseEntity.status(200).body("Success");
    }

    @GetMapping("/accept-member/{userId}")
    @PreAuthorize("hasAuthority('PARENT')")
    @Operation(summary = "Accept bank account`s member",
            description = "Endpoint for accepting request to join the bank account")
    public ResponseEntity<String> acceptMember(Authentication authentication, @PathVariable Long userId) {
        bankAccountService.acceptUser(authentication, userId);
        return ResponseEntity.status(200).body("User successfully added to your bank account");
    }

    @PostMapping("/add-child")
    @PreAuthorize("hasAuthority('PARENT')")
    @Operation(summary = "Add child",
            description = "Register child and add to bank account")
    public AddChildResponseDto addChild(Authentication authentication,
                                        @RequestBody AddChildRequestDto request) {
        return bankAccountService.addChild(authentication, request);
    }

    @PostMapping("/create-regular-payment")
    @PreAuthorize("hasAuthority('PARENT')")
    @Operation(summary = "Create regular payment",
            description = "Create regular payment with providing first time of payment and frequency")
    public void createRegularPayment(Authentication authentication,
            @RequestBody CreateRegularPaymentDto paymentDto) {
        paymentService.createRegularPayment(authentication, paymentDto);
    }
}

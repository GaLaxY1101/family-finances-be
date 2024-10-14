package com.mindspark.family_finances.controllers;

import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.User;
import com.mindspark.family_finances.repository.UserRepository;
import com.mindspark.family_finances.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank-account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<BankAccount> createBankAccount(
            @RequestBody CreateBankAccountRequest request,
            @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        BankAccount createdAccount = bankAccountService.createBankAccount(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PostMapping("/join-request")
    public ResponseEntity<String> requestToJoinBankAccount(
            @RequestBody JoinRequest joinRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        String email = userDetails.getUsername();
        boolean requestSent = bankAccountService.sendJoinRequest(
                email, joinRequest.getInviterEmail());
        if (requestSent){
            return ResponseEntity.ok("Join request sent to the account owner.");
        }else {
            return ResponseEntity.badRequest().body("An error occurred or the inviter does not have a bank account.");
        }
    }

    @GetMapping("/confirm-join")
    public ResponseEntity<String> confirmJoinRequest(
            @RequestBody Long bankAccountId,
            @RequestBody String requestEmail){
        boolean success = bankAccountService.confirmJoinRequest(bankAccountId, requestEmail);
        if(success){
            return ResponseEntity.ok("user added to bank account.");
        }else{
            return ResponseEntity.badRequest().body("Failed to add user to the bank account.");
        }
    }
}

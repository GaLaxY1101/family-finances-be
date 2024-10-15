package com.mindspark.family_finances.controllers;

import com.mindspark.family_finances.dto.CreateBankAccountRequest;
import com.mindspark.family_finances.dto.CreateBankAccountResponseDto;
import com.mindspark.family_finances.dto.GoalRequest;
import com.mindspark.family_finances.dto.JoinToBankAccountRequestDto;
import com.mindspark.family_finances.model.Goal;
import com.mindspark.family_finances.services.BankAccountService;
import com.mindspark.family_finances.services.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank-account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final GoalService goalService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('PARENT')")
    public ResponseEntity<CreateBankAccountResponseDto> createBankAccount(
            @RequestBody CreateBankAccountRequest request,
            @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        CreateBankAccountResponseDto createdAccount = bankAccountService.createBankAccount(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PostMapping("/join")
    @PreAuthorize("hasAuthority('PARENT')")
    public ResponseEntity<String> joinToBankAccount(Authentication authentication,
                                                    @RequestBody JoinToBankAccountRequestDto requestDto) {
        bankAccountService.sendJoinRequest(authentication, requestDto);
        return ResponseEntity.status(200).body("Success");
    }

    @GetMapping("/accept-member/{userId}")
    @PreAuthorize("hasAuthority('PARENT')")
    public ResponseEntity<String> acceptMember(Authentication authentication, @PathVariable Long userId) {
        bankAccountService.acceptUser(authentication, userId);
        return ResponseEntity.status(200).body("User successfully added to your bank account");
    }

    @PostMapping("/сreate-goal")
    @PreAuthorize("hasAuthority('PARENT')")
    public ResponseEntity<Goal> createGoal(@RequestBody GoalRequest goal){
        Goal savedGoal = goalService.saveGoal(goal);
        return new ResponseEntity<>(savedGoal, HttpStatus.CREATED);
    }
}

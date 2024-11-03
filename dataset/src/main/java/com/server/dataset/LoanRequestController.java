package com.server.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class LoanRequestController {
    private final Random random = new Random();

    @GetMapping("/loan_requests")
    public List<LoanRequest> generateLoanRequests(@RequestParam(defaultValue = "1") int count) {
        List<LoanRequest> loanRequests = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            LoanRequest loanRequest = new LoanRequest();
            loanRequest.setName("Client " + random.nextInt(1000));
            loanRequest.setAmount(1000 + random.nextInt(49000)); // montant entre 1000 et 50000
            loanRequest.setTerm(12 + random.nextInt(49)); // terme entre 12 et 60 mois
            loanRequest.setIncome(30000 + random.nextInt(90001)); // revenu entre 30000 et 120000
            loanRequest.setCreditScore(300 + random.nextInt(551)); // score de crédit entre 300 et 850
            loanRequests.add(loanRequest);
        }
        
        return loanRequests;
    }
}

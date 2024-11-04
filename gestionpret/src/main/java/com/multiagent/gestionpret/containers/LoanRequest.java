package com.multiagent.gestionpret.containers;

import lombok.Data;

@Data
public class LoanRequest {
    private String name;
    private double Amount;
    private int term;
    private double income;
    private int creditScore;
}

package com.investment.neosurge.FinSightAI.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.investment.neosurge.FinSightAI.utils.Constants.*;

import java.util.List;

/**
 * Entitity defined for MongoDB collection
 * to manage Financial Preferences for a User
 * */

@Document
@Getter
@Setter
public class UserFinancialProfile {

    @Id
    private String id;

    private String userId;  //Reference from the Users

    private RiskTolerance riskTolerance;   // "LOW", "MEDIUM", "HIGH"

    private InvestmentHorizon investmentHorizon;  // "shortTerm", "longTerm"

    private InvestmentManagementPreference investmentManagementPreference; // "ACTIVE", "PASSIVE"

    private List<String> sourcesOfIncome; // e.g: "Salary", "Rent", "Investments", etc.

    private List<String> financialGoals; // e.g:  "Retirement", "Education Fund", "Starting a Business"

    private List<String> investmentCategoryPreference; // e.g: "Tax-saving" , "National Benefit schemes", "Sustainabl

    public UserFinancialProfile(String userId, RiskTolerance riskTolerance, InvestmentHorizon investmentHorizon, InvestmentManagementPreference investmentManagementPreference, List<String> sourcesOfIncome, List<String> financialGoals, List<String> investmentCategoryPreference) {
        this.userId = userId;
        this.riskTolerance = riskTolerance;
        this.investmentHorizon = investmentHorizon;
        this.investmentManagementPreference = investmentManagementPreference;
        this.sourcesOfIncome = sourcesOfIncome;
        this.financialGoals = financialGoals;
        this.investmentCategoryPreference = investmentCategoryPreference;
    }
}


package com.investment.neosurge.FinSightAI.utils;

public class Constants {

    public static final String SECRET_KEY = "FinSightAI-Cybernetech";

    // creating enums for financial preference fields
    public enum InvestmentHorizon {
        shortTerm,
        longTerm
    }

    public enum InvestmentManagementPreference {
        ACTIVE,
        PASSIVE
    }

    public enum RiskTolerance {
        LOW,
        MEDIUM,
        HIGH
    }
}

package com.investment.neosurge.FinSightLLMService.service;

import com.investment.neosurge.FinSightAI.model.UserFinancialProfile;
import org.springframework.stereotype.Service;

/**
 * Generating prompt for the given user inputs
 * */
@Service
public class LLMPromptGeneratorService {

    public static String getPromptForUserFinancialPreferences(UserFinancialProfile userFinancialProfile) {
        return String.format(
                "Analyze given investment preferences of user and provide tailored investment insights:\n" +
                        "Risk Tolerance:%s, " +
                        "Investment Horizon:%s, " +
                        "Investment Management Preference:%s," +
                        "Sources of Income:%s," +
                        "Financial Goals:%s," +
                        "Investment Category Preference:%s. " +
                        "Factor in current Indian Capital Market trends and offer a range of suitable investment options and insights, considering the investor's unique preferences.",
                userFinancialProfile.getRiskTolerance(),
                userFinancialProfile.getInvestmentHorizon(),
                userFinancialProfile.getInvestmentManagementPreference(),
                userFinancialProfile.getSourcesOfIncome(),
                userFinancialProfile.getFinancialGoals(),
                userFinancialProfile.getInvestmentCategoryPreference()
        );
    }
}

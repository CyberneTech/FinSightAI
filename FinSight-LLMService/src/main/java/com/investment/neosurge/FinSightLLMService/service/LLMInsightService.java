package com.investment.neosurge.FinSightLLMService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.neosurge.FinSightLLMService.model.Message;
import com.investment.neosurge.FinSightLLMService.model.OpenAIRequest;
import com.investment.neosurge.FinSightAI.model.UserFinancialProfile;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class LLMInsightService {

    private static String OPENAI_URL;
    private static String API_KEY;
    private static String LLM_MODEL;

    // injecting dependencies from properties file for static fields
    @Value("${api.openai.url}")
    public void setOpenaiUrl(String openaiUrl){
        LLMInsightService.OPENAI_URL = openaiUrl;
    }

    @Value("${api.gpt.3.5.token}")
    public void setApiKey(String apiKey){
        LLMInsightService.API_KEY = apiKey;
    }

    @Value("${api.llm.model}")
    public void setLlmModel(String llmModel){
        LLMInsightService.LLM_MODEL = llmModel;
    }

    /**
     * params: @UserFinancial profile
     * returns: @HTTPResponse.body,
     * accepts the financial preference for a user, creates optimal prompt for it and calls GPT-3.5-Turbo api
     * returns the response containing investment insights content generated by LLM
     * */
    public String llmGenerateInsight(UserFinancialProfile userFinancialProfile) throws IOException {
        // convert the preferences to prompt string
        String prompt = LLMPromptGeneratorService.getPromptForUserFinancialPreferences(userFinancialProfile);
        try{
            // create a new request for OpenAI api call to get the insights
            List<Message> messages = new ArrayList<>();
            messages.add(new Message("system","You are an expert financial advisor"));
            messages.add(new Message("user",prompt));

            OpenAIRequest openAIRequestBody = new OpenAIRequest(LLM_MODEL,messages,600);
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(openAIRequestBody);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            // receiving response from the api
            HttpResponse<String> response = null;
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseJson = new JSONObject(response.body());
            return responseJson.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

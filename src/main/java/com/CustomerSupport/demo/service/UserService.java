package com.CustomerSupport.demo.service;

import com.CustomerSupport.demo.RestClientConfig;
import com.CustomerSupport.demo.entity.User;
import com.CustomerSupport.demo.repository.UserRepo;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.springframework.mail.SimpleMailMessage;

import java.util.List;
import java.util.Map;


@Service
public class UserService {
    UserRepo repo;
    OpenAiChatModel model;

  private final RestClientConfig client;
    @Value("${brevo_api_key}")
    String brevo_api_key;



    UserService(UserRepo repo,OpenAiChatModel model,RestClientConfig client){
        this.repo=repo;
        this.model=model;
        this.client=client;
    }

    public void  adduser(@RequestBody User user) {
    String email=user.getEmail();
    String subject="Customer Support Agent";
    String body=model.call(
  "Take the information provided below and transform it into a clear, professional, customer-ready response. Do not simply rewrite or summarize the information. First understand the actual purpose and identify the most important information, remove unnecessary repetition, remove irrelevant or low-value details, correct unclear or misleading statements when necessary, organize related information logically, prioritize what the customer actually needs to know, and turn the content into a smooth and easy-to-read response. The final response should feel like it was written by an experienced professional who understands the subject and respects the customer's time. Use a clear structure with a short introduction, the most important points first, logical sections only where they improve readability, practical recommendations, and a concise conclusion or next step. Do not overload the customer with excessive technologies, buzzwords, tools, statistics, or unnecessary explanations. Do not include information merely because it sounds impressive. Every section must have a clear purpose. If there are too many options, prioritize the best and most relevant ones instead of listing everything. Clearly distinguish essential requirements from optional or advanced topics. If the original information contains questionable, outdated, exaggerated, or unsupported claims, do not repeat them as facts; correct them or clearly qualify them. If the customer needs to make a decision, give a direct recommendation and explain the reasoning briefly. If the customer needs to take action, provide a practical sequence of next steps. Keep the language natural, professional, confident, and easy to understand. Avoid unnecessary tables, giant lists, excessive headings, repetitive explanations, and complicated terminology. Do not make the response unnecessarily long just because the source material is long. Preserve important technical accuracy while making the content accessible to the intended customer. The final answer should be polished enough to send directly to a customer without additional editing. Before producing the final response, silently ask yourself: What does the customer actually need? What information is essential? What can be removed? What should come first? What action should the customer take after reading this? Then produce only the refined customer-ready response."
            +user.getIssue() );
    Map<String ,Object> payload= Map.of(
            "sender" , Map.of(
                    "name", "Tharun",
                    "email","tharunprakash999@gmail.com"),
            "to",List.of(Map.of("email",email)),
            "subject",subject,
            "htmlContent",body
    );

    client.restClient().post().uri("https://api.brevo.com/v3/smtp/email")
            .header("api-key",brevo_api_key)
            .header("Content-Type" ,"application/json")
            .body(payload)
            .retrieve().toBodilessEntity();
user.setResponse(body);
repo.save(user);
    }

   

}

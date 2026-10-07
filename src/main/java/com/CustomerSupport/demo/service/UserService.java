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
    String body=model.call(user.getIssue()+"  " +
            " Analyze the customer’s submitted issue," +
            "Identify the problem and required action, " +
            "Generate a professional, " +
            "concise response,Maintain a polite," +
            " helpful, and confident tone, " +
            "Explain the solution or next steps clearly" +
            " Add relevant links when they help the customer resolve the issue Use descriptive link text instead of displaying raw URLs" +
            " Ensure all links are directly relevant " +
            "and functional End with a clear next action or resolution and short ,powerful ");
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

package com.CustomerSupport.demo.service;

import com.CustomerSupport.demo.entity.User;
import com.CustomerSupport.demo.repository.UserRepo;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
@Service
public class UserService {
    UserRepo repo;
    OpenAiChatModel model;
    JavaMailSender mailsender;
    UserService(UserRepo repo,OpenAiChatModel model,JavaMailSender mailsender){
        this.repo=repo;
        this.model=model;
        this.mailsender=mailsender;
    }

    public void  adduser(@RequestBody User user) {
    User savedUser= repo.save(user);
    String email=savedUser.getEmail();
    
    String subject="Customer Support Agent";
    String body=model.call(savedUser.getIssue());
    SimpleMailMessage message=new SimpleMailMessage();
   // message.setFrom("CustomerSupportAgent");
    message.setTo(email);
    message.setSubject(subject);
    message.setText(body);
    mailsender.send(message);
    }
    
    
   

}

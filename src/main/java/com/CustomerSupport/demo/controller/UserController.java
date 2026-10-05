package com.CustomerSupport.demo.controller;
import com.CustomerSupport.demo.entity.User;
import com.CustomerSupport.demo.service.UserService;
import com.openai.client.OpenAIClient;

import java.net.http.HttpResponse;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController 
@CrossOrigin (origins = "http://localhost:5173/")
@ControllerAdvice 
public class UserController {


    UserService service;
    OpenAiChatModel model;
    UserController(UserService service, OpenAiChatModel model){
        this.service=service;
        this.model=model;
    }


    @GetMapping ("/greet")
    public ResponseEntity<String> message(){

        return ResponseEntity.ok("  project is working ");
    }
    @PostMapping ("/user")
    public ResponseEntity<Void> adduser(@RequestBody User user ){
        service.adduser(user);
        return new  ResponseEntity(HttpStatus.CREATED);
    }
    // @GetMapping ("/{text}")
    // public String getResponse( @PathVariable String text){
    //     return model.call(text);
    // }
   

}

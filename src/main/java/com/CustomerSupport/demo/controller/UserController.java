package com.CustomerSupport.demo.controller;
import com.CustomerSupport.demo.entity.User;
import com.CustomerSupport.demo.service.UserService;
import com.openai.client.OpenAIClient;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@CrossOrigin (origins = "http://localhost:5173/")
public class UserController {
    
    UserService service;
    OpenAiChatModel model;
    UserController(UserService service, OpenAiChatModel model){
        this.service=service;
        this.model=model;
    }
    @GetMapping ("/greet")
    public String message(){
        return "hello world";
    }
    @PostMapping ("/user")
    public void adduser(@RequestBody User user ){
        service.adduser(user);
    }
    @GetMapping ("/{text}")
    public String getResponse( @PathVariable String text){
        return model.call(text);
    }

}

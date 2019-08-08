package com.michaelyu.playlisttracker;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping("/api")
    public String indexBack(){
        return "Welcome to the api page";
    }
}

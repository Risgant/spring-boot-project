package com.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/version")
public class VersionController {
    @Autowired
    private Environment environment;

    @GetMapping
    public String version(){
        return environment.getProperty("app.version");
    }

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleAllExceptions(Exception ex){
//        log.error("Exception in VersionController Class");
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject("message", ex.getMessage());
//        return modelAndView;
//    }
}

package com.example.controller;

import com.example.dto.UserDto;
import com.example.exception.NoSuchEntityException;
import com.example.serviсe.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public List<UserDto> read(){
        return userService.findAll().stream()
                .map(u->modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto read(@PathVariable(value="id") int id){
        return modelMapper.map(userService.findById(id), UserDto.class);
    }

//    @ExceptionHandler(NoSuchEntityException.class)
//    public ModelAndView handleNoSuchEntityException(RuntimeException ex){
//        log.error("Exception during fetching user");
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject("message", "Exception during fetching user");
//        return modelAndView;
//    }

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleAllExceptions(Exception ex){
//        log.error("Exception in UserController Class");
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject("message", ex.getMessage());
//        return modelAndView;
//    }
}

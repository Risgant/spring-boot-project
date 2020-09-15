package com.example.controller;

import com.example.dto.ProductDto;
import com.example.exception.NoSuchEntityException;
import com.example.serviсe.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ProductDto> read(){
        return productService.findAll().stream()
                .map(p->modelMapper.map(p, ProductDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto read(@PathVariable("id") Integer id){
        return modelMapper.map(productService.findById(id), ProductDto.class);
    }

//    @ExceptionHandler(NoSuchEntityException.class)
//    public ModelAndView handleNoSuchEntityException(RuntimeException ex){
//        log.error("Exception during fetching product");
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject("message", "Exception during fetching product");
//        return modelAndView;
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleAllExceptions(Exception ex){
//        log.error("Exception in {} Class", ProductController.class.getSimpleName());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject("message", ex.getMessage());
//        return modelAndView;
//    }
}

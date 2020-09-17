package com.example.controller;

import com.example.dto.ProductDto;
import com.example.exception.NoSuchObjectException;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Seller;
import com.example.repository.ProductRepository;
import com.example.repository.SellerRepository;
import com.example.serviсe.OrderService;
import com.example.serviсe.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/get_all")
    public List<ProductDto> read(){
        return productService.findAll().stream()
                .map(p->modelMapper.map(p, ProductDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/get_all_sorted")
    public List<ProductDto> readSorted(){
        List<ProductDto> list = read();
        list.sort((a, b)-> a.getTitle().compareTo(b.getTitle()));
        return list;
    }

    @PostMapping("/create")
    public void create(@RequestBody ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        Seller seller = sellerRepository.findById(productDto.getSellerId()).orElseThrow(NoSuchObjectException::new);
        seller.getProducts().add(product);
        product.setSeller(seller);
        List<Order> orders = orderService.findAll();
        orders.stream().forEach(o->o.getProducts().add(product));
        product.setOrder(orders);
        productService.create(product);
    }

    @PutMapping("/update")
    public void update(@RequestBody ProductDto productDto){
        productService.update(modelMapper.map(productDto, Product.class));
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody ProductDto productDto){
        productService.delete(modelMapper.map(productDto, Product.class));
    }


//    @GetMapping("/{id}")
//    public ProductDto read(@PathVariable("id") Integer id){
//        return modelMapper.map(productService.findById(id), ProductDto.class);
//    }

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

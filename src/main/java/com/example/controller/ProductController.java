package com.example.controller;

import com.example.dto.OrderDto;
import com.example.dto.ProductDto;
import com.example.exception.NoSuchObjectException;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Seller;
import com.example.repository.ProductRepository;
import com.example.serviсe.OrderService;
import com.example.serviсe.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/get_page/{page}")
    public List<ProductDto> read(@PathVariable int page){
        return  productService.findPage(page).stream().
                map(p->modelMapper.map(p, ProductDto.class)).
                collect(Collectors.toList());
//        Stream<Pair<Product, ProductDto>> stream = productService.findAll().stream()
//                .map(p-> Pair.of(p, modelMapper.map(p, ProductDto.class)));
//        stream.forEach(p->{
//            p.getSecond().setOrderId(p.getFirst().getOrder().getId());
//            p.getSecond().setSellerId(p.getFirst().getSeller().getId());
//        });
//        return stream.map(Pair::getSecond).collect(Collectors.toList());
    }

    @GetMapping("/get_page_sorted/{page}")
    public List<ProductDto> readSorted(int page){
        List<ProductDto> list = read(page);
        list.sort(Comparator.comparing(ProductDto::getTitle));
        return list;
    }

    @PostMapping("/create")
    public void create(@RequestBody ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
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
}

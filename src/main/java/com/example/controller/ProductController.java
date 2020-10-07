package com.example.controller;

import com.example.dto.ProductDto;
import com.example.model.Product;
import com.example.serviсe.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    }

    @GetMapping("/get_page_sorted/{page}")
    public List<ProductDto> readSorted(@PathVariable int page){
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

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        productService.delete(id);
    }
}

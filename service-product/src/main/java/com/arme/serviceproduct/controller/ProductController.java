package com.arme.serviceproduct.controller;

import com.arme.serviceproduct.entity.Category;
import com.arme.serviceproduct.entity.ProductEntity;
import com.arme.serviceproduct.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> listProduncts(@RequestParam(name = "categoryId", required = false)
                                                                         Long categoryId){
        List<ProductEntity> products = new ArrayList<>();
        if(null == categoryId){
            products = productService.listAllProduct();
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable(name = "id") Long id){
        ProductEntity product = productService.getProduct(id);

        if(null == product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@Valid @RequestBody ProductEntity product, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        ProductEntity productCreated = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping(value = "/{id}")
    public  ResponseEntity<ProductEntity> updateProduct(@PathVariable("id") Long id, @RequestBody ProductEntity product){

        ProductEntity productDB = productService.getProduct(id);
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setPrice(product.getPrice());
        productDB.setCategory(product.getCategory());

        ProductEntity productUpdate = productService.updateProduct(productDB);

        if(productDB == null){
            return ResponseEntity.notFound().build();
        }
        System.out.println(productDB.toString());
        System.out.println(productUpdate.toString());
        return  ResponseEntity.ok(productUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductEntity> deleteProduct(@PathVariable Long id){
        ProductEntity productDeleted = productService.deleteProduct(id);
        if (productDeleted == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDeleted);
    }

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<ProductEntity> updateStockProduct(@PathVariable Long id,@RequestParam(name = "quantity", required = true) Double quality){
        ProductEntity product = productService.updateStock(id, quality);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors= result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
        }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .cod("01")
                .messages(errors).build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}

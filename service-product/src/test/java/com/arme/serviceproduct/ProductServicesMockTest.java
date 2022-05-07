package com.arme.serviceproduct;

import com.arme.serviceproduct.entity.Category;
import com.arme.serviceproduct.entity.ProductEntity;
import com.arme.serviceproduct.repository.ProductRepository;
import com.arme.serviceproduct.service.ProductService;
import com.arme.serviceproduct.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServicesMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
        ProductEntity computer = ProductEntity.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer))
                .thenReturn(computer);
    }

    @Test
    public void whenValidGetId_thenReturnProduct(){
        ProductEntity found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock(){
        ProductEntity newStock = productService.updateStock(1L,Double.parseDouble("8"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);
    }
}

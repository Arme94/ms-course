package com.arme.serviceproduct.service;

import com.arme.serviceproduct.entity.Category;
import com.arme.serviceproduct.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    public List<ProductEntity> listAllProduct();
    public ProductEntity getProduct(Long id);

    public ProductEntity createProduct(ProductEntity product);
    public ProductEntity updateProduct(ProductEntity product);
    public  ProductEntity deleteProduct(Long id);
    public List<ProductEntity> findByCategory(Category category);
    public ProductEntity updateStock(Long id, Double quantity);

}

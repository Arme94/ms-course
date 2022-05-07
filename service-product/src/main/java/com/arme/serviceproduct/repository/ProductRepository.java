package com.arme.serviceproduct.repository;

import com.arme.serviceproduct.entity.Category;
import com.arme.serviceproduct.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    public List<ProductEntity> findByCategory(Category category);

}

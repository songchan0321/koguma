package com.fiveguys.koguma.repository.product;

import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.ProductStateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

    Page<Product> findByTradeStatusContaining(Pageable pageable, ProductStateType state);

    @Query("SELECT p FROM Product p JOIN LikeFilterAssociation l ON p.id = l.product.id")
    List<Product> findProductsInAssociation();
}

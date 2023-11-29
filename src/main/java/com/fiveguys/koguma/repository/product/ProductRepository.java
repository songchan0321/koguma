package com.fiveguys.koguma.repository.product;

import com.fiveguys.koguma.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

}

package com.fiveguys.koguma.repository.product;

import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.ProductStateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

    Page<Product> findByBuyerIdAndTradeStatusContaining(Long memberId, Pageable pageable, ProductStateType state);


    Page<Product> findBySellerIdAndTradeStatusContaining(Long memberId, Pageable pageable, ProductStateType state);
    @Query("SELECT p FROM Product p JOIN LikeFilterAssociation l ON p.id = l.product.id")
    List<Product> findProductsInAssociation();

    @Query("select p from Product p left join fetch p.image where p.id = :productId")
    Product findByProductIdWithImages(@Param("productId") Long productId);

}

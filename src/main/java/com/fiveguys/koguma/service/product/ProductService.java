package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.ProductStateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    void addProduct(ProductDTO productDTO);
    Page<Product> listProduct(Long memberId,int page, int size);
    ProductDTO getProduct(Long productId);
    ProductDTO updateProduct(ProductDTO productDTO);
    void deleteProduct(Long productId);

    void updateState(ProductDTO productDTO, ProductStateType state);
    void updateView(Long productId);

    Page<Product> listStateProduct(Long memberId, Pageable pageable, ProductStateType state) throws Exception;

    Page<Product> listBuyProduct(Long memberId, Pageable pageable) throws Exception;
    void newProductAlert();

    void raiseProduct(Long productId) throws Exception;

    void isOwnProduct(Long memberId, Long sellerId) throws Exception;


}

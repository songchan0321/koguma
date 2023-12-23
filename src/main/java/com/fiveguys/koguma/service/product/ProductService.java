package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.ProductStateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO);
    Page<Product> listProduct(Long memberId,int page, int size);
    List<ProductDTO> listProductByLocation(LocationDTO locationDTO, String keyword,Long categoryId) throws Exception;
    ProductDTO getProduct(Long productId);
    ProductDTO updateProduct(ProductDTO productDTO);
    void deleteProduct(Long productId);

    void updateState(ProductDTO productDTO, ProductStateType state);
    void updateActiveFlag(Long productId);
    void updateView(Long productId);

    List<ProductDTO> listStateProduct(Long memberId, ProductStateType state) throws Exception;

    List<ProductDTO> listBuyProduct(Long memberId) throws Exception;
    void newProductAlert();

    void raiseProduct(Long productId) throws Exception;

    void isOwnProduct(Long memberId, Long sellerId) throws Exception;

}

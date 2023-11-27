package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface ProductService {
    void addProduct(ProductDTO productDTO);
    Page<ProductDTO> listProduct(Long id);
    ProductDTO getProduct(Long id);
    void updateProduct(Long id);

    void updateState(Long id);

    Page<ProductDTO> listSaleProduct();
    Page<ProductDTO> listSaledProduct();
    Page<ProductDTO> listHideProduct();
    Page<ProductDTO> listBuyProduct();
    void newProductAlert();

}

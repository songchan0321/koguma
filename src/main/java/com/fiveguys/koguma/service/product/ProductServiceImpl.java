package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;


    @Override
    public void addProduct(ProductDTO productDTO) {

    }

    @Override
    public Page<ProductDTO> listProduct(Long id) {
        return null;
    }

    @Override
    public ProductDTO getProduct(Long id) {
        return null;
    }

    @Override
    public void updateProduct(Long id) {

    }

    @Override
    public void updateState(Long id) {

    }

    @Override
    public Page<ProductDTO> listSaleProduct() {
        return null;
    }

    @Override
    public Page<ProductDTO> listSaledProduct() {
        return null;
    }

    @Override
    public Page<ProductDTO> listHideProduct() {
        return null;
    }

    @Override
    public Page<ProductDTO> listBuyProduct() {
        return null;
    }

    @Override
    public void newProductAlert() {

    }
}

package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.ProductStateType;
import com.fiveguys.koguma.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;


    public void addProduct(ProductDTO productDTO) {
        productRepository.save(productDTO.toEntity());
    }

    public Page<ProductDTO> listProduct(Long memberId) {
        return null;
    }

    public ProductDTO getProduct(Long productId) {              //시큐리티 본인 확인 대상
        Product product = productRepository.findById(productId).orElseThrow(()->new NoResultException("해당 상품의 정보가 존재하지 않습니다."));
        return ProductDTO.fromEntity(product);
    }

    public void updateProduct(ProductDTO productDTO) {
//        if (productDTO.getSellerDTO().getId().equals(memberId))   //시큐리티 본인 확인 대상
        productRepository.save(productDTO.toEntity());

    }
    public void updateState(ProductDTO productDTO, ProductStateType state) {  //시큐리티 본인 확인 대상
        productDTO.setTradeStatus(state);
        productRepository.save(productDTO.toEntity());

    }
    @Transactional
    public void updateView(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new NoResultException("해당 상품의 정보가 존재하지 않습니다."));
        product.appendView(product.getViews());
    }


    public Page<Product> listStateProduct(int page, int size, ProductStateType state) throws Exception{
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByTradeStatusContaining(pageable,state);
    };
    public void newProductAlert() {     //이후 추가

    }

    public void raiseProduct(Long productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("해당 상품의 정보가 없습니다"));
        String customLocalDateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime parsedCreateDate = LocalDateTime.parse(customLocalDateTimeFormat, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        Period day = Period.between(product.getRegDate().toLocalDate(), parsedCreateDate.toLocalDate());

        Duration time = Duration.between(product.getRegDate().toLocalTime(), parsedCreateDate.toLocalTime());

        long leftHour = 24-time.toSeconds()/3600;
        long leftMinute = 60-time.toSeconds()%3600/60;
        long leftSecond = 60-time.toSeconds()%3600%60;
        if (leftHour>=24){
            product.resetRegDate();
        }
        else{
            throw new Exception("끌어올리기 가능 시간까지" + leftHour + "시" + leftMinute + "분" + leftSecond + "초 남았습니다.");
        }
    }

    public void addLikeProduct(Long productId) {

    }

    public void deleteLikeProduct(long productId) {

    }
}

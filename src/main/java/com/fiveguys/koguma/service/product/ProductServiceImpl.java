package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.MemberDTO;
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

    public Page<Product> listProduct(Long memberId,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }


    public ProductDTO getProduct(Long productId) {              //시큐리티 본인 확인 대상
        Product product = productRepository.findById(productId).orElseThrow(()->new NoResultException("해당 상품의 정보가 존재하지 않습니다."));
        return ProductDTO.fromEntity(product);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {

        return ProductDTO.fromEntity(productRepository.save(productDTO.toEntity()));

    }
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
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

    @Override
    public Page<Product> listStateProduct(Long memberId, Pageable pageable, ProductStateType state) throws Exception {
        return productRepository.findBySellerIdAndTradeStatusContaining(memberId, pageable, state);
    }
    public Page<Product> listBuyProduct(Long memberId, Pageable pageable) throws Exception {
        return productRepository.findByBuyerIdAndTradeStatusContaining(memberId, pageable, ProductStateType.SALED);
    }

    public void newProductAlert() {     //이후 추가
        //AlertSerivce.addAlert(memberDTO, title, content, url);
    }

    @Transactional
    public void raiseProduct(Long productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("해당 상품의 정보가 없습니다"));

        LocalDateTime parsedCreateDate = LocalDateTime.now();

        Period period = Period.between(product.getRegDate().toLocalDate(), parsedCreateDate.toLocalDate());
        Duration duration = Duration.between(product.getRegDate().toLocalTime(), parsedCreateDate.toLocalTime());

        long leftHour = 24 - period.getDays() * 24L - duration.toHours();
        long leftMinute = 60 - (duration.toMinutes() % 60);
        long leftSecond = 60 - (duration.getSeconds() % 60);

        System.out.println(leftHour + "시" + leftMinute + "분" + leftSecond);

        if (leftHour < 0) {
            product.resetRegDate();
        } else {
            throw new Exception("끌어올리기 가능 시간까지" + leftHour + "시간" + leftMinute + "분" + leftSecond + "초 남았습니다.");
        }
    }

    public void isOwnProduct(Long memberId, Long sellerId) throws Exception {
        if (!sellerId.equals(memberId))
            throw new Exception("상품에 대한 권한이 없습니다");
    }
}

package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.ProductStateType;
import com.fiveguys.koguma.repository.common.QueryRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final QueryRepository queryRepository;

    public ProductDTO addProduct(ProductDTO productDTO) {
        return ProductDTO.fromEntity(productRepository.save(productDTO.toEntity()));
    }

    public Page<Product> listProduct(Long memberId,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }
    public List<ProductDTO> listProductByLocation(LocationDTO locationDTO,String keyword,Long categoryId) throws Exception {
        List<Product> productList = queryRepository.findAllByDistanceProduct(locationDTO,keyword,categoryId);
        return productList.stream().map(ProductDTO::fromEntityContainImage).filter(productDTO -> !ProductStateType.HIDE.equals(productDTO.getTradeStatus())).collect(Collectors.toList());

    }


    public ProductDTO getProduct(Long productId) {              //시큐리티 본인 확인 대상
//        Product product = productRepository.findById(productId).orElseThrow(()->new NoResultException("해당 상품의 정보가 존재하지 않습니다."));
//        return ProductDTO.fromEntity(product);
        Product product = productRepository.findByProductIdWithImages(productId);
        product.appendView(product.getViews());
        return ProductDTO.fromEntityContainImage(product);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {

        return ProductDTO.fromEntity(productRepository.save(productDTO.toEntity()));

    }
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }


    public void updateState(ProductDTO productDTO, ProductStateType state) {  //시큐리티 본인 확인 대상
        productDTO.setTradeStatus(state);
        // 숨김상태의 상품은 판매중으로 , 판매완료로 바뀌어야함
        // 만약 리뷰가 존재한다면 판매완료 됬던 상품이기 때문에 판매완료로 변경
        // 만약 리뷰가 없다면 판매중이었던 상품이기 떄문에 판매중으로 변경
        // 숨김중 상태는 restore state로 받음 ->> 리뷰확인후 복구

        productRepository.save(productDTO.toEntity());

    }

    @Override
    public void updateActiveFlag(Long productId) {
        ProductDTO productDTO = getProduct(productId);
        productDTO.setActiveFlag(false);
        productRepository.save(productDTO.toEntity());
    }

    @Transactional
    public void updateView(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new NoResultException("해당 상품의 정보가 존재하지 않습니다."));
        product.appendView(product.getViews());
    }

    @Override
    public List<ProductDTO> listStateProduct(Long memberId, ProductStateType state) throws Exception {
        List<Product> productList = productRepository.findAllBySellerIdAndTradeStatusAndActiveFlagOrderByRegDateDesc(memberId,state,true);
        return productList.stream()
                .map(ProductDTO::fromEntityContainImage)
                .collect(Collectors.toList());
    }
    public List<ProductDTO> listBuyProduct(Long memberId) throws Exception {
        List<Product> productList =  productRepository.findAllByBuyerIdAndTradeStatusAndActiveFlagOrderByBuyDateDesc(memberId,ProductStateType.SALED,true);
        return productList.stream()
                .map(ProductDTO::fromEntityContainImage)
                .collect(Collectors.toList());
    }

    public void newProductAlert() {     //이후 추가
        //AlertSerivce.addAlert(memberDTO, title, content, url);
    }

    @Transactional
    public void raiseProduct(Long productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("해당 상품의 정보가 없습니다"));

        LocalDateTime parsedCreateDate = LocalDateTime.now();

//        Period period = Period.between(product.getRegDate().toLocalDate(), parsedCreateDate.toLocalDate());
//        Duration duration = Duration.between(product.getRegDate().toLocalTime(), parsedCreateDate.toLocalTime());
//
//        long leftHour = 24 - period.getDays() * 24L - duration.toHours();
//        long leftMinute = 60 - (duration.toMinutes() % 60);
//        long leftSecond = 60 - (duration.getSeconds() % 60);
//
//        System.out.println(leftHour + "시" + leftMinute + "분" + leftSecond);
//
//        if (leftHour < 0) {
//            product.resetRegDate();
//        } else {
//            throw new Exception(String.format("%02d시간 %02d분 %02d초 남았습니다.", leftHour, leftMinute, leftSecond));
//        }
        Duration duration = Duration.between(product.getRegDate(), parsedCreateDate);
        Duration twentyFourHours = Duration.ofHours(24);

        System.out.println(duration);

        if (duration.compareTo(twentyFourHours) >= 0) {
            // 24시간 이상 경과했을 경우 등록 날짜를 초기화합니다.
            product.resetRegDate();
        } else {
            // 남은 시간을 계산합니다.
            long leftHour = twentyFourHours.toHours() - duration.toHours()-1;
            long leftMinute = 60 - (duration.toMinutes() % 60)-1;
            long leftSecond = 60 - (duration.getSeconds() % 60)-1;
            throw new Exception(String.format("%02d시간 %02d분 %02d초 남았습니다.", leftHour, leftMinute, leftSecond));
        }

    }

    public void isOwnProduct(Long memberId, Long sellerId) throws Exception {
        if (!sellerId.equals(memberId))
            throw new Exception("상품에 대한 권한이 없습니다");
    }

}

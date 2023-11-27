package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private Long seller_id;
    private Long buyer_id;
    private Long category_id;
    private String title;
    private String content;
    private int price;
    private char trade_status;
    private String dong;
    private Double latitude;
    private Double longitude;
    private int views;
    private String category_name;
    private Boolean active_flag;
    private LocalDateTime buy_date;

}

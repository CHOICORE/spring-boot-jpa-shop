package me.choicore.study.springbootjpashop.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}

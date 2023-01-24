package me.choicore.study.springbootjpashop.domain;


import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}

package me.choicore.study.springbootjpashop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @NotEmpty(message = "회원 이름은 필수 입력 값 입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public MemberDTO(String name) {
        this.name = name;
    }


    @Getter
    @Setter
    static class CreateMemberRequest {

    }
}

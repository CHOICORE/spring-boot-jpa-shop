package me.choicore.study.springbootjpashop.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.choicore.study.springbootjpashop.domain.Address;
import me.choicore.study.springbootjpashop.domain.Member;
import me.choicore.study.springbootjpashop.dto.MemberDTO;
import me.choicore.study.springbootjpashop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        log.info("====> [Member Controller] createForm()");
        model.addAttribute("memberDTO", new MemberDTO());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Valid MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("====> [Member Controller] create - bindingResult.hasErrors()");
            return "members/createMemberForm";
        }
        log.info("====> [Member Controller] createMember()");

        Address address = new Address(memberDTO.getCity(), memberDTO.getStreet(), memberDTO.getZipcode());
        Member member = new Member(null, memberDTO.getName(), address, new ArrayList<>());
        memberService.join(member);
        return "redirect:/";
    }


    @GetMapping("/members")
    public String getMembers(Model model) {
        log.info("====> [Member Controller] getMembers()");

        List<Member> members = memberService.findMembers();

        model.addAttribute("members", members);

        return "members/memberList";
    }
}

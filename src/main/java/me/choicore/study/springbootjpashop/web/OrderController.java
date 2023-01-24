package me.choicore.study.springbootjpashop.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.choicore.study.springbootjpashop.domain.Member;
import me.choicore.study.springbootjpashop.domain.Order;
import me.choicore.study.springbootjpashop.domain.item.Item;
import me.choicore.study.springbootjpashop.repository.order.OrderSearch;
import me.choicore.study.springbootjpashop.service.ItemService;
import me.choicore.study.springbootjpashop.service.MemberService;
import me.choicore.study.springbootjpashop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    private final ItemService itemService;


    @GetMapping("/order")
    public String createForm(Model model) {
        log.info("====> [Order Controller] createForm()");


        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }


    @PostMapping("/order")
    public String createOrder(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{itemId}/cancel")
    public String orderList(@PathVariable Long itemId) {
        orderService.cancelOrder(itemId);
        return "order/orderList";
    }
}


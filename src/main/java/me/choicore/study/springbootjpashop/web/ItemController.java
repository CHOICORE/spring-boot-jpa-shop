package me.choicore.study.springbootjpashop.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.choicore.study.springbootjpashop.domain.item.Book;
import me.choicore.study.springbootjpashop.domain.item.Item;
import me.choicore.study.springbootjpashop.dto.ItemDTO;
import me.choicore.study.springbootjpashop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @GetMapping("/items/new")
    public String createForm(Model model) {

        log.info("====> [Item Controller] createForm()");
        model.addAttribute("itemDTO", new ItemDTO());
        return "items/createItemForm";
    }

    /**
     * 상품 등록
     *
     * @param itemDTO
     * @return
     */
    @PostMapping("/items/new")
    public String createItem(ItemDTO itemDTO) {
        log.info("====> [Item Controller] create()");

        Book book = new Book();
        book.setName(itemDTO.getName());
        book.setPrice(itemDTO.getPrice());
        book.setStockQuantity(itemDTO.getStockQuantity());
        book.setAuthor(itemDTO.getAuthor());
        book.setIsbn(itemDTO.getIsbn());
        itemService.saveItem(book);
        return "redirect:/";
    }


    /**
     * 상품 목록
     */
    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Book book = (Book) itemService.findItem(itemId);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(book.getId());
        itemDTO.setName(book.getName());
        itemDTO.setPrice(book.getPrice());
        itemDTO.setStockQuantity(book.getStockQuantity());
        itemDTO.setAuthor(book.getAuthor());
        itemDTO.setIsbn(book.getIsbn());

        model.addAttribute("itemDTO", itemDTO);

        return "/items/updateItemForm";
    }


    /**
     * 상품 수정 폼
     */
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, ItemDTO itemDTO) {

        /*Book book = new Book();
        book.setId(itemDTO.getId());
        book.setName(itemDTO.getName());
        book.setPrice(itemDTO.getPrice());
        book.setStockQuantity(itemDTO.getStockQuantity());
        book.setAuthor(itemDTO.getAuthor());
        book.setIsbn(itemDTO.getIsbn());
        itemService.saveItem(book);*/

        itemService.updateItem(itemId,
                itemDTO.getName(),
                itemDTO.getPrice(),
                itemDTO.getStockQuantity());

        return "redirect:/items";
    }
}


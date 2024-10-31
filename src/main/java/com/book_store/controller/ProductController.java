package com.book_store.controller;

import com.book_store.entity.Product;
import com.book_store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/product")
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CartService cartService;


    @GetMapping("/listproducts")
    public String viewProduct(Model model,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "categoryId", defaultValue = "-1") Integer categoryId,
                              @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                              @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        return listProductByPages(1, sortField, sortDir, keyword, categoryId, model);
    }

    //phân trang
    @GetMapping("/listproducts/{pageNumber}")
    public String listProductByPages(@PathVariable(name = "pageNumber") int currentPage,
                                     @RequestParam("sortField") String sortField,
                                     @RequestParam("sortDir") String sortDir,
                                     @RequestParam("keyword") String keyword,
                                     @RequestParam(value = "categoryId") Integer categoryId,
                                     Model model) {
        Page<Product> page = productService.listAll(currentPage, sortField, sortDir, keyword, categoryId);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Product> listproduct = page.getContent();
        List<Product> listproductRegister = new ArrayList<>();
        model.addAttribute("listproductRegister", listproductRegister);
        model.addAttribute("listproduct", listproduct);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("query", "?sortField=" + sortField + "&sortDir="
                + sortDir + "&keyword=" + keyword + "&categoryId=" + categoryId);
        model.addAttribute("categoryList", productService.getCategoryList());
        return "products";
    }
}

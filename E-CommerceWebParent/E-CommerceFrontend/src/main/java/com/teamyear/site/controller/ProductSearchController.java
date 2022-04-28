package com.teamyear.site.controller;

import com.teamyear.common.entity.Brand;
import com.teamyear.common.entity.Category;
import com.teamyear.common.entity.Product;
import com.teamyear.common.entity.SubCategory;
import com.teamyear.site.formmodel.ProductFilterForm;
import com.teamyear.site.service.BrandService;
import com.teamyear.site.service.CategoryService;
import com.teamyear.site.service.ProductSearchService;
import com.teamyear.site.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductSearchController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private ProductSearchService searchService;

    @ModelAttribute("brandList")
    public List<Brand> brandList() {
        return brandService.findAll();
    }

    @ModelAttribute("categoryList")
    public List<Category> categoryList() {
        return categoryService.findAll();
    }

    @ModelAttribute("subCategoryList")
    public List<SubCategory> subCategoryList() {
        return subCategoryService.findAll();
    }

    @GetMapping("/components")
    public String components(ModelMap model) {
        List<Product> productList = searchService.findAll();
        model.addAttribute("productList", productList);
        model.addAttribute("filter", new ProductFilterForm());
        return "frontend/ECS-WEB003";
    }

    @GetMapping("/product-detail")
    public String productDetail(@RequestParam("id") Integer id, ModelMap model) {
        Product product = searchService.findById(id).get();
        model.addAttribute("product", product);
        return "frontend/ECS-WEB004";
    }

    @PostMapping("/filter")
    public String filterProduct(@ModelAttribute("filter") ProductFilterForm filter, ModelMap model) {
        List<Product> productList = searchService.productFilter(filter);

        model.addAttribute("productList", productList);
        return "frontend/ECS-WEB003";
    }

    @GetMapping("/search-keyword")
    public String searchProduct(@RequestParam(name = "searchKeyword") String keyword, ModelMap model) {
        List<Product> productList = searchService.productSearch(keyword);

        model.addAttribute("filter", new ProductFilterForm());
        model.addAttribute("productList", productList);
        return "frontend/ECS-WEB003";
    }
}
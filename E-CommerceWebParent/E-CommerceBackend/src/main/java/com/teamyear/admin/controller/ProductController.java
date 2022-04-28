package com.teamyear.admin.controller;

import com.teamyear.admin.formmodel.ProductForm;
import com.teamyear.admin.service.*;
import com.teamyear.admin.util.ProductSaveHelper;
import com.teamyear.common.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubCategoryService sCategoryService;
    @Autowired
    private DiscountService discountService;

    @ModelAttribute("brandList")
    public List<Brand> brandList() {
        return brandService.findAll();
    }

    @ModelAttribute("categoryList")
    public List<Category> categoryList() {
        return categoryService.findAll();
    }

    @ModelAttribute("subList")
    public List<SubCategory> subCategoryList() {
        return sCategoryService.findAll();
    }

    @ModelAttribute("discountList")
    public List<Discount> discountList() {
        return discountService.findAll();
    }

    @GetMapping("/products")
    public String products(ModelMap model) {
        List<Product> productList = productService.findAll();

        model.addAttribute("productList", productList);
        return "admin/ECS-PD001";
    }

    @GetMapping("/add-product")
    public String addProductForm(ModelMap model) {
        String[] detailNames = new String[3];
        String[] detailValues = new String[3];

        model.addAttribute("detailNames", detailNames);
        model.addAttribute("detailValues", detailValues);
        model.addAttribute("form", new ProductForm());
        return "admin/ECS-PD002";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute("form") @Validated ProductForm form, BindingResult br,
                             @RequestParam(name = "detailId") Integer[] detailIds,
                             @RequestParam(name = "detailName") String[] detailNames,
                             @RequestParam(name = "detailValue") String[] detailValues,
                             RedirectAttributes ra, ModelMap model) throws IOException {
        if (br.hasErrors()) {
            model.addAttribute("detailNames", detailNames);
            model.addAttribute("detailValues", detailValues);
            if (form.getProductImages().size() != 4) {
                model.addAttribute("errorimg", "Product image must be least 5 images.");
            }
            return "admin/ECS-PD002";
        } else if (form.getProductImages().size() != 4) {
            model.addAttribute("detailNames", detailNames);
            model.addAttribute("detailValues", detailValues);
            model.addAttribute("errorimg", "Product image must be least 4 images.");
            return "admin/ECS-PD002";
        }

        Product product = new Product();
        product.setProductName(form.getProductName());
        product.setProductDescritpion(form.getProductDescritpion());
        product.setPrice(form.getPrice());
        product.setQuantity(form.getQuantity());
        product.setBrand(form.getBrand());
        product.setCategory(form.getCategory());
        product.setSubCategory(form.getSubCategory());
        product.setDiscount(form.getDiscount());

        ProductSaveHelper.setProductDetails(detailIds, detailNames, detailValues, product);
        Product savedProduct = productService.save(product);
        ProductSaveHelper.setProductImages(form.getProductImages(), product);
        productService.save(product);

        ra.addFlashAttribute("success", "New Product successfully added.");
        return "redirect:/admin/products";
    }

    @GetMapping("/update-product")
    public String updateProductForm(@RequestParam("id") Integer id, ModelMap model) {
        Product product = productService.findById(id);
        ProductForm form = new ProductForm();
        form.setId(id);
        form.setProductName(product.getProductName());
        form.setProductDescritpion(product.getProductDescritpion());
        form.setPrice(product.getPrice());
        form.setQuantity(product.getQuantity());
        form.setBrand(product.getBrand());
        form.setCategory(product.getCategory());
        form.setSubCategory(product.getSubCategory());
        form.setProductDetails(product.getDetails());
        form.setDiscount(product.getDiscount());

        model.addAttribute("form", form);
        return "admin/ECS-PD002-01";
    }

    @PostMapping("/update-product")
    public String updateProduct(@RequestParam("id") Integer id, @ModelAttribute("form") @Validated ProductForm form,
                                BindingResult br,
                                @RequestParam(name = "detailId") Integer[] detailIds,
                                @RequestParam(name = "detailName") String[] detailNames,
                                @RequestParam(name = "detailValue") String[] detailValues,
                                RedirectAttributes ra, ModelMap model) throws IOException {

        if (br.hasErrors()) {
            return "admin/ECS-PD002-01";
        }

        Product product = productService.findById(id);
        product.setId(id);
        product.setProductName(form.getProductName());
        product.setProductDescritpion(form.getProductDescritpion());
        product.setPrice(form.getPrice());
        product.setQuantity(form.getQuantity());
        product.setBrand(form.getBrand());
        product.setCategory(form.getCategory());
        product.setSubCategory(form.getSubCategory());
        product.setDiscount(form.getDiscount());

        ProductSaveHelper.setProductDetails(detailIds, detailNames, detailValues, product);
        if (form.getProductImages().size() != 0 && form.getProductImages().size() > 1) {
            ProductSaveHelper.setProductImages(form.getProductImages(), product);
        }
        productService.save(product);

        ra.addFlashAttribute("success", "Update Successful.");
        return "redirect:/admin/products";
    }

    @GetMapping("/delete-product")
    public String deleteProduct(@RequestParam("id") Integer id, RedirectAttributes ra) throws IOException {

        Product product = productService.findById(id);

        String delDir = "./images/ProductImg/" + product.getId();
        Path delPath = Paths.get(delDir);
        if (product.getImg1() != null && product.getImg2() != null && product.getImg3() != null) {
            Files.walk(delPath).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        }

        productService.deleteById(id);
        ra.addFlashAttribute("success", "Delete successful.");
        return "redirect:/admin/products";
    }
}
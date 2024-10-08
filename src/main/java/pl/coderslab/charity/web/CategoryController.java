package pl.coderslab.charity.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.service.CategoryService;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    String categoryList(Model model) {
        model.addAttribute("setCategory", categoryService.getDistinctCategories());
        return "categoryList";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("category", new Category());
        return "categoryForm";
    }

    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "categoryForm";
        }
        categoryService.add(category);
        return "redirect:/admin/category/all";
    }
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.getCategory(id);
        if (category.isPresent()) {
            Category c = category.get();
            model.addAttribute("categoryToEdit", c);
            return "editCategory";
        } else return "redirect:/admin/category/all";

    }
    @PostMapping("/edit")
    public String editUser( @ModelAttribute("categoryToEdit") @Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editCategory";
        }
        categoryService.update(category);
        return "redirect:/admin/category/all";
    }
    @GetMapping("/remove/{id}")
    public String removeCategory(@PathVariable Long id){
        categoryService.delete(id);
        return "redirect:/admin/category/all";
    }


}

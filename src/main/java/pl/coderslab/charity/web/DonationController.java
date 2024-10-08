package pl.coderslab.charity.web;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Donation;
import jakarta.validation.Valid;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/donation")
@RequiredArgsConstructor
public class DonationController {
    private final DonationRepository donationRepository;
    private final DonationService donationService;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;
    private final UserService userService;
    private List<Category> categoryList;


    @GetMapping("/addStep1")
    public String addStep1(Model model, HttpSession session) {
        Donation donation = (Donation) session.getAttribute("donation");
        if (donation == null) {
            donation = new Donation();
        }
        model.addAttribute("donation", donation);
        return "step1";
    }

    @PostMapping("/addStep1")
    public String addStep1(@Valid @ModelAttribute("donation") Donation donation, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "step1";
        }
        session.setAttribute("donation", donation);
        return "redirect:/donation/addStep2";
    }

    @GetMapping("/addStep2")
    public String addStep2(Model model, HttpSession session) {
        Donation donation = (Donation) session.getAttribute("donation");
        if (donation == null) {
            return "redirect:/donation/addStep1";
        }
        categoryList = donation.getCategories();
        model.addAttribute("donation", donation);
        return "step2";
    }

    @PostMapping("/addStep2")
    public String addStep2(@Valid @ModelAttribute("donation") Donation donation, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "step2";
        }

        session.setAttribute("donation", donation);
        return "redirect:/donation/addStep3";
    }

    @GetMapping("/addStep3")
    public String addStep3(Model model, HttpSession session) {
        Donation donation = (Donation) session.getAttribute("donation");
        if (donation == null) {
            return "redirect:/donation/addStep1";
        }
        model.addAttribute("donation", donation);
        return "step3";
    }

    @PostMapping("/addStep3")
    public String addStep3(@Valid @ModelAttribute("donation") Donation donation, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "step3";
        }

        session.setAttribute("donation", donation);
        return "redirect:/donation/addStep4";
    }

    @GetMapping("/addStep4")
    public String addStep4(Model model, HttpSession session) {
        Donation donation = (Donation) session.getAttribute("donation");
        if (donation == null) {
            return "redirect:/donation/addStep1";
        }

        model.addAttribute("donation", donation);
        return "step4";
    }

    @PostMapping("/addStep4")
    public String addStep4(@Valid @ModelAttribute("donation") Donation donation, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            FieldError error = result.getFieldError("categories");
            if (error != null) {
                log.info("Field: {}, Error: {}", error.getField(), error.getDefaultMessage());
            }
            return "step4";
        }
        session.setAttribute("donation", donation);
        return "redirect:/donation/addStep5";
    }

    @GetMapping("/addStep5")
    public String addStep5(Model model, HttpSession session) {
        Donation donation = (Donation) session.getAttribute("donation");
        if (donation == null) {
            return "redirect:/donation/addStep1";
        }
        donation.setCategories(categoryList);
        model.addAttribute("donation", donation);
        return "step5";
    }

    @PostMapping("/addStep5")
    public String addStep5(@Valid @ModelAttribute("donation") Donation donation, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            FieldError error = result.getFieldError("categories");
            if (error != null) {
                log.info("Field: {}, Error: {}", error.getField(), error.getDefaultMessage());
            }
            return "step5";
        }
        donation.setCategories(categoryList.stream().map(c -> categoryService.getCategory(c.getId()).get()).collect(Collectors.toList()));
        donation.setUser(userService.getCurrentUser());
        donationRepository.save(donation);
        return "step6";
    }

    @ModelAttribute("categorySet")
    public Set<Category> categorySet() {
        return categoryService.getDistinctCategories();
    }

    @ModelAttribute("institutionSet")
    public Set<Institution> institutionSet() {
        return institutionService.institutionSet();
    }
}

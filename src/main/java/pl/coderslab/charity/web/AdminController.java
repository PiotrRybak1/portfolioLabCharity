package pl.coderslab.charity.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DonationService donationService;
    private final CategoryService categoryService;
    private List<Category> categoryList;

    @GetMapping("/details")
    public String adminDetails(Model model) {
        model.addAttribute("aDetails", userService.getCurrentUser());
        return "adminSite";
    }
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<User> user = userService.get(id);
        if (user.isPresent()) {
            User u = user.get();
            model.addAttribute("adminToEdit", u);
            return "editAdmin";
        } else return "redirect:/admin/details";

    }
    @PostMapping("/edit")
    public String editUser(@ModelAttribute("adminToEdit") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editAdmin";
        }
        userService.update(user);
        return "redirect:/admin/details";
    }
    @GetMapping("/edit/password/{id}")
    public String editPassword(@PathVariable Long id, Model model, HttpSession session) {
        Optional<User> user = userService.get(id);
        if (user.isPresent()) {
            User u = user.get();
            model.addAttribute("passwordEdit", u);
            session.setAttribute("nativePassword", u.getPassword());
            return "editAdminPassword";
        } else return "redirect:/admin/details";

    }
    @PostMapping("/edit/password")
    public String editParty(@RequestParam String password1,@RequestParam String password2, @ModelAttribute("passwordEdit") @Valid User user, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "editAdminPassword";
        }
        if(!passwordEncoder.matches(password1,(String)(session.getAttribute("nativePassword"))) && !password2.equals(user.getPassword())){
            return "editAdminPassword";
        }
        userService.updatePassword(user);
        return "redirect:/admin/details";
    }
    @GetMapping("/add")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "addAdminForm";
    }

    @PostMapping("/add")
    public String registerUser(@RequestParam String password2, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "addAdminForm";
        }
        if(!password2.equals(user.getPassword())){
            return "addAdminForm";
        }
        userService.addAdmin(user);
        return "redirect:/admin/details";
    }
    @GetMapping("/all")
    public String allAdmins(Model model){
        model.addAttribute("adminsSet", userService.allAdmins());
        return "allAdmins";
    }
    @GetMapping("/remove/{id}")
    public String removeAdmin(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/admin/all";
    }
    @GetMapping("/removeUser/{id}")
    public String removeUserByAdmin(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/admin/user/all";
    }
    @GetMapping("/user/all")
    public String allUsers(Model model){
        model.addAttribute("usersSet", userService.allUsers());
        return "allUsers";
    }
    @GetMapping("/editUser/{id}")
    public String editUserByAdmin(@PathVariable Long id, Model model) {
        Optional<User> user = userService.get(id);
        if (user.isPresent()) {
            User u = user.get();
            model.addAttribute("userToEditAdmin", u);
            return "adminEditUser";
        } else return "redirect:/admin/details";

    }
    @PostMapping("/editUser")
    public String editUserbyAdmin(@ModelAttribute("userToEditAdmin") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "adminEditUser";
        }
        userService.update(user);
        return "redirect:/admin/details";
    }
    @GetMapping("/editUser/password/{id}")
    public String adminEditPasswordUser(@PathVariable Long id, Model model, HttpSession session) {
        Optional<User> user = userService.get(id);
        if (user.isPresent()) {
            User u = user.get();
            model.addAttribute("passwordEdit", u);
            session.setAttribute("nativePassword", u.getPassword());
            return "adminEditUserPassword";
        } else return "redirect:/admin/details";

    }
    @PostMapping("/editUser/password")
    public String adminEditPasswordUser(@RequestParam String password1,@RequestParam String password2, @ModelAttribute("passwordEdit") @Valid User user, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "adminEditUserPassword";
        }
        if(!passwordEncoder.matches(password1,(String)(session.getAttribute("nativePassword"))) && !password2.equals(user.getPassword())){
            return "adminEditUserPassword";
        }
        userService.updatePassword(user);
        return "redirect:/admin/user/all";
    }
    @GetMapping("/donation/all")
    public String all(Model model) {
        model.addAttribute("all", donationService.donationSet());
        return "donationsList";
    }
    @GetMapping("/donation/edit/{id}")
    public String editDonation(@PathVariable Long id, Model model) {
        Optional<Donation> don = donationService.getDonation(id);
        if (don.isPresent()) {
            Donation d = don.get();
            categoryList = d.getCategories();
            model.addAttribute("donationToEdit", d);
            return "editDon";
        } else return "redirect:/admin/donation/all";

    }
    @PostMapping("/donation/edit")
    public String editUser( @ModelAttribute("donationToEdit") @Valid Donation donation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editDon";
        }
        donation.setCategories(categoryList.stream().map(c -> categoryService.getCategory(c.getId()).get()).collect(Collectors.toList()));
        donationService.update(donation);
        return "redirect:/admin/donation/all";
    }

}

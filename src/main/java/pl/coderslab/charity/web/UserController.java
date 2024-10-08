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
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.UserService;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DonationService donationService;

    @GetMapping("/details")
    public String userDetails(Model model) {
        model.addAttribute("uDetails", userService.getCurrentUser());
        model.addAttribute("userDonationSet", donationService.byUser(userService.getCurrentUser()));
        return "userSite";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<User> user = userService.get(id);
        if (user.isPresent()) {
            User u = user.get();
            model.addAttribute("userToEdit", u);
            return "editUser";
        } else return "redirect:/user/details";

    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("userToEdit") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editUser";
        }
        userService.update(user);
        return "redirect:/user/details";
    }

    @GetMapping("/edit/password/{id}")
    public String editPassword(@PathVariable Long id, Model model, HttpSession session) {
        Optional<User> user = userService.get(id);
        if (user.isPresent()) {
            User u = user.get();
            model.addAttribute("passwordEdit", u);
            session.setAttribute("nativePassword", u.getPassword());
            return "editUserPassword";
        } else return "redirect:/user/details";

    }

    @PostMapping("/edit/password")
    public String editParty(@RequestParam String password1, @RequestParam String password2, @ModelAttribute("passwordEdit") @Valid User user, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "editUserPassword";
        }
        if (!passwordEncoder.matches(password1, (String) (session.getAttribute("nativePassword"))) && !password2.equals(user.getPassword())) {
            return "editUserPassword";
        }
        userService.updatePassword(user);
        return "redirect:/user/details";
    }


}

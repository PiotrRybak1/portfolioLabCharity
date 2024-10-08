package pl.coderslab.charity.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.repository.UserRepository;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UserService;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
private final InstitutionRepository institutionRepository;
private final DonationRepository donationRepository;
private final UserService userService;
private final InstitutionService institutionService;

    @RequestMapping("/")
    public String homeAction(Model model){
        model.addAttribute("list", IntStream.range(0,institutionRepository.findDistinctInstitution().size()).boxed().collect(Collectors.toSet()));
        model.addAttribute("institutionsSet", institutionRepository.findDistinctInstitution());
        model.addAttribute("sumBags",donationRepository.sumBags());
        model.addAttribute("sumInstitution", institutionRepository.findDistinctInstitution());
        model.addAttribute("sumDonations", donationRepository.sumDonations());
        return "index";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String password2, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        if(!password2.equals(user.getPassword())){
            return "register";
        }
        userService.add(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/all")
    String setInst(Model model){
        model.addAttribute("setInst", institutionService.institutionSet());
        return "institutionListFree";
    }
    @PostMapping("/logout")
    String logout(){
        return "redirect:/";
    }
}

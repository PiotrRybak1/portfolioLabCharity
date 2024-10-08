package pl.coderslab.charity.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.service.InstitutionService;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin/institution")
@RequiredArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;

    @GetMapping("/all")
    String setInst(Model model){
        model.addAttribute("setInst", institutionService.institutionSet());
        return "institutionList";
    }

    @GetMapping("/add")
    public String addInst(Model model){
        model.addAttribute("inst", new Institution());
        return "institutionForm";
    }
    @PostMapping("/add")
    public String addInst(@Valid Institution institution, BindingResult result){
        if(result.hasErrors()){
            return "institutionForm";
        }
        institutionService.add(institution);
        return "redirect:/admin/institution/all";
    }
    @GetMapping("/edit/{id}")
    public String editInstitution(@PathVariable Long id, Model model) {
        Optional<Institution> inst = institutionService.getInstitution(id);
        if (inst.isPresent()) {
            Institution i = inst.get();
            model.addAttribute("institutionToEdit", i);
            return "editInst";
        } else return "redirect:/admin/institution/all";

    }
    @PostMapping("/edit")
    public String editUser( @ModelAttribute("institutionToEdit") @Valid Institution institution, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editInst";
        }
        institutionService.update(institution);
        return "redirect:/admin/institution/all";
    }
    @GetMapping("/remove/{id}")
    public String removeInstitution(@PathVariable Long id){
        institutionService.delete(id);
        return "redirect:/admin/institution/all";
    }

}

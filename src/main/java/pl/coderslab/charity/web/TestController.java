package pl.coderslab.charity.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final CategoryRepository categoryRepository;
    private final DonationRepository donationRepository;
    private final InstitutionRepository institutionRepository;
    @GetMapping("/add")
    public String addTestValues(){
       // Category sport = new Category("ball");
      //  Category rtv = new Category("xbox");

       // Institution sportFans = new Institution("SportFans","Fan of sports");
      //  Institution gameFansInst = new Institution("gameFans","Fan of game");

      //  Donation categorySport = new Donation(5,"testStreet", "testCity","22-100", LocalDate.now(), LocalTime.now(), "testcomment");
       // categorySport.getCategories().add(sport);
      //  categorySport.setInstitution(sportFans);
      //  sport.getDonation().add(categorySport);
      //  sportFans.getDonations().add(categorySport);
        /*institutionRepository.save(sportFans);
        donationRepository.save(categorySport);
        categoryRepository.save(sport);*/




        /*Donation categoryRTV = new Donation(6,"testStreetGame", "testCityGame","22-100", LocalDate.now(), LocalTime.now(), "testcommentGame");
        categoryRTV.getCategories().add(rtv);
        categoryRTV.setInstitution(gameFansInst);
        rtv.getDonation().add(categoryRTV);
        gameFansInst.getDonations().add(categoryRTV);*/
        /*institutionRepository.save(gameFansInst);
        donationRepository.save(categoryRTV);
        categoryRepository.save(rtv);*/
        Institution cookFansInst = new Institution("cookFans","Fan of cook");
        Category agd = new Category("microwave");
        //Donation categoryagd = new Donation(10,"testStreetCook", "testCityCook","22-100", LocalDate.now(), LocalTime.now(), "testcommentCook");
       // categoryagd.getCategories().add(agd);
       // categoryagd.setInstitution(cookFansInst);
       // agd.getDonation().add(categoryagd);
       // cookFansInst.getDonations().add(categoryagd);
        institutionRepository.save(cookFansInst);
      //  donationRepository.save(categoryagd);
        categoryRepository.save(agd);

return "redirect:/";









    }
}

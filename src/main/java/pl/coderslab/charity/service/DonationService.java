package pl.coderslab.charity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;

    public void add(Donation donation) {
        donationRepository.save(donation);
    }
    public Optional<Donation> getDonation(Long id) {
        return donationRepository.findById(id);
    }
    public void update(Donation donation){
        donationRepository.save(donation);
    }
    public void delete(Long id){
        donationRepository.deleteById(id);
    }
    public Set<Donation> donationSet(){
       return donationRepository.findDistinctDonations();
    }
    public List<Donation> donationList(){
        return donationRepository.findAll();
    }
    public Set<Donation> byUser(User user){
        return donationRepository.findAllByUser(user.getId());
    }

}

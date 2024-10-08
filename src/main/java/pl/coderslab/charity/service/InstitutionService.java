package pl.coderslab.charity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

    public void add(Institution institution){
        institutionRepository.save(institution);
    }
    public Optional<Institution> getInstitution(Long id){
        return institutionRepository.findById(id);
    }
    public void update(Institution institution){
        institutionRepository.save(institution);
    }
    public void delete(Long id){
        institutionRepository.deleteById(id);
    }
    public Set<Institution> institutionSet (){
        return institutionRepository.findDistinctInstitution();
    }
}

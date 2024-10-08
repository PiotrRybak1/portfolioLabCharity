package pl.coderslab.charity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.repository.CategoryRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void add(Category category) {
        categoryRepository.save(category);
    }
    public Optional<Category> getCategory (Long id) {
        return categoryRepository.findById(id);
    }
    public void update(Category category){
        categoryRepository.save(category);
    }
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
    public Set<Category> getDistinctCategories(){
       return categoryRepository.getDistinctCategory();
    }

}

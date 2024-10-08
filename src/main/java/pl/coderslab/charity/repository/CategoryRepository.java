package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.model.Category;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select distinct c from Category c")
    public Set<Category> getDistinctCategory();

}

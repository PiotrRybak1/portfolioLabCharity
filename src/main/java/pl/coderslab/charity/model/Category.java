package pl.coderslab.charity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 256)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    @ToString.Exclude
    private List<Donation> donation = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }
}

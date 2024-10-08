package pl.coderslab.charity.model;

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
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 256)
    private String name;
    @Size(max = 256)
    private String description;
    @OneToMany(mappedBy = "institution")
    @ToString.Exclude
    private List<Donation> donations = new ArrayList<>();

    public Institution(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

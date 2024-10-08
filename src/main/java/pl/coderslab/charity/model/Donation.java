package pl.coderslab.charity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Integer quantity;
    @ManyToMany(fetch = EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Category> categories = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Size(max = 256)
    private String street;
    @Size(max = 256)
    private String city;
    private String zipCode;
    private LocalDate pickUpDate;
    private LocalTime pickUpTime;
    @Size(max = 650)
    private String pickUpComment;
    @Size(max = 256)
    private String phoneNumber;
    private Boolean isReceived;

    @PrePersist
    public void prePersist() {
        isReceived = false;
    }

}

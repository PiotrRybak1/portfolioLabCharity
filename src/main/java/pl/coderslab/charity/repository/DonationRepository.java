package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.charity.model.Donation;

import java.util.Set;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Query("select sum(d.quantity) from Donation d")
    Integer sumBags();
    @Query("select count(distinct d.institution) from Donation d")
    Integer sumInstitution();
    @Query("select count(distinct d) from Donation d")
    Integer sumDonations();
    @Query("select distinct d from Donation d")
    Set<Donation> findDistinctDonations();
    @Query("select distinct d from Donation d join fetch d.user u where u.id= :id order by d.isReceived")
    Set<Donation> findAllByUser(@Param("id") Long id);
}
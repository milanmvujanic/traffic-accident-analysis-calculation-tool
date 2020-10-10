package pro.trafficaccidentanalysis.calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pro.trafficaccidentanalysis.calculation.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

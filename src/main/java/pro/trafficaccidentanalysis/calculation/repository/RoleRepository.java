package pro.trafficaccidentanalysis.calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.trafficaccidentanalysis.calculation.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByName(String name);
}

package sg.edu.iss.CA.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.CA.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

}

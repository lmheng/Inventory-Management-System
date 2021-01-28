package sg.edu.iss.CA.repo;

import sg.edu.iss.CA.model.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

	@Query("SELECT s FROM Supplier s")
	Page<Supplier> findAllSuppliers(Pageable pageable);
	
}


package sg.edu.iss.CA.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.CA.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

	public Brand findByBrandName(String name);
	
	@Query("SELECT b.brandName FROM Brand b")
	public List<String> getBrandNames();
}

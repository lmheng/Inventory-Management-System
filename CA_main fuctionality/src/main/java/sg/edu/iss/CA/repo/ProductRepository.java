
package sg.edu.iss.CA.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import sg.edu.iss.CA.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>,QueryByExampleExecutor<Product>{

	@Query("SELECT DISTINCT p.type FROM Product p")
	public List<String> getTypes();
	@Query("SELECT DISTINCT p.category FROM Product p")
	public List<String> getCategories();
	@Query("SELECT DISTINCT p.subcategory FROM Product p")
	public List<String> getSubcategories();
	
	@Query("SELECT DISTINCT p.colour FROM Product p")
	public List<String> getColours();
	
	@Query("SELECT p FROM Product p WHERE p.inventory.units < p.inventory.reorderLevel and p.supplier.supplierId=:sid")
	public List<Product> findReorderProducts(@Param("sid") long sid);

}

package sg.edu.iss.CA.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.CA.model.Brand;
import sg.edu.iss.CA.model.Inventory;
import sg.edu.iss.CA.model.Product;
import sg.edu.iss.CA.model.Supplier;

public interface ProductInterface {

	public List<Product> listAllProducts();
	public Page<Product> list(int pageNum);
	public Page<Product> filterList(int pageNum,Product product,int pageSize);
	public Product findProductById(Long pid);
	
	public List<String> findBrandNames();
	public List<String> getTypes();
	public List<String> getCategories();
	public List<String> getSubcategories();
	public List<String> findColours();
	
	public Brand findBrand(String brand);
	
	public List<Supplier> findAllSuppliers();
	public Supplier findSupplierById(Long id);
	
	public void saveProduct(Product product);	
	public void deleteProduct(Product product);
	public void saveBrand(Brand brand);
	public void setBrandtoProduct(Product product, Brand brand);
	public void setSuppliertoProduct(Product product);
	
	public void generateReport() throws IOException;
	//public void generateReportException();

}

package sg.edu.iss.CA.service;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.CA.model.Supplier;

public interface SupplierInterface {
	
	public void createSupplier(Supplier supplier);
	public void updateSupplier(Supplier supplier);
	public void deleteSupplier(Supplier supplier);
	public Supplier findSupplier(Long id);
	
	public List<Supplier> listAllSuppliers();
	public Page<Supplier> getSupplierPage(int pageNum);
	
}

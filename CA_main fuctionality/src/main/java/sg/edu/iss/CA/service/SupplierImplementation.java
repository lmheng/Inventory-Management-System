package sg.edu.iss.CA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.CA.model.Supplier;
import sg.edu.iss.CA.model.User;
import sg.edu.iss.CA.repo.SupplierRepository;

@Service
@Transactional
public class SupplierImplementation implements SupplierInterface{

	@Autowired
	SupplierRepository srepo;
	
	@Override
	public void createSupplier(Supplier supplier) {
		srepo.save(supplier);
	}

	@Override
	public void updateSupplier(Supplier supplier) {
		srepo.save(supplier);
	}

	@Override
	public List<Supplier> listAllSuppliers() {
		return srepo.findAll();
	}

	@Override
	public void deleteSupplier(Supplier supplier) {
		srepo.delete(supplier);	
	}

	@Override
	public Supplier findSupplier(Long id) {
		Supplier supplier = srepo.findById(id).get();
		return supplier;
	}
	
	@Override
	public Page<Supplier> getSupplierPage(int pageNum){
		int pageSize = 5;
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		 
		Page<Supplier> page = srepo.findAllSuppliers(pageable);

		return page;
	}
}

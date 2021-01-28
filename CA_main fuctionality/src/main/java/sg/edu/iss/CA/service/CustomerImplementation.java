package sg.edu.iss.CA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.CA.model.Customer;
import sg.edu.iss.CA.model.Product;
import sg.edu.iss.CA.model.TransactionDetails;
import sg.edu.iss.CA.repo.CustomerRepository;

@Service
@Transactional
public class CustomerImplementation implements CustomerInterface {

	@Autowired
	CustomerRepository crepo;

	@Override
	public void createCustomer(Customer customer) {
		crepo.save(customer);

	}

	@Override
	public void updateCustomer(Customer customer) {
		crepo.save(customer);

	}

	@Override
	public List<Customer> listAllCustomers() {
		return crepo.findAll();
	}

	@Override
	public void deleteCustomer(Customer customer) {
		crepo.delete(customer);

	}


	@Override
	public Customer findCustomer(Long id) {
		Customer customer = crepo.findById(id).get();
		return customer;
	}

	@Override
	public Page<Customer> getCustomerPage(int pageNum){
		int pageSize = 5;
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		 
		Page<Customer> page = crepo.findAllCustomers(pageable);

		return page;
	}

}

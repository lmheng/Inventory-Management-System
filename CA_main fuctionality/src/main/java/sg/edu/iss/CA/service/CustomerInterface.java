package sg.edu.iss.CA.service;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.CA.model.Customer;

public interface CustomerInterface {

	public void createCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public List<Customer> listAllCustomers();
	public void deleteCustomer(Customer customer);
	public Customer findCustomer(Long id);
	public Page<Customer> getCustomerPage(int pageNum);
}
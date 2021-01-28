package sg.edu.iss.CA.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.iss.CA.model.Customer;
import sg.edu.iss.CA.service.CustomerInterface;

@Controller
@RequestMapping("/Customer")
public class CustomerController extends ExceptionHandlingController{

	@Autowired
	private CustomerInterface cservice;

	@Autowired
	public void setCustomer(CustomerInterface customer) {
		this.cservice = customer;
	}

	@GetMapping("/customerform")
	public String customerform(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "customerform";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("customer")@Valid Customer customer, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors())
		{
			return"customerform";
		}
		cservice.createCustomer(customer);
		return "forward:/Customer/customerlist/1";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {

		Customer customer = cservice.findCustomer(id);		
		model.addAttribute("customer", customer);

		return "customerform";
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id) {

		Customer customer = cservice.findCustomer(id);
		cservice.deleteCustomer(customer);

		return "forward:/Customer/customerlist/1";
	}

	@RequestMapping(value="/customerlist/{pageNum}", method= {RequestMethod.GET, RequestMethod.POST})
	public String viewCustomerList(Model model, @PathVariable(name = "pageNum") int pageNum) {

		Page<Customer> custPage = cservice.getCustomerPage(pageNum);
		List<Customer> custSlice = custPage.getContent();
		
		model.addAttribute("cslice", custSlice);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", custPage.getTotalPages());
		model.addAttribute("totalItems", custPage.getTotalElements());
		
		return "customerlist";
	}

}
package sg.edu.iss.CA.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.CA.model.Customer;
import sg.edu.iss.CA.model.Transaction;
import sg.edu.iss.CA.model.TransactionDetails;
import sg.edu.iss.CA.model.TransactionType;
import sg.edu.iss.CA.service.CustomerImplementation;
import sg.edu.iss.CA.service.CustomerInterface;
import sg.edu.iss.CA.service.ProductInterface;
import sg.edu.iss.CA.service.ProductService;
import sg.edu.iss.CA.service.TransactionImplementation;
import sg.edu.iss.CA.service.TransactionInterface;

@Controller
@RequestMapping("/stockentry")
public class StockEntryController {

	@Autowired
	TransactionInterface transactService;

	@Autowired
	ProductInterface prodRepo;
	
	@Autowired
	CustomerInterface customerService;

	@Autowired
	public void setTransactionImplementation(TransactionImplementation timpl) {
		this.transactService = timpl;
	}
	
	@Autowired
	public void setCustomerImplementation(CustomerImplementation cusimpl) {
		this.customerService = cusimpl;
	}
	
	@Autowired
	public void setProductImplementation(ProductService prodImpl) {
		this.prodRepo = prodImpl;
	}

	@GetMapping("/")
	public String transactionLandingPage(Model model) {
		
		return "transactionlanding";
	}
	
	@GetMapping("/raise")
	public String raiseTransaction(Model model, HttpServletRequest request, HttpSession session) {

		if(transactService.readCookie("transaction", request) == "no cookie") {

			model.addAttribute("transaction", transactService.setDefaultTransactionDetails(session));
			return "stockentry";
		}
		
		return "redirect:/stockentry/transactiondetaillist";
	}

	@PostMapping("/save")
	public String saveTransaction(@Valid @ModelAttribute("transaction") Transaction transaction, BindingResult bindingResult, Model model,
			HttpServletResponse response) {

		if(bindingResult.hasErrors()) {
			
			if(transaction.getType() == TransactionType.USAGE) {
				
				List<Customer> customers = customerService.listAllCustomers();
				
				model.addAttribute("customers", customers);
				
				return "usageEntry";
			}
			else {
			return "stockentry";}
		}
		
		transactService.setCustomertoTransaction(transaction);
		transactService.saveTransaction(transaction);
		transactService.createCookie(transaction, response);

		return "redirect:/stockentry/transactiondetaillist";
	}

	@GetMapping("/transactiondetaillist")
	public String TransactionDetailList(Model model, HttpServletRequest request) {

		Transaction transaction = transactService.findTransactionById(request);
		List<TransactionDetails> td = transactService.listTransactionDetails(transaction);

		if(transaction.getType() == TransactionType.USAGE) {
			model.addAttribute("customer", transaction.getCustomer().getCustomerID());
		}
		
		model.addAttribute("td", td);

		return "addtransactiondetails";
	}

	@GetMapping("/transactiondetailconfirm")
	public String transactionDetailConfirm(Model model, HttpServletRequest request, HttpServletResponse response) {

		Transaction transaction = transactService.findTransactionById(request);
		List<TransactionDetails> td = transactService.listTransactionDetails(transaction);

		
		transactService.confirmTransaction(td);
		transactService.deleteCookie(transaction, response);
		
		return "redirect:/stockentry/";
	}

	@GetMapping("/addtransactiondetails")
	public String addTransactionDetails(Model model, HttpServletRequest request) {
		
		return "redirect:/Product/list/1";
	}

	@PostMapping("/addtransactiondetails/save")
	public String saveTransactionDetails(@ModelAttribute("detail") TransactionDetails detail, Model model) {
		
		transactService.setProducttoTransactionDetail(detail);
		
		if(detail.getProduct().getInventory().getUnits() + detail.getQuantity() < 0) {
			model.addAttribute("error","Exceeded quantity in inventory, only " + detail.getProduct().getInventory().getUnits() + " available in-stock");
			model.addAttribute("detail", detail);
			return "detailsentry";
		}
		
		transactService.updateTransactionDetails(detail);

		return "redirect:/stockentry/transactiondetaillist";

	}

	@GetMapping("/edittransactiondetails/{id}")
	public String editTransactionDetails(Model model, @PathVariable("id") Long id) {

		TransactionDetails td = transactService.findTransactionDetail(id);

		model.addAttribute("detail", td);
		model.addAttribute("transactiontype", td.getTransaction().getType());
		return "detailsentry";

	}

	@GetMapping("/deletetransactiondetails/{id}")
	public String deleteTransactionDetails(Model model, @PathVariable("id") Long id) {

		TransactionDetails transactDetail = transactService.findTransactionDetail(id);

		transactService.deleteTransactionDetail(transactDetail);

		return "redirect:/stockentry/transactiondetaillist";
	}
	
	@GetMapping("/usage")
	public String raiseUsageTransaction(Model model, HttpServletRequest request, HttpSession session) {
		

		if(transactService.readCookie("transaction", request) == "no cookie") {

			List<Customer> customers = customerService.listAllCustomers();
			
			model.addAttribute("transaction", transactService.setDefaultTransactionDetails(session));
			model.addAttribute("customers", customers);
			
			return "usageEntry";
		}
		
		return "redirect:/stockentry/transactiondetaillist";
	}
	
	
	@GetMapping("/transactiondetailcancel")
	public String transactionDetailCancel(Model model, HttpServletRequest request, HttpServletResponse response) {

		Transaction transaction = transactService.findTransactionById(request);

		transactService.deleteTransaction(request);
		transactService.deleteCookie(transaction, response);
		
		return "redirect:/stockentry/";
	}

}


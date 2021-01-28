package sg.edu.iss.CA.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.edu.iss.CA.model.Customer;
import sg.edu.iss.CA.model.DateFilter;
import sg.edu.iss.CA.model.Product;
import sg.edu.iss.CA.model.Transaction;
import sg.edu.iss.CA.model.TransactionDetails;
import sg.edu.iss.CA.model.TransactionType;
import sg.edu.iss.CA.model.User;
import sg.edu.iss.CA.repo.CustomerRepository;
import sg.edu.iss.CA.repo.ProductRepository;
import sg.edu.iss.CA.repo.TransactionDetailRepository;
import sg.edu.iss.CA.repo.TransactionRepository;
import sg.edu.iss.CA.repo.UserRepository;

@Service
@Transactional
public class TransactionImplementation implements TransactionInterface{

	@Autowired
	private TransactionRepository transRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TransactionDetailRepository tdRepo;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	ProductRepository prodRepo;
	
	@Autowired
	CustomerRepository custRepo;
	
	@Override
	public void saveTransaction(Transaction transaction) {
		transRepo.save(transaction);
	}
	
	@Override
	public Transaction setDefaultTransactionDetails(HttpSession session) {
		Transaction transaction = new Transaction();
		
		User user = (User) session.getAttribute("usession");
		
		transaction.setTdate(LocalDateTime.now());
		transaction.setUser(user);
		
		return transaction;
	}
	
	@Override
	public Transaction findTransactionById(HttpServletRequest request) {
		
		String idString =  readCookie("transaction", request);
		Long id = Long.parseLong(idString);

		Transaction transaction = transRepo.findById(id).get();
		
		return transaction;
	}
	
	@Override
	public List<TransactionDetails> listTransactionDetails(Transaction transaction){
		
		List<TransactionDetails> transactDetails = tdRepo.findTransactionDetailsByTransaction(transaction.getTransactionId());
		
		if(transactDetails == null) {
			transactDetails = new ArrayList<TransactionDetails>();
		}
		
		return transactDetails;
	}
	
	@Override
	public String readCookie(String key, HttpServletRequest request) {
	    	
			String transactionValue = Arrays.stream(request.getCookies()).filter(c -> key.equals(c.getName())).map(Cookie::getValue).reduce((first,second) -> second).orElse(null);
			
			if(transactionValue == null) {
				return "no cookie";
			}
			
			return transactionValue;

	}

	@Override
	public TransactionDetails findTransactionDetail(Long id) {
		TransactionDetails transactDetails= tdRepo.findTransactionDetailsByTransacDetailID(id);
		
		return transactDetails;
	}

	@Override
	public void deleteTransactionDetail(TransactionDetails transactDetail) {
		tdRepo.delete(transactDetail);
	}
	
	@Override
	public void createCookie(Transaction transaction, HttpServletResponse response) {
		Long id = transaction.getTransactionId();

		String idString = id.toString();
		Cookie transactionCookie = new Cookie("transaction", idString);
		transactionCookie.setPath("/");
		response.addCookie(transactionCookie);
	}
	
	@Override
	public void deleteCookie(Transaction transaction, HttpServletResponse response) {

		Cookie transactionCookie = new Cookie("transaction", "");
		transactionCookie.setPath("/");
		transactionCookie.setMaxAge(0);
		response.addCookie(transactionCookie);
	}
	
	@Override
	public void confirmTransaction(List<TransactionDetails> td) {
		for (TransactionDetails detail : td) {
			Product product = prodRepo.findById(detail.getProduct().getProductId()).get();
			product.getInventory().setUnits(product.getInventory().getUnits() + detail.getQuantity());
			prodRepo.save(product);
			
			if(detail.getTransaction().getType() == TransactionType.PO) {
				product.getInventory().setReorderFlag(true);
			}

				if (product.getInventory().getUnits() < product.getInventory().getReorderLevel() && (LocalDateTime.now().minusHours(24).isAfter(product.getInventory().getLastEmailSent()) || product.getInventory().getReorderFlag())) {
					product.getInventory().setLastEmailSent(LocalDateTime.now());
					product.getInventory().setReorderFlag(false);
					sendMailToAllAdmins(product);
				}
		}
	}
	
	@Override
	public Page<TransactionDetails> getProductHistory(Product product, int pageNum){
		int pageSize = 5;
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		 
		Page<TransactionDetails> page = tdRepo.findByProduct(product, pageable);

		return page;
	}
	
	@Override
	public Page<TransactionDetails> getFilteredProductHistory(int pageNum,Product product,DateFilter df){
		int pageSize = 5;
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		 
		Page<TransactionDetails> page = tdRepo.getProductHistory(product,df.getStartDate(),df.getEndDate(), pageable);

		return page;
		
	}
	
	@Override
	public void deleteTransaction(HttpServletRequest request) {
		
		Transaction transaction= findTransactionById(request);

		tdRepo.deleteByTransaction(transaction);
		transRepo.deleteById(transaction.getTransactionId());
		
	}
	
	@Override
	public void sendMailToAllAdmins(Product product) {
		
		List<User> admins = userRepo.findAllAdmins();
		
		for(User admin : admins) {
			try {
				mailService.sendNotification(admin, product);
			}
			catch(MessagingException e){
				Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, e);
			}
		
		}
	}

	@Override
	public void setCustomertoTransaction(Transaction transaction) {
		if(transaction.getType() == TransactionType.USAGE) {
			Long id = transaction.getCustomer().getCustomerID();
			Customer customer = custRepo.findById(id).get();
			transaction.setCustomer(customer);
		}
		
	}

	@Override
	public void setProducttoTransactionDetail(TransactionDetails transactDetail) {
		Long id2 = transactDetail.getProduct().getProductId();
		Product p = prodRepo.findById(id2).get();
		transactDetail.setProduct(p);
	}

	@Override
	public void updateTransactionDetails(TransactionDetails transactDetail) {
		List<TransactionDetails> transactDetails = listTransactionDetails(transactDetail.getTransaction());
		transactDetails.add(transactDetail);

		Transaction transaction = transactDetail.getTransaction();
		transaction.setTd(transactDetails);

		saveTransaction(transaction);
		
	}
}

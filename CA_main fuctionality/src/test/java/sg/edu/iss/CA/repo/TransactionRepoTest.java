package sg.edu.iss.CA.repo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.CA.model.Customer;
import sg.edu.iss.CA.model.Transaction;
import sg.edu.iss.CA.model.TransactionDetails;
import sg.edu.iss.CA.model.TransactionType;
import sg.edu.iss.CA.model.User;

@SpringBootTest
public class TransactionRepoTest {

		@Autowired
		private TransactionRepository transRepo;
		
		@Autowired
		private TransactionDetailRepository tdRepo;
		
		@Autowired
		private ProductRepository productRepo;
		
		@Autowired
		private BrandRepository brandRepo;
		
		@Autowired
		private SupplierRepository supplierRepo;
		
		@Autowired
		private UserRepository userRepo;
		
		@Autowired
		private CustomerRepository crepo;
		
		@Test
		void createTransaction() {
			
			Transaction transaction = new Transaction();
			
			User user = userRepo.findById((long) 1).get();
			
			Customer customer = crepo.findById((long) 1).get();
			
			transaction.setTdate(LocalDateTime.now());
			transaction.setCustomer(customer);
			transaction.setUser(user);
			transaction.setType(TransactionType.PO);
			
			List<TransactionDetails> transDetails = new ArrayList<TransactionDetails>();
			
			transDetails.add(new TransactionDetails(transaction, productRepo.findById((long) 3).get(), 10));
			transDetails.add(new TransactionDetails(transaction, productRepo.findById((long) 4).get(), 10));
			
			transaction.setTd(transDetails);
			transRepo.save(transaction);
			
			System.out.println(transaction.getType());
		}
		
		@Test
		void testTdRepo() {
			List<TransactionDetails> td = tdRepo.findAll();
		
			System.out.println(td);
			
			for(TransactionDetails t : td) {
				System.out.println(t);
			}
		}
}


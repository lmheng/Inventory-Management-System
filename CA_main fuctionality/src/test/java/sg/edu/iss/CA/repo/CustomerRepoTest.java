package sg.edu.iss.CA.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.CA.model.Customer;

@SpringBootTest
public class CustomerRepoTest {

	@Autowired
	CustomerRepository crepo;
	
	@Test
	void userCreation() {
		crepo.save(new Customer("Miguel", "SKT9009A", "miguel28@gmail.com", "82369009"));
		crepo.save(new Customer("Carter", "SMA4836B", "carter36@gmail.com", "92444836"));
		crepo.save(new Customer("Luciana", "SKN1999I", "luciana99@gmail.com", "95111999"));
		crepo.save(new Customer("Harrison", "SKC6016C", "harrison16@gmail.com", "93596016"));
		crepo.save(new Customer("Abraham", "SML5261D", "abraham61@gmail.com", "81625261"));
		crepo.save(new Customer("Janet", "SJV3701P", "janet01@gmail.com", "86523701"));
		crepo.save(new Customer("Phoenix", "SLH9808M", "phoenix08@gmail.com", "84459808"));
		crepo.save(new Customer("Nash", "SMN5511U", "nash11@gmail.com", "90855511"));
		crepo.save(new Customer("Joaquin", "SJK6040P", "joaquin40@gmail.com", "83086040"));
		crepo.save(new Customer("Penelope", "SNN3939W", "penelope39@gmail.com", "92463939"));
		crepo.save(new Customer("Brooklyn", "SJB4533K", "brooklyn33@gmail.com", "95584533"));
		crepo.save(new Customer("Gabriella", "SKN1938A", "gabriella38@gmail.com", "81131938"));
		crepo.save(new Customer("Melody", "SFP2898B", "melody98@gmail.com", "94902898"));
		crepo.save(new Customer("Gemma", "SMV1685X", "gemma85@gmail.com", "98911685"));
		crepo.save(new Customer("Oaklynn", "SKL2007Z", "oaklynn07@gmail.com", "97372007"));
		
	}
}
	//public Customer(String customerName, String vehicle, String email, long contactno) 
package sg.edu.iss.CA.repo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.CA.model.Role;
import sg.edu.iss.CA.model.User;

@SpringBootTest
public class UserRepoTest {

	@Autowired
	UserRepository urepo;
	
	@Test
	public void createUser()
	{
		User u= new User("mech","password","mech","heng.l.meng@gmail.com",999,Role.MECHANIC);
		User u2= new User("admin","password","Admin","jstinwang@gmail.com",999,Role.ADMIN);
		ArrayList<User>users=new ArrayList<>();
		users.add(u);
		users.add(u2);
		urepo.saveAll(users);
		
		//String userName, String password, String name, String email, long contactno, Role role
		
	}
	
	@Test
	public void testFindAdmin() {
		List<User> admins = urepo.findAllAdmins();
		
		for(User admin : admins) {
			System.out.println(admin);
		}
	}
	
}

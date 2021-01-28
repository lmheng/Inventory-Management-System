package sg.edu.iss.CA.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.CA.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findUserByUserName(String userName);
	
	@Query("SELECT u FROM User u WHERE u.role = sg.edu.iss.CA.model.Role.ADMIN")
	public List<User> findAllAdmins(); 
	
	@Query("SELECT u FROM User u")
	Page<User> findAllUsers(Pageable pageable);
	
}

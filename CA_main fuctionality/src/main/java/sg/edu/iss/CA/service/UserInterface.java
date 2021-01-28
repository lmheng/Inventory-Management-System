package sg.edu.iss.CA.service;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.CA.model.User;

public interface UserInterface {
	
	public List<User> list();
	public List<User>ListAllUser();
	
	public void save(User user);
	public void deleteUser(User user);
	
	public boolean authenticate(User user);
	public boolean authenticateException(User user);
	
	public User findByUserName(String userName);
	public User findUserById(long id);
	
	public Page<User> getUserPage(int pageNum);
}

package sg.edu.iss.CA.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.edu.iss.CA.model.Customer;
import sg.edu.iss.CA.model.User;
import sg.edu.iss.CA.repo.UserRepository;

@Service
@Transactional
public class UserImplementation implements UserInterface {

	@Autowired
	UserRepository urepo;

	@Override
	public List<User> ListAllUser() {
		return urepo.findAll();
	}

	@Override
	public void deleteUser(User user) {
		urepo.delete(user);
	}

	@Override
	public boolean authenticate(User user) throws NullPointerException {
			User Dbuser = urepo.findUserByUserName(user.getUserName());
			if (Dbuser.getUserName().equals(user.getUserName()) && Dbuser.getPassword().equals(user.getPassword()))
				return true;
			else
				return false;
	}

	@Override
	public User findByUserName(String userName) {
		return urepo.findUserByUserName(userName);
	}

	@Override
	public List<User> list() {
		return urepo.findAll();
	}

	@Override
	public void save(User user) {
		urepo.save(user);
	}

	@Override
	public User findUserById(long id) {
		return urepo.findById(id).get();
	}

	@Override
	public boolean authenticateException(User user) {
		try {
			return authenticate(user);
		}
		catch(NullPointerException e) {
			return false;
		}
	}

	@Override
	public Page<User> getUserPage(int pageNum){
		int pageSize = 5;
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		 
		Page<User> page = urepo.findAllUsers(pageable);

		return page;
	}
	
}

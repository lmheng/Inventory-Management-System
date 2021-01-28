package sg.edu.iss.CA.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

import sg.edu.iss.CA.model.Transaction;
import sg.edu.iss.CA.model.User;
import sg.edu.iss.CA.service.TransactionImplementation;
import sg.edu.iss.CA.service.TransactionInterface;
import sg.edu.iss.CA.service.UserImplementation;
import sg.edu.iss.CA.service.UserInterface;

@Controller

public class UserController extends ExceptionHandlingController{

	@Autowired
	UserInterface uservice;

	@Autowired
	TransactionInterface transactService;

	@Autowired
	public void setUserImplementation(UserImplementation uimpl) {
		this.uservice = uimpl;
	}

	@Autowired
	public void setTransactionImplementation(TransactionImplementation timpl) {
		this.transactService = timpl;
	}

	@GetMapping("/")
	public String login(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "index";

	}

	@PostMapping("/authenticate")
	public String authenticate(@ModelAttribute("user") User user, Model model, HttpSession session) {

		if (uservice.authenticateException(user)) {

			User u = uservice.findByUserName(user.getUserName());
			session.setAttribute("usession", u);

			return "redirect:/welcome";
		} else {
			model.addAttribute("wrongCred", "Incorrect user credentials, please try again");
			return "index";
		}
	}

	@GetMapping("/welcome")
	public String welcomePage() {
		return "welcome";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest session, HttpServletRequest request, HttpServletResponse response) {
		
		if (transactService.readCookie("transaction", request) != "no cookie") {
			Transaction transaction = transactService.findTransactionById(request);

			transactService.deleteTransaction(request);
			transactService.deleteCookie(transaction, response);
		}

		session.getSession().invalidate();

		return "forward:/";
	}

	@GetMapping("/user/form")
	public String showForm(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "newemp";
	}

	@PostMapping("/user/save")
	public String save(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "newemp";
		}

		uservice.save(user);
		return "forward:/user/list/1";
	}

	@RequestMapping(value = "/user/list/{pageNum}", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model, @PathVariable(name = "pageNum") int pageNum) {
		Page<User> userPage = uservice.getUserPage(pageNum);
		List<User> userSlice = userPage.getContent();

		model.addAttribute("uslice", userSlice);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", userPage.getTotalPages());
		model.addAttribute("totalItems", userPage.getTotalElements());
		return "employeeportal";
	}

	@GetMapping("/user/edit/{userID}")
	public String edit(Model model, @PathVariable("userID") long id) {
		model.addAttribute("user", uservice.findUserById(id));
		return "newemp";
	}

	@GetMapping("/user/resetpw/{userID}")
	public String resetPassword(Model model, @PathVariable("userID") long id) {
		model.addAttribute("user", uservice.findUserById(id));
		return "resetpw";
	}

	@PostMapping("/user/savepw")
	public String savepw(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "resetpw";
		}

		uservice.save(user);
		return "redirect:/welcome";
	}

	@GetMapping("/user/delete/{userID}")
	public String delete(Model model, @PathVariable("userID") long id) {
		User del = uservice.findUserById(id);
		uservice.deleteUser(del);
		return "forward:/user/list/1";
	}

}

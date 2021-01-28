package sg.edu.iss.CA.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userID;
	@NotEmpty(message="Username may not be left empty")
	private String userName;
	@NotEmpty(message="Please enter your password")
	@Size(min = 2, max = 32, message = "Password must be between 6 and 32 characters long")
	private String password;
	@NotEmpty(message="Please enter your full name")
	private String name;
	@Email
	private String email;
	
	private long contactno;

	@NotNull(message="please ascribe a role")
	private Role role;

	

	public User(String userName, String password, String name, String email, long contactno, Role role) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.email = email;
		this.contactno = contactno;
		this.role = role;
	}


	
	
}

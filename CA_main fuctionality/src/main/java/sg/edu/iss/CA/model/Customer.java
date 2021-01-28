package sg.edu.iss.CA.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerID;
	@NotEmpty(message= "Customer name cannot be empty")
	private String customerName;
	@NotEmpty
	private String vehicle;
	@Email(message="Please fill in a valid email address")
	private String email;
	@NotEmpty
	@Size(min=8,max=8, message="Please fill in a valid contact number (8-digits)")
	private String contactno;

	public Customer(String customerName, String vehicle, String email, String contactno) {
		super();
		this.customerName = customerName;
		this.vehicle = vehicle;
		this.email = email;
		this.contactno = contactno;
	}
}
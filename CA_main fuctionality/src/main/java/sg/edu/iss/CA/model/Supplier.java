package sg.edu.iss.CA.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long supplierId;
	@NotEmpty(message="name field cannot be empty")
	private String name;
	@NotEmpty(message="address cannot be empty")
	private String address;
	@NotEmpty(message="Phone number cannot be empty")
	private String phone;
	@NotEmpty(message="Email address cannot be empty")
	private String email;
	
	public Supplier(String name, String address, String email, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
	
	
	
}

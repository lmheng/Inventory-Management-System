package sg.edu.iss.CA.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	@NotEmpty(message="Field 'product name' cannot be empty")
	private String productName;
	@NotEmpty(message="Field 'description' cannot be empty")
	private String description;
	private String type;
	@NotEmpty(message="Field 'category' cannot be empty")
	private String category;
	@NotEmpty(message="Field 'subcategory' cannot be empty")
	private String subcategory;
	private String colour;
	private String dimenision;
	
	@ManyToOne
	private Brand brand;
	@ManyToOne
	private Supplier supplier;
	
	@Valid
	@OneToOne (cascade=CascadeType.ALL)
	private Inventory inventory;

	public Product(String productName, String description, String type, String category,
			String subcategory, String colour, String dimenision, Brand brand, Supplier supplier, Inventory inventory) {
		this.productName = productName;
		this.description = description;
		this.type = type;
		this.category = category;
		this.subcategory = subcategory;
		this.colour = colour;
		this.dimenision = dimenision;
		this.brand = brand;
		this.supplier = supplier;
		this.inventory = inventory;
	}
	
	
	
}

package sg.edu.iss.CA.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class TransactionDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Trans_Detail_Id;
	
	@ManyToOne
	private Transaction transaction;
	
	@ManyToOne
	private Product product;
	
	private int quantity;

	public TransactionDetails(Transaction transaction, Product product, int quantity) {
		this.transaction = transaction;
		this.product = product;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "TransactionDetails [Trans_Detail_Id=" + Trans_Detail_Id + ", product="
				+ product + ", quantity=" + quantity + "]";
	}
		
}

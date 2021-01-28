package sg.edu.iss.CA.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@PastOrPresent
	private LocalDateTime tdate;
	
	@OneToOne
	private Customer customer;
	
	@OneToOne
	private User user;
	private TransactionType type;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "transaction")
	private List<TransactionDetails> td;

	public Transaction(LocalDateTime tdate, Customer customer, User user, TransactionType type, List<TransactionDetails> td) {
		this.tdate = tdate;
		this.customer = customer;
		this.user = user;
		this.type = type;
		this.td = td;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", tdate=" + tdate + ", customer=" + customer + ", user="
				+ user + ", type=" + type + "]";
	}

	
}

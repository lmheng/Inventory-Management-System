package sg.edu.iss.CA.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Inventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long inventoryId;
	@NotNull(message="Field 'unitPrice' must be filled") 
	@Digits(integer=7,fraction=2,message="fill in numbers, up to 2 d.p")
	private Double unitPrice;
	@NotNull(message="Field 'reorderLevel' must be filled")
	@Digits(integer=7,fraction=0,message="fill in numbers, up to 2 d.p")
	private Integer reorderLevel;
	@NotNull(message="Field 'Wholesale Price' must be filled")
	@Positive(message="fill in only positive numbers")
	@Digits(integer=7,fraction=2,message="fill in numbers, up to 2 d.p")
	private Double wholesalePrice;
	@NotNull(message="Field 'PartnerPrice' must be filled")
	@Positive(message="fill in only positive numbers")
	@Digits(integer=7,fraction=2,message="fill in numbers, up to 2 d.p")
	private Double partnerPrice;
	@NotNull(message="Field 'retailPrice' must be filled")
	@Positive(message="fill in only postive numbers")
	@Digits(integer=7,fraction=2,message="field cannot be empty, enter numbers up to 2 decimal place")
	private Double retailPrice;
	@NotEmpty(message="Please enter a valid storage location")
	private String shelfLocation;
	@NotNull(message="Field 'MOQ' must be filled")
	@Positive
	@Digits(integer=7,message="Please fill in the minimal order quantity required", fraction = 0)
	private Integer moq;
	@Min(value=0, message="Please enter incoming quantity (must be more than 0)")
	private Integer units;
	private Boolean reorderFlag;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime lastEmailSent;
	
	@OneToOne(mappedBy = "inventory")
	private Product product;

	public Inventory(Double unitPrice, Integer reorderLevel, Double wholesalePrice, Double partnerPrice,
			Double retailPrice, String shelfLocation, Integer moq, Integer units, Boolean reorderFlag,
			LocalDateTime lastEmailSent) {
		this.unitPrice = unitPrice;
		this.reorderLevel = reorderLevel;
		this.wholesalePrice = wholesalePrice;
		this.partnerPrice = partnerPrice;
		this.retailPrice = retailPrice;
		this.shelfLocation = shelfLocation;
		this.moq = moq;
		this.units = units;
		this.reorderFlag = reorderFlag;
		this.lastEmailSent = lastEmailSent;
	}
	
	
	
}

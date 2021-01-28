package sg.edu.iss.CA.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.CA.model.Product;
import sg.edu.iss.CA.model.Transaction;
import sg.edu.iss.CA.model.TransactionDetails;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetails, Long>{

	@Query("Select td from TransactionDetails td where td.transaction.transactionId = :tId")
	List<TransactionDetails> findTransactionDetailsByTransaction(@Param("tId") Long transactionId);
	
	@Query("Select td from TransactionDetails td where td.Trans_Detail_Id = :tdId")
	TransactionDetails findTransactionDetailsByTransacDetailID(@Param("tdId") Long tdId);
	

	Page<TransactionDetails> findByProduct(Product product,Pageable pageable);
	
	@Query("Select td from TransactionDetails td where td.product = :product AND (td.transaction.tdate BETWEEN :sdate AND :edate)")
	Page<TransactionDetails> getProductHistory(@Param("product")Product product,@Param("sdate")LocalDateTime startdate,@Param("edate")LocalDateTime endDate, Pageable pageable);
	
	void deleteByTransaction(Transaction transaction);
}

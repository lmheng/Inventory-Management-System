package sg.edu.iss.CA.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;

import sg.edu.iss.CA.model.DateFilter;
import sg.edu.iss.CA.model.Product;
import sg.edu.iss.CA.model.Transaction;
import sg.edu.iss.CA.model.TransactionDetails;

public interface TransactionInterface {
	
	public Transaction setDefaultTransactionDetails(HttpSession session);
	public Transaction findTransactionById(HttpServletRequest request);	
	
	public List<TransactionDetails> listTransactionDetails(Transaction transaction);
	public Page<TransactionDetails> getProductHistory(Product product,int pageNum);
	public Page<TransactionDetails> getFilteredProductHistory(int pageNum,Product product,DateFilter df);
	
	public void saveTransaction(Transaction transaction);
	public void deleteTransaction(HttpServletRequest request);
	public void confirmTransaction(List<TransactionDetails> td);
	public void setCustomertoTransaction(Transaction transaction);
	
	public TransactionDetails findTransactionDetail(Long id);
	public void deleteTransactionDetail(TransactionDetails transactDetail);
	public void setProducttoTransactionDetail(TransactionDetails transactDetail);
	public void updateTransactionDetails(TransactionDetails transactDetail);
	
	public String readCookie(String key, HttpServletRequest request);
	public void createCookie(Transaction transaction, HttpServletResponse response);
	public void deleteCookie(Transaction transaction, HttpServletResponse response);
	
	public void sendMailToAllAdmins(Product product);
	
}

package sg.edu.iss.CA.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import sg.edu.iss.CA.model.Product;
import sg.edu.iss.CA.model.User;
import sg.edu.iss.CA.service.MailService;

@Service
public class MailService {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendNotification(User user, Product product) throws MessagingException{
		
		try{MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "Dear user,"
							+ "<br>"
							+ "<br>"
							+ "Please be informed that the following products are currently low in stock:"
							+ "<br>"
							+ "<table border=\"1\">"
							+ "    <tr>"
							+ "        <th>"
							+ "            Product ID"
							+ "        </th>"
							+ "        <th>"
							+ "            Product Name"
							+ "        </th>"
							+ "        <th>"
							+ "            Quantity left"
							+ "        </th>"
							+ "    </tr>"
							+ "    <tr>"
							+ "        <td>"
							+ product.getProductId()
							+ "        </td>"
							+ "        <td>"
							+ product.getProductName()
							+ "        </td>"
							+ "        <td>"
							+ product.getInventory().getUnits()
							+ "        </td>"
							+ "    </tr>"
							+ "</table>"
							+ "<br>"
							+ "--This is a system generated email, no response is required--";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setText(htmlMsg, true);
		helper.setTo(user.getEmail());
		helper.setSubject("[Low Stock Notification] Product "+product.getProductId()+" "+product.getProductName());
		helper.setFrom("iosoverture@gmail.com");
		
		javaMailSender.send(mimeMessage);}
		catch(MessagingException e) {
			Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
}

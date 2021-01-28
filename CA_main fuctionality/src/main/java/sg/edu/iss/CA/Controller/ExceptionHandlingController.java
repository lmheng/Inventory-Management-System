package sg.edu.iss.CA.Controller;

import java.io.IOException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ExceptionHandlingController {

	@ResponseStatus(value= HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public String foreignKeyContraint() {
		return "del_error";
	}
	
	@ExceptionHandler(IOException.class)
	public void ioException(IOException e) {
		e.printStackTrace();
	}
}

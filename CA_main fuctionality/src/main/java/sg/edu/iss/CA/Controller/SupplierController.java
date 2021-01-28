package sg.edu.iss.CA.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.iss.CA.model.Supplier;
import sg.edu.iss.CA.model.User;
import sg.edu.iss.CA.service.SupplierInterface;

@Controller
@RequestMapping("/Supplier")
public class SupplierController extends ExceptionHandlingController{
	
	@Autowired
	private SupplierInterface sservice;
	
	@Autowired
	public void setSupplier(SupplierInterface supplier) {
		this.sservice = supplier;
	}
	
	@GetMapping("/supplierform")
	public String supplierform(Model model) {
		Supplier supplier = new Supplier();
		model.addAttribute("supplier", supplier);
		return "supplierform";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("supplier") @Valid Supplier supplier, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "supplierform";
		}
		
		sservice.createSupplier(supplier);
		return "forward:/Supplier/supplierlist/1";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		
		Supplier supplier = sservice.findSupplier(id);		
		model.addAttribute("supplier", supplier);
		
		return "supplierform";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id) {
		
		Supplier supplier = sservice.findSupplier(id);
		sservice.deleteSupplier(supplier);
		
		return "forward:/Supplier/supplierlist/1";
	}
	
	@RequestMapping(value="/supplierlist/{pageNum}",method= {RequestMethod.GET, RequestMethod.POST})
	public String viewSupplierList(Model model, @PathVariable(name = "pageNum") int pageNum) {
		
		Page<Supplier> supplierPage = sservice.getSupplierPage(pageNum);
		List<Supplier> supplierSlice = supplierPage.getContent();
		
		model.addAttribute("sslice", supplierSlice);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", supplierPage.getTotalPages());
		model.addAttribute("totalItems", supplierPage.getTotalElements());
		
		return "supplierlist";
	}
}
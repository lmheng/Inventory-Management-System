package sg.edu.iss.CA.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import sg.edu.iss.CA.model.Brand;
import sg.edu.iss.CA.model.DateFilter;
import sg.edu.iss.CA.model.Product;
import sg.edu.iss.CA.model.Supplier;
import sg.edu.iss.CA.model.Transaction;
import sg.edu.iss.CA.model.TransactionDetails;
import sg.edu.iss.CA.service.ProductInterface;
import sg.edu.iss.CA.service.ProductService;
import sg.edu.iss.CA.service.TransactionImplementation;
import sg.edu.iss.CA.service.TransactionInterface;

@Controller
@RequestMapping("/Product")
public class ProductController extends ExceptionHandlingController{

	@Autowired
	private ProductInterface productService;
	
	@Autowired
	private TransactionInterface transactService;
	
	@Autowired
	public void setTransactionImplementation(ProductService productService) {
		this.productService = productService;
	}
	
	@Autowired
	public void setTransactionImplementation(TransactionImplementation timpl) {
		this.transactService = timpl;
	}
	
	@RequestMapping(value = "/list/{pageNum}", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model,@PathVariable(name = "pageNum") int pageNum, HttpServletRequest request) {
		
		Integer pageSize=10;
		Product product= new Product();
		List<String> types=productService.getTypes();
		List<String> categories=productService.getCategories();
		List<String> subcategories=productService.getSubcategories();
		List<String> brandNames=productService.findBrandNames();

		Page<Product> page = productService.list(pageNum);
		List<Product> plist = page.getContent();
		List<String> colours=productService.findColours();
		
		if(transactService.readCookie("transaction", request) == "no cookie") {
			model.addAttribute("fromTransaction","browse");
		}else {
			model.addAttribute("fromTransaction","stock");
		}
		
		model.addAttribute("colours", colours);
		model.addAttribute("brands", brandNames);
	    model.addAttribute("types", types);
	    model.addAttribute("categories", categories);
	    model.addAttribute("subcategories", subcategories);
		model.addAttribute("product",product);
	    model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
	    model.addAttribute("source", "list");
		model.addAttribute("plist", plist);
		model.addAttribute("pageSize",pageSize);
		return "productlist";
	}
	
	@RequestMapping(value = "/filterlist/{pageNum}", method= {RequestMethod.GET, RequestMethod.POST})
	public String filterList(@ModelAttribute("pageSize")Integer pageSize,@ModelAttribute("product") Product product, Model model,@PathVariable(name = "pageNum") int pageNum, HttpServletRequest request) {
		
		Page<Product> page = productService.filterList(pageNum,product,pageSize);
		List<Product> plist = page.getContent();
		List<String> types=productService.getTypes();
		List<String> categories=productService.getCategories();
		List<String> subcategories=productService.getSubcategories();
		List<String> brandNames=productService.findBrandNames();
		List<String> colours=productService.findColours();
		
		if(transactService.readCookie("transaction", request) == "no cookie") {
			model.addAttribute("fromTransaction","browse");
		}else {
			model.addAttribute("fromTransaction","stock");
		}		
		
		model.addAttribute("colours", colours);
		model.addAttribute("brands", brandNames);
		model.addAttribute("types", types);
	    model.addAttribute("categories", categories);
	    model.addAttribute("subcategories", subcategories);
	    model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("plist", plist);
		model.addAttribute("source", "filterlist");
		model.addAttribute("product",product);
		
		return "productlist";
	}
	
	@GetMapping("/addToStock/{pid}")
	public String addToStock(@PathVariable("pid") Long prodId, Model model, HttpServletRequest request) {
		Transaction transaction = transactService.findTransactionById(request);

		TransactionDetails detail = new TransactionDetails();
		Product product = productService.findProductById(prodId);
		
		detail.setTransaction(transaction);
		detail.setProduct(product);
		
		model.addAttribute("detail", detail);
		model.addAttribute("products", product);
		model.addAttribute("transactiontype", transaction.getType());
		
		return "detailsentry";
	}
	
	@GetMapping("/viewHistory/{pid}/{pageNum}")
	public String viewHistory(@PathVariable("pid") Long prodId, Model model,@PathVariable(name = "pageNum") int pageNum) {
		
		DateFilter df= new DateFilter();
		Product product = productService.findProductById(prodId);

		Page<TransactionDetails> page = transactService.getProductHistory(product,pageNum);
		List<TransactionDetails> history = page.getContent();
		
		model.addAttribute("df",df);
		model.addAttribute("product", product);
		model.addAttribute("history", history);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("hSource", "viewHistory");
		return "viewHistory";
	}
	
	@GetMapping("/filterHistory/{pid}/{pageNum}")
	public String filterHistory(@ModelAttribute("df") DateFilter df,@PathVariable("pid") Long prodId, Model model,@PathVariable(name = "pageNum") int pageNum) {

		Product product = productService.findProductById(prodId);
		
		Page<TransactionDetails> page = transactService.getFilteredProductHistory(pageNum,product,df);
		List<TransactionDetails> history = page.getContent();
		

		model.addAttribute("product", product);
		model.addAttribute("history", history);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("hSource", "filterHistory");
		return "viewHistory";
	}
	
	@GetMapping("/productform")
	public String addProduct(Model model) {
		Product product = new Product();
		
		List<Supplier> suppliers = productService.findAllSuppliers();

		model.addAttribute("product", product);
		model.addAttribute("suppliers", suppliers);
		return "productform";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors())
		{
			List<Supplier> suppliers = productService.findAllSuppliers();
			
			model.addAttribute("suppliers", suppliers);
			
			return "productform";
		}
		
		Brand brand=productService.findBrand(product.getBrand().getBrandName().toUpperCase());
		productService.setBrandtoProduct(product, brand);
		productService.setSuppliertoProduct(product);
				
		productService.saveProduct(product);
		
		return "forward:/Product/list/1";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		
		Product product = productService.findProductById(id);
		List<Supplier> suppliers = productService.findAllSuppliers();
		
		model.addAttribute("product", product);
		model.addAttribute("suppliers", suppliers);
		
		return "productform";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id) {
		
		Product product = productService.findProductById(id);
		productService.deleteProduct(product);
		
		return "forward:/Product/list/1";
	}
	
	@GetMapping("/generateReport")
	public String generateReport() throws IOException{

		productService.generateReport();
		
		return "sample";
	}

	
}

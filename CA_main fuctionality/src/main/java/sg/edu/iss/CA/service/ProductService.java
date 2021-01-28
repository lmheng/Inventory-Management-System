package sg.edu.iss.CA.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.CA.model.Brand;
import sg.edu.iss.CA.model.Product;
import sg.edu.iss.CA.model.Supplier;
import sg.edu.iss.CA.repo.BrandRepository;
import sg.edu.iss.CA.repo.InventoryRepository;
import sg.edu.iss.CA.repo.ProductRepository;
import sg.edu.iss.CA.repo.SupplierRepository;

@Service
@Transactional
public class ProductService implements ProductInterface {
	@Autowired
	ProductRepository prepo;

	@Autowired
	BrandRepository brepo;

	@Autowired
	SupplierRepository srepo;

	@Autowired
	InventoryRepository irepo;

	@Override
	public Page<Product> list(int pageNum) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

		Page<Product> page = prepo.findAll(pageable);

		return page;
	}

	@Override
	public Page<Product> filterList(int pageNum, Product product, int pageSize) {
		if (product.getProductName().equals("")) {
			product.setProductName(null);
		}

		if (product.getType().equals("SELECT")) {
			product.setType(null);
		}
		if (product.getCategory().equals("SELECT")) {
			product.setCategory(null);
		}
		if (product.getSubcategory().equals("SELECT")) {
			product.setSubcategory(null);
		}

		if (product.getColour().equals("SELECT")) {
			product.setColour(null);
		}
		if (product.getBrand().getBrandName().equals("SELECT")) {
			product.setBrand(null);
		} else {
			product.setBrand(findBrand(product.getBrand().getBrandName()));
		}
		Example<Product> example = Example.of(product);

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		Page<Product> page = prepo.findAll(example, pageable);
		List<Product> plist = page.getContent();
		System.out.println("Hello" + plist.size());
		return page;

	}

	@Override
	public Brand findBrand(String brand) {
		return brepo.findByBrandName(brand);
	}

	@Override
	public List<String> findBrandNames() {
		return brepo.getBrandNames();
	}

	@Override
	public List<String> getTypes() {
		return prepo.getTypes();
	}

	@Override
	public List<String> getCategories() {
		return prepo.getCategories();
	}

	@Override
	public List<String> getSubcategories() {
		return prepo.getSubcategories();
	}

	@Override
	public List<String> findColours() {
		return prepo.getColours();
	}

	@Override
	public Product findProductById(Long pid) {
		return prepo.findById(pid).get();
	}

	@Override
	public void saveProduct(Product product) {
		prepo.save(product);
	}

	@Override
	public List<Product> listAllProducts() {
		return prepo.findAll();
	}

	@Override
	public void deleteProduct(Product product) {
		prepo.delete(product);
	}

	@Override
	public Supplier findSupplierById(Long id) {
		return srepo.findById(id).get();
	}

	@Override
	public List<Supplier> findAllSuppliers() {
		return srepo.findAll();
	}

	@Override
	public void generateReport() throws IOException {
		BufferedWriter bw = null;
		List<Supplier> suppliers = srepo.findAll();
		
		DecimalFormat df = new DecimalFormat("#,###.00");
		
		try {
			File file = new File("report.dat");
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			for (Supplier s : suppliers) {
				List<Product> reorderList = prepo.findReorderProducts(s.getSupplierId());
				if(reorderList.size()!=0) {
				Iterator<Product> itr = reorderList.iterator();
				String title = "Inventory reorder report for Supplier " + s.getName();
				String end = "End of report for supplier " + s.getName();
				String headerLine = "+------------+----------------+---------+-----------------+-------------------+-------------+-----------+";
				String titleLine = "-".repeat((105 - title.length()) / 2) + title
						+ "-".repeat((105 - title.length()) / 2);
				String endLine = "-".repeat((105 - end.length()) / 2) + end + "-".repeat((105 - end.length()) / 2);
				bw.write(titleLine + "\n");
				bw.write(	
						"+------------+----------------+---------+-----------------+-------------------+-------------+-----------+\n");
				bw.write(
						"| PartNo     | Unit Price     | Qty     | Reorder Qty     | Min Order Qty     | Ord Qty     | Price     |\n");
				bw.write(
						"+------------+----------------+---------+-----------------+-------------------+-------------+-----------+\n");
				double sum=0.0;
				while (itr.hasNext()) {
					Product p = itr.next();
					int orderQuantity = (int) Math
							.ceil(1.0 * (p.getInventory().getReorderLevel() - p.getInventory().getUnits())
									/ p.getInventory().getMoq())
							* p.getInventory().getMoq();
					double price = orderQuantity * p.getInventory().getUnitPrice();
					sum+=price;
					bw.write("| "+String.format("%-10s",String.format("%06d",p.getProductId()))+" |");
					bw.write(String.format("%15s",p.getInventory().getUnitPrice())+" |");
					bw.write(String.format("%8s",p.getInventory().getUnits())+" |");
					bw.write(String.format("%16s",p.getInventory().getReorderLevel())+" |");
					bw.write(String.format("%18s",p.getInventory().getMoq())+" |");
					bw.write(String.format("%12s",orderQuantity)+" |");
					bw.write(String.format("%10s",df.format(price))+" |"+"\n");
					bw.write(
							"|------------|----------------|---------|-----------------|-------------------|-------------|-----------|\n");
				}
				bw.write(
						"|            |                |         |                 |                   |        Total|");
				bw.write(String.format("%10s",df.format(sum))+" |"+"\n");
				bw.write("+------------+----------------+---------+-----------------+-------------------+-------------+-----------+\n");
				bw.write(endLine + "\n");
				bw.newLine();
				bw.flush();
				}
			}

		}finally {
			if (bw != null)
				bw.close();
		}
	}

//	@Override
//	public void generateReportException() {
//		try {
//			generateReport();
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Override
	public void saveBrand(Brand brand) {
		brepo.save(brand);
	}

	@Override
	public void setBrandtoProduct(Product product, Brand brand) {
		if(brand!=null) {
			product.setBrand(brand);
		}
		else {
			Brand create= new Brand(product.getBrand().getBrandName().toUpperCase()); 
			saveBrand(create);
			product.setBrand(findBrand(create.getBrandName()));
		}
	}

	@Override
	public void setSuppliertoProduct(Product product) {
		Long idS = product.getSupplier().getSupplierId();
		Supplier supplier = findSupplierById(idS);
		product.setSupplier(supplier);
		
		
		if(product.getProductId() == null) {
			product.getInventory().setLastEmailSent(LocalDateTime.now());
			product.getInventory().setReorderFlag(true);
		}
		
	}
	
}

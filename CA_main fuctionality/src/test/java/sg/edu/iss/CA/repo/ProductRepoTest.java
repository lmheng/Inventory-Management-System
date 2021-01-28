package sg.edu.iss.CA.repo;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.CA.model.Inventory;
import sg.edu.iss.CA.model.Product;

@SpringBootTest
public class ProductRepoTest {

	@Autowired
	private ProductRepository prepo;
	
	@Autowired
	private BrandRepository brepo;
	
	@Autowired
	private SupplierRepository srepo;
	
	@Test
	void repoTest() {
		Inventory inv1 = new Inventory(1.0, 10, 1.85, 1.85, 2.76, "m01-01", 20, 64, true, LocalDateTime.now());
		prepo.save(new Product("Oil Filter(Spin-on)", "Toyota Genuine Oil Filter", "filter", "Maintenance", "m-1", "black", "90mm", brepo.findById((long) 1).get() , srepo.findById((long) 1).get(), inv1));
		Inventory inv2 = new Inventory(1.2, 20, 2.22, 2.22, 3.72, "m02-02", 5, 44, true, LocalDateTime.now());
		prepo.save(new Product("Oil Filter(Element)", "Yamaha Motorcycle Oil Filter", "filter", "Maintenance", "m-2", "black", "80mm", brepo.findById((long) 2).get() , srepo.findById((long) 2).get(), inv2));
		Inventory inv3 = new Inventory(0.5, 20, 0.93, 0.93, 1.90, "m03-03", 10, 32, true, LocalDateTime.now());
		prepo.save(new Product("Air Filter", "Air Filter K&N UNIVERSAL 35mm/42mm/48mm 3in1", "filter", "Maintenance", "m-3", "grey", "35mm*42mm*48mm", brepo.findById((long) 3).get() , srepo.findById((long) 3).get(), inv3));
		Inventory inv4 = new Inventory(0.5, 2, 0.93, 0.93, 1.90, "m04-01", 1, 1, false, LocalDateTime.now());
		prepo.save(new Product("Fuel Filter", "Fuel filter with flange Audi a6, A7, S6, S7", "filter", "Maintenance", "m-4", "blue", "90mm", brepo.findById((long) 4).get() , srepo.findById((long) 4).get(), inv4));
		Inventory inv5 = new Inventory(0.7, 10, 1.30, 1.30, 2.05, "m04-02", 5, 12, true, LocalDateTime.now());
		prepo.save(new Product("Cabin Air Filter", "Cabin Air Filter Carbon Fiber Mitsubishi Lancer/Inspira/Asx", "filter", "Maintenance", "m-4", "blue", "20.5*21.5*3cm", brepo.findById((long) 5).get() , srepo.findById((long) 5).get(),inv5));
		
		Inventory inv6 = new Inventory(0.9, 10, 1.67, 1.67, 2.61, "m05-01", 5, 14, true, LocalDateTime.now());
		prepo.save(new Product("Wiper Blade", "Bosch Clear Advantage FRONT Wiper Blade ", "wiper", "Maintenance", "m-5", "black", "0.5m", brepo.findById((long) 6).get() , srepo.findById((long) 6).get(), inv6));
		Inventory inv7 = new Inventory(1.4, 10, 2.59, 2.59, 3.42, "m06-01", 10, 23, true, LocalDateTime.now());
		prepo.save(new Product("Spark Plug", "High Performance Spark Plug Iridium Racing", "Spark plug", "Maintenance", "m-6", "silver", "14mm", brepo.findById((long) 7).get() , srepo.findById((long) 7).get(), inv7));
		Inventory inv8 = new Inventory(0.90, 10, 1.67, 1.67, 2.77, "m06-02", 10, 56, true, LocalDateTime.now());
		prepo.save(new Product("Spark Plug", "High Performance Spark Plug Iridium Power","Spark PLug", "Maintenance", "m-6", "silver", "14mm", brepo.findById((long) 8).get() , srepo.findById((long) 8).get(), inv8));
		Inventory inv9 = new Inventory(6.90, 10, 12.77, 12.77, 17.96, "m06-03", 10, 9, false, LocalDateTime.now());
		prepo.save(new Product("Spark Plug", "High Performance Spark Plug Iridium Tough", "Spark plug", "Maintenance", "m-6", "grey", "14mm", brepo.findById((long) 9).get() , srepo.findById((long) 9).get(), inv9));
		Inventory inv10 = new Inventory(16.20, 6, 29.97, 29.97, 40.28, "m07-01", 1, 2, false, LocalDateTime.now());
		prepo.save(new Product("Brake Pad Kit", "Brake Pads Toyota - Front Axle Brake", "Brake", "Maintenance", "m-7", "silver", "116.5mm", brepo.findById((long) 10).get() , srepo.findById((long) 10).get(), inv10));
		
		Inventory inv11 = new Inventory(5.20, 5, 9.62, 9.62, 12.11, "m09-01", 2, 7, true, LocalDateTime.now());
		prepo.save(new Product("Brake Shoe Kit", "Yamaha LC135 LC 135 135LC RX-Z 135 RXZ135 RXZ Brake Shoe Set Kit", "Brake", "Maintenance", "m-9", "black", "50mm", brepo.findById((long) 11).get() , srepo.findById((long) 11).get(), inv11));
		Inventory inv12 = new Inventory(3.50, 30, 6.48, 6.48, 9.04, "c01-01", 10, 33, true, LocalDateTime.now());
		prepo.save(new Product("Brake Fluid", "Mobil Brake Fluid DOT 4 500ml", "fluid", "Chemical", "c-1", "black", "50mm", brepo.findById((long) 12).get() , srepo.findById((long) 12).get(), inv12));
		Inventory inv13 = new Inventory(8.10, 10, 14.99, 14.99, 25.00, "o01-01", 10, 25, true, LocalDateTime.now());
		prepo.save(new Product("MTF", "HYUNDAI MTF & DCTF 70W Synthetic Oil", "Oil", "Oil", "o-1", "black", "6.60x22.60x11.60mm", brepo.findById((long) 13).get() , srepo.findById((long) 1).get(), inv13));
		Inventory inv14 = new Inventory(4.90, 10, 9.07, 9.07, 15.36, "o01-02", 10, 19, true, LocalDateTime.now());
		prepo.save(new Product("ATF/CVTF", "AMSOIL Signature Series Multi-Vehicle Synthetic ATF (1 Quart)", "Oil", "Oil", "o-2", "black", "6.60x22.60x11.60mm", brepo.findById((long) 14).get() , srepo.findById((long) 2).get(), inv14));
		Inventory inv15 = new Inventory(3.80, 10, 7.03, 7.03, 11.21, "e01-01", 1, 10, true, LocalDateTime.now());
		prepo.save(new Product("Engine Mount", "Left Engine Mounting Genio Estillo City Z 50820 SR3 NOK", "Engine", "Engine Parts", "e-1", "silver", "90mm", brepo.findById((long) 15).get() , srepo.findById((long) 3).get(), inv15));
		}
	}


package sg.edu.iss.CA.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.CA.model.Brand;
import sg.edu.iss.CA.model.Supplier;

@SpringBootTest
public class SupplierBrandRepoTest {

	@Autowired
	private SupplierRepository srepo;
	
	@Autowired
	private BrandRepository brepo;
	
	@Test
	void supplierCreation() {
		srepo.save(new Supplier("Chongqing Hoosen Technology Co. , Ltd","nanxi Industrial Park 174#, Jingkou Town, Shapingba District","main@chongqinghosen.com", "(424) 802-2688"));
		srepo.save(new Supplier("GUANGZHOU FREY AUTO PARTS CO., LTD , Ltd","No. 2901-2908, Building No. A1, Yihe Four Seasons Mansion, Dongguanzhuang Road, Tianhe District, Guangzhou City, Guangzhou, Guangdong, China 510405 ","general@guangzhoufa.com", "(809) 268-2281"));
		srepo.save(new Supplier("KETAI INDUSTRIES", "SHANGHAI SHI, 200040", "sales@ktc-china.com", "(414) 737-1647"));
		srepo.save(new Supplier("SHANDONG DONGDU AUTO PARTS CO., LTD","Zhai Li He Industry, Juxian, City, Rizhao, Shandong 276517","admin@shandongdd.com", "(604) 930-8833"));
		srepo.save(new Supplier("Windsor Sophiscated Engineering","766, Pace City II, Sector-37 Gurugram 122001(HR) India","customerservice@windsorauto.com", "(842) 630-2877"));
		srepo.save(new Supplier("Tata Genuine Parts","4th Floor, Ahura Centre 82 Mahakali Caves Road MIDC, Andheri East Mumbai - 400093","customercare@tatamotors.com", "(544) 694-5018"));
		srepo.save(new Supplier("Primal Enterprises Corporation","8 Miller St., Barangay Bungad, San Francisco Del Monte, Quezon City NCR Philippines 1105","sales-1@primal.com.ph", "(861) 291-9253"));
		srepo.save(new Supplier("Schaeffler (Singapore) Pte. Ltd","18 Tai Seng Street # 09-07 & 09-08 539775 Singapore","info.sg@schaeffler.com", "(921) 328-5676"));
		srepo.save(new Supplier("Denso International Asia Pte. Ltd.","51 Science Park Road, The Aries #01-19, Science Park II, Singapore 117586","contactus@denso.com", "(312) 476-9824"));
		srepo.save(new Supplier("Shell Eastern Petroleum (Pte) Ltd","The Metropolis Tower 1 9 North Buona Vista Drive, #06-01 Singapore 138588","Lubes-SG@shell.com", "(975) 402-7530"));
		srepo.save(new Supplier("ExxonMobil Chemical Asia Pacific","1 HarbourFront Place #06-00 HarbourFront Tower One Singapore 098633","chemical-ap@exxonmobil.com", "(570) 535-8076"));
		srepo.save(new Supplier("Advics Manufacturing (Thailand) Co Ltd","Pinthong Industrial Estate 3,219/9 M 6 Bowin Chon Buri; Eastern Thailand; Postal Code: 20230","enquiry@advics.com", "(787) 260-6695"));
	}
	
	@Test
	void brandCreation() {
		brepo.save(new Brand("Amsoil"));
		brepo.save(new Brand("Audi"));
		brepo.save(new Brand("Bosch"));
		brepo.save(new Brand("Denso"));
		brepo.save(new Brand("Ducati"));
		brepo.save(new Brand("Honda"));
		brepo.save(new Brand("Hyundai"));
		brepo.save(new Brand("K&N"));
		brepo.save(new Brand("Liqui Moly"));
		brepo.save(new Brand("Mitsubishi"));
		brepo.save(new Brand("Mobil"));
		brepo.save(new Brand("Nissan"));
		brepo.save(new Brand("Toyota"));
		brepo.save(new Brand("Uma Racing"));
		brepo.save(new Brand("Varta"));
		brepo.save(new Brand("Volkswagen"));
		brepo.save(new Brand("Yamaha"));
	}
	
}

package sg.edu.iss.CA.repo;

import sg.edu.iss.CA.model.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}

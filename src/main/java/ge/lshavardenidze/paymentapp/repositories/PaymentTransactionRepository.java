package ge.lshavardenidze.paymentapp.repositories;

import ge.lshavardenidze.paymentapp.repositories.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    List<PaymentTransaction> findAllBySendOrderByCommission(boolean send);

}

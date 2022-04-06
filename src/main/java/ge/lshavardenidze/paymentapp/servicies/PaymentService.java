package ge.lshavardenidze.paymentapp.servicies;

import ge.lshavardenidze.paymentapp.dtos.PaymentTransactionDTO;
import ge.lshavardenidze.paymentapp.exception.PaymentException;

import java.util.List;

public interface PaymentService {

    PaymentTransactionDTO savePayment(PaymentTransactionDTO payment) throws PaymentException;

    List<PaymentTransactionDTO> getUnsendPayments();

}

package ge.lshavardenidze.paymentapp.servicies;

import ge.lshavardenidze.paymentapp.dtos.PaymentTransactionDTO;
import ge.lshavardenidze.paymentapp.exception.PaymentException;
import ge.lshavardenidze.paymentapp.repositories.PaymentTransactionRepository;
import ge.lshavardenidze.paymentapp.repositories.model.PaymentTransaction;
import ge.lshavardenidze.paymentapp.repositories.model.PaymentType;
import ge.lshavardenidze.paymentapp.servicies.modelmapper.DTOMapperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Transactional
@Service
@Slf4j
public class PaymentServiceImol implements PaymentService {

    @Autowired
    private DTOMapperService mapperService;
    @Autowired
    private PaymentTransactionRepository repository;

    @Override
    public PaymentTransactionDTO savePayment(PaymentTransactionDTO payment) throws PaymentException {
        checkPayment(payment);
        return mapperService.mapObject(repository.save(mapperService.mapObject(payment, PaymentTransaction.class)), PaymentTransactionDTO.class);
    }

    @Override
    public List<PaymentTransactionDTO> getUnsendPayments() {
        return mapperService.mapObjects(repository.findAllBySendOrderByCommission(false), PaymentTransactionDTO.class);
    }

    private void checkPayment(PaymentTransactionDTO payment) throws PaymentException {
        if (ObjectUtils.isEmpty(payment)) {
            throw new PaymentException("Fill necessary fields");
        } else if (payment.getPaymentType().equals(PaymentType.MOBILE) && ObjectUtils.isEmpty(payment.getAmount())) {
            throw new PaymentException("Fill necessary fields");
        } else if ((payment.getPaymentType().equals(PaymentType.CHARITY) || payment.getPaymentType().equals(PaymentType.UTILITY_TAX)) && (ObjectUtils.isEmpty(payment.getAmount()) || ObjectUtils.isEmpty(payment.getIdentityNumber()))) {
            throw new PaymentException("Amount is required");
        } else if ((payment.getPaymentType().equals(PaymentType.FINANCIAL_SERVICES)) && (ObjectUtils.isEmpty(payment.getAmount()) || ObjectUtils.isEmpty(payment.getIdentityNumber()) || ObjectUtils.isEmpty(payment.getAccountNumber()))) {
            throw new PaymentException("Amount is required");
        }
    }
}

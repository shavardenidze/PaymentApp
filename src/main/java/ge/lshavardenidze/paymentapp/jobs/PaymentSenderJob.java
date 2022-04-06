package ge.lshavardenidze.paymentapp.jobs;

import ge.lshavardenidze.paymentapp.dtos.PaymentTransactionDTO;
import ge.lshavardenidze.paymentapp.servicies.PaymentService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.openjdk.jol.vm.VM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class PaymentSenderJob {

    @Autowired
    private PaymentService paymentService;

    @Scheduled(cron = "${crons.payment.sender.time}")
    @SchedulerLock(name = "PaymentSenderJob", lockAtLeastFor = "PT1M", lockAtMostFor = "PT14M")
    public void kafkaEventsJob() {
        log.info("Starting Payment Sender job");
        try {
            List<PaymentTransactionDTO> unsendPayments = paymentService.getUnsendPayments();
            AtomicLong dataSize = new AtomicLong();
            var ref = new Object() {
                List<PaymentTransactionDTO> dataToSend = new ArrayList<>();
            };
            unsendPayments.forEach(payment -> {
                if (dataSize.get() > 1000) {
                    return;
                }
                dataSize.addAndGet(VM.current().sizeOf(payment));
                if (dataSize.get() >= 1000) {
                    System.out.println("sending data : " + ref.dataToSend);
                    ref.dataToSend = new ArrayList<>();
                }
                ref.dataToSend.add(payment);
            });
        } catch (Exception e) {
            log.error("Kafka Error catched in kafkaEventsJob -> {}", e.getMessage());
        }
        log.info("Finished Payment Sender job");
    }

}

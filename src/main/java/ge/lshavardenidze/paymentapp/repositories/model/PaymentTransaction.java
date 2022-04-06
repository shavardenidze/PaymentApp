package ge.lshavardenidze.paymentapp.repositories.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;
    private String mobileNumber;
    private BigDecimal amount;
    private BigDecimal commission;
    private String identityNumber;
    private String accountNumber;
    private boolean send;

}

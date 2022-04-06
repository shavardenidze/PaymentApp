package ge.lshavardenidze.paymentapp.dtos;

import ge.lshavardenidze.paymentapp.repositories.model.PaymentType;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentTransactionDTO {

    private Long id;
    private PaymentType paymentType;
    @Pattern(regexp = "^5[0-9]{8}", message = "length must be 9 and must start with 5")
    private String mobileNumber;
    private BigDecimal amount;
    private BigDecimal commission;
    @Pattern(regexp = "^[0-9]{11}", message = "length must be 11")
    private String identityNumber;
    @Pattern(regexp = "^GE[0-9]{2}[A-Z]{2}[0-9]{16}", message = "accountNumber must be in 'GE00XX0000000000000000' format")
    private String accountNumber;
    private boolean send;

}

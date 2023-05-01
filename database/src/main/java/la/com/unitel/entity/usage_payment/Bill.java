package la.com.unitel.entity.usage_payment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import la.com.unitel.entity.constant.BillStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "BILL")
public class Bill implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(unique = true, nullable = false)
    private String usageId;

    @Column(nullable = false)
    private String contractId;

    @Column(nullable = false)
    private String policyId;
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    @Column(nullable = false)
    private String cashier;

    private BigDecimal totalAmount;
    private BigDecimal usageCharge;
    private BigDecimal serviceCharge;
    private BigDecimal tax;
    private BigDecimal lateFee;
    private LocalDateTime paymentDeadline;
    private Integer overDate;
    private String createdBy;
    private String updatedBy;
    private String paidBy;
    private String remark;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime paidAt;

    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastUpdatedAt;
}

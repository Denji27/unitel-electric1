package la.com.unitel.entity.usage_payment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import la.com.unitel.entity.constant.ConsumptionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CONSUMPTION")
public class Consumption implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(nullable = false)
    private String contractId;

//    @Column(nullable = false)
    private String meterCode;

//    @Column(nullable = false)
    private Integer readUnit;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private String readBy;
    private String updatedBy;
    private String remark;

//    @Column(nullable = false)
    private String imageId;

    @Enumerated(EnumType.STRING)
    private ConsumptionStatus status;

    private String latitude;
    private String longitude;
    private Integer oldMeterUnit;
    private Integer usageConsumption;
    private Boolean isRollOver;
    private Integer meterMaxUnit;
    private Boolean isMeterReplace;
    private Integer stopUnit;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime readAt;

    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastUpdatedAt;
}

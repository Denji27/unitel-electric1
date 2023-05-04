package la.com.unitel.entity.usage_payment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import la.com.unitel.entity.constant.Gender;
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
@Table(name = "CONTRACT")
public class Contract implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;  //waiting demo data

    @Column(unique = true, nullable = false)
    private String name;

    /*@Column(nullable = false)
    private String accountId;*/

    @Column(unique = true, nullable = false)
    private String phoneNumber;
    //TODO check phone number exist when create contract

    @Column(nullable = false)
    private String districtId;

    @Column(nullable = false)
    private String provinceId;

    @Column(nullable = false)
    private String contractType;

    /*@Column(unique = true, nullable = false)
    private String meterCode;*/

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String avatarId;
    private String latitude;
    private String longitude;
    private Boolean isActive;
    private String address;
    private String remark;
    private String createdBy;
    private String updatedBy;

    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastUpdatedAt;
}

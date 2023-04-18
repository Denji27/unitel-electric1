package la.com.unitel.entity.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderContractId implements Serializable {
    String readerId;
    String contractId;
}

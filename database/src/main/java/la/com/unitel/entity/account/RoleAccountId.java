package la.com.unitel.entity.account;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Embeddable
public class RoleAccountId implements Serializable {
    String roleCode;
    String accountId;
}

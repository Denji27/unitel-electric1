package la.com.unitel;

import org.keycloak.representations.idm.MappingsRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

/**
 * @author : Tungct
 * @since : 4/29/2023, Sat
 **/
public interface KeycloakUtil {
    UserRepresentation createUser(String username, String password, List<String> roleList);
    UserRepresentation createUser(String username, String password, List<String> roleList, String phoneNumber);
    UserRepresentation createUser(String username, String password, String phoneNumber, String district, String contractType);
    UserRepresentation createUser(String username, String password, List<String> roleList, String phoneNumber, String district, String contractType);

    boolean updateUser(String userId, String newPassword);
    boolean updateUser(String userId, String newPassword, String phoneNumber, String district, String contractType);
    boolean updateRoleUser(String userId, List<String> roleList);

    UserRepresentation getUserProfile(String id);
    UserRepresentation findByUsername(String username);
    MappingsRepresentation findUserRoles(String userId);
}

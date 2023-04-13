package la.com.unitel.business;

import la.com.unitel.Util;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserProfileResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import javax.ws.rs.core.Response;
import java.util.*;

/**
 * @author : Tungct
 * @since : 4/13/2023, Thu
 **/
@Service
@Slf4j
public class KeycloakUtil {

    private final Keycloak keycloak;
    private final String realm;
    private final Util util;
    private final String clientId;

    public KeycloakUtil(Environment environment, Keycloak keycloak, Util util) {
        this.keycloak = keycloak;
        this.realm = environment.getProperty("keycloak.realm");
//        this.clientName = environment.getProperty("keycloak.resource");
        this.clientId = keycloak.realm(realm).clients().findByClientId(environment.getProperty("keycloak.resource")).get(0).getId();
        this.util = util;
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    private boolean addRole(String username, String roleName) {
        try {
            log.info("Add role {} for username {}", roleName, username);
            UserResource user = keycloak.realm(realm).users().get(keycloak.realm(realm).users().search(username).get(0).getId());

            List<RoleRepresentation> roleToAdd = new LinkedList<>();
            roleToAdd.add(keycloak.realm(realm).clients().get(clientId).roles().get(roleName).toRepresentation());

            user.roles().clientLevel(clientId).add(roleToAdd);
            return true;
        } catch (Exception e) {
            log.error("Error when add role {} for username {}", roleName, username);
            log.error("Due to: ", e);
            return false;
        }
    }

    public UserRepresentation createUser(String username, String password, String roleName) {
        return this.createUser(username, password, roleName, null);
    }

    public UserRepresentation createUser(String username, String password, String roleName, String phoneNumber) {
        UsersResource usersResource = keycloak.realm(realm).users();

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);

        UserRepresentation newUser = new UserRepresentation();
        newUser.setCredentials(Collections.singletonList(credentialRepresentation));
        newUser.setUsername(username);
        newUser.setEnabled(true);
        newUser.setEmailVerified(false);

        if (phoneNumber != null) {
//            add attribute
            HashMap<String, List<String>> map = new HashMap<>();
            map.put("phoneNumber", new ArrayList<>() {{
                add(phoneNumber);
            }});
            newUser.setAttributes(map);
        }

/*//        attribute
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> companyCode = new ArrayList<>();
        companyCode.add(company.getCode());
        List<String> adminSim = new ArrayList<>();
        adminSim.add(company.getAdminSim());

        map.put("companyCode",companyCode);
        map.put("adminSim",adminSim);
        map.put("mobile_number",adminSim);
        newUser.setAttributes(map);*/

        log.info(">>>[KEYCLOAK] create user request: {}", util.toJson(newUser));
        try {
            Response response = usersResource.create(newUser);
            log.info("<<<[KEYCLOAK] create user response: {}, family {}", response.getStatusInfo().getReasonPhrase(), response.getStatusInfo().getFamily());
            log.info("Create User Response Status : {}", response.getStatus());
            if (response.getStatus() == 201) {
                List<UserRepresentation> search = keycloak.realm(realm).users().search(newUser.getUsername());
                newUser.setId(search.get(0).getId());
                if (!addRole(newUser.getUsername(), roleName)) {
                    Response deleteUserResponse = usersResource.delete(newUser.getId());
                    log.info("<<<[KEYCLOAK] delete user response: {}", deleteUserResponse.getStatusInfo().getReasonPhrase());
                    log.info("Delete User Response Status : {}", deleteUserResponse.getStatus());
                    return null;
                }
                return newUser;
            } else {
                log.error("Keycloak create user failed");
                return null;
            }
        } catch (HttpStatusCodeException exception) {
            log.error("Response of calling API in not 200 status code of " + username + " is " +
                    ResponseEntity.status(exception.getRawStatusCode()).body(exception.getResponseBodyAsString()));
        } catch (RestClientException ce) {
            log.error("Rest Client Exception - Timeout");
            log.error("", ce);
        } catch (Exception e) {
            log.error("Exception in user creation in Keycloak: ", e);
        }
        return null;
    }

    public UserRepresentation getUserProfile(String id) {
        return keycloak.realm(realm).users().get(id).toRepresentation();
    }

    /*public Response deleteUser(String id) {
        return keycloak.realm(realm).users().delete(id);
    }*/

}

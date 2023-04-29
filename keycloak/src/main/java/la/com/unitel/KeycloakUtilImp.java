package la.com.unitel;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.MappingsRepresentation;
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
 * @since : 4/29/2023, Sat
 **/
@Service
@Slf4j
public class KeycloakUtilImp implements KeycloakUtil {
    private final Keycloak keycloak;
    private final String realm;
    private final Util util;
    private final Environment environment;
    private final String clientId;
    private final String clientName;

    public KeycloakUtilImp(Environment environment, Keycloak keycloak, Util util) {
        this.keycloak = keycloak;
        this.environment = environment;
        this.util = util;
        this.realm = environment.getProperty("keycloak.realm");
        this.clientName = environment.getProperty("keycloak.resource");
        this.clientId = keycloak.realm(realm).clients().findByClientId(this.clientName).get(0).getId();
        log.info("Client id: {}, client name: {}", this.clientId, this.clientName);
    }

    @Override
    public UserRepresentation createUser(String username, String password, List<String> roleList) {
        return this.createUser(username, password, roleList, null, null, null);
    }

    @Override
    public UserRepresentation createUser(String username, String password, List<String> roleList, String phoneNumber) {
        return this.createUser(username, password, roleList, phoneNumber, null, null);
    }

    @Override
    public UserRepresentation createUser(String username, String password, String phoneNumber, String district, String contractType) {
        return this.createUser(username, password, null, phoneNumber, district, contractType);
    }

    @Override
    public UserRepresentation createUser(String username, String password, List<String> roleList, String phoneNumber, String district, String contractType) {
        UsersResource usersResource = keycloak.realm(realm).users();

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);

        UserRepresentation newUser = new UserRepresentation();
        newUser.setCredentials(Collections.singletonList(credentialRepresentation));
        newUser.setUsername(username);
        newUser.setEnabled(true);
        newUser.setEmailVerified(false);

        HashMap<String, List<String>> map = new HashMap<>();

        if (phoneNumber != null) {
//            add attribute
            map.put("phoneNumber", new ArrayList<>() {{
                add(phoneNumber);
            }});
        }
        if (district != null) {
            map.put("district", new ArrayList<>() {{
                add(district);
            }});
        }

        if (contractType != null) {
            map.put("contractType", new ArrayList<>() {{
                add(contractType);
            }});
        }

        newUser.setAttributes(map);

        log.info(">>>[KEYCLOAK] create user request: {}", util.toJson(newUser));
        try {
            Response response = usersResource.create(newUser);
            log.info("<<<[KEYCLOAK] create user response: {}, family {}", response.getStatusInfo().getReasonPhrase(), response.getStatusInfo().getFamily());
            log.info("Create User Response Status : {}", response.getStatus());
            if (response.getStatus() == 201) {
                UserRepresentation userRepre = this.findByUsername(newUser.getUsername());
                if (userRepre == null) {
                    log.error("Username {} created, but now not found. Unexpected Error", newUser.getUsername());
                    return null;
                }
                newUser.setId(userRepre.getId());
                if (roleList != null) {
                    if (!addRole(newUser.getId(), roleList)) {
                        Response deleteUserResponse = usersResource.delete(newUser.getId());
                        log.info("<<<[KEYCLOAK] delete user response: {}", deleteUserResponse.getStatusInfo().getReasonPhrase());
                        log.info("Delete User Response Status : {}", deleteUserResponse.getStatus());
                        return null;
                    }
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

    @Override
    public boolean updateUser(String userId, String newPassword) {
        return this.updateUser(userId, newPassword, null, null, null);
    }

    @Override
    public boolean updateUser(String userId, String newPassword, String phoneNumber, String district, String contractType) {
        UserResource userResource = keycloak.realm(realm).users().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();

        if (newPassword != null) {
            CredentialRepresentation credentialRepresentation = createPasswordCredentials(newPassword);
            userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        }

        HashMap<String, List<String>> map = new HashMap<>();
        if (phoneNumber != null) {
//            add attribute
            map.put("phoneNumber", new ArrayList<>() {{
                add(phoneNumber);
            }});
        }
        if (district != null) {
            map.put("district", new ArrayList<>() {{
                add(district);
            }});
        }

        if (contractType != null) {
            map.put("contractType", new ArrayList<>() {{
                add(contractType);
            }});
        }

        userRepresentation.setAttributes(map);

        try {
            userResource.update(userRepresentation);
            return true;
        } catch (HttpStatusCodeException exception) {
            log.error("Response of calling API in not 200 status code of update keycloak user id " + userId + " is " +
                    ResponseEntity.status(exception.getRawStatusCode()).body(exception.getResponseBodyAsString()));
        } catch (RestClientException ce) {
            log.error("Rest Client Exception - Timeout");
            log.error("", ce);
        } catch (Exception e) {
            log.error("Exception in user creation in Keycloak: ", e);
        }
        return false;
    }

    @Override
    public boolean updateRoleUser(String userId, List<String> roleList) {
        return this.addRole(userId, roleList);
    }

    @Override
    public UserRepresentation getUserProfile(String id) {
        return keycloak.realm(realm).users().get(id).toRepresentation();
    }

    @Override
    public UserRepresentation findByUsername(String username) {
        return keycloak.realm(realm).users().search(username).parallelStream()
                .filter(userRepre -> userRepre.getUsername().equals(username)).findFirst().orElse(null);
    }

    @Override
    public MappingsRepresentation findUserRoles(String userId) {
        return keycloak.realm(realm).users().get(userId).roles().getAll();
    }


    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    private boolean addRole(String userId, List<String> roleList) {
        try {
            log.info("Add role {} for user id {}", Arrays.toString(roleList.toArray()), userId);
            UserResource user = keycloak.realm(realm).users().get(userId);

            List<RoleRepresentation> roleToRemove = keycloak.realm(realm).users().get(userId).roles().clientLevel(this.clientId).listAll();
            user.roles().clientLevel(clientId).remove(roleToRemove);
            log.info("List role removed: {}", Arrays.toString(roleToRemove.toArray()));

            List<RoleRepresentation> roleToAdd = new LinkedList<>();
            RolesResource rolesResource = keycloak.realm(realm).clients().get(clientId).roles();
            for (String roleName : roleList) {
                roleToAdd.add(rolesResource.get(roleName).toRepresentation());
            }

            user.roles().clientLevel(clientId).add(roleToAdd);
            return true;
        } catch (Exception e) {
            log.error("Error when add role {} for user id {}", Arrays.toString(roleList.toArray()), userId);
            log.error("Due to: ", e);
            return false;
        }
    }
}

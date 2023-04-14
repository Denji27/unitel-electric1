package la.com.unitel.controller;

import la.com.unitel.Util;
import la.com.unitel.business.KeycloakUtil;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.MappingsRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Tungct
 * @since : 4/13/2023, Thu
 **/
@EnableScheduling
@RestController
@Slf4j
public class Controller {
    @Autowired
    private KeycloakUtil keycloakUtil;
    @Autowired
    private Util util;

    @Scheduled(fixedDelay = 900000000000000L)
    public void test() {
        /*List<String> roleList = new ArrayList<>();
        roleList.add("unitel");
        roleList.add("ministry");

        UserRepresentation unitel = keycloakUtil.createUser("tung-uni-test-2", "tung-uni-test-2", roleList, "959664006310");
        log.info("User: {}", util.toJsonPretty(unitel));

        UserRepresentation profile = keycloakUtil.getUserProfile("21f96f66-9399-464a-8f05-59b6bb537497");
        log.info("Profile: {}", util.toJsonPretty(profile));
        log.info("Profile client roles: {}", util.toJsonPretty(profile.getClientRoles()));


        Response response = keycloakUtil.deleteUser("31d51e24-5e85-4a15-a1f8-b464ed79dedd");
        log.info("Response: {}", response.toString());*/

        MappingsRepresentation mappingsRepresentation = keycloakUtil.findUserRoles("21f96f66-9399-464a-8f05-59b6bb537497");
        log.info("mappingsRepresentation: {}", util.toJsonPretty(mappingsRepresentation));
    }
}

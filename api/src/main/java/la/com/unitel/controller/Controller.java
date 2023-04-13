package la.com.unitel.controller;

import la.com.unitel.Util;
import la.com.unitel.business.KeycloakUtil;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

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

    /*@Scheduled(fixedDelay = 900000000000000L)
    public void test() {
        UserRepresentation unitel = keycloakUtil.createUser("tung-uni", "tung-uni", "unitel", "959664006310");
        log.info("User: {}", util.toJsonPretty(unitel));

        *//*Response response = keycloakUtil.deleteUser("31d51e24-5e85-4a15-a1f8-b464ed79dedd");
        log.info("Response: {}", response.toString());*//*
    }*/
}

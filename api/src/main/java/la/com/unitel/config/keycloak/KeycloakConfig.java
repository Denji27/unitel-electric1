package la.com.unitel.config.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Slf4j
public class KeycloakConfig {
    final Environment env;

    public KeycloakConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @Qualifier("Keycloak")
    public Keycloak getInstance(){
        log.info("Realm: {}", env.getProperty("keycloak.realm"));
        log.info("url: {}", env.getProperty("keycloak.auth-server-url"));
        log.info("Admin username: {}", env.getProperty("admin_kc_username"));
        log.info("Admin pwd: {}", env.getProperty("admin_kc_password"));
        return KeycloakBuilder.builder()
                .realm(env.getProperty("keycloak.realm"))
                .serverUrl(env.getProperty("keycloak.auth-server-url"))
                .clientSecret(env.getProperty("keycloak.credentials.secret"))
                .clientId(env.getProperty("keycloak.resource"))
                .username(env.getProperty("admin_kc_username"))
                .password(env.getProperty("admin_kc_password"))
                .build();
    }
}

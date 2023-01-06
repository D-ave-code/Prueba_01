package com.distribuida.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;


@ApplicationScoped
public class DbConfig {

    private HikariDataSource dataSource;
    @Inject
    @ConfigProperty(name="db.user")
    String dbUser;
    @Inject
    @ConfigProperty(name="db.password")
    String dbPassword;
    @Inject
    @ConfigProperty(name="db.url")
    String dbUrl;

    private Jdbi jdbi;

    @PostConstruct
    public  void init(){
//pool de conexiones y crear coneccion a DB con JDBI3
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setMaximumPoolSize(7);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        dataSource = new HikariDataSource(config);

        jdbi = Jdbi.create(dataSource);

}
@ApplicationScoped
@Produces
public Handle test(){
    return jdbi.open();
}
}

package com.parquesoft.ecommerce.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * DataSource configuration for Render environment.
 * Uses Spring's native DATABASE_URL support with proper parsing.
 */
@Configuration
public class DataSourceConfig {

    /**
     * Creates a DataSource from Render's DATABASE_URL environment variable.
     * This bean is only created when DATABASE_URL is set (Render environment).
     */
    @Bean
    @Primary
    @ConditionalOnProperty(name = "DATABASE_URL")
    public DataSource renderDataSource() throws URISyntaxException {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl == null || databaseUrl.isEmpty()) {
            throw new IllegalStateException("DATABASE_URL environment variable is not set");
        }

        // Parse the DATABASE_URL (format: postgresql://user:password@host:port/database)
        URI dbUri;
        try {
            dbUri = new URI(databaseUrl);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid DATABASE_URL format: " + databaseUrl, e);
        }
        
        String host = dbUri.getHost();
        int port = dbUri.getPort() != -1 ? dbUri.getPort() : 5432;
        String database = dbUri.getPath().substring(1); // Remove leading /
        
        // Parse user info without decoding - credentials are already in plain text
        String userInfo = dbUri.getUserInfo();
        if (userInfo == null || !userInfo.contains(":")) {
            throw new IllegalStateException("DATABASE_URL must contain user:password");
        }
        
        String[] credentials = userInfo.split(":", 2);
        String username = credentials[0];
        String password = credentials[1];
        
        String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        
        System.out.println("🔗 DataSource Config:");
        System.out.println("   URL: " + jdbcUrl);
        System.out.println("   User: " + username);
        System.out.println("   Password length: " + password.length() + " chars");
        
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }
}


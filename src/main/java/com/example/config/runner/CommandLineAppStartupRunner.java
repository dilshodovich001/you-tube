package com.example.config.runner;

import com.example.repository.ProfileRepository;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private DataSource dataSource;
    @Override
    public void run(String... args) throws Exception {
        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
    }
}

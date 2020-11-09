package ru.kpfu.group903.safiullin.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.kpfu.group903.safiullin.repositories.*;
import ru.kpfu.group903.safiullin.services.BankService;
import ru.kpfu.group903.safiullin.services.CategoryService;
import ru.kpfu.group903.safiullin.services.RecordService;
import ru.kpfu.group903.safiullin.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class JdbcListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("/WEB-INF/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver.classname"));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        servletContext.setAttribute("dataSource", dataSource);

        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        UserService userService = new UserService(userRepository);
        servletContext.setAttribute("userService", userService);

        RecordRepository recordRepository = new RecordRepositoryJdbcImpl(dataSource);
        RecordService recordService = new RecordService(recordRepository);
        servletContext.setAttribute("recordsService", recordService);

        BankRepository bankRepository = new BankRepositoryJdbcImpl(dataSource);
        BankService bankService = new BankService(bankRepository);
        servletContext.setAttribute("bankService", bankService);

        CategoryRepository categoryRepository = new CategoryRepositoryJdbcImpl(dataSource);
        CategoryService categoryService = new CategoryService(categoryRepository);
        servletContext.setAttribute("categoryService", categoryService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}


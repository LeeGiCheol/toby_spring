package me.gicheol.test;

import com.mysql.jdbc.Driver;
import me.gicheol.dao.UserDao;
import me.gicheol.dao.UserDaoJdbc;
import me.gicheol.service.DummyMailSender;
import me.gicheol.service.UserService;
import me.gicheol.test.UserServiceTest.TestUserService;
import me.gicheol.service.UserServiceImpl;
import me.gicheol.sql.EmbeddedDbSqlRegistry;
import me.gicheol.sql.OxmSqlService;
import me.gicheol.sql.SqlRegistry;
import me.gicheol.sql.SqlService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.mail.MailSender;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
@EnableTransactionManagement
public class TestApplicationContext {

    /**
     * DB연결과 트랜잭션
     */
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/test_toby_spring?serverTimezone=UTC");
        dataSource.setUsername("toby_spring");
        dataSource.setPassword("toby_spring");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());

        return transactionManager;
    }

    /**
     * 애플리케이션 로직 & 테스트용 빈
     */
    @Bean
    public UserDao userDao() {
        return new UserDaoJdbc();
    }

    @Bean
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao());
        userService.setMailSender(mailSender());

        return userService;
    }

    @Bean
    public UserService testUserService() {
        TestUserService testUserService = new TestUserService();
        testUserService.setUserDao(userDao());
        testUserService.setMailSender(mailSender());

        return testUserService;
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

    /**
     * SQL서비스
     */
    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlRegistry(sqlRegistry());

        return sqlService;
    }

    @Bean
    public SqlRegistry sqlRegistry() {
        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
        sqlRegistry.setDataSource(embeddedDatabase());

        return sqlRegistry;
    }

    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("me.gicheol.sql.jaxb");

        return marshaller;
    }

    @Bean
    public DataSource embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setName("embeddedDatabase")
                .setType(HSQL)
                .addScript("classpath:me/gicheol/sql/sql/sqlRegistrySchema.sql")
            .build();
    }

}

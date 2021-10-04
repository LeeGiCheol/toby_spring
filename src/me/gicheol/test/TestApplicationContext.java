package me.gicheol.test;

import com.mysql.jdbc.Driver;
import me.gicheol.dao.UserDao;
import me.gicheol.dao.UserDaoJdbc;
import me.gicheol.sql.SqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ImportResource("classpath:/test-applicationContext.xml")
public class TestApplicationContext {

    @Autowired
    private SqlService sqlService;

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

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        userDao.setSqlService(this.sqlService);

        return userDao;
    }

}

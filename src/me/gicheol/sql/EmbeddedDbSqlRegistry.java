package me.gicheol.sql;

import me.gicheol.exception.SqlNotFoundException;
import me.gicheol.exception.SqlUpdateFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Map;

public class EmbeddedDbSqlRegistry implements UpdatableSqlRegistry {

    SimpleJdbcTemplate jdbc;
    TransactionTemplate transactionTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbc = new SimpleJdbcTemplate(dataSource);
        transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
    }


    @Override
    public String findSql(String key) throws SqlNotFoundException {
        try {
            return jdbc.queryForObject("SELECT sql_ FROM sqlmap WHERE key_ = ?", String.class, key);
        } catch (EmptyResultDataAccessException e) {
            throw new SqlNotFoundException("SQL을 찾을 수 없습니다.");
        }
    }

    @Override
    public void registerSql(String key, String sql) {
        jdbc.update("INSERT INTO sqlmap(key_, sql_) VALUES(?, ?)", key, sql);
    }

    @Override
    public void updateSql(String key, String sql) throws SqlUpdateFailureException {
        int affected = jdbc.update("UPDATE sqlmap SET sql_ = ? WHERE key_ = ?", sql, key);

        if (affected == 0) {
            throw new SqlUpdateFailureException("SQL을 찾을 수 없습니다.");
        }
    }

    @Override
    public void updateSql(final Map<String, String> sqlmap) throws SqlUpdateFailureException {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                for (Map.Entry<String, String> entry : sqlmap.entrySet()) {
                    updateSql(entry.getKey(), entry.getValue());
                }
            }
        });
    }
}

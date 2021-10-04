package me.gicheol.learningtest.jdk.updatable.embeddeddb;

import me.gicheol.exception.SqlUpdateFailureException;
import me.gicheol.learningtest.jdk.updatable.AbstractUpdatableSqlRegistryTest;
import me.gicheol.sql.EmbeddedDbSqlRegistry;
import me.gicheol.sql.UpdatableSqlRegistry;
import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {

    EmbeddedDatabase db;

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:/me/gicheol/sql/sql/sqlRegistrySchema.sql")
            .build();

        EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlRegistry.setDataSource(db);

        return embeddedDbSqlRegistry;
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

    @Test
    public void transactionalUpdate() {
        checkFind("SQL1", "SQL2", "SQL3");

        Map<String, String> sqlmap = new HashMap<String, String>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY9999", "Modified9999");

        try {
            sqlRegistry.updateSql(sqlmap);
            fail();
        } catch (SqlUpdateFailureException e) {}

        checkFind("SQL1", "SQL2", "SQL3");
    }

}

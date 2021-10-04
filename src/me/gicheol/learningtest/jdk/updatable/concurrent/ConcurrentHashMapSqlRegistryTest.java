package me.gicheol.learningtest.jdk.updatable.concurrent;

import me.gicheol.learningtest.jdk.updatable.AbstractUpdatableSqlRegistryTest;
import me.gicheol.sql.ConcurrentHashMapSqlRegistry;
import me.gicheol.sql.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();
    }

}

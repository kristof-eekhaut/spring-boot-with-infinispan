package be.eekhaut.kristof.springbootwithinfinispan;

import infinispan.autoconfigure.embedded.InfinispanCacheConfigurer;
import infinispan.autoconfigure.embedded.InfinispanGlobalConfigurer;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.persistence.jdbc.DatabaseType;
import org.infinispan.persistence.jdbc.configuration.JdbcStringBasedStoreConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfinispanCacheConfiguration {

    public static final String CLUSTER = "GREETING_CACHE_CLUSTER";
    public static final String CACHE_NAME = "greetings";

    @Bean
    public InfinispanCacheConfigurer cacheConfigurer() {
        return cacheManager -> {
            final org.infinispan.configuration.cache.Configuration testCache =
                    new ConfigurationBuilder()
                            .clustering().cacheMode(CacheMode.DIST_SYNC)
                            .persistence().addStore(JdbcStringBasedStoreConfigurationBuilder.class)
                                .fetchPersistentState(false)
                                .ignoreModifications(false)
                                .purgeOnStartup(false)
                                .shared(true)
                                .dialect(DatabaseType.POSTGRES)
                                .table()
                                    .createOnStart(true)
                                    .tableNamePrefix("GREETINGS_CACHE")
                                    .idColumnName("ID").idColumnType("VARCHAR(255)")
                                    .dataColumnName("DATA").dataColumnType("BINARY")
                                    .timestampColumnName("TIMESTAMP").timestampColumnType("BIGINT")
                                .connectionPool()
                                    .connectionUrl("jdbc:postgresql://infinispan-postgresql:5432/greetings")
                                    .username("infinispan").password("infinispan").driverClass(org.postgresql.Driver.class)
                            .eviction().size(3L).strategy(EvictionStrategy.LRU)
                            .build();

            cacheManager.defineConfiguration(CACHE_NAME, testCache);
        };
    }

    @Bean
    public InfinispanGlobalConfigurer globalConfigurer() {
        return () -> {
            final GlobalConfiguration globalConfiguration = new GlobalConfigurationBuilder()
                    .transport().defaultTransport().clusterName(CLUSTER)
                    .build();
            return globalConfiguration;
        };
    }
}

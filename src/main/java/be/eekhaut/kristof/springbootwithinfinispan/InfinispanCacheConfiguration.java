package be.eekhaut.kristof.springbootwithinfinispan;

import infinispan.autoconfigure.embedded.InfinispanCacheConfigurer;
import infinispan.autoconfigure.embedded.InfinispanGlobalConfigurer;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfinispanCacheConfiguration {

    public static final String TEST_CLUSTER = "TEST_CLUSTER";
    public static final String TEST_CACHE_NAME = "test-simple-cache";
    public static final String TEST_GLOBAL_JMX_DOMAIN = "test.infinispan";

    @Bean
    public InfinispanCacheConfigurer cacheConfigurer() {
        return cacheManager -> {
            final org.infinispan.configuration.cache.Configuration testCache =
                    new ConfigurationBuilder()
                            .clustering().cacheMode(CacheMode.INVALIDATION_SYNC)
                            .eviction().size(3L).strategy(EvictionStrategy.LRU)
                            .build();
            cacheManager.defineConfiguration(TEST_CACHE_NAME, testCache);
        };
    }

    @Bean
    public InfinispanGlobalConfigurer globalConfigurer() {
        return () -> {
            final GlobalConfiguration globalConfiguration = new GlobalConfigurationBuilder()
                    .transport().clusterName(TEST_CLUSTER)
                    .build();
            return globalConfiguration;
        };
    }
}
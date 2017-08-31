package be.eekhaut.kristof.springbootwithinfinispan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static be.eekhaut.kristof.springbootwithinfinispan.InfinispanCacheConfiguration.CACHE_NAME;

@Repository
public class GreetingsCacheDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllCacheKeysForName(String name) {
        final String query = "SELECT id FROM cache_" + CACHE_NAME + " WHERE id LIKE ?";
        return jdbcTemplate.queryForList(query, String.class, name + "%");
    }
}

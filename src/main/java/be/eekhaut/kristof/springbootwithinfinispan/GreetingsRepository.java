package be.eekhaut.kristof.springbootwithinfinispan;

import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static be.eekhaut.kristof.springbootwithinfinispan.InfinispanCacheConfiguration.CACHE_NAME;

@Repository
public class GreetingsRepository {

    private List<String> greetings = new ArrayList<>();

    private SpringEmbeddedCacheManager cacheManager;
    private GreetingsCacheDAO greetingsCacheDAO;

    @Autowired
    public GreetingsRepository(SpringEmbeddedCacheManager cacheManager, GreetingsCacheDAO greetingsCacheDAO) {
        this.cacheManager = cacheManager;
        this.greetingsCacheDAO = greetingsCacheDAO;

        initGreetings();
    }

    private void initGreetings() {
        greetings.add("Hello");
        greetings.add("Hi");
        greetings.add("Hallo");
        greetings.add("Bonjour");
        greetings.add("Ciao");
        greetings.add("Hej");
        greetings.add("Ola");
        greetings.add("Konnichiwa");
        greetings.add("Salut");
        greetings.add("Dobry den");
    }

    @Cacheable(value = CACHE_NAME, key = "#name + #id")
    public String getGreeting(String name, int id) {

        System.out.println("Looking up the greeting for name: " + name);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // do nothing
        }

        return greetings.get(id) + ", " + name;
    }

    public void resetGreeting(String name) {
        List<String> keysToEvict = greetingsCacheDAO.getAllCacheKeysForName(name);
        keysToEvict.forEach((key) -> cacheManager.getCache(CACHE_NAME).evict(key));
    }
}

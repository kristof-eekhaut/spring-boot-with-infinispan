package be.eekhaut.kristof.springbootwithinfinispan;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GreetingsRepository {

    private List<String> greetings = new ArrayList<>();

    public GreetingsRepository() {
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

    @Cacheable(value = InfinispanCacheConfiguration.CACHE_NAME, key = "#id")
    public String getGreeting(int id) {

        System.out.println("Looking up the greeting for id: " + id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // do nothing
        }

        return greetings.get(id);
    }

    public int getNrOfGreetings() {
        return greetings.size();
    }

    @CacheEvict(value = InfinispanCacheConfiguration.CACHE_NAME, key = "#id")
    public void resetGreeting(int id) {
        // Method clears a cached item
    }
}

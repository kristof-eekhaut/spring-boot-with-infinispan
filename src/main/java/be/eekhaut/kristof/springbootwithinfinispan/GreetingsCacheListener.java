package be.eekhaut.kristof.springbootwithinfinispan;

import org.infinispan.Cache;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.Event;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStarted;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStopped;
import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Listener
public class GreetingsCacheListener implements InitializingBean {

    @Autowired(required = true)
    private SpringEmbeddedCacheManager embeddedCacheManager;

    private Cache<String, String> getGreetingsCache() {
        return (Cache<String, String>) this.embeddedCacheManager.getCache("greetings").getNativeCache();
    }

    public void installCacheListener() {
        Cache<String,String> greetingsCache = getGreetingsCache();
        greetingsCache.addListener(this);
    }

    @Override
    public void afterPropertiesSet() throws IllegalStateException {
        installCacheListener();
    }


    @CacheStarted
    public void cacheStarted(Event event) {
        System.out.println("Cache started. Details = " + event);
    }

    @CacheStopped
    public void cacheStopped(Event event) {
        System.out.println("Cache stopped. Details = " + event);
    }

    @CacheEntryCreated
    @CacheEntryModified
    @CacheEntryRemoved
    public void handle(Event event) {
        System.out.println("Cache entry modified. Details = " + event);
    }
}

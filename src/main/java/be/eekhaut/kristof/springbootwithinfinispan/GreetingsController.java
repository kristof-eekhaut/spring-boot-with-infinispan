package be.eekhaut.kristof.springbootwithinfinispan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("greet")
public class GreetingsController {

    @Autowired
    private GreetingsRepository greetingsRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String sayHello(@PathVariable int id) {
        System.out.println("Say hello... (" + id + ")");
        return greetingsRepository.getGreeting(id);
    }

    @RequestMapping(value = "/{id}/reset", method = RequestMethod.GET)
    public String resetGreeting(@PathVariable int id) {
        System.out.println("Reset greeting: " + id);
        greetingsRepository.resetGreeting(id);
        return "Removed from cache...";
    }
}

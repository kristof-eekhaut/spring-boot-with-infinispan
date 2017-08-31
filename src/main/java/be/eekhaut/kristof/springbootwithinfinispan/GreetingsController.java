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

    @RequestMapping(value = "/{name}/{id}", method = RequestMethod.GET)
    public String sayHello(@PathVariable String name, @PathVariable int id) {
        System.out.println("Say hello to " + name + "...");
        return greetingsRepository.getGreeting(name, id);
    }

    @RequestMapping(value = "/{name}/reset", method = RequestMethod.GET)
    public String resetGreeting(@PathVariable String name) {
        System.out.println("Reset greetings: " + name);
        greetingsRepository.resetGreeting(name);
        return "Removed from cache...";
    }
}

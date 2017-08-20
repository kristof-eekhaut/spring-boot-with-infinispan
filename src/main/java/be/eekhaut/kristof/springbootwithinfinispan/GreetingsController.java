package be.eekhaut.kristof.springbootwithinfinispan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("greet")
public class GreetingsController {

    @Autowired
    private GreetingsRepository greetingsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello() {
        int id = getRandomId();
        System.out.println("Say hello... (" + id + ")");
        return greetingsRepository.getGreeting(id);
    }

    private int getRandomId() {
        int nrOfGreetings = greetingsRepository.getNrOfGreetings();
        int randomId = (int) Math.floor(Math.random() * nrOfGreetings);
        return randomId < nrOfGreetings ? randomId : 0;
    }
}

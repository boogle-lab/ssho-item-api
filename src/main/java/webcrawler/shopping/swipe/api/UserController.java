package webcrawler.shopping.swipe.api;

import org.springframework.web.bind.annotation.*;
import webcrawler.shopping.swipe.domain.User;
import webcrawler.shopping.swipe.repository.UserRepository;

import java.util.List;

@RequestMapping("users")
@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public void saveUser(@RequestBody List<User> userList){
        userRepository.saveAll(userList);
    }

    @GetMapping("/signin")
    public String signin(@RequestParam("name") final String name){
        if(userRepository.findByName(name).isPresent()) return userRepository.findByName(name).get().getId();
        return "";
    }
}

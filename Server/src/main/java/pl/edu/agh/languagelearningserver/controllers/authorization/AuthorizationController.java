package pl.edu.agh.languagelearningserver.controllers.authorization;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.languagelearningserver.beans.AuthorizationService;
import pl.edu.agh.languagelearningserver.controllers.AbstractController;
import pl.edu.agh.languagelearningserver.db.enities.User;
import pl.edu.agh.languagelearningserver.db.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class AuthorizationController extends AbstractController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthorizationService authorizationService;

    public Boolean checkPassword(String login, String password){
        User user = userRepository.findByUsername(login);
        return user.getPassword().equals(password);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@RequestBody String user) {

        JSONObject userCredentials = new JSONObject(user);
        String login = userCredentials.getString("login");
        String password = userCredentials.getString("password");
        if(authorizationService.isUserRegistered(login)) {
            return new ResponseEntity<>(
                    "false",
                    HttpStatus.BAD_REQUEST);
        }
        authorizationService.registerUser(new User(login, password));
        return new ResponseEntity<>(
                "true",
                HttpStatus.OK);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<String> getLogin(@RequestBody String user){
        JSONObject userCredentials = new JSONObject(user);
        String login = userCredentials.getString("login");
        String password = userCredentials.getString("password");
        User userInRepo = authorizationService.getUser(login);
        if(userInRepo!=null && password.equals(userInRepo.getPassword())) {
            getApplicationContext().initContext(userRepository.findByUsername(login));//tu by nie wystarczylo userInRepo?
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return ResponseEntity.accepted().headers(headers).body("username: " +  getApplicationContext().getUser().getUsername());
        }
        return new ResponseEntity<>(
                "false",
                HttpStatus.UNAUTHORIZED);
    }

}

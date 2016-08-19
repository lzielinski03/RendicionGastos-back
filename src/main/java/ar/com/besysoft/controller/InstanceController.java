package ar.com.besysoft.controller;

import ar.com.besysoft.entity.User;
import ar.com.besysoft.security.Token;
import ar.com.besysoft.webService.PapiService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lzielinski on 16/08/2016.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class InstanceController {

    HttpHeaders headers = new HttpHeaders();

    public InstanceController() {
        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/instance",
            method = RequestMethod.GET,
            consumes= MediaType.APPLICATION_JSON_VALUE,
            headers = "content-type=application/json"
    )
    public ResponseEntity<String> getInstances(@RequestHeader String authorization) {
        if (authorization.length() == 0) return new ResponseEntity<>("{\"sucess\": \"false\"}", HttpStatus.FORBIDDEN);
        User user = new Token(authorization).getUser();
        headers.set("username", user.getUsername());
        headers.set("password", user.getPassword());

        String response = new PapiService(user.getUsername(), user.getPassword()).getInstances();
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/instance/{id}",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "content-type=application/json"
    )
    public ResponseEntity get(
            @RequestHeader String authorization,
            @PathVariable("id") String id) {
        if (authorization.length() == 0) return new ResponseEntity<>("{\"sucess\": \"false\"}", HttpStatus.FORBIDDEN);

        User user = new Token(authorization).getUser();
        headers.set("username", user.getUsername());
        headers.set("password", user.getPassword());

        System.out.println("instance info id");

        String response = new PapiService(user.getUsername(), user.getPassword()).getInstanceInfo(id);
        System.out.println(response);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}

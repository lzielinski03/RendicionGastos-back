package ar.com.besysoft.controller;

import ar.com.besysoft.entity.User;
import ar.com.besysoft.security.Token;
import ar.com.besysoft.webService.PapiService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

/**
 * Created by lzielinski on 16/08/2016.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    HttpHeaders headers = new HttpHeaders();

    public AuthController() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/auth",
            method = RequestMethod.POST,
            consumes= MediaType.APPLICATION_JSON_VALUE,
            headers = "content-type=application/json"
    )
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            Object participant = new PapiService(user.getUsername(), user.getPassword()).getCurrentParticipant();

            if (participant == null)
                return new ResponseEntity<String>("{\"sucess\": \"false\"}", HttpStatus.FORBIDDEN);

        } catch(SOAPFaultException e) {
            return new ResponseEntity<String>("{\"sucess\": \"false\"}", HttpStatus.FORBIDDEN);

        } catch (WebServiceException e) {
            return new ResponseEntity<String>("{\"sucess\": \"false\"}", HttpStatus.SERVICE_UNAVAILABLE);

        }
        return new ResponseEntity<>("{\"success\": \"true\", \"token\": \"" + Token.getJwt(user) + "\"}", headers, HttpStatus.OK);
    }
}

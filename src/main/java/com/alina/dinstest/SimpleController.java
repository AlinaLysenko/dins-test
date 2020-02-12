package com.alina.dinstest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {

    @RequestMapping(method = RequestMethod.GET, value = "/company/{companyId}/users")
    public ResponseEntity findUser(@PathVariable("companyId") Long companyId,
                                   @RequestParam("name") String name,
                                   @CookieValue(value = "token", required = false) String token) {
        return new ResponseEntity(HttpStatus.OK);
    }
}

package com.johnhunsley.returns.api;

import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import com.johnhunsley.returns.domain.Return;
import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 27/10/2017
 *         Time : 15:07
 */
@RestController
@RequestMapping("app/return")
public class ReturnController {

    @Autowired
    private ReturnsRepositoryJpaImpl returnsRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    //@PreAuthorize("hasPermission('serviceProvider', 'SERVICE_PROVIDER')")
    public ResponseEntity createReturn(@RequestBody Return template) {
        AuthenticationJsonWebToken authentication = (AuthenticationJsonWebToken)
                                                        SecurityContextHolder.getContext().getAuthentication();
        returnsRepository.save(template);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    //@PreAuthorize("hasPermission('serviceProvider', 'SERVICE_PROVIDER')")
    public ResponseEntity<Return> getReturnById(@PathVariable("id") final long id) {
        return new ResponseEntity<Return>(returnsRepository.getOne(id), HttpStatus.OK);
    }
}

package com.johnhunsley.returns.api;

import com.johnhunsley.returns.domain.Lookup;
import com.johnhunsley.returns.repository.LookupRepositoryJpaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/02/2018
 *         Time : 15:37
 */
@RestController
@RequestMapping("app/lookup/")
public class LookupController {

    @Autowired
    private LookupRepositoryJpaImpl lookupRepository;


    @CrossOrigin
    @RequestMapping(value = "{type}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Lookup>> lookupByType(@PathVariable("type") final String type) {
        return new ResponseEntity<>(lookupRepository.findLookupsByType(type), HttpStatus.OK);
    }
}

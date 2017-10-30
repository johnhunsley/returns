package com.johnhunsley.returns.api;

import com.johnhunsley.returns.domain.ReturnStats;
import com.johnhunsley.returns.domain.SimpleReturnFacade;
import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 27/10/2017
 *         Time : 14:52
 */
@RestController
@RequestMapping("app/returns")
public class ReturnsController {

    @Autowired
    private ReturnsRepositoryJpaImpl returnsRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<SimpleReturnFacade>> pageReturns(@RequestParam("filter") final String filter,
                                                               @RequestParam("fishery") final String fishery,
                                                               @RequestParam("page") final int page,
                                                               @RequestParam("size") final int size) {
        return new ResponseEntity(returnsRepository.pageReturnsByName(
                filter, new PageRequest(page - 1, size, Sort.Direction.ASC, "id")), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/stats", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ReturnStats> getStats(@RequestParam("toDate") final String toDate,
                                                @RequestParam("fromDate") final String fromDate,
                                                @RequestParam("fishery") final String fishery) {
        return new ResponseEntity(HttpStatus.OK);
    }


}

package com.johnhunsley.returns.api;

import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import com.johnhunsley.returns.domain.Return;
import com.johnhunsley.returns.domain.ReturnStats;
import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import com.johnhunsley.returns.service.ReturnStatsCollator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 27/10/2017
 */
@RestController
@RequestMapping("app/returns/")
public class ReturnsController {

    @Autowired
    private ReturnsRepositoryJpaImpl returnsRepository;

    @Autowired
    private ReturnStatsCollator returnStatsCollator;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<Return>> pageReturns(@RequestParam("filter") String filter,
                                                               @RequestParam("fishery") final String fishery,
                                                               @RequestParam("page") final int page,
                                                               @RequestParam("size") final int size) {
        if(filter == null || filter.length() < 1) filter = "";

        if(fishery == null || fishery.length() < 1)
            return new ResponseEntity(returnsRepository.findReturnsByName(
               filter, new PageRequest(page - 1, size, Sort.Direction.ASC, "id")), HttpStatus.OK);

        else return new ResponseEntity(returnsRepository.findReturnsByNameAndFishery(
                filter, fishery,  new PageRequest(page - 1, size, Sort.Direction.ASC, "id")), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "stats", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ReturnStats> getStats(@RequestParam("toDate") final Date toDate,
                                                @RequestParam("fromDate") final Date fromDate,
                                                @RequestParam("fishery") final String fishery) {
        return new ResponseEntity(returnStatsCollator.collateStats(fromDate, toDate, fishery), HttpStatus.OK);
    }


}

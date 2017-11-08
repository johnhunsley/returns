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

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 27/10/2017
 */
@RestController
@RequestMapping("app/returns/")
public class ReturnsController {

    final static String DATE_PATTERN = "yyyy-MM-dd";
    private static final String VALIDATION_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    @Autowired
    private ReturnsRepositoryJpaImpl returnsRepository;

    @Autowired
    private ReturnStatsCollator returnStatsCollator;

    private final DateFormat df = new SimpleDateFormat(DATE_PATTERN);

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
    public ResponseEntity<List<ReturnStats>> getStats(@Valid @Pattern(regexp = VALIDATION_REGEX) @RequestParam("toDate") String toDate,
                                                @Valid @Pattern(regexp = VALIDATION_REGEX) @RequestParam("fromDate") String fromDate,
                                                @RequestParam("fishery") final String fishery) throws ParseException {
        Date from = df.parse(fromDate);
        Date to = df.parse(toDate);
        List<ReturnStats> stats;

        if(fishery == null || fishery.length() < 1)
            stats =  returnStatsCollator.collateStats(from, to);

        else stats = returnStatsCollator.collateStatsForFishery(from, to, fishery);


        return new ResponseEntity(stats, HttpStatus.OK);
    }


}

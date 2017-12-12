package com.johnhunsley.returns.service;

import com.johnhunsley.returns.domain.ReturnStats;
import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/11/2017
 */
@Service("returnStatsCollator")
public class ReturnStatsCollator {

    @Autowired
    private ReturnsRepositoryJpaImpl returnsRepository;

    /**
     * <p>
     *     get counts for given date range and fishery for all distinct species
     * </p>
     * @param from
     * @param to
     * @param fishery
     * @return
     */
    public List<ReturnStats> collateStatsForFishery(final Date from, final Date to, final String fishery) {
        List<ReturnStats> stats = new ArrayList<>();
        List<String> species = returnsRepository.findDistinctSpeciesForFishery(fishery);

        for(String type: species) {
            Integer count = returnsRepository.getCatchCountForFisheryAndDateRange(from, to, fishery, type);
            if(count == null) count = 0;
            ReturnStats stat = new ReturnStats(type, count);
            stats.add(stat);
        }

        return stats;
    }

    /**
     *
     * @param from
     * @param to
     * @return
     */
    public List<ReturnStats> collateStats(final Date from, final Date to) {
        List<ReturnStats> stats = new ArrayList<>();
        List<String> species = returnsRepository.findDistinctSpecies();

        for(String type: species) {
            Integer count = returnsRepository.getCatchCountAndDateRange(from, to, type);
            if(count == null) count = 0;
            ReturnStats stat = new ReturnStats(type, count);
            stats.add(stat);
        }

        return stats;
    }

    /**
     * <p>
     *     Count the number of returns for each day between the given dates
     * </p>
     * @param from
     * @param to
     * @return
     */
    public List<Integer> countSessionsPerDay(final Date from, final Date to) {
        List<Integer> results = new ArrayList<>();

        int count = Days.daysBetween(new DateTime(from).toLocalDate(), new DateTime(to).toLocalDate()).getDays();

        for(int i = 0 ; i <= count; i++) {
            results.add(returnsRepository.countReturnsForDateRange(new DateTime(from).plusDays(i).toDate(),
                    new DateTime(from).plusDays(i + 1).toDate()));
        }

        return results;
    }

    /**
     * <p>
     *     Count the number of returns for each day between the given dates for the given fishery
     * </p>
     * @param from
     * @param to
     * @return
     */
    public List<Integer> countSessionsPerDayForFishery(final Date from, final Date to, final String fishery) {
        List<Integer> results = new ArrayList<>();

        int count = Days.daysBetween(new DateTime(from).toLocalDate(), new DateTime(to).toLocalDate()).getDays();

        for(int i = 0 ; i <= count; i++) {
            results.add(returnsRepository.countReturnsForDateRangeAndFishery(new DateTime(from).plusDays(i).toDate(),
                    new DateTime(from).plusDays(i+1).toDate(), fishery));
        }

        return results;
    }

}

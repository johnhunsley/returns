package com.johnhunsley.returns.service;

import com.johnhunsley.returns.domain.ReturnStats;
import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
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
            ReturnStats stat = new ReturnStats(type, returnsRepository.getCatchCountForFisheryAndDateRange(from, to, fishery, type));
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
            ReturnStats stat = new ReturnStats(type, returnsRepository.getCatchCountAndDateRange(from, to, type));
            stats.add(stat);
        }

        return stats;
    }

}

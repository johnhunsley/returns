package com.johnhunsley.returns.service;

import com.johnhunsley.returns.domain.ReturnStats;
import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/11/2017
 *         Time : 16:07
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
    public ReturnStats collateStats(final Date from, final Date to, final String fishery) {
        ReturnStats stats = new ReturnStats();
        List<String> species = returnsRepository.findDistinctSpecies();

        for(String type: species) {
            stats.addStat(type, returnsRepository.getCatchCountForFisheryAndDateRange(from, to, fishery, type));
        }

        return stats;
    }
}

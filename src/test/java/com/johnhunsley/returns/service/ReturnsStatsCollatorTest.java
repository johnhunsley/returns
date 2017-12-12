package com.johnhunsley.returns.service;

import com.johnhunsley.returns.domain.ReturnStats;
import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 07/12/2017
 *         Time : 18:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReturnsStatsCollatorTest {

    private static final List<String> DISTINCT_SPECIES = new ArrayList<>();

    static {
        DISTINCT_SPECIES.add("Bream");
        DISTINCT_SPECIES.add("Carp");
        DISTINCT_SPECIES.add("Pike");
    }


    @MockBean
    private ReturnsRepositoryJpaImpl returnsRepository;

    @Autowired
    private ReturnStatsCollator returnStatsCollator;

    @Test
    public void testCollateStats() {
        final int count = 5;
        when(returnsRepository.findDistinctSpecies()).thenReturn(DISTINCT_SPECIES);
        when(returnsRepository.getCatchCountAndDateRange(anyObject(), anyObject(), anyString())).thenReturn(count);
        List<ReturnStats> result = returnStatsCollator.collateStats(DateTime.now().toDate(), DateTime.now().toDate());
        assertTrue(result.size() == 3);

        for(ReturnStats returnStats : result) {
            assertNotNull(returnStats.getSpecies());
            assertTrue(returnStats.getCount() == 5);
        }
    }

    @Test
    public void testCollateStatsWithNull() {
        when(returnsRepository.findDistinctSpecies()).thenReturn(DISTINCT_SPECIES);
        when(returnsRepository.getCatchCountAndDateRange(anyObject(), anyObject(), anyString())).thenReturn(null);
        List<ReturnStats> result = returnStatsCollator.collateStats(DateTime.now().toDate(), DateTime.now().toDate());
        assertTrue(result.size() == 3);

        for(ReturnStats returnStats : result) {
            assertNotNull(returnStats.getSpecies());
            assertTrue(returnStats.getCount() == 0);
        }
    }

    @Test
    public void testCountSessionsPerDay() {
        when(returnsRepository.countReturnsForDateRange(anyObject(), anyObject())).thenReturn(10);
        List<Integer> sessions = returnStatsCollator.countSessionsPerDay(
                DateTime.now().minusDays(9).toDate(), DateTime.now().toDate());
        assertTrue(sessions.size() == 10);

        for(Integer i : sessions) {
            assertTrue(i == 10);
        }

    }
}

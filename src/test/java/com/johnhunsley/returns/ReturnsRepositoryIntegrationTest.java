package com.johnhunsley.returns;

import com.johnhunsley.returns.domain.Catch;
import com.johnhunsley.returns.domain.Return;
import com.johnhunsley.returns.domain.ReturnStats;
import com.johnhunsley.returns.service.ReturnStatsCollator;
import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/10/2017
 *         Time : 16:30
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-int.properties")
public class ReturnsRepositoryIntegrationTest {

    @Autowired
    private ReturnsRepositoryJpaImpl returnsRepository;

    @Before
    public void testCreateReturn() {

        for(int i = 1; i <=10; i++ ) {
            Catch myCatch = new Catch();
            myCatch.setSpecies("Barbel");
            myCatch.setPounds(8);
            myCatch.setOunces(3);
            myCatch.setCount(2);

            Return myReturn = new Return();
            myReturn.setFrom(DateTime.now().minusDays(i+1).toDate());
            myReturn.setTo(DateTime.now().minusDays(i).toDate());
            myReturn.setFromhh(13);
            myReturn.setFrommm(0);
            myReturn.setTohh(22);
            myReturn.setTomm(30);
            myReturn.setFishery("Wroxeter");
            myReturn.setName("John Hunsley");
            myReturn.setMemberId("PP702");
            myReturn.addCatch(myCatch);

            returnsRepository.save(myReturn);
            assertNotNull(myReturn.getId());
            System.out.println("RETURN ID : " + myReturn.getId() +
                              " CATCH ID : " + myReturn.getCatches().iterator().next().getId());
        }
    }

    @Test
    public void testFindReturnsByName() {
        Page<Return> result = returnsRepository.findReturnsByName("Huns", new PageRequest(0, 10));
        assertTrue(result.getContent().size() == 10);
        for(Return myReturn : result.getContent()) {
            System.out.println("RETURN ID : " + myReturn.getId() +
                    " CATCH ID : " + myReturn.getCatches().iterator().next().getId());
        }
    }

    @Test
    public void testFailFindReturnsByName() {
        Page<Return> result = returnsRepository.findReturnsByName("chump", new PageRequest(0, 10));
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    public void testFindDistinctSpecies() {
        List<String> species = returnsRepository.findDistinctSpecies();
        species.forEach(System.out::println);
    }

    @Test
    public void testGetCatchCountForFisheryAndDateRange() {
        Integer count = returnsRepository.getCatchCountForFisheryAndDateRange(DateTime.now().minusDays(25).toDate(), DateTime.now().toDate(), "Wroxeter", "Barbel");
        System.out.println(count);
    }

}

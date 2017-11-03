package com.johnhunsley.returns.repository;

import com.johnhunsley.returns.domain.Catch;
import com.johnhunsley.returns.domain.Return;
import com.johnhunsley.returns.domain.ReturnStats;
import com.johnhunsley.returns.service.ReturnStatsCollator;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/11/2017
 *         Time : 16:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
public class ReturnStatsCollatorIntegrationTest {
    @Autowired
    private ReturnsRepositoryJpaImpl returnsRepository;

    @Autowired
    private ReturnStatsCollator returnStatsCollator;

    @Before
    public void testCreateReturn() {

        for(int i = 1; i <=10; i++ ) {
            Catch myCatch = new Catch();

            if(i > 5) myCatch.setSpecies("Chub");
            else myCatch.setSpecies("Barbel");

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
    public void testCollateStats() {
        ReturnStats stats = returnStatsCollator.collateStats(DateTime.now().minusDays(25).toDate(), DateTime.now().toDate(), "Wroxeter");
        assertTrue(stats.getCatchStats().keySet().size() == 2);
        assertTrue(stats.getCatchStats().containsKey("Barbel"));
        assertTrue(stats.getCatchStats().get("Barbel").equals(10));
        assertTrue(stats.getCatchStats().containsKey("Chub"));
        assertTrue(stats.getCatchStats().get("Chub").equals(10));

        for(String type: stats.getCatchStats().keySet()) {
            System.out.println(type + ": "+stats.getCatchStats().get(type));
        }
    }
}

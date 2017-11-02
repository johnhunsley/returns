package com.johnhunsley.returns.domain;

import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 02/11/2017
 *         Time : 15:32
 */
@JsonTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ReturnsJsonTest {

    @Autowired
    private JacksonTester<Return> tester;

    @MockBean
    private ReturnsRepositoryJpaImpl returnsRepository;

    @Test
    public void testSerialize() throws Exception {
        Catch myCatch = new Catch();
        myCatch.setSpecies("Barbel");
        myCatch.setPounds(8);
        myCatch.setOunces(3);
        myCatch.setCount(2);

        Return myReturn = new Return();
        myReturn.setFrom(DateTime.now().minusDays(2).toDate());
        myReturn.setTo(DateTime.now().minusDays(1).toDate());
        myReturn.setFromhh(13);
        myReturn.setFrommm(0);
        myReturn.setTohh(22);
        myReturn.setTomm(30);
        myReturn.setFishery("Wroxeter");
        myReturn.setName("John Hunsley");
        myReturn.setMemberId("PP702");
        myReturn.addCatch(myCatch);

        System.out.println(tester.write(myReturn).getJson());
    }
}

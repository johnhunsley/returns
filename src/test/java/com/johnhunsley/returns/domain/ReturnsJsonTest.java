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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

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
        Date from = DateTime.now().minusDays(2).toDate();
        Date to = DateTime.now().minusDays(1).toDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        final String expected = "{\"class\":\"Return\",\"fishery\":\"Wroxeter\",\"memberId\":\"PP702\",\"name\":\"John Hunsley\",\"from\":\""+df.format(from)+"\",\"fromhh\":13,\"frommm\":0,\"to\":\""+df.format(to)+"\",\"tohh\":22,\"tomm\":30,\"catches\":[{\"class\":\"Catch\",\"species\":\"Barbel\",\"count\":2,\"pounds\":8,\"ounces\":3}]}";
        System.out.println(expected);
        Catch myCatch = new Catch();
        myCatch.setSpecies("Barbel");
        myCatch.setPounds(8);
        myCatch.setOunces(3);
        myCatch.setCount(2);

        Return myReturn = new Return();
        myReturn.setFrom(from);
        myReturn.setTo(to);
        myReturn.setFromhh(13);
        myReturn.setFrommm(0);
        myReturn.setTohh(22);
        myReturn.setTomm(30);
        myReturn.setFishery("Wroxeter");
        myReturn.setName("John Hunsley");
        myReturn.setMemberId("PP702");
        myReturn.addCatch(myCatch);
        System.out.println(tester.write(myReturn).getJson());
        assertTrue(tester.write(myReturn).getJson().toString().equals(expected));
    }
}

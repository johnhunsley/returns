package com.johnhunsley.returns.api;

import com.johnhunsley.returns.domain.Catch;
import com.johnhunsley.returns.domain.Return;
import com.johnhunsley.returns.repository.LookupRepositoryJpaImpl;
import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import com.johnhunsley.returns.service.ReturnStatsCollator;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 02/11/2017
 *         Time : 12:04
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ReturnsController.class, secure = false)
public class ReturnsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReturnsRepositoryJpaImpl returnsRepository;

    @MockBean
    private LookupRepositoryJpaImpl lookupRepository;

    @MockBean
    private ReturnStatsCollator returnStatsCollator;

    private Page<Return> page;

    @Before
    public void initData() {
        List<Return> returns = new ArrayList<>();

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

            returns.add(myReturn);
        }

        page = new PageImpl<>(returns);
    }



    @Test
    public void testPageReturns() throws Exception {
        given(returnsRepository.findReturnsByNameAndFishery(anyString(), anyString(), anyObject())).willReturn(page);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("fishery", "ababab");
        params.add("filter", "xyxyxyx");
        params.add("page", "0");
        params.add("size", "10");
        mockMvc.perform(get("app/returns").params(params))
                .andDo(print())
//                .andExpect(status().isOk())
                .andReturn();
    }
}

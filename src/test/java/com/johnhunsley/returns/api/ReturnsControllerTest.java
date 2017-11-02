package com.johnhunsley.returns.api;

import com.johnhunsley.returns.repository.ReturnsRepositoryJpaImpl;
import com.johnhunsley.returns.service.ReturnStatsCollator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

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
    private ReturnStatsCollator returnStatsCollator;


    @Test
    public void testPageReturns() {
        given(returnsRepository.findReturnsByName(anyString(), anyObject()));
    }
}

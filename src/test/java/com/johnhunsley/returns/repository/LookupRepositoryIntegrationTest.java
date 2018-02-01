package com.johnhunsley.returns.repository;

import com.johnhunsley.returns.domain.Lookup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/02/2018
 *         Time : 16:17
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
public class LookupRepositoryIntegrationTest {

    @Autowired
    private LookupRepositoryJpaImpl lookupRepository;

    @Test
    public void createLookup() {
        Lookup lookup = new Lookup("FISHERY", "Wroxeter");
        lookupRepository.save(lookup);
    }
}

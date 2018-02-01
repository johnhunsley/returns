package com.johnhunsley.returns.repository;

import com.johnhunsley.returns.domain.Lookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/02/2018
 *         Time : 15:32
 */
@Repository("lookupRepository")
public interface LookupRepositoryJpaImpl extends JpaRepository<Lookup, Long> {

    @Query("select l from Lookup l where l.lookupType = :lookupType order by l.lookupValue desc")
    List<Lookup> findLookupsByType(@Param("lookupType") String lookupType);
}

package com.johnhunsley.returns.repository;

import com.johnhunsley.returns.domain.Return;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/10/2017
 *         Time : 11:12
 */
@Repository("returnsRepository")
public interface ReturnsRepositoryJpaImpl extends JpaRepository<Return, Long> {

    Page<Return> pageReturnsByName(@Param("name") String name, Pageable pageable);
}

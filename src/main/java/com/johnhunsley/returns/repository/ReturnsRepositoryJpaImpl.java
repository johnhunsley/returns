package com.johnhunsley.returns.repository;

import com.johnhunsley.returns.domain.Return;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/10/2017
 *         Time : 11:12
 */
@Repository("returnsRepository")
public interface ReturnsRepositoryJpaImpl extends JpaRepository<Return, Long> {

    @Query("select r from Return r where r.name like concat('%',:queryValue,'%')")
    Page<Return> findReturnsByName(@Param("queryValue") String queryValue, Pageable pageable);

    @Query("select r from Return r where r.from >= :fromdate and r.to <= :todate and r.fishery = :fishery")
    Page<Return> findReturnsByFisheryAndDateRange(
            @Param("fromdate") Date from, @Param("todate") Date to, @Param("fishery") String fishery, Pageable pageable);

    @Query("select distinct c.species from Catch c")
    List<String> findDistinctSpecies();

    @Query("select SUM(c.count) from Catch c where c.aReturn in (select r from Return r where r.from >= :fromdate and r.to <= :todate and r.fishery = :fishery)")
    Integer getCatchCountForFisheryAndDateRange(@Param("fromdate") Date from, @Param("todate") Date to, @Param("fishery") String fishery);
}

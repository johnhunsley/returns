package com.johnhunsley.returns.repository;

import com.johnhunsley.returns.domain.Return;
import org.springframework.context.annotation.Profile;
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
 */
@Repository("returnsRepository")
public interface ReturnsRepositoryJpaImpl extends JpaRepository<Return, Long> {

    @Query("select r from Return r where r.name like concat('%',:queryValue,'%') and r.fishery = :fishery order by r.from desc")
    Page<Return> findReturnsByNameAndFishery(@Param("queryValue") String queryValue, @Param("fishery") String fishery, Pageable pageable);

    @Query("select r from Return r where r.name like concat('%',:queryValue,'%') order by r.from desc")
    Page<Return> findReturnsByName(@Param("queryValue") String queryValue, Pageable pageable);

    @Query("select r from Return r where r.from >= :fromdate and r.to <= :todate and r.fishery = :fishery order by r.from desc")
    Page<Return> findReturnsByFisheryAndDateRange(
            @Param("fromdate") Date from, @Param("todate") Date to, @Param("fishery") String fishery, Pageable pageable);

    @Query("select distinct c.species from Catch c")
    List<String> findDistinctSpecies();

    @Query("select distinct c.species from Catch c where c.aReturn in (select r from Return r where r.fishery = :fishery)")
    List<String> findDistinctSpeciesForFishery(@Param("fishery") String fishery);

    @Query("select SUM(c.count) from Catch c where c.species = :species and c.aReturn in (select r from Return r where r.from >= :fromdate and r.to <= :todate and r.fishery = :fishery)")
    Integer getCatchCountForFisheryAndDateRange(@Param("fromdate") Date from, @Param("todate") Date to, @Param("fishery") String fishery, @Param("species") String species);

    @Query("select SUM(c.count) from Catch c where c.species = :species and c.aReturn in (select r from Return r where r.from >= :fromdate and r.to <= :todate)")
    Integer getCatchCountAndDateRange(@Param("fromdate") Date from, @Param("todate") Date to, @Param("species") String species);

    @Query("select COUNT(r) from Return r where r.from >= :fromdate and r.to <= :todate")
    Integer countReturnsForDateRange(@Param("fromdate") Date from, @Param("todate") Date to);

    @Query("select COUNT(r) from Return r where r.from >= :fromdate and r.to <= :todate and r.fishery = :fishery")
    Integer countReturnsForDateRangeAndFishery(@Param("fromdate") Date from, @Param("todate") Date to, @Param("fishery") String fishery);
}

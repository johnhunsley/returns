package com.johnhunsley.returns.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     representation of multiple different statistics for
 * </p>
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 27/10/2017
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
public class ReturnStats implements Comparable {

    private String species;

    private int count;

    public ReturnStats() {}

    public ReturnStats(String species) {
        this.species = species;
    }

    public ReturnStats(String species, int count) {
        this.species = species;
        this.count = count;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(Object o) {
        ReturnStats that = (ReturnStats)o;
        return this.getSpecies().compareTo(that.getSpecies());
    }
}

package com.johnhunsley.returns.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 27/10/2017
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
@Entity
@Table(name = "CATCHES", catalog = "lymmac", schema = "")
public class Catch implements Serializable {
    private static final long serialVersionUID = 100L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "SPECIES")
    private String species;

    @Basic
    @Column(name = "CATCH_COUNT")
    private int count;

    @Basic
    @Column(name = "AVG_POUNDS")
    private int pounds;

    @Basic
    @Column(name = "AVG_OUNCES")
    private int ounces;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RETURN_ID")
    private Return aReturn;

    public Long getId() {
        return id;
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

    public int getPounds() {
        return pounds;
    }

    public void setPounds(int pounds) {
        this.pounds = pounds;
    }

    public int getOunces() {
        return ounces;
    }

    public void setOunces(int ounces) {
        this.ounces = ounces;
    }

    public Return getaReturn() {
        return aReturn;
    }

    public void setaReturn(Return aReturn) {
        this.aReturn = aReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Catch)) return false;

        Catch aCatch = (Catch) o;

        if (getCount() != aCatch.getCount()) return false;
        if (getPounds() != aCatch.getPounds()) return false;
        if (getOunces() != aCatch.getOunces()) return false;
        if (getId() != null ? !getId().equals(aCatch.getId()) : aCatch.getId() != null) return false;
        if (!getSpecies().equals(aCatch.getSpecies())) return false;
        return !(getaReturn() != null ? !getaReturn().equals(aCatch.getaReturn()) : aCatch.getaReturn() != null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getSpecies().hashCode();
        result = 31 * result + getCount();
        result = 31 * result + getPounds();
        result = 31 * result + getOunces();
        result = 31 * result + (getaReturn() != null ? getaReturn().hashCode() : 0);
        return result;
    }
}

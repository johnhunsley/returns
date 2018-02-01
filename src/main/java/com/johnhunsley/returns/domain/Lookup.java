package com.johnhunsley.returns.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 *     A simple item which is read by lookupType
 * </p>
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/02/2018
 *         Time : 15:24
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
@Entity
@Table(name = "LOOKUP", catalog = "lymmac", schema = "")
public class Lookup implements Serializable {
    private static final long serialVersionUID = 100L;

    @Basic
    @Column(name = "LOOKUP_TYPE")
    private String lookupType;

    @Basic
    @Column(name = "LOOKUP_VALUE")
    private String lookupValue;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Lookup() {}

    public Lookup(String lookupType, String lookupValue) {
        this.lookupType = lookupType;
        this.lookupValue = lookupValue;
    }

    public Long getId() {
        return id;
    }

    public String getLookupType() {
        return lookupType;
    }

    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }

    public String getLookupValue() {
        return lookupValue;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lookup)) return false;

        Lookup lookup = (Lookup) o;

        if (getLookupType() != null ? !getLookupType().equals(lookup.getLookupType()) : lookup.getLookupType() != null) return false;
        return !(getLookupValue() != null ? !getLookupValue().equals(lookup.getLookupValue()) : lookup.getLookupValue() != null);

    }

    @Override
    public int hashCode() {
        int result = getLookupType() != null ? getLookupType().hashCode() : 0;
        result = 31 * result + (getLookupValue() != null ? getLookupValue().hashCode() : 0);
        return result;
    }
}

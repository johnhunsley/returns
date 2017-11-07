package com.johnhunsley.returns.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
@Table(name = "RETURNS", catalog = "lymmac", schema = "")
public class Return implements Serializable {
    private static final long serialVersionUID = 100L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "FISHERY")
    private String fishery;

    @Basic
    @Column(name = "MEMBER_ID")
    private String memberId;

    @Basic
    @Column(name = "MEMBER_NAME")
    private String name;

    @Basic
    @Column(name = "DATE_FROM")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Europe/London")
    private Date from;

    @Basic
    @Column(name = "HOUR_FROM")
    private int fromhh;

    @Basic
    @Column(name = "MIN_FROM")
    private int frommm;

    @Basic
    @Column(name = "DATE_TO")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Europe/London")
    private Date to;

    @Basic
    @Column(name = "HOUR_TO")
    private int tohh;

    @Basic
    @Column(name = "MIN_TO")
    private int tomm;

    @Basic
    @Max(250)
    @Column(name = "NOTES")
    private String notes;

    @OneToMany(mappedBy = "aReturn", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Catch> catches;

    public Long getId() {
        return id;
    }

    public String getFishery() {
        return fishery;
    }

    public void setFishery(String fishery) {
        this.fishery = fishery;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public int getFromhh() {
        return fromhh;
    }

    public void setFromhh(int fromhh) {
        this.fromhh = fromhh;
    }

    public int getFrommm() {
        return frommm;
    }

    public void setFrommm(int frommm) {
        this.frommm = frommm;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public int getTohh() {
        return tohh;
    }

    public void setTohh(int tohh) {
        this.tohh = tohh;
    }

    public int getTomm() {
        return tomm;
    }

    public void setTomm(int tomm) {
        this.tomm = tomm;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Catch> getCatches() {
        return catches;
    }

    public void setCatches(Set<Catch> catches) {
        this.catches = catches;
    }

    public void addCatch(Catch myCatch) {
        if(this.catches == null) this.catches = new HashSet<>();

        this.catches.add(myCatch);
        myCatch.setaReturn(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Return)) return false;

        Return aReturn = (Return) o;

        if (getFromhh() != aReturn.getFromhh()) return false;
        if (getFrommm() != aReturn.getFrommm()) return false;
        if (getTohh() != aReturn.getTohh()) return false;
        if (getTomm() != aReturn.getTomm()) return false;
        if (getId() != null ? !getId().equals(aReturn.getId()) : aReturn.getId() != null) return false;
        if (!getFishery().equals(aReturn.getFishery())) return false;
        if (!getMemberId().equals(aReturn.getMemberId())) return false;
        if (!getName().equals(aReturn.getName())) return false;
        if (!getFrom().equals(aReturn.getFrom())) return false;
        return getTo().equals(aReturn.getTo());

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getFishery().hashCode();
        result = 31 * result + getMemberId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getFrom().hashCode();
        result = 31 * result + getFromhh();
        result = 31 * result + getFrommm();
        result = 31 * result + getTo().hashCode();
        result = 31 * result + getTohh();
        result = 31 * result + getTomm();
        return result;
    }
}

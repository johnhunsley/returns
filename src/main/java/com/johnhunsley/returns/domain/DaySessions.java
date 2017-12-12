package com.johnhunsley.returns.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

/**
 * <p>
 *     Represents the number of sessions calculated from the number of returns for a given day
 * </p>
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 12/12/2017
 *         Time : 16:37
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
public class DaySessions {

    private String dayStartDate;
    private int count;

    public DaySessions() {
    }

    public DaySessions(String dayStartDate, int count) {
        this.dayStartDate = dayStartDate;
        this.count = count;
    }

    public String getDayStartDate() {
        return dayStartDate;
    }

    public void setDayStartDate(String dayStartDate) {
        this.dayStartDate = dayStartDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

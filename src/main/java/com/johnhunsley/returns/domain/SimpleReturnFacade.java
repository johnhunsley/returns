package com.johnhunsley.returns.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * <p>
 *     A basic view facade representation of the {@link Return} instance which
 *     just contains id, memberId, fished from data, name and fishery of the full Return type
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
public class SimpleReturnFacade implements Serializable {
    private static final long serialVersionUID = 100L;


}

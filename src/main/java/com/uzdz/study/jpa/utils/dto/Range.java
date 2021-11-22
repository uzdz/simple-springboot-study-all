/*
 * @Author: your name
 * @Date: 2020-07-20 19:17:35
 * @LastEditTime: 2020-09-23 14:18:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /yq-mall-member/src/main/java/com/yq/mall/framework/support/dto/Range.java
 */
package com.uzdz.study.jpa.utils.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Range<E extends Comparable> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String field;
    private E from;
    private E to;
    private Boolean includeNull;


    public Range(String field) {
        this.field = field;
    }


    public Range(String field, E from, E to) {
        this.field = field;
        this.from = from;
        this.to = to;
    }

    public Range(String field, E from, E  to, Boolean includeNull) {
        this.field = field;
        this.from = from;
        this.to = to;
        this.includeNull = includeNull;
    }



    public String getField() {
        return field;
    }

    public E getFrom() {
        return from;
    }


    public void setFrom(E from) {
        this.from = from;
    }

    public boolean isFromSet() {
        return getFrom() != null;
    }


    public E getTo() {
        return to;
    }

    public void setTo(E to) {
        this.to = to;
    }

    public boolean isToSet() {
        return getTo() != null;
    }

    public void setIncludeNull(boolean includeNull) {
        this.includeNull = includeNull;
    }

    public Boolean getIncludeNull() {
        return includeNull;
    }

    public boolean isIncludeNullSet() {
        return includeNull != null;
    }

    public boolean isBetween() {
        return isFromSet() && isToSet();
    }

    public boolean isSet() {
        return isFromSet() || isToSet() || isIncludeNullSet();
    }

    public boolean isValid() {
        if (isBetween()) {
            return getFrom().compareTo(getTo())<0;
        }

        return true;
    }

}
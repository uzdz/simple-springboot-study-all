package com.uzdz.study.jpa.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @program:
 * @author: captain.ma
 * @date: 2018-11-20
 * @since: 1.0.0.0
 */

public class QueryVO {
    @Getter
    private Map<String, Object> parameters;
    @Setter
    @Getter
    private String query;
    @Setter
    @Getter
    private String select;

    public QueryVO(Map<String, Object> parameters, String query) {
        this.parameters = parameters;
        this.query = query;
    }

    public QueryVO select(String select) {
        this.setSelect(select);
        return this;
    }

    public String groupBy() {
        String[] as = getSelect().split(",");
        StringBuilder gr = new StringBuilder();
        for (String a : as) {
            if (a.contains("as"))
                a = a.substring(0, a.indexOf("as"));
            gr.append(a).append(",");
        }
        return gr.delete(gr.length() - 1, gr.length()).toString();
    }

}

package com.example.eventservice.util.querybuilder;

public interface SortQueryBuilder {
    String ORDER_BY = " ORDER BY ";
    String COMA_DELIMITER = ", ";
    String DESC = "desc";
    String ASC = "asc";

    default String buildSortQuery(String query, String sortParams) {
        String sortQuery;

        if(sortParams == null || sortParams.isBlank()) {
            sortQuery = query;
        } else {
            sortQuery = new StringBuffer(query)
                    .append(ORDER_BY)
                    .append(sortParams)
                    .toString();
        }
        return sortQuery;
    }

    String buildSortParamString(String sortParams);
}
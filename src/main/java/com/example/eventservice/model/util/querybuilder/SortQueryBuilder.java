package com.example.eventservice.model.util.querybuilder;

public interface SortQueryBuilder {
    String ORDER_BY = " ORDER BY ";
    String COMA_DELIMITER = ", ";
    String DESC = "desc";
    String ASC = "asc";

    /**
     * Add a part with sorting parameters to the query.
     * @param query - the main part of query to database.
     * @param sortParams - part of a query with sorting parameters, it can be null.
     * @return - a sorted query, if sortParams is null, the provided query will be returned.
     */
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

    String buildSortParamString(String sortParam);
}
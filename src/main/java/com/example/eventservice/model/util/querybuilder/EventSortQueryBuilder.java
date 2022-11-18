package com.example.eventservice.model.util.querybuilder;

import com.example.eventservice.model.util.validator.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.example.eventservice.model.dao.ParamNames.*;

@Component
public class EventSortQueryBuilder implements SortQueryBuilder {
    public static final String EVENT_PREFIX = "e.";
    public static final String ORGANIZER_PREFIX = "e.organizer.";
    public static final String DEFAULT_SORT = "e.eventDate desc";
    private static final List<String> eventParameters = List.of(EVENT_TOPIC, ORGANIZER_NAME, EVENT_DATE);
    private DataValidator validator;

    @Autowired
    public EventSortQueryBuilder(DataValidator validator) {
        this.validator = validator;
    }

    public EventSortQueryBuilder() {
    }

    /**
     * Prepare the sorting part of sorted query from a provided string.
     * @param sortParam - a sort parameter provided from a http request.
     * @return - the part of a query with sorting parameters or empty string if the sortParam is null.
     */
    @Override
    public String buildSortParamString(String sortParam) {
        StringBuilder sortParamStrBuilder = new StringBuilder();

        if (sortParam != null) {
            List<String> sortParamList = Arrays.stream(sortParam.split(COMA_DELIMITER))
                    .filter(param -> validator.isRequestParamNameValid(param) && isSuchParamExist(param))
                    .toList();

            for (int i = 0; i < sortParamList.size(); i++) {
                addParameterPrefix(sortParamStrBuilder, sortParamList.get(i));

                if (i < sortParamList.size() - 1) {
                    sortParamStrBuilder.append(sortParamList.get(i)).append(COMA_DELIMITER);
                } else {
                    sortParamStrBuilder.append(sortParamList.get(i));
                }
            }
        }

        return sortParamStrBuilder.isEmpty() ? DEFAULT_SORT : sortParamStrBuilder.toString();
    }

    /**
     * Check if such a provided param exist at the list of parameters.
     */
    private boolean isSuchParamExist(String param) {
        String paramName = param;

        if (param.contains(ASC)) {
            paramName = param.substring(0, param.indexOf(ASC));
        } else if (param.contains(DESC)) {
            paramName = param.substring(0, param.indexOf(DESC));
        }

        return eventParameters.contains(paramName.trim());
    }


    private void addParameterPrefix(StringBuilder sortParamStrBuilder, String sortParam) {
        if (sortParam.contains(ORGANIZER)) {
            sortParamStrBuilder.append(ORGANIZER_PREFIX);
        } else if (sortParam.contains(EVENT)) {
            sortParamStrBuilder.append(EVENT_PREFIX);
        }
    }
}
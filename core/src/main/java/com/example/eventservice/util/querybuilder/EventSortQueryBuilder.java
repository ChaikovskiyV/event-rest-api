package com.example.eventservice.util.querybuilder;

import com.example.eventservice.util.validator.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.example.eventservice.dao.ParamNames.*;

@Component
public class EventSortQueryBuilder implements SortQueryBuilder {
    public static final String EVENT_PREFIX = "e.";
    public static final String ORGANIZER_PREFIX = "e.organizer.";
    public static final String DEFAULT_SORT = "e.eventDate desc";
    private static final List<String> eventParameters = List.of(EVENT_TOPIC, ORGANIZER_NAME, EVENT_DATE);
    private final DataValidator validator;

    @Autowired
    public EventSortQueryBuilder(DataValidator validator) {
        this.validator = validator;
    }

    @Override
    public String buildSortParamString(String sortParameters) {
        if (sortParameters == null) {
            return DEFAULT_SORT;
        }

        StringBuilder sortParamStrBuilder = new StringBuilder();

        List<String> sortParamList = Arrays.stream(sortParameters.split(COMA_DELIMITER))
                .filter(param -> validator.isRequestParamNameValid(param) && isSuchParamExist(param))
                .toList();

        for (int i = 0; i < sortParamList.size(); i++) {
            addParameterPrefix(sortParamStrBuilder, sortParamList.get(i));

            if (i > 0 && i <= sortParamList.size() - 1) {
                sortParamStrBuilder.append(COMA_DELIMITER).append(sortParamList.get(i));
            } else {
                sortParamStrBuilder.append(sortParamList.get(i));
            }
        }

        return sortParamStrBuilder.toString();
    }

    private boolean isSuchParamExist(String param) {
        String paramName = param;

        if (param.contains(SortQueryBuilder.ASC)) {
            paramName = param.substring(0, param.indexOf(ASC));
        } else if (param.contains(SortQueryBuilder.DESC)) {
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
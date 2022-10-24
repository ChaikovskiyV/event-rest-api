package com.example.eventservice.util.querybuilder;

import com.example.eventservice.util.validator.DataValidator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventSortQueryBuilderTest {
    private static final String DEFAULT_SORT_PARAMS = "e.eventDate desc";
    private static final String DATABASE_QUERY = "From Event e";
    private AutoCloseable closeable;
    @InjectMocks
    private EventSortQueryBuilder sortQueryBuilder;
    @Spy
    private DataValidator dataValidatorMock;
    private String sortParams;
    private String sortParamsWithOneNotCorrectParam;
    private String notCorrectSortParams;


    @BeforeAll
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        sortParams = "eventDate asc, eventTopic desc, organizerName asc";
        sortParamsWithOneNotCorrectParam = "eventDate asc, topic desc, organizerName asc";
        notCorrectSortParams = "some text";
    }

    @BeforeEach
    void init() {
        Mockito.doReturn(true).when(dataValidatorMock).isRequestParamNameValid(Mockito.matches("[\\w\\s]{3,}"));
    }

    @AfterAll
    void releaseMock() throws Exception {
        closeable.close();
    }

    @Test
    void buildSortParamStringMethodTestWhenAllParamsCorrect() {
        String expected = "e.eventDate asc, e.eventTopic desc, e.organizer.organizerName asc";
        String result = sortQueryBuilder.buildSortParamString(sortParams);

        assertEquals(expected, result);
    }

    @Test
    void buildSortParamStringMethodTestWhenSortParamsIncludeNotCorrectParam() {
        String expected = "e.eventDate asc, e.organizer.organizerName asc";
        String result = sortQueryBuilder.buildSortParamString(sortParamsWithOneNotCorrectParam);

        assertEquals(expected, result);
    }

    @Test
    void buildSortParamStringMethodTestWhenSortParamsNotCorrect() {
        String result = sortQueryBuilder.buildSortParamString(notCorrectSortParams);

        assertEquals(DEFAULT_SORT_PARAMS, result);
    }

    @Test
    void buildSortParamStringMethodTestWhenSortParamsNull() {
        String result = sortQueryBuilder.buildSortParamString(null);

        assertEquals(DEFAULT_SORT_PARAMS, result);
    }

    @Test
    void buildSortQueryMethodTestWhenSortParamsNotNull() {
        String expected = "From Event e ORDER BY e.eventDate desc";
        String result = sortQueryBuilder.buildSortQuery(DATABASE_QUERY, DEFAULT_SORT_PARAMS);

        assertEquals(expected, result);
    }

    @Test
    void buildSortQueryMethodTestWhenSortParamsEmpty() {
        String result = sortQueryBuilder.buildSortQuery(DATABASE_QUERY, "");

        assertEquals(DATABASE_QUERY, result);
    }

    @Test
    void buildSortQueryMethodTestWhenSortParamsNull() {
        String result = sortQueryBuilder.buildSortQuery(DATABASE_QUERY, null);

        assertEquals(DATABASE_QUERY, result);
    }
}
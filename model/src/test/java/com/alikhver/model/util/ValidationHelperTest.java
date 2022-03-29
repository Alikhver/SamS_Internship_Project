package com.alikhver.model.util;

import com.alikhver.model.configuration.ModelConfigurationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class ValidationHelperTest {

    @Autowired
    private ValidationHelper validationHelper;

    @Test
    public void validateForCorrectIdWhenCorrectIdTest() {
        //Given
        Long correctId = 1L;

        ValidationHelper validationHelper = mock(ValidationHelper.class);


        //When
        doNothing().when(validationHelper).validateForCorrectId(isA(Long.class), isA(String.class));
        validationHelper.validateForCorrectId(correctId, "CorrectId");

        //Then
        verify(validationHelper, times(1)).validateForCorrectId(correctId, "CorrectId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForCorrectIdWhenIncorrectIdTest() {
        //Given
        Long incorrectId = -1L;

        //When
        validationHelper.validateForCorrectId(incorrectId, "IncorrectId");
    }

    @Test
    public void validateForCorrectStringWhenCorrectStringTest() {
        //Given
        String correctStr = "CorrectString";

        //When
        validationHelper.validateForCorrectString(correctStr, "CorrectStr");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForCorrectStringWhenEmptyStringTest() {
        //Given
        String incorrectString = "";

        //When
        validationHelper.validateForCorrectString(incorrectString, "incorrectString");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForCorrectStringWhenStringContainsOnlyWhitespacesTest() {
        //Given
        String incorrectStr = "   ";

        //When
        validationHelper.validateForCorrectString(incorrectStr, "incorrectStr");
    }

    @Test
    public void validateForCorrectPriceWhenCorrectPriceTest() {
        //Given
        Double correctPrice = 213D;

        //When
        validationHelper.validateForCorrectPrice(correctPrice, "CorrectPrice");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForCorrectPriceWhenIncorrectCorrectPriceTest() {
        //Given
        Double incorrectPrice = -213D;

        //When
        validationHelper.validateForCorrectPrice(incorrectPrice, "incorrectPrice");
    }
}
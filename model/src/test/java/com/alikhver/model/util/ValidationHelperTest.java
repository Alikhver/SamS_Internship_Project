package com.alikhver.model.util;

import com.alikhver.model.configuration.ModelConfigurationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class ValidationHelperTest {

    private static final ValidationHelper validationHelper;

    static {
        validationHelper = spy(ValidationHelper.class);
    }

    @Test
    public void validateForCorrectIdWhenCorrectIdTest() {
        //Given
        Long correctId = 1L;

        //When
        validationHelper.validateForCorrectId(correctId, "CorrectId");

        //Then
        verify(validationHelper, times(1)).validateForCorrectId(correctId, "CorrectId");
    }

    @Test
    public void validateForCorrectIdWhenIncorrectIdTest() {
        //Given
        Long incorrectId = -1L;

        //When
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> validationHelper.validateForCorrectId(incorrectId, "IncorrectId")
        );

        //Then
        String expected = "IncorrectId must not be empty. It must be positive number";
        String actual = e.getMessage();

        verify(validationHelper, times(1)).validateForCorrectId(incorrectId, "IncorrectId");

        assertEquals(expected, actual);
    }

    @Test
    public void validateForCorrectStringWhenCorrectStringTest() {
        //Given
        String correctStr = "CorrectString";

        //When
        validationHelper.validateForCorrectString(correctStr, "CorrectString");

        //Then
        verify(validationHelper, times(1)).validateForCorrectString(correctStr, "CorrectString");
    }

    @Test
    public void validateForCorrectStringWhenEmptyStringTest() {
        //Given
        String incorrectString = "";

        //When
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> validationHelper.validateForCorrectString(incorrectString, "IncorrectString")
        );

        //Then
        String expected = "IncorrectString must not be empty";
        String actual = e.getMessage();

        assertEquals(expected, actual);
        verify(validationHelper, times(1)).validateForCorrectString(incorrectString, "IncorrectString");
    }

    @Test
    public void validateForCorrectStringWhenStringContainsOnlyWhitespacesTest() {
        //Given
        String incorrectStr = "   ";

        //When
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> validationHelper.validateForCorrectString(incorrectStr, "IncorrectStr")
        );

        //Then
        String expected = "IncorrectStr must not be empty";
        String actual = e.getMessage();

        assertEquals(expected, actual);
        verify(validationHelper, times(1)).validateForCorrectString(incorrectStr, "IncorrectStr");
    }

    @Test
    public void validateForCorrectPriceWhenCorrectPriceTest() {
        //Given
        Double correctPrice = 213D;

        //When
        validationHelper.validateForCorrectPrice(correctPrice, "CorrectPrice");

        //Then
        verify(validationHelper, times(1)).validateForCorrectPrice(correctPrice, "CorrectPrice");
    }

    @Test
    public void validateForCorrectPriceWhenIncorrectPriceTest() {
        //Given
        Double incorrectPrice = -213D;

        //When
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> validationHelper.validateForCorrectPrice(incorrectPrice, "IncorrectPrice")
        );

        //Then
        String expected = "IncorrectPrice must not be empty. It must be positive number";
        String actual = e.getMessage();

        assertEquals(expected, actual);
        verify(validationHelper).validateForCorrectPrice(incorrectPrice, "IncorrectPrice");
    }
}
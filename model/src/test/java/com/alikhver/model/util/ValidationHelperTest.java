package com.alikhver.model.util;

import com.alikhver.model.configuration.ModelConfigurationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class ValidationHelperTest {

    @Autowired
    private ValidationHelper validationHelper;

    @Test
    public void whenCorrectId_validateForCorrectId_Test() {
        //Given
        Long correctId = 1L;

        //When
        validationHelper.validateForCorrectId(correctId, "Id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIncorrectId_validateForCorrectId_Test() {
        //Given
        Long incorrectId = -1L;

        //When
        validationHelper.validateForCorrectId(incorrectId, "IncorrectId");
    }

    @Test
    public void whenCorrectString_validateForCorrectString_Test() {
        //Given
        String correctStr = "CorrectString";

        //When
        validationHelper.validateForCorrectString(correctStr, "CorrectStr");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenEmptyString_validateForCorrectString_Test() {
        //Given
        String incorrectString = "";

        //When
        validationHelper.validateForCorrectString(incorrectString, "incorrectString");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenStringContainsOnlyWhitespaces_validateForCorrectString_Test() {
        //Given
        String incorrectStr = "   ";

        //When
        validationHelper.validateForCorrectString(incorrectStr, "incorrectStr");
    }

    @Test
    public void whenCorrectPrice_validateForCorrectPrice_Test() {
        //Given
        Double correctPrice = 213D;

        //When
        validationHelper.validateForCorrectPrice(correctPrice, "CorrectPrice");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIncorrectCorrectPrice_validateForCorrectPrice_Test() {
        //Given
        Double incorrectPrice = -213D;

        //When
        validationHelper.validateForCorrectPrice(incorrectPrice, "incorrectPrice");
    }
}
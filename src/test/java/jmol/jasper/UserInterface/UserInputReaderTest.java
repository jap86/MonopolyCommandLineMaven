package jmol.jasper.UserInterface;

import jmol.jasper.UserInterface.Logic.UserInputReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class UserInputReaderTest {
    private ByteArrayInputStream inputStream;
    private UserInputReader userInputReader;
    private String[] options = new String[]{"OptionA", "OptionB", "OptionC"};
    private final int LOWER_BOUND = 10;
    private final int UPPER_BOUND = 100;

    @Test
    public void getStringTest() {
        String input = "test is ok";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals("test is ok", userInputReader.getString());
    }

    @Test
    public void getCharacterTest() {
        String input = "c";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals('c', userInputReader.getCharacter());
    }

    @Test
    public void getCharacterTestWithMultipleCharacters() {
        String input = "input";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals(null, userInputReader.getCharacter());
    }

    @Test
    public void getIntegerTest() {
        String input = "123";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals(123, userInputReader.getInteger());
    }

    @Test
    public void getIntegerTestNoValidNumber()  {
        String input = "123kj1";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals(null, userInputReader.getInteger());
    }

    @Test
    public void getBooleanTest() {
        String input = " ja \n j \n yes \n y \n n \n no \n nee";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertTrue(userInputReader.getBoolean());
        assertTrue(userInputReader.getBoolean());
        assertTrue(userInputReader.getBoolean());
        assertTrue(userInputReader.getBoolean());
        assertFalse(userInputReader.getBoolean());
        assertFalse(userInputReader.getBoolean());
        assertFalse(userInputReader.getBoolean());
    }

    @Test
    public void getBooleanTestNoValidBoolean() {
        String input = " yep \n nope";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals(null, userInputReader.getBoolean());
    }

    @Test
    public void getInputTestWithNoInput() {
        String input = "";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals(null, userInputReader.getString());
        assertEquals(null, userInputReader.getInteger());
        assertEquals(null, userInputReader.getCharacter());
        assertEquals(null, userInputReader.getBoolean());
        assertEquals(null, userInputReader.getMultipleChoiceAnswer(options));
        assertEquals(null, userInputReader.getIntegerWithBoundary(LOWER_BOUND, UPPER_BOUND));
    }

    @Test
    public void getMultipleChoiceAnswerTest() {
        String input = options[1];
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals(1, userInputReader.getMultipleChoiceAnswer(options));
    }

    @Test
    public void getMultipleChoiceAnswerTestNonValidAnswer() {
        String input = "0\n7";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals(null, userInputReader.getMultipleChoiceAnswer(options));
        assertEquals(null, userInputReader.getMultipleChoiceAnswer(options));
    }

    @Test
    public void getIntegerWithBoundTest() {
        String input = "45";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

        assertEquals(45, userInputReader.getIntegerWithBoundary(LOWER_BOUND, UPPER_BOUND));
    }

    @Test
    public void getIntegerWithBoundTestToHighInput() {
        String input = "\n145";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

         assertEquals(null, userInputReader.getIntegerWithBoundary(LOWER_BOUND, UPPER_BOUND));
    }

    @Test
    public void getIntegerWithBoundTestToLowInput() {
        String input = "\n5";
        setStreamAndReader(input);
        userInputReader = new UserInputReader();

         assertEquals(null, userInputReader.getIntegerWithBoundary(LOWER_BOUND, UPPER_BOUND));
    }

    private void setStreamAndReader(String input) {
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }
}

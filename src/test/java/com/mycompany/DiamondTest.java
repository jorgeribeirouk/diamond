package com.mycompany;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit test for simple App.
 */
public class DiamondTest {
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;



    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @ParameterizedTest
    @MethodSource("provideCorrectDiamonds")
    public void testDiamonds(String input, String expected) throws Exception {
        String[] arguments = {input};
        Diamond.main(arguments);

        assertThat(getOutput()).isEqualTo(expected);

    }

    private String getOutput() {
        return testOut.toString();
    }

    @ParameterizedTest
    @MethodSource("provideCorrectDiamondsAlternative")
    public void testDiamondsAlternativeSeparator(String input, String separator, String expected) throws Exception {
        String[] arguments = {input, separator};
        Diamond.main(arguments);

        assertThat(getOutput()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideWrongDiamonds")
    public void testWrongDiamonds(String input, String expected) throws Exception {
        String[] arguments = {input};
        Diamond.main(arguments);

        assertThat(getOutput()).isNotEqualTo(expected);
    }

    @Test
    public void whenInputIsNumber_throwsException() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            String[] arguments = {"1"};
            Diamond.main(arguments);
        });

        String expectedMessage = "Input must be on character between A and Z";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenInputIsInvalid_throwsException() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            String[] arguments = {"AB"};
            Diamond.main(arguments);
        });

        String expectedMessage = "Input must be on character between A and Z";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenMoreThan2ArgumentsAreProvided_throwsException() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            String[] arguments = {"A", "B", "!"};
            Diamond.main(arguments);
        });

        String expectedMessage = "Only 2 arguments allowed [input separator]";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenSeparatorIsInvalid_throwsException() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            String[] arguments = {"A", "!"};
            Diamond.main(arguments);
        });

        String expectedMessage = "Alternative Separator acceptable is '_'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenInputIsEmpty_throwsException() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            String[] arguments = {""};
            Diamond.main(arguments);
        });

        String expectedMessage = "Input must be on character between A and Z";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private static Stream<Arguments> provideCorrectDiamonds() throws IOException {
        return Stream.of(
                Arguments.of("A", "A"),
                Arguments.of("C", new String(DiamondTest.class.getClassLoader().getResourceAsStream("C.txt")
                        .readAllBytes())),
                Arguments.of("E", new String(DiamondTest.class.getClassLoader().getResourceAsStream("E.txt")
                        .readAllBytes())),
                Arguments.of("J", new String(DiamondTest.class.getClassLoader().getResourceAsStream("J.txt")
                        .readAllBytes())),
                Arguments.of("Q", new String(DiamondTest.class.getClassLoader().getResourceAsStream("Q.txt")
                        .readAllBytes())),
                Arguments.of("Z", new String(DiamondTest.class.getClassLoader().getResourceAsStream("Z.txt")
                        .readAllBytes()))
        );
    }

    private static Stream<Arguments> provideCorrectDiamondsAlternative() throws IOException {
        return Stream.of(
                Arguments.of("A", "_", "A"),
                Arguments.of("C", "_", new String(DiamondTest.class.getClassLoader().getResourceAsStream("C_.txt")
                        .readAllBytes())),
                Arguments.of("E", "_", new String(DiamondTest.class.getClassLoader().getResourceAsStream("E_.txt")
                        .readAllBytes())),
                Arguments.of("Q", "_", new String(DiamondTest.class.getClassLoader().getResourceAsStream("Q_.txt")
                        .readAllBytes()))
        );
    }

    private static Stream<Arguments> provideWrongDiamonds() throws IOException {
        return Stream.of(
                Arguments.of("A", "B"),
                Arguments.of("E", new String(DiamondTest.class.getClassLoader().getResourceAsStream("C.txt")
                        .readAllBytes())),
                Arguments.of("C", new String(DiamondTest.class.getClassLoader().getResourceAsStream("E.txt")
                        .readAllBytes()))
        );
    }
}

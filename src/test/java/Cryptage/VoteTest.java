package Cryptage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoteTest {
    static KeyGenCryptage keyGenCryptage;
    static MessageCrypte control;

    @BeforeEach
    void setUp() {
        this.keyGenCryptage = new KeyGenCryptage(256);
        control = new MessageCrypte(0, keyGenCryptage.getPublicKey());
    }

    @ParameterizedTest
    @MethodSource("errorControl")
    void testManInTheMiddle(int message, boolean result) {
        MessageCrypte messageCrypte = new MessageCrypte(message, keyGenCryptage.getPublicKey());
        messageCrypte.setProof(control.getProof());
        assertEquals(result, messageCrypte.isValid());
    }

    /*
    Il n'est pas nécessaire de tester pour les valeurs différentes de {0,1}
    car la classe Proof ne permet pas de générer des preuves pour des messages hors de cet ensemble.
     */
    @ParameterizedTest
    @MethodSource("normalControl")
    void testNormal(int message, boolean result) {
        MessageCrypte messageCrypte = new MessageCrypte(message, keyGenCryptage.getPublicKey());
        assertEquals(result, messageCrypte.isValid());
    }

    public static Stream<Arguments> errorControl() {
        return Stream.of(
                Arguments.of(-1, false),
                Arguments.of(0, false),
                Arguments.of(1, false),
                Arguments.of(2, false),
                Arguments.of(Integer.MAX_VALUE, false),
                Arguments.of(Integer.MIN_VALUE, false));
    }

    public static Stream<Arguments> normalControl() {
        return Stream.of(
                Arguments.of(0, true),
                Arguments.of(1, true));
    }


}

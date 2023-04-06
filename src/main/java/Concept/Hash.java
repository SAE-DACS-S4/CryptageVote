package Concept;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static BigInteger make(BigInteger... bigIntegers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (BigInteger bigInteger : bigIntegers) {
            stringBuilder.append(bigInteger.toString());
        }
        String elMix = stringBuilder.toString();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return new BigInteger(1, md.digest(elMix.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyGenTest {


    @Test
    public void testEtapeUnFormeP(){
        int taille = 50;
        KeyGen keyGen = new KeyGen(taille);
        BigInteger p = keyGen.etapeUn();

        assertEquals(p.bitCount(), taille);
        assertTrue(p.isProbablePrime(100));

        BigInteger pPrime = (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
        assertTrue(pPrime.isProbablePrime(100));
    }

}

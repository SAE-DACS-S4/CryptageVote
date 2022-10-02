import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EncryptTest {

    KeyGen keyGen = new KeyGen(50);

    HashMap<String, BigInteger> pk = keyGen.getPublicKey();

    BigInteger message = new BigInteger("1");

    Encrypt encrypt = new Encrypt(pk, message);

    @Test
    public void testTirerR() {
        BigInteger r = encrypt.tirerR();

        //on teste si r est bien compris entre 0 et p'-1
        BigInteger p = pk.get("p");
        BigInteger pPrime = (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
        assertTrue(r.compareTo(BigInteger.ZERO) >= 0 && r.compareTo(pPrime.subtract(BigInteger.ONE)) <= 0, "r n'est pas compris entre 0 et p'-1");
    }

}

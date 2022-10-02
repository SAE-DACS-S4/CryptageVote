import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class Encrypt {
    private HashMap<String, BigInteger> pk;
    private BigInteger message;

    private BigInteger r;
    private BigInteger u;
    private BigInteger v;

    public Encrypt(HashMap<String, BigInteger> pk, BigInteger message) {
        this.pk = pk;
        this.message = message;
    }


    public BigInteger tirerR() {
        BigInteger pPrime = (this.pk.get("p").subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));

        Random rand = new Random();
        BigInteger r = new BigInteger(pPrime.bitLength(), rand);

        //teste si r est dans [0;p'-1]
        while (r.compareTo(BigInteger.ZERO) < 0 || r.compareTo(pPrime.subtract(BigInteger.ONE)) > 0) {
            r = new BigInteger(pPrime.bitLength(), rand);
        }

        this.r = r;
        return r;
    }

    public BigInteger encryptU(BigInteger r) {
        BigInteger g = this.pk.get("g");
        BigInteger p = this.pk.get("p");

        BigInteger u = g.modPow(r, p);

        this.u = u;
        return u;
    }

    public BigInteger encryptV(BigInteger r) {

        return null;
    }

    public HashMap<String, BigInteger> getEncrypt() {

        return null;
    }
}

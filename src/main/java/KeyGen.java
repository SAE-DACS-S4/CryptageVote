import java.math.BigInteger;
import java.util.Random;

public class KeyGen {

    private int taille;
    private BigInteger p;
    private BigInteger g;
    private BigInteger x;
    private BigInteger h;


    public KeyGen(int taille) {
        this.taille = taille;
    }

    public BigInteger tirerPremierP() {
        BigInteger p = BigInteger.probablePrime(taille, new Random());
        BigInteger pPrime = (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
        while (!pPrime.isProbablePrime(100)) {
            p = BigInteger.probablePrime(taille, new Random());
            pPrime = (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
        }

        this.p = p;
        return p;
    }

    public BigInteger tirerElementG(BigInteger p) {

        return null;
    }

    public BigInteger tirerEntierX(BigInteger p) {

        return null;
    }

    public BigInteger calculerH(BigInteger g, BigInteger x, BigInteger p) {
        
        return null;
    }
}

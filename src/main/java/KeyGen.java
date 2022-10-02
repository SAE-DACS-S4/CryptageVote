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
        boolean gBonneForme = false;

        while (!gBonneForme) {
            BigInteger g = new BigInteger(p.bitLength(), new Random());
            //test si g est dans l'intervalle [0, p-1]
            if (g.compareTo(BigInteger.ZERO) > 0 && g.compareTo(p.subtract(BigInteger.ONE)) < 0) {

                BigInteger pPrime = (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
                //test si g^p' = 1 mod p
                if (g.modPow(pPrime, p).equals(BigInteger.ONE)) {
                    gBonneForme = true;
                    this.g = g;
                }
            }
        }
        return g;
    }

    public BigInteger tirerEntierX(BigInteger p) {
        BigInteger pPrime = (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
        BigInteger x = new BigInteger(pPrime.bitLength(), new Random());

        //test si x est dans l'intervalle [0, p'-1]
        while (x.compareTo(BigInteger.ZERO) < 0 || x.compareTo(pPrime.subtract(BigInteger.ONE)) > 0) {
            x = new BigInteger(pPrime.bitLength(), new Random());
        }

        this.x = x;
        return x;
    }

    public BigInteger calculerH(BigInteger g, BigInteger x, BigInteger p) {
        
        return null;
    }
}

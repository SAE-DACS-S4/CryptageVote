import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class KeyGen {

    private final int taille;
    private final BigInteger p;
    private final BigInteger g;
    private final BigInteger x;
    private final BigInteger h;


    public KeyGen(int taille) {
        this.taille = taille;

        this.p = tirerPremierP();
        this.g = tirerElementG(p);
        this.x = tirerEntierX(p);
        this.h = calculerH(g, x, p);
    }

    public BigInteger tirerPremierP() {
        BigInteger p = BigInteger.probablePrime(taille, new Random());
        BigInteger pPrime = (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
        while (!pPrime.isProbablePrime(100)) {
            p = BigInteger.probablePrime(taille, new Random());
            pPrime = (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
        }

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
        return x;
    }

    public BigInteger calculerH(BigInteger g, BigInteger x, BigInteger p) {
        return g.modPow(x, p);
    }

    public BigInteger getPublicKey() {
        HashMap<String, BigInteger> publicKey = new HashMap<>();
        publicKey.put("p", p);
        publicKey.put("g", g);
        publicKey.put("h", h);

        return h;
    }

    public BigInteger getPrivateKey() {
        return x;
    }
}

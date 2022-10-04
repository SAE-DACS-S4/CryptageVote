package Cryptage;

import java.math.BigInteger;
import java.util.HashMap;

import Concept.KeyGen;

public class KeyGenCryptage {
    private BigInteger p;
    private BigInteger g;
    private BigInteger h;
    private BigInteger x;

    public KeyGenCryptage(int taille) {
        KeyGen keyGen = new KeyGen(taille);
        HashMap<String, BigInteger> hashMap = keyGen.getPublicKey();

        this.p = hashMap.get("p");
        this.g = hashMap.get("g");
        this.h = hashMap.get("h");
        this.x = keyGen.getPrivateKey();
    }

    public PublicKey getPublicKey() {
        return new PublicKey(this.p, this.g, this.h);
    }

    public PrivateKey getPrivateKey() {
        return new PrivateKey(this.x);
    }
}

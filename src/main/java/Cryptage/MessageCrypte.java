package Cryptage;

import Concept.Agrege;
import Concept.Decrypt;
import Concept.Encrypt;
import Concept.Hash;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

public class MessageCrypte implements Serializable {
    private PublicKey pk;
    private BigInteger u;
    private BigInteger v;
    private Proof proof;

    /**
     * MessageCrypte génère un message crypté à partir d'un message clair et d'une clé publique générée à l'aide de la classe KeyGenCryptage
     *
     * @param message message clair à crypter de type int
     * @param pk      clé publique de type PublicKey
     */
    public MessageCrypte(int message, PublicKey pk) {
        this.pk = pk;
        HashMap<String, BigInteger> hashMapPK = new HashMap<String, BigInteger>();
        hashMapPK.put("p", pk.getP());
        hashMapPK.put("g", pk.getG());
        hashMapPK.put("h", pk.getH());

        Encrypt encrypt = new Encrypt(hashMapPK, BigInteger.valueOf(message));
        HashMap<String, BigInteger> messageCrypte = encrypt.getEncrypt();
        this.u = messageCrypte.get("u");
        this.v = messageCrypte.get("v");
        this.proof = buildProof(message, encrypt.getR());
    }

    private MessageCrypte(BigInteger u, BigInteger v) {
        this.u = u;
        this.v = v;
    }

    private BigInteger getU() {
        return this.u;
    }

    private BigInteger getV() {
        return this.v;
    }

    /**
     * Permet l'addition de deux messages cryptés
     *
     * @param messageCrypte premier message crypté de type MessageCrypte
     * @param pk            clé publique de type PublicKey
     * @return l'agrégation (la somme) des deux messages cryptés de type MessageCrypte
     */
    public MessageCrypte agregate(MessageCrypte messageCrypte, PublicKey pk) {
        HashMap<String, BigInteger> hashMapPK = new HashMap<String, BigInteger>();
        hashMapPK.put("p", pk.getP());
        hashMapPK.put("g", pk.getG());
        hashMapPK.put("h", pk.getH());

        Agrege agrege = new Agrege(hashMapPK);

        HashMap<String, BigInteger> message1 = new HashMap<String, BigInteger>();
        message1.put("u", this.u);
        message1.put("v", this.v);

        HashMap<String, BigInteger> message2 = new HashMap<String, BigInteger>();
        message2.put("u", messageCrypte.getU());
        message2.put("v", messageCrypte.getV());

        HashMap<String, BigInteger> messageAgreg = agrege.agregate(message1, message2);

        return new MessageCrypte(messageAgreg.get("u"), messageAgreg.get("v"));
    }

    /**
     * Permet de décrypter un message crypté à l'aide d'une clé privée
     *
     * @param pk clé privée de type PrivateKey
     * @param sk clé publique de type PublicKey
     * @return le message clair de type int
     */
    public int decrypt(PrivateKey sk, PublicKey pk) {

        HashMap<String, BigInteger> messageCrypte = new HashMap<String, BigInteger>();
        messageCrypte.put("u", this.u);
        messageCrypte.put("v", this.v);

        HashMap<String, BigInteger> hashMapPK = new HashMap<String, BigInteger>();
        hashMapPK.put("p", pk.getP());
        hashMapPK.put("g", pk.getG());
        hashMapPK.put("h", pk.getH());

        BigInteger BigIntSK = sk.getX();

        Decrypt decrypt = new Decrypt(messageCrypte, BigIntSK, hashMapPK);

        return decrypt.getMessageDecrypte().intValue();
    }

    public Proof buildProof(int m, BigInteger r) {
        BigInteger p, g, h, pPrime, challenge0, answer0, challenge1, answer1, A0, B0, A1, B1, gamma;
        Random RANDOM = new SecureRandom();
        p = pk.getP();
        g = pk.getG();
        h = pk.getH();
        BigInteger c1 = this.u;
        BigInteger c2 = this.v;

        // récupération p'
        pPrime = p.add(BigInteger.valueOf(-1)).divide(BigInteger.TWO);
        gamma = new BigInteger(pPrime.bitLength(), RANDOM);

        if (m == 0) {
            //P1
            challenge1 = new BigInteger(pPrime.bitLength(), RANDOM);
            answer1 = new BigInteger(pPrime.bitLength(), RANDOM);
            A1 = g.modPow(answer1, p).multiply(c1.modPow(challenge1, p)).mod(p);
            B1 = h.modPow(answer1, p).multiply(c2.multiply(g.modInverse(p)).modPow(challenge1, p)).mod(p);

            // P0
            A0 = g.modPow(gamma, p);
            B0 = h.modPow(gamma, p);

            challenge0 = Hash.make(c1, c2, A0, B0, A1, B1).mod(pPrime).subtract(challenge1).mod(pPrime);
            answer0 = gamma.subtract(r.multiply(challenge0)).mod(pPrime);
        } else if (m == 1) {
            //P0
            challenge0 = new BigInteger(pPrime.bitLength(), RANDOM);
            answer0 = new BigInteger(pPrime.bitLength(), RANDOM);
            A0 = g.modPow(answer0, p).multiply(c1.modPow(challenge0, p)).mod(p);
            B0 = h.modPow(answer0, p).multiply(c2.modPow(challenge0, p)).mod(p);

            //P1
            A1 = g.modPow(gamma, p);
            B1 = h.modPow(gamma, p);
            challenge1 = Hash.make(c1, c2, A0, B0, A1, B1).mod(pPrime).subtract(challenge0).mod(pPrime);
            answer1 = gamma.subtract(r.multiply(challenge1)).mod(pPrime);
        } else {
            challenge0 = null;
            challenge1 = null;
            answer0 = null;
            answer1 = null;
        }

        Proof proof = new Proof();
        proof.setChallenges(new BigInteger[]{challenge0, challenge1});
        proof.setAnswers(new BigInteger[]{answer0, answer1});
        return proof;
    }

    public Proof getProof() {
        return this.proof;
    }

    public void setProof(Proof proof) {
        this.proof = proof;
    }

    public boolean isValid() {
        ///////
        BigInteger p, g, h, pPrime;
        p = pk.getP();
        g = pk.getG();
        h = pk.getH();
        ///////
        pPrime = p.add(BigInteger.valueOf(-1)).divide(BigInteger.TWO);
        BigInteger challenge0 = this.proof.getChallenges()[0];
        BigInteger challenge1 = this.proof.getChallenges()[1];
        BigInteger answer0 = this.proof.getAnswers()[0];
        BigInteger answer1 = this.proof.getAnswers()[1];
        BigInteger c1 = this.u;
        BigInteger c2 = this.v;
        ///////
        BigInteger A0 = g.modPow(answer0, p).multiply(c1.modPow(challenge0, p)).mod(p);
        BigInteger B0 = h.modPow(answer0, p).multiply(c2.modPow(challenge0, p)).mod(p);
        BigInteger A1 = g.modPow(answer1, p).multiply(c1.modPow(challenge1, p)).mod(p);
        BigInteger B1 = h.modPow(answer1, p).multiply(c2.multiply(g.modInverse(p)).modPow(challenge1, p)).mod(p);
        BigInteger hash = Hash.make(c1, c2, A0, B0, A1, B1);
        ///////
        return hash.mod(pPrime).equals(challenge0.add(challenge1).mod(pPrime));
    }

    public String toString() {
        return "u = " + this.u + " v = " + this.v;
    }


}

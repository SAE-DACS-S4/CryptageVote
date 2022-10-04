package Cryptage;

import java.math.BigInteger;
import java.util.HashMap;

import Concept.Encrypt;
import Concept.Decrypt;
import Concept.Agrege;

public class MessageCrypte {
    private BigInteger u;
    private BigInteger v;

    public MessageCrypte(int message, PublicKey pk) {
        HashMap<String, BigInteger> hashMapPK = new HashMap<String, BigInteger>();
        hashMapPK.put("p", pk.getP());
        hashMapPK.put("g", pk.getG());
        hashMapPK.put("h", pk.getH());

        Encrypt encrypt = new Encrypt(hashMapPK, BigInteger.valueOf(message));

        HashMap<String, BigInteger> messageCrypte = encrypt.getEncrypt();

        this.u = messageCrypte.get("u");
        this.v = messageCrypte.get("v");
    }

    private MessageCrypte(BigInteger u, BigInteger v) {
        this.u = u;
        this.v = v;
    }

    public BigInteger getU() {
        return this.u;
    }

    public BigInteger getV() {
        return this.v;
    }

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

    public int decrypt(PrivateKey sk, PublicKey pk) {

        HashMap<String, BigInteger> messageCrypte = new HashMap<String, BigInteger>();
        messageCrypte.put("u", this.u);
        messageCrypte.put("v", this.v);

        HashMap<String, BigInteger> hashMapPK = new HashMap<String, BigInteger>();
        hashMapPK.put("p", pk.getP());
        hashMapPK.put("g", pk.getG());
        hashMapPK.put("h", pk.getH());

        BigInteger BigIntSK = sk.getX();

        Decrypt decrypt = new Decrypt(hashMapPK, BigIntSK, messageCrypte);

        return decrypt.getMessageDecrypte().intValue();
    }

}

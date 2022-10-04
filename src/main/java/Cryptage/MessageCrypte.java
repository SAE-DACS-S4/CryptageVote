package Cryptage;

import java.math.BigInteger;

public class MessageCrypte {
    private BigInteger u;
    private BigInteger v;

    public MessageCrypte(BigInteger u, BigInteger v) {
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
        BigInteger u = this.u.multiply(messageCrypte.getU()).mod(pk.getP());
        BigInteger v = this.v.multiply(messageCrypte.getV()).mod(pk.getP());

        return new MessageCrypte(u, v);
    }
}

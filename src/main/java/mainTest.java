import java.math.BigInteger;
import java.util.HashMap;

public class mainTest{

    public static void main (String[] args){
        KeyGen keyGen = new KeyGen(50);
        System.out.println("La clé privée est : " + keyGen.getPrivateKey());
        System.out.println("La clé publique est : " + keyGen.getPublicKey());

        BigInteger message = BigInteger.valueOf(24567);
        System.out.println("Le message non crypté est : " + message);

        Encrypt encrypt = new Encrypt(keyGen.getPublicKey(), message);
        HashMap<String, BigInteger> messagecrypte = encrypt.getEncrypt();
        System.out.println("Le message crypté est : " + messagecrypte);

        Decrypt decrypt = new Decrypt(messagecrypte, keyGen.getPrivateKey(), keyGen.getPublicKey());

        BigInteger m = decrypt.getMessageDecrypte();
        System.out.println("Le message déchiffré est : " + m);
    }
}

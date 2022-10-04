package Cryptage;

public class mainCryptage {
    public static void main(String[] args) {
        KeyGenCryptage keyGenCryptage = new KeyGenCryptage(100);
        PublicKey publicKey = keyGenCryptage.getPublicKey();
        PrivateKey privateKey = keyGenCryptage.getPrivateKey();

        System.out.println("public key = " + publicKey.getP() + ", " + publicKey.getG() + ", " + publicKey.getH());
        System.out.println("private key = " + privateKey.getX());

        System.out.println("message 1 = 10");
        MessageCrypte messageCrypte1 = new MessageCrypte(10, publicKey);
        //System.out.println("message crypte 1 : u = " + messageCrypte1.getU() + " v = " + messageCrypte1.getV());

        System.out.println("message 2 = 20");
        MessageCrypte messageCrypte2 = new MessageCrypte(20, publicKey);
        //System.out.println("message crypte 2 : u = " + messageCrypte2.getU() + " v = " + messageCrypte2.getV());

        MessageCrypte MessageAgrege = messageCrypte1.agregate(messageCrypte2, publicKey);
        //System.out.println("message agregé : u = " + MessageAgrege.getU() + " v = " + MessageAgrege.getV());

        System.out.println("message decrypté : " + MessageAgrege.decrypt(privateKey, publicKey));


    }
}


package aldovalzani.capstone_be.tools;

import aldovalzani.capstone_be.exceptions.CryptoException;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.Base64;

@Component
public class KeyGenerator {
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptoException("Errore nella generazione della coppia di chiavi");
        }
    }

    public String generateAddress(PublicKey publicKey) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(publicKey.getEncoded());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptoException("Errore nella generazione dell'indirizzo");
        }
    }
}

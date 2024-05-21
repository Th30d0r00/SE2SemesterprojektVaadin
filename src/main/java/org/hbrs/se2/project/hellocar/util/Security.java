package org.hbrs.se2.project.hellocar.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Klasse fürs Management der Passwörter in gehashter Form mit Salt
 */
public class Security {

    /**
     * Beim Registrieren neuer User:
     * Salt generieren, der in der Datenbank zusammen mit dem erstellten Hashwert (getHash()) gespeichert wird
     * und in die Berechnung des Hashwertes mit eingeht
     */
    public byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Generiert einen Hashwert der länge 256Bit mit 10000 Runden und einem zuvor generierten Salt
     */
    public byte[] getHash(String password, byte[] salt) {
        byte[] hash = new byte[0];
        try {
            char[] passwordChar = password.toCharArray();
            PBEKeySpec keySpec = new PBEKeySpec(passwordChar, salt, 10000, 256);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            hash = keyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Problem beim Hash Algorithmus");
        } catch (InvalidKeySpecException e) {
            System.out.println("Problem mit den Schlüsselparametern");
        }
        return hash;
    }

    /**
     * Die Methode soll der Authentifizierung eines bestehenden Nutzers dienen.
     * Dazu nimmt sie das vom Nutzer eingegebene Klartext-Passwort und die dem Nutzer zugehörigen Salt- und Hashwerte aus der DB.
     * Aus dem eingegebenen Passwort (passwordToConfirm) und dem Salt des Nutzers wird ein Hashwert berechnet und mit dem
     * in der DB gespeicherten Hash verglichen. Bei Positiven Vergleich kann der Nutzer authentifiziert werden.
     */
    public Boolean testHash(String passwordToConfirm, byte[] storedSalt, byte[] storedHash) {
        byte[] testHash = new byte[0];
        try {
            char[] passwordChar = passwordToConfirm.toCharArray();
            PBEKeySpec keySpec = new PBEKeySpec(passwordChar, storedSalt, 10000, 256);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            testHash = keyFactory.generateSecret(keySpec).getEncoded();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Problem beim Hash Algorithmus bei der Authentifizierung");
        } catch (InvalidKeySpecException e) {
            System.out.println("Problem mit den Schlüsselparametern bei der Authentifizierung");
        }
        return MessageDigest.isEqual(storedHash, testHash);
    }

}


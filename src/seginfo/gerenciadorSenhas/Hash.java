package seginfo.gerenciadorSenhas;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hash {
    private MessageDigest gerador;

    public Hash(String algoritmo) {
        try {
            gerador = MessageDigest.getInstance(algoritmo);
        } catch (NoSuchAlgorithmException e) {
            System.err.printf("O algoritmo %s não é suportado, encerrando!", algoritmo);
            System.exit(1);
        }
    }

    public String codificar(String s) {
        byte[] bytes = gerador.digest(s.getBytes());
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    public String gerarSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return codificar(new String(salt)); // Retorna o sal codificado
    }
}
package seginfo.gerenciadorSenhas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GerenciadorSenhas {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sal"; // Altere para o seu banco
    private static final String DB_USER = "root"; // Altere para o seu usuário
    private static final String DB_PASSWORD = "root"; // Altere para a sua senha

    public static String codificar(String senha, String salt) {
        Hash h = new Hash("SHA-1");
        return h.codificar(senha + salt);
    }

    public static boolean autenticar(String email, String senha) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT senha, salt FROM usuarios WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("senha");
                String salt = rs.getString("salt");
                String tentativa = codificar(senha, salt);
                return hash.equals(tentativa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean cadastrar(String nome, String email, String senha) {
        String salt = new Hash("SHA-1").gerarSalt();
        String hash = codificar(senha, salt);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO usuarios (nome, email, senha, salt) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, hash);
            stmt.setString(4, salt);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false se o email já existir ou houver erro
        }
    }
}
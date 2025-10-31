import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoReader {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {

            String sql = "SELECT estoque FROM Produto WHERE id = 1";

            try {
                conn.setAutoCommit(false);

                System.out.println("[PRODUTO-READER] Lendo estoque (Leitura 1)..");
                try (PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
                    ResultSet rs1 = pstmt1.executeQuery();
                    if (rs1.next()) {
                        System.out.println("[PRODUTO-READER] Estoque lido (1): " + rs1.getInt("estoque"));
                    }
                }

                // Espera controlada para simular concorrência
                System.out.println("[PRODUTO-READER] Aguardando 2 segundos (antes do commit do Writer)...");
                Thread.sleep(2000);

                System.out.println("[PRODUTO-READER] Lendo estoque (Leitura 2)...");
                try (PreparedStatement pstmt2 = conn.prepareStatement(sql)) {
                    ResultSet rs2 = pstmt2.executeQuery();
                    if (rs2.next()) {
                        System.out.println("[PRODUTO-READER] Estoque lido (2): " + rs2.getInt("estoque"));
                    }
                }

                conn.commit();
                System.out.println("[PRODUTO-READER] COMMIT da leitura executado.");

            } catch (Exception e) {
                conn.rollback();
                System.out.println("[PRODUTO-READER] Erro, ROLLBACK executado: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Falha na conexão ou transação: " + e.getMessage());
        }
    }
}

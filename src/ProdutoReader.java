import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoReader {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {

            String sql = "SELECT estoque FROM Produto WHERE id = 1";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // LEITURA 1
                System.out.println("[PRODUTO-READER] Lendo estoque (Leitura 1)...");
                ResultSet rs1 = pstmt.executeQuery();
                if (rs1.next()) {
                    System.out.println("[PRODUTO-READER] Estoque lido (1): " + rs1.getInt("estoque"));
                }

                System.out.println("[PRODUTO-READER] Aguardando 5 segundos (esperando Writer)...");
                Thread.sleep(5000); // 5 segundos

                // LEITURA 2 (Para testar Dirty Read ou Non-Repeatable Read)
                System.out.println("[PRODUTO-READER] Lendo estoque (Leitura 2)...");
                ResultSet rs2 = pstmt.executeQuery();
                if (rs2.next()) {
                    System.out.println("[PRODUTO-READER] Estoque lido (2): " + rs2.getInt("estoque"));
                }

                conn.commit(); // Finaliza a transação do leitor

            } catch (Exception e) {
                conn.rollback();
                System.out.println("[PRODUTO-READER] Erro, ROLLBACK executado: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Falha na conexão ou transação: " + e.getMessage());
        }
    }
}
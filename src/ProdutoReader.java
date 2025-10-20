import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoReader {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {

            String sql = "SELECT estoque FROM Produto WHERE id = 1";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                System.out.println("[PRODUTO-READER] Lendo estoque (Leitura 1)...");
                ResultSet rs1 = pstmt.executeQuery();
                if (rs1.next()) {
                    System.out.println("[PRODUTO-READER] Estoque lido (1): " + rs1.getInt("estoque"));
                }

                // *** MUDANÇA AQUI ***
                System.out.println("[PRODUTO-READER] Aguardando 10 segundos (esperando Writer)...");
                Thread.sleep(10000); // 10 segundos

                System.out.println("[PRODUTO-READER] Lendo estoque (Leitura 2)...");
                ResultSet rs2 = pstmt.executeQuery();
                if (rs2.next()) {
                    System.out.println("[PRODUTO-READER] Estoque lido (2): " + rs2.getInt("estoque"));
                }

                conn.commit();

            } catch (Exception e) {
                conn.rollback();
                System.out.println("[PRODUTO-READER] Erro, ROLLBACK executado: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Falha na conexão ou transação: " + e.getMessage());
        }
    }
}
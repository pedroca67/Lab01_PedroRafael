import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoWriter {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {

            String sql = "UPDATE Produto SET estoque = 40 WHERE id = 1";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                System.out.println("[PRODUTO-WRITER] Reduzindo estoque para 40 (Produto 1)...");
                pstmt.executeUpdate();
                System.out.println("[PRODUTO-WRITER] Estoque atualizado (pendente).");

                System.out.println("[PRODUTO-WRITER] Aguardando 10 segundos antes do ROLLBACK...");
                Thread.sleep(10000); // 10 segundos

                conn.rollback();
                System.out.println("[PRODUTO-WRITER] ROLLBACK executado.");

            } catch (Exception e) {
                conn.rollback();
                System.out.println("[PRODUTO-WRITER] Erro, ROLLBACK executado: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Falha na conexão ou transação: " + e.getMessage());
        }
    }
}
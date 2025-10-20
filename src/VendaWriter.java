import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VendaWriter {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {

            String sql = "INSERT INTO Venda (quantidade, valor) VALUES (5, 99.99)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                System.out.println("[VENDA-WRITER] Inserindo nova Venda...");
                pstmt.executeUpdate();

                System.out.println("[VENDA-WRITER] Aguardando 5 segundos antes do COMMIT...");
                Thread.sleep(5000); // 5 segundos

                conn.commit();
                System.out.println("[VENDA-WRITER] COMMIT realizado.");

            } catch (Exception e) {
                conn.rollback();
                System.out.println("[VENDA-WRITER] Erro, ROLLBACK executado: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Falha na conexão ou transação: " + e.getMessage());
        }
    }
}
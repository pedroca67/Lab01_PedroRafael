import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaReader {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {

            String sql = "SELECT COUNT(*) AS total FROM Venda";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // LEITURA 1
                System.out.println("[VENDA-READER] Contando Vendas (Leitura 1)...");
                ResultSet rs1 = pstmt.executeQuery();
                if (rs1.next()) {
                    System.out.println("[VENDA-READER] Total (1): " + rs1.getInt("total"));
                }

                System.out.println("[VENDA-READER] Aguardando 10 segundos (esperando Writer)...");
                Thread.sleep(10000); // 10 segundos

                // LEITURA 2 (Para testar Phantom Read)
                System.out.println("[VENDA-READER] Contando Vendas (Leitura 2)...");
                ResultSet rs2 = pstmt.executeQuery();
                if (rs2.next()) {
                    System.out.println("[VENDA-READER] Total (2): " + rs2.getInt("total"));
                }

                conn.commit(); // Finaliza a transação do leitor

            } catch (Exception e) {
                conn.rollback();
                System.out.println("[VENDA-READER] Erro, ROLLBACK executado: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Falha na conexão ou transação: " + e.getMessage());
        }
    }
}
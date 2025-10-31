import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetDatabase {
    public static void main(String[] args) {
        System.out.println("[RESET] Iniciando reset do banco de dados...");

        String sqlProduto = "UPDATE Produto SET estoque = 50 WHERE id = 1";
        // Limpa TODAS as vendas para garantir que a contagem comece em 0
        String sqlVenda = "DELETE FROM Venda";

        try (Connection conn = DBUtil.getConnection()) {

            try (PreparedStatement pstmtProduto = conn.prepareStatement(sqlProduto);
                 PreparedStatement pstmtVenda = conn.prepareStatement(sqlVenda)) {
// A mensagem de saída, após execultar o .bat
                // Reseta o Produto
                int linhasProduto = pstmtProduto.executeUpdate();
                System.out.println("[RESET] Tabela 'Produto' resetada (Linhas afetadas: " + linhasProduto + ")");

                // Limpa as Vendas
                int linhasVenda = pstmtVenda.executeUpdate();

                System.out.println("[RESET] Tabela 'Venda' limpa (Total de linhas removidas: " + linhasVenda + ")");

                conn.commit();
                System.out.println("[RESET] Banco de dados resetado com sucesso! (Estoque: 50, Vendas: 0).");

            } catch (Exception e) {
                conn.rollback();
                System.out.println("[RESET] Erro, ROLLBACK executado: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Falha na conexão ou transação: " + e.getMessage());
        }
    }
}
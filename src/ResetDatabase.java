import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetDatabase {
    public static void main(String[] args) {
        System.out.println("[RESET] Iniciando reset do banco de dados...");

        String sqlProduto = "UPDATE Produto SET estoque = 50 WHERE id = 1";
        // Vamos assumir que as vendas originais são IDs 1, 2, 3
        String sqlVenda = "DELETE FROM Venda WHERE id > 3";

        try (Connection conn = DBUtil.getConnection()) {

            try (PreparedStatement pstmtProduto = conn.prepareStatement(sqlProduto);
                 PreparedStatement pstmtVenda = conn.prepareStatement(sqlVenda)) {

                // Reseta o Produto
                int linhasProduto = pstmtProduto.executeUpdate();
                System.out.println("[RESET] Tabela 'Produto' resetada (Linhas afetadas: " + linhasProduto + ")");

                // Limpa as Vendas
                int linhasVenda = pstmtVenda.executeUpdate();
                if (linhasVenda > 0) {
                    System.out.println("[RESET] Tabela 'Venda' limpa (Linhas fantasmas removidas: " + linhasVenda + ")");
                } else {
                    System.out.println("[RESET] Tabela 'Venda' já estava limpa.");
                }

                conn.commit();
                System.out.println("[RESET] Banco de dados resetado com sucesso!");

            } catch (Exception e) {
                conn.rollback();
                System.out.println("[RESET] Erro, ROLLBACK executado: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Falha na conexão ou transação: " + e.getMessage());
        }
    }
}
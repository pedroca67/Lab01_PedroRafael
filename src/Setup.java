import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Setup {
    private static final Properties props = new Properties();

    public static void main(String[] args) {
        // Carrega as propriedades do arquivo .env
        try (FileInputStream fis = new FileInputStream(".env")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo .env: " + e.getMessage());
            System.exit(1);
        }

        // A conexão já será feita NO banco de dados do .env (DB_NAME)
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("Conectado ao banco de dados: " + props.getProperty("DB_NAME"));

            // 1. Apaga as tabelas antigas (na ordem correta por causa da FK)
            stmt.executeUpdate("DROP TABLE IF EXISTS Venda");
            stmt.executeUpdate("DROP TABLE IF EXISTS Produto");
            System.out.println("Tabelas antigas removidas (se existiam).");

            // 2. Cria a tabela Produto
            stmt.executeUpdate("""
                CREATE TABLE Produto (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    nome VARCHAR(100),
                    estoque INT
                )
            """);
            System.out.println("Tabela 'Produto' criada.");

            // 3. Cria a tabela Venda
            stmt.executeUpdate("""
                CREATE TABLE Venda (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    produto_id INT,
                    quantidade INT,
                    data_venda TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (produto_id) REFERENCES Produto(id)
                )
            """);
            System.out.println("Tabela 'Venda' criada.");

            // 4. Insere os dados fictícios iniciais
            stmt.executeUpdate("INSERT INTO Produto (id, nome, estoque) VALUES (1, 'Produto Teste', 50)");
            System.out.println("Registro inicial inserido em 'Produto' (Estoque: 50).");

            // 5. Confirma a transação
            conn.commit();
            System.out.println("Setup das tabelas concluído com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro de SQL durante o setup: " + e.getMessage());
            System.err.println("Verifique se o banco '" + props.getProperty("DB_NAME") + "' existe e se o usuário '" + props.getProperty("DB_USER") + "' tem permissão.");
            e.printStackTrace();
        }
    }
}
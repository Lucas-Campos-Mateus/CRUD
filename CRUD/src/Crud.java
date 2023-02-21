import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

public class Crud {
	Scanner in = new Scanner(System.in);

	public void Select() throws SQLException {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.recuperarConexao();

		PreparedStatement stm = connection.prepareStatement("SELECT ID, NOME, CPF FROM PESSOAS");
		stm.execute();

		ResultSet rst = stm.getResultSet();

		while (rst.next()) {
			Integer id = rst.getInt("ID");
			System.out.println("ID: " + id);
			String nome = rst.getString("NOME");
			System.out.println("Nome: " + nome);
			String cpf = rst.getString("CPF");
			System.out.println("CPF: " + cpf + "\n");
		}

		rst.close();
		stm.close();
		connection.close();
	}

	public void Insercao() throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.recuperarConexao();

		System.out.println("NOME: ");
		String NOME = in.next();

		System.out.println("CPF: ");
		Long CPFConverter = in.nextLong();

		String CPF = Long.toString(CPFConverter);

		try {
			PreparedStatement stm = connection.prepareStatement("INSERT INTO PESSOAS (nome, cpf) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stm.setString(1, NOME);
			stm.setString(2, CPF);
			stm.execute();

			ResultSet rst = stm.getGeneratedKeys();

			while (rst.next()) {
				Integer id = rst.getInt(1);
				System.out.println("O id criado foi: " + id);
			}
			rst.close();
			stm.close();

		} catch (MysqlDataTruncation Ex) {
			System.out.println("CPF com numeração maior do que o permitido, tente novamente.");
		} catch (SQLIntegrityConstraintViolationException Ex) {
			System.out.println("CPF já existente, tente novamente.");
		} catch (SQLException Ex) {
			System.out.println("Preencha o CPF apenas com números.");
		} finally {
			connection.close();
		}
	}

	public void Delete() throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.recuperarConexao();

		System.out.println("Digite o número do ID que deseja deletar: ");
		Integer id = in.nextInt();

		PreparedStatement stm = connection.prepareStatement("DELETE FROM PESSOAS WHERE ID = ?");
		stm.setInt(1, +id);
		stm.execute();

		Integer linhasModificadas = stm.getUpdateCount();

		System.out.println("Quantidade de linhas que foram modificadas: " + linhasModificadas);

		stm.close();
		connection.close();

	}

	public void Update() throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.recuperarConexao();
		try {
			System.out.println("Digite o ID do registro que deseja modificar: ");
			Integer ID = in.nextInt();

			System.out.println("Escolha a opção que deseja: " + "\nPara editar o nome: 1" + "\nPara editar o CPF: 2"
					+ "\nPara editar ambos: 3");

			int escolha = in.nextInt();

			String NOME;

			try {

				if (escolha == 1) {

					System.out.println("Digite o novo nome do registro a qual deseja editar: ");
					NOME = in.next();

					PreparedStatement stm = connection.prepareStatement("UPDATE PESSOAS SET NOME = ? WHERE ID = ?");
					stm.setString(1, NOME);
					stm.setInt(2, ID);
					stm.execute();

					System.out.println("Linha modificada do ID: " + ID);
					stm.close();

				} else if (escolha == 2) {
					System.out.println("Caso tenha digitado errado o número do CPF, edite o CPF do registro agora: ");
					Long CPFConverter = in.nextLong();

					String CPF = Long.toString(CPFConverter);

					PreparedStatement stm = connection.prepareStatement("UPDATE PESSOAS SET CPF = ? WHERE ID = ?");
					stm.setString(1, CPF);
					stm.setInt(2, ID);
					stm.execute();

					System.out.println("Linha modificada do ID: " + ID);
					stm.close();
				}

			} catch (MysqlDataTruncation Ex) {
				System.out.println("CPF com numeração maior do que o permitido, tente novamente.");
			} catch (SQLIntegrityConstraintViolationException Ex) {
				System.out.println("CPF já existente, tente novamente.");
			}
			try {
				if (escolha == 3) {
					System.out.println("Digite o novo nome do registro a qual deseja editar: ");
					NOME = in.next();
					System.out.println("Caso tenha digitado errado o número do CPF, edite o CPF do registro agora: ");
					Long CPFConverter = in.nextLong();

					String CPF = Long.toString(CPFConverter);

					PreparedStatement stm = connection
							.prepareStatement("UPDATE PESSOAS SET NOME = ?, CPF = ? WHERE ID = ?");
					stm.setString(1, NOME);
					stm.setString(2, CPF);
					stm.setInt(3, ID);
					stm.execute();

					System.out.println("Linha modificada do ID: " + ID);
					stm.close();
				}
			} catch (MysqlDataTruncation Ex) {
				System.out.println("CPF com numeração maior do que o permitido, tente novamente.");
			} catch (SQLIntegrityConstraintViolationException Ex) {
				System.out.println("CPF já existente, tente novamente.");
			} catch (InputMismatchException Ex) {
				System.out.println("Apenas numéricos.");
			}
			if (escolha < 1 || escolha > 3) {
				System.out.println("Número escolhido incorreto dentre as opções, tente novamente.");
			}

		} finally {
			connection.close();
		}

	}
}

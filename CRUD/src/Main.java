import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {

		Crud crud = new Crud();
		Scanner in = new Scanner(System.in);

		try {
			System.out.println("O que deseja fazer: " + "\n (1) Inserir um registro." + "\n (2) Consultar os registros."
					+ "\n (3) Deletar um registro." + "\n (4) Modificar um registro. \n");
			int i = in.nextInt();

			if (i == 1) {
				crud.Insercao();
			} else if (i == 2) {
				crud.Select();
			} else if (i == 3) {
				crud.Delete();
			} else if (i == 4) {
				crud.Select();
				crud.Update();
			}
			if (i < 1 || i > 4)
				System.out.println("Número escolhido incorreto dentre as opções, tente novamente.");

		} catch (InputMismatchException Ex) {
			System.out.println("Apenas numéricos.");
		}
	}
}

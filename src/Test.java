import java.util.*;

public class Test {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		SintaxAnaliz sa = new SintaxAnaliz();
		int t;

		System.out.println();
		System.out.println("1 - Арефметический, логический и др. супер-анализатор и много мучений №1");
		System.out.println("0 - Выйти");

		while (true) {
			System.out.print("Ваш выбор - ");
			t = in.nextInt();
			switch (t) {
				case 1:
					sa.checkArithmeticExpression();
					break;
				case 0:
					System.exit(t);
					break;
				default:
					System.out.println("Такого варианта нет!!");
			}
		}
	}
}

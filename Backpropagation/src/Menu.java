import java.util.Scanner;

public class Menu {

	private static Scanner in = new Scanner(System.in);
	private String pathDataSetOne;
	private String pathDataSetTwo;
	private String pathDataSetThree;

	
	public String getPathDataSetOne() {
		return pathDataSetOne;
	}

	public void setPathDataSetOne(String pathDataSetOne) {
		this.pathDataSetOne = pathDataSetOne;
	}

	public String getPathDataSetTwo() {
		return pathDataSetTwo;
	}

	public void setPathDataSetTwo(String pathDataSetTwo) {
		this.pathDataSetTwo = pathDataSetTwo;
	}

	public String getPathDataSetThree() {
		return pathDataSetThree;
	}

	public void setPathDataSetThree(String pathDataSetThree) {
		this.pathDataSetThree = pathDataSetThree;
	}

	public Menu() {
		super();

	}

	public void show() {
		int option = 0;
		
		try {
		do{
			System.out.println("----------Menu----------"
					+ "\n------------------------");
			System.out.println("0. Cancer");
			System.out.println("1. Card");
			System.out.println("2. Diabetes");
			System.out.println("3. Gene");
			System.out.println("4. Glass");
			System.out.println("5. Heart");
			System.out.println("6. Horse");
			System.out.println("7. Mushroom");
			System.out.println("8. Soybean");
			System.out.println("9. Thryroid");
			System.out.println("10. Building");
			System.out.println("11. Flare");
			System.out.println("12. Pruebas");
			System.out.println("13. Exit");
			System.out.println("------------------------\n");
			System.out.println("Choose a data set: \n");

			option = in.nextInt();

	

			switch (option) {
			case 0:
				pathDataSetOne = "files/cancer/cancer1.dt";
				pathDataSetTwo = "files/cancer/cancer2.dt";
				pathDataSetThree = "files/cancer/cancer3.dt";
				break;
			case 1:
				pathDataSetOne = "files/card/card1.dt";
				pathDataSetTwo = "files/card/card2.dt";
				pathDataSetThree = "files/card/card3.dt";
				break;
			case 2:
				pathDataSetOne = "files/diabetes/diabetes1.dt";
				pathDataSetTwo = "files/diabetes/diabetes2.dt";
				pathDataSetThree = "files/diabetes/diabetes3.dt";
				break;
			case 3:
				pathDataSetOne = "files/gene/gene1.dt";
				pathDataSetTwo = "files/gene/gene2.dt";
				pathDataSetThree = "files/gene/gene3.dt";
				break;
			case 4:
				pathDataSetOne = "files/glass/glass1.dt";
				pathDataSetTwo = "files/glass/glass2.dt";
				pathDataSetThree = "files/glass/glass3.dt";
				break;
			case 5:
				pathDataSetOne = "files/heart/heart1.dt";
				pathDataSetTwo = "files/heart/heart2.dt";
				pathDataSetThree = "files/heart/heart3.dt";
				break;
			case 6:
				pathDataSetOne = "files/horse/horse1.dt";
				pathDataSetTwo = "files/horse/horse2.dt";
				pathDataSetThree = "files/horse/horse3.dt";
				break;
			case 7:
				pathDataSetOne = "files/mushroom/mushroom1.dt";
				pathDataSetTwo = "files/mushroom/mushroom2.dt";
				pathDataSetThree = "files/mushroom/mushroom3.dt";
				break;
			case 8:
				pathDataSetOne = "files/soybean/soybean1.dt";
				pathDataSetTwo = "files/soybean/soybean2.dt";
				pathDataSetThree = "files/soybean/soybean3.dt";
				break;
			case 9:
				pathDataSetOne = "files/thyroid/thyroid1.dt";
				pathDataSetTwo = "files/thyroid/thyroid2.dt";
				pathDataSetThree = "files/thyroid/thyroid3.dt";
				break;
			case 10:
				pathDataSetOne = "files/building/building1.dt";
				pathDataSetTwo = "files/building/building2.dt";
				pathDataSetThree = "files/building/building3.dt";
				break;
			case 11:
				pathDataSetOne = "files/flare/flare1.dt";
				pathDataSetTwo = "files/flare/flare2.dt";
				pathDataSetThree = "files/flare/flare3.dt";
				break;
			case 12:
				pathDataSetOne = "files/pruebas/prueba1.dt";
				break;
			case 13:
				System.out.println("Finished");
				System.exit(0);
				}

		} while (option > 13 || option < 0);
		
		} catch (Exception e) {
			System.out.println("Invalid option");
		}
	}
}

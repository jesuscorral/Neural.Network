import java.util.Scanner;

public class Menu {

	private static Scanner in = new Scanner(System.in);
	private String pathDataSetOne;
	private String pathDataSetTwo;
	private String pathDataSetThree;
	
	static final String PATH = "files/cancer";

	
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
			System.out.println("10. Flare");	
			System.out.println("11. Exit");
			System.out.println("------------------------\n");
			System.out.println("Choose a data set: \n");

			option = in.nextInt();

			switch (option) {
			case 0:
				System.out.println("Dentro de la eleccion");
				pathDataSetOne = "files/cancer/cancer1.dt";
				pathDataSetTwo = "files/cancer/cancer2.dt";
				pathDataSetThree = "files/cancer/cancer3.dt";
				break;
			case 1:
				pathDataSetOne = "files/cancer/card1.dt";
				pathDataSetTwo = "files/cancer/card2.dt";
				pathDataSetThree = "files/cancer/card3.dt";
				break;
			case 2:
				pathDataSetOne = "files/cancer/diabetes1.dt";
				pathDataSetTwo = "files/cancer/diabetes2.dt";
				pathDataSetThree = "files/cancer/diabetes3.dt";
				break;
			case 3:
				pathDataSetOne = "files/cancer/gene1.dt";
				pathDataSetTwo = "files/cancer/gene2.dt";
				pathDataSetThree = "files/cancer/gene3.dt";
				break;
			case 4:
				pathDataSetOne = "files/cancer/glass1.dt";
				pathDataSetTwo = "files/cancer/glass2.dt";
				pathDataSetThree = "files/cancer/glass3.dt";
				break;
			case 5:
				pathDataSetOne = "files/cancer/heart1.dt";
				pathDataSetTwo = "files/cancer/heart2.dt";
				pathDataSetThree = "files/cancer/heart3.dt";
				break;
			case 6:
				pathDataSetOne = "files/cancer/horse1.dt";
				pathDataSetTwo = "files/cancer/horse2.dt";
				pathDataSetThree = "files/cancer/horse3.dt";
				break;
			case 7:
				pathDataSetOne = "files/cancer/mushroom1.dt";
				pathDataSetTwo = "files/cancer/mushroom2.dt";
				pathDataSetThree = "files/cancer/mushroom3.dt";
				break;
			case 8:
				pathDataSetOne = "files/cancer/soybean1.dt";
				pathDataSetTwo = "files/cancer/soybean2.dt";
				pathDataSetThree = "files/cancer/soybean3.dt";
				break;
			case 9:
				pathDataSetOne = "files/cancer/thryroid1.dt";
				pathDataSetTwo = "files/cancer/thryroid2.dt";
				pathDataSetThree = "files/cancer/thryroid3.dt";
				break;
			case 10:
				pathDataSetOne = "files/flare/flare1.dt";
				pathDataSetTwo = "files/flare/flare2.dt";
				pathDataSetThree = "files/flare/flare3.dt";
				break;
			case 11:
				break;
				
			}

		} while (option > 11 || option < 0);

	}
}

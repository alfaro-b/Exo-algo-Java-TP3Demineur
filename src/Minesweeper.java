import java.util.Random;
import java.util.Scanner;

public class Minesweeper {

	public static void main(String[] args) {
		int[][] initialGrid = createGrid(6, 12);
		placeBombs(9, initialGrid);
		
		displayGrid(initialGrid);
		
		Scanner userInput = new Scanner(System.in);
		
		int[] coordinates = askCoordinates(userInput, initialGrid);

		System.out.println("Ligne : " + coordinates[0]);
		System.out.println("Colonne : " + coordinates[1]);
		
		userInput.close();
	}
	
	// =================== MÉTHODES ===================

	/**
	 *  Crée la grille de jeu
	 *  @param rows Nombre de lignes
	 *  @param columns Nombre de colonnes
	 *  @return La grille créée
	 */
	public static int [][] createGrid(int rows, int columns) {
		int[][] grid = new int[rows][columns];
		return grid;
	}
	
	/**
	 *  Place les bombes aléatoirement sur la grille de jeu
	 *  @param bombsNumber Nombre de bombes à placer
	 *  @param grid Grille de jeu
	 */
	public static void placeBombs(int bombsNumber, int[][] grid) {
		
		// Vérifie que la grille est valide.
		if (grid == null || grid.length == 0 || grid[0].length == 0) {
	        System.out.println("La grille est invalide.");
	        return;
	    }

		// Vérifie que le nombre de bombes est valide.
	    int maxBombs = grid.length * grid[0].length;

	    if (bombsNumber < 0 || bombsNumber > maxBombs) {
	        System.out.println("Le nombre de bombes est invalide.");
	        return;
	    }
		
	    // Après les vérifications, place les bombes aléatoirement dans la grille.
		int maxRow = grid.length;
		int maxColumn = grid[0].length;
		Random random = new Random();
		
		for (int i = 0; i<bombsNumber; i++) {
			int randomNumRow = random.nextInt(maxRow);
			int randomNumColumn = random.nextInt(maxColumn);
			
			while(grid[randomNumRow][randomNumColumn] == -1) {
				randomNumRow = random.nextInt(maxRow);
				randomNumColumn = random.nextInt(maxColumn);
			}
			grid[randomNumRow][randomNumColumn] = -1;
		}
	}
	
	/**
	 *  Affiche la grille de jeu
	 *  @param grid Grille de jeu
	 */
	public static void displayGrid(int[][] grid) {
		
		// Affiche les en-têtes de colonnes
		System.out.print(" ");
		for (int column = 1; column <= grid[0].length; column++) {
		    System.out.printf("%4d" ,column);
		}
		
		System.out.println();
		
		// Affiche chaque ligne avec en-tête de ligne et valeur de chaque colonne
		for (int row = 0; row < grid.length; row++) {
			char rowHeader = (char) ('A' + row);
			System.out.printf("%c", rowHeader);
						
			for (int column = 0; column <grid[row].length; column++) {
				if (grid[row][column] == -1) {
					System.out.printf("%4s", "*");
				} else {
					System.out.printf("%4s", (grid[row][column]));
				}
			}
		    System.out.println();
		    
		}
		 System.out.println();
	}
	
	/**
	 *  Demande à l'utilisateur quelle case il souhaite découvrir.
	 *  @param userInput Scanner utilisé pour lire la saisie 
	 *  @param grid Grille de jeu
	 *  @return Tableau contenant les indices de ligne et colonne (coordonnées de la case jouée) 
	 */
	public static int[] askCoordinates(Scanner userInput, int[][] grid) {
		
		boolean validInput = false;
		int row = 0;
		int column = 0;
		
		while (!validInput) {
			System.out.println("Choisissez une case à découvrir. Réponse au format A10 par ex.");
			String chosenCase = userInput.nextLine().toUpperCase();
			
			boolean inputIsCorrect = true;
			
			// Vérifie la longueur de la saisie avant de poursuivre.
			if(chosenCase.length() > 3 || chosenCase.length()<2) {
				System.out.println("La saisie doit contenir une lettre et 1 ou 2 chiffres.");
				inputIsCorrect = false;
			}
			
			// Si longueur correcte, on fait les vérifications suivantes.
			if (inputIsCorrect) {
				// Vérifie que le premier caractère est bien une lettre.
				if(!Character.isLetter(chosenCase.charAt(0))) {
					System.out.println("Le premier caractère doit être une lettre.");
					inputIsCorrect = false;
				}
				
				// Si 2 caractères saisis, vérifie que le deuxième est bien un chiffre.
				if (chosenCase.length() == 2) {
					if(!Character.isDigit(chosenCase.charAt(1))) {
						System.out.println("La colonne doit être un nombre.");
						inputIsCorrect = false;
					}
				}
				
				// Si 3 caractères saisis, vérifie que les deuxième et troisième sont bien des chiffres.
				if (chosenCase.length() == 3 ) {
					if(!Character.isDigit(chosenCase.charAt(1)) 
							|| !Character.isDigit(chosenCase.charAt(2))) {
						System.out.println("La colonne doit être un nombre.");
						inputIsCorrect = false;
					}
				}
			}
			
			// Si le format est valide, on convertit.
			if(inputIsCorrect) {
				// Vérifie le premier caractère après conversion en indice.
				// Vérifie que l'indice correspond à une ligne existante dans la grille.
				row = chosenCase.charAt(0) - 'A';
				if (row >= grid.length ||row < 0) {
					System.out.println("La ligne saisie ne fait pas partie de la grille.");
					inputIsCorrect = false;
				}
				
				// Vérifie que le nombre correspond à une colonne existante de la grille 
				column = Integer.parseInt(chosenCase.substring(1)) - 1;
				if (column < 0 || column >= grid[0].length) {
					System.out.println("La colonne saisie ne fait pas partie de la grille.");
					inputIsCorrect = false;
				}
			}
			
			if (inputIsCorrect) {
				validInput = true;
			}
		}
		
		return new int[] {row, column};
	}

}

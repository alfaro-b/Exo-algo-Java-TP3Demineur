import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Minesweeper {

	public static void main(String[] args) {

		Scanner userInput = new Scanner(System.in);

		// Demande les dimensions de la grille de jeu
		int[] dimensions = askDimensions(userInput);
		int rows = dimensions[0];
		int columns = dimensions[1];

		// Calcule le nombre de bombes en fonction de la difficulté choisie
		int bombsNumber = askDifficulty(userInput, rows, columns);

		// Grille de jeu (à cacher, utile pour test)
		int[][] gameGrid = createGrid(rows, columns);
		placeBombs(bombsNumber, gameGrid);
		System.out.println("Grille de jeu utilisée pour test, à cacher ensuite");
		displayGrid(gameGrid);

		// Grille affichée au joueur
		int[][] visibleGrid = createGrid(rows, columns);
		initializeVisibleGrid(visibleGrid);
		System.out.println(bombsNumber + " bombes sont cachées, à vous de les trouver.");

		
		// Début du jeu
		boolean gameOver = false;
		while (!gameOver) {
			System.out.println("Grille de jeu JOUEUR");
			displayGrid(visibleGrid);

			// Demande les coordonnées de la case à jouer
			int[] coordinates = askCoordinates(userInput, gameGrid);

			int rowCase = coordinates[0];
			int columnCase = coordinates[1];

			// Vérifie si la case a déjà été jouée
			if (isCellAlreadyPlayed(visibleGrid, rowCase, columnCase)) {
				System.out.println("Cette case a déjà été découverte. Choisissez en une autre.");
				System.out.println();
				continue;
			}

			// Si la case jouée contient une bombe
			if (gameGrid[rowCase][columnCase] == -1) {
				System.out.println("Vous avez perdu !");
				System.out.println("Voilà où étaient les bombes : ");
				displayGrid(gameGrid);
				gameOver = true;

				// Si la case jouée ne contient pas de bombe
			} else {
				revealCell(gameGrid, visibleGrid, rowCase, columnCase);
				if (checkWin(gameGrid, visibleGrid)) {
					revealBombs(gameGrid, visibleGrid);
					displayGrid(visibleGrid);
					System.out.println("Bravo, vous avez gagné ! ");
					gameOver = true;
				}
			}

		}
		userInput.close();
	}

	// =================== MÉTHODES ===================

	/**
	 * Demande la taille de la grille
	 * 
	 * @param rows    Nombre de lignes
	 * @param columns Nombre de colonnes
	 * @return Tableau d'entiers avec nombre de lignes et de colonnes
	 */
	public static int[] askDimensions(Scanner userInput) {
		int rows = 0;
		int columns = 0;
		
		System.out.println("Choisissez les dimensions de la grille de jeu.");
		    
			while (rows < 6 || rows > 26) {
				System.out.println("Combien de lignes doit comporter la grille? Saisissez un nombre entre 6 et 26: ");
				if (userInput.hasNextInt()) {
			        rows = userInput.nextInt();
			        userInput.nextLine();

			        if (rows < 6 || rows > 26) {
			            System.out.println("Le nombre doit être compris entre 6 et 26.");
			        }
				} else {
					System.out.println("Vous devez saisir un nombre entier.");
					userInput.nextLine(); // retire la saisie incorrecte
				}
			}		
		
			while (columns < 12 || columns > 52) {
				System.out.println("Combien de colonnes doit comporter la grille? Saisissez un nombre entre 12 et 52: ");
				if (userInput.hasNextInt()) {
					columns = userInput.nextInt();
					userInput.nextLine();
					if (columns < 12 || columns > 52) {
						System.out.println("Le nombre doit être compris entre 12 et 52.");
					}
				} else {
					System.out.println("Vous devez saisir un nombre entier.");
					userInput.nextLine(); // retire la saisie incorrecte
				}
			}
		int[] dimensions = {rows, columns};
		
		return dimensions;
	}

	/**
	 * Demande le niveau de difficulté
	 * 
	 * @param rows    Nombre de lignes
	 * @param columns Nombre de colonnes
	 * @return Le nombre de bombes en fonction de la difficulté choisie et de la taille de la grille de jeu
	 */
	public static int askDifficulty(Scanner userInput, int rows, int columns) {

		boolean validInput = false;
		int difficulty = 0;

		while (!validInput) {
			System.out.println("Choisissez un niveau de difficulté :");
			System.out.println("1 - Facile");
			System.out.println("2 - Moyen");
			System.out.println("3 - Difficile");

			if (userInput.hasNextInt()) {
				difficulty = userInput.nextInt();
				userInput.nextLine();

				if (difficulty > 3 || difficulty < 1) {
					System.out.println("Saisie invalide. Choisissez 1, 2 ou 3.");
				} else {
					validInput = true;
				}
			} else {
				System.out.println("Vous devez saisir un nombre entier.");
			    userInput.nextLine();
			}
		}

		int bombsNumber;

		switch (difficulty) {
		case 1:
			bombsNumber = rows * columns / 10;
			break;
		case 2:
			bombsNumber = rows * columns / 6;
			break;
		case 3:
			bombsNumber = rows * columns / 4;
			break;
		default:
			bombsNumber = rows * columns / 10;
		}
		return bombsNumber;
	}

	/**
	 * Crée la grille de jeu
	 * 
	 * @param rows    Nombre de lignes
	 * @param columns Nombre de colonnes
	 * @return La grille créée
	 */
	public static int[][] createGrid(int rows, int columns) {
		int[][] grid = new int[rows][columns];
		return grid;
	}

	/**
	 * Place les bombes aléatoirement sur la grille de jeu
	 * 
	 * @param bombsNumber Nombre de bombes à placer
	 * @param grid        Grille de jeu
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

		for (int i = 0; i < bombsNumber; i++) {
			int randomNumRow = random.nextInt(maxRow);
			int randomNumColumn = random.nextInt(maxColumn);

			while (grid[randomNumRow][randomNumColumn] == -1) {
				randomNumRow = random.nextInt(maxRow);
				randomNumColumn = random.nextInt(maxColumn);
			}
			grid[randomNumRow][randomNumColumn] = -1;
		}
	}

	/**
	 * Initialise la grille de jeu qui sera affichée
	 * 
	 * @param visibleGrid Grille affichée au joueur
	 */
	public static void initializeVisibleGrid(int[][] visibleGrid) {
		// On met -2 dans toutes les cases
		for (int row = 0; row < visibleGrid.length; row++) {
			for (int column = 0; column < visibleGrid[row].length; column++) {
				visibleGrid[row][column] = -2;
			}
		}
	}

	/**
	 * Affiche la grille de jeu
	 * 
	 * @param grid Grille de jeu
	 */
	public static void displayGrid(int[][] grid) {

		// Affiche les en-têtes de colonnes
		System.out.print(" ");
		for (int column = 1; column <= grid[0].length; column++) {
			System.out.printf("%4d", column);
		}

		System.out.println();

		// Affiche chaque ligne avec en-tête de ligne et valeur de chaque colonne
		for (int row = 0; row < grid.length; row++) {
			char rowHeader = (char) ('A' + row);
			System.out.printf("%c", rowHeader);

			for (int column = 0; column < grid[row].length; column++) {
				if (grid[row][column] == -2) {
					System.out.printf("%4s", ".");
				} else if (grid[row][column] == -1) {
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
	 * Demande à l'utilisateur quelle case il souhaite découvrir.
	 * 
	 * @param userInput Scanner utilisé pour lire la saisie
	 * @param grid      Grille de jeu
	 * @return Tableau contenant les indices de ligne et colonne (coordonnées de la
	 *         case jouée)
	 */
	public static int[] askCoordinates(Scanner userInput, int[][] grid) {

		boolean validInput = false;
		int row = 0;
		int column = 0;

		while (!validInput) {
			System.out.println("Choisissez une case à découvrir. Réponse au format A10 par exemple.");
			String chosenCase = userInput.nextLine().toUpperCase();

			boolean inputIsCorrect = true;

			// Vérifie la longueur de la saisie avant de poursuivre.
			if (chosenCase.length() > 3 || chosenCase.length() < 2) {
				System.out.println("La saisie doit contenir une lettre et 1 ou 2 chiffres.");
				inputIsCorrect = false;
			}

			// Si longueur correcte, on fait les vérifications suivantes.
			if (inputIsCorrect) {
				// Vérifie que le premier caractère est bien une lettre.
				if (!Character.isLetter(chosenCase.charAt(0))) {
					System.out.println("Le premier caractère doit être une lettre.");
					inputIsCorrect = false;
				}

				// Si 2 caractères saisis, vérifie que le deuxième est bien un chiffre.
				if (chosenCase.length() == 2) {
					if (!Character.isDigit(chosenCase.charAt(1))) {
						System.out.println("La colonne doit être un nombre.");
						inputIsCorrect = false;
					}
				}

				// Si 3 caractères saisis, vérifie que les deuxième et troisième sont bien des
				// chiffres.
				if (chosenCase.length() == 3) {
					if (!Character.isDigit(chosenCase.charAt(1)) || !Character.isDigit(chosenCase.charAt(2))) {
						System.out.println("La colonne doit être un nombre.");
						inputIsCorrect = false;
					}
				}
			}

			// Si le format est valide, on convertit.
			if (inputIsCorrect) {
				// Vérifie le premier caractère après conversion en indice.
				// Vérifie que l'indice correspond à une ligne existante dans la grille.
				row = chosenCase.charAt(0) - 'A';
				if (row >= grid.length || row < 0) {
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

		return new int[] { row, column };
	}

	/**
	 * Vérifie si la case a déjà été jouée
	 * 
	 * @param visibleGrid Grille affichée au joueur
	 * @param rowCase     Indice de ligne de la case jouée
	 * @param columnCase  Indice de colonne de la case jouée
	 * @return True si case déjà jouée
	 */
	public static boolean isCellAlreadyPlayed(int[][] visibleGrid, int rowCase, int columnCase) {
		// Si la case est différente de -2, elle a déjà été découverte.
		if (visibleGrid[rowCase][columnCase] != -2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Vérifie le nombre de bombes autour d'une case
	 * 
	 * @param gameGrid   Grille contenant les bombes
	 * @param rowCase    Indice de ligne de la case jouée
	 * @param columnCase Indice de colonne de la case jouée
	 * @return Nombre de bombes dans les cases adjacentes
	 */
	public static int checkCellsAround(int[][] gameGrid, int rowCase, int columnCase) {
		int bombsAround = 0;

		for (int row = rowCase - 1; row <= rowCase + 1; row++) {
			for (int column = columnCase - 1; column <= columnCase + 1; column++) {

				// Vérifie que la case existe dans la grille
				if (row >= 0 && row < gameGrid.length && column >= 0 && column < gameGrid[0].length) {

					// Ne vérifie pas la case jouée elle-même
					if (row != rowCase || column != columnCase) {

						if (gameGrid[row][column] == -1) {
							bombsAround++;
						}
					}
				}
			}
		}
		return bombsAround;
	}

	/**
	 * Révèle les cases voisines
	 * 
	 * @param gameGrid   Grille contenant les bombes
	 * @param rowCase    Indice de ligne de la case jouée
	 * @param columnCase Indice de colonne de la case jouée
	 */
	public static void revealCell(int[][] gameGrid, int[][] visibleGrid, int rowCase, int columnCase) {
		// Si la case est déjà découverte, on arrête cette branche.
		if (isCellAlreadyPlayed(visibleGrid, rowCase, columnCase)) {
			return;
		}
		// Compte les bombes autour et découvre la case.
		int bombsAround = checkCellsAround(gameGrid, rowCase, columnCase);
		visibleGrid[rowCase][columnCase] = bombsAround;

		// Si cette case a au moins une bombe autour, on la découvre mais on ne propage
		// pas plus loin.
		if (bombsAround != 0) {
			return;
		}

		// Si aucune bombe autour, on parcourt les cases adjacentes.
		for (int row = rowCase - 1; row <= rowCase + 1; row++) {
			for (int column = columnCase - 1; column <= columnCase + 1; column++) {

				// Vérifie que la case existe dans la grille
				if (row >= 0 && row < gameGrid.length && column >= 0 && column < gameGrid[0].length) {

					// Ignore la case actuelle
					if (row == rowCase && column == columnCase) {
						continue;
					}

					// Ne découvre jamais une bombe
					if (gameGrid[row][column] == -1) {
						continue;
					}

					// Découvre la case voisine
					revealCell(gameGrid, visibleGrid, row, column);
				}
			}
		}
	}

	/**
	 * Vérifie que toutes les cases soient découvertes
	 * 
	 * @param gameGrid    Grille contenant les bombes
	 * @param visibleGrid Grille affichée au joueur
	 * @return Un booléen, true si partie gagnée et sinon false
	 */
	public static boolean checkWin(int[][] gameGrid, int[][] visibleGrid) {
		for (int row = 0; row < gameGrid.length; row++) {
			for (int column = 0; column < gameGrid[0].length; column++) {
				// Ignore les cases contenant une bombe
				if (gameGrid[row][column] == -1) {
					continue;
				}
				// Si une case sans bombe est encore cachée, la partie n'est pas gagnée
				if (visibleGrid[row][column] == -2) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Révèle les bombes dans la grille affichée au joueur
	 * 
	 * @param gameGrid    Grille contenant les bombes
	 * @param visibleGrid Grille affichée au joueur
	 */
	public static void revealBombs(int[][] gameGrid, int[][] visibleGrid) {
		for (int row = 0; row < gameGrid.length; row++) {
			for (int column = 0; column < gameGrid[row].length; column++) {
				if (gameGrid[row][column] == -1) {
					visibleGrid[row][column] = -1;
				}
			}
		}
	}

}

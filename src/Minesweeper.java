import java.util.Random;

public class Minesweeper {

	public static void main(String[] args) {
		int[][] grid = createGrid(6, 12);
		placeBombs(9, grid);
	}
	
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

}

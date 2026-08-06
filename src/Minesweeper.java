
public class Minesweeper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] grid = createGrid(6, 12);
	}
	
	/**
	 *  Crée la grille de jeu
	 *  @param row Nombre entier pour le nombre de lignes
	 *  @param column Nombre entier pour le nombre de colonnes
	 */
	public static int [][] createGrid(int row, int column) {
		int [][] grid = new int[row][column];
		return grid;
	}

}

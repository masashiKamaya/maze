public class App {
	public static void main(String[] args) throws Exception {
		int row, col;
		//row = Integer.parseInt(args[0]);
		//col = Integer.parseInt(args[1]);
		row = 15;
		col = row;
		if(row % 2 == 0 || col % 2 == 0) System.exit(0);
		Maze maze = new Maze(row, col);
		maze.output();
	}
}

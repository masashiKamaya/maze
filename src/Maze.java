import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Maze {
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	private int row, col;
	private int[][] data;
	private Random r;

	public Maze(int row, int col){
		this.row = row;
		this.col = col;

		setData();

		r = new Random();

		create();
	}

	private void setData(){
		data = new int[row][col];
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				data[i][j] = 0;
			}
		}
	}

	public void create(){
		int max = col / 2;
		int x = r.nextInt(max) * 2 + 1;
		max = row / 2;
		int y = r.nextInt(max) * 2 + 1;
		create(x, y);
	}

	public void create(int x, int y){
		/** 再帰処理 */
		dig(x, y);
	}

	private void dig(int x, int y){
		/** 現在地を掘削 */
		data[y][x] = 1;
		List<Integer> directionList = new ArrayList<Integer>(Arrays.asList(LEFT, RIGHT, UP, DOWN));
		Collections.shuffle(directionList);

		//4方向をランダムですべて検索する
		for(int direction : directionList){
			if(isDig(x, y, direction)){
				Point nextPoint = movePoint(x, y, direction);
				dig(nextPoint.x, nextPoint.y);
			}
		}
	}

	private boolean isDig(int x, int y, int direction){
		/** 2マス先に進めるか */
		int result = 0;
		switch(direction){
			case LEFT:
				if(x == 1) return false;
				result = data[y][x - 2];
				break;
			case RIGHT:
				if(x == col - 2) return false;
				result = data[y][x + 2];
				break;
			case UP:
				if(y == 1) return false;
				result = data[y - 2][x];
				break;
			case DOWN:
				if(y == row - 2) return false;
				result = data[y + 2][x];
				break;
		}
		return result == 0;
	}

	private Point movePoint(int x, int y, int direction){
		/** 2マス先に進む */
		switch(direction){
			case LEFT:
				data[y][--x] = 1;
				x--;
				break;
			case RIGHT:
				data[y][++x] = 1;
				x++;
				break;
			case UP:
				data[--y][x] = 1;
				y--;
				break;
			case DOWN:
				data[++y][x] = 1;
				y++;
				break;
		}
		return new Point(x, y);
	}

	/** test */
	public void println(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				sb.append(data[i][j]).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	public void output(){
		File dir = new File(".");
		File[] listFile = dir.listFiles();
		int count = 0;
		for(File auto : listFile){
			if(auto.getName().contains("map")) count++;
		}
		StringBuffer sb = new StringBuffer();
		String name = "map" + count;
		String fileName = name + ".map";
		FileWriter fw;
		BufferedWriter bw;
		PrintWriter pw = null;

		try{
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);

			sb.append(name).append("\n");
			sb.append(col).append("\n");
			sb.append(row).append("\n");
			for(int i = 0; i < row; i++){
				for(int j = 0; j < col; j++){
					sb.append(data[i][j]);
					if(j != col - 1) sb.append(",");
				}
				if(i != row - 1) sb.append("\n");
			}

			pw.println(sb.toString());

		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(pw != null) pw.close();
		}
	}
}

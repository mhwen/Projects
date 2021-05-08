import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


//Takes in a 9x9 grid of numbers (1-9) and * (denoting an empty cell)
//And solves the sudoku puzzle

public class Sudoku {
	
	//Value of an empty cell
	final int EMPTY = '*'-'1';
	
	char[][] originalGrid;
	//The current state of the puzzle
	int[][] state;
	//Stores whether a number has been placed in a certain square, row, or column
	boolean[][] inSquare, inRow, inColumn;
	
	//Stores solution
	boolean solutionFound;
	int[][] solved;
	
	public Sudoku(String input) throws FileNotFoundException {
		
		originalGrid = new char[9][9];
		state = new int[9][9];
		inSquare = new boolean[9][9];
		inRow = new boolean[9][9];
		inColumn = new boolean[9][9];
		solved = new int[9][9];
		
		boolean validBoard = true;
		
		Scanner s = new Scanner(input);
		for(int i = 0; i < 9; i++) {
			originalGrid[i] = s.nextLine().toCharArray();
			for(int j = 0; j < 9; j++) {
				int num = originalGrid[i][j]-'1';
				state[i][j] = num;
				if(num != EMPTY) {
					if(inSquare[3*(i/3)+j/3][num] || inRow[i][num] || inColumn[j][num]) {
						validBoard = false;
					}
					inSquare[3*(i/3)+j/3][num] = true;
					inRow[i][num] = true;
					inColumn[j][num] = true;
				}
			}
		}
		s.close();
		
		if(validBoard)
			solve(0, 0);
	}

	public static void main(String[] args) throws IOException {
		Scanner file = new Scanner(new File("sudoku.txt"));
		int inputs = file.nextInt();
		for(int p = 0; p < inputs; p++) {
			file.nextLine();
			String puzzle = "";
			for(int i = 0; i < 9; i++) {
				puzzle += file.nextLine() + "\n";
			}
			puzzle.substring(0, puzzle.length()-1);
			Sudoku s = new Sudoku(puzzle);
			System.out.println("Puzzle #" + (p+1));
			s.printPuzzle();
			System.out.println("---------");
			s.printSolved();
			System.out.println();
			
		}
		file.close();
	}
	
	public void solve(int r, int c) {
		if(solutionFound)
			return;
		//Found a solution
		if(r == 9 && c == 0) {
			solutionFound = true;
			for(int r1 = 0; r1 < 9; r1++) {
				for(int c1 = 0; c1 < 9; c1++) {
					solved[r1][c1] = state[r1][c1]+1;
				}
			}
			return;
		}
		int nextC = (c+1)%9;
		int nextR = (nextC == 0) ? r+1 : r;
		
		//There is already a number here
		if(state[r][c] != EMPTY) {
			solve(nextR, nextC);
			return;
		}
		
		//Try all the possible numbers at this position
		for(int i = 0; i < 9; i++) {
			if(state[r][c] == EMPTY && !inSquare[3*(r/3)+c/3][i] && !inRow[r][i] && !inColumn[c][i]) {
				boolean squareState = inSquare[3*(r/3)+c/3][i];
				boolean rowState = inRow[r][i];
				boolean columnState = inColumn[c][i];
				state[r][c] = i;
				inSquare[3*(r/3)+c/3][i] = inRow[r][i] = inColumn[c][i] = true;
				solve(nextR, nextC);
				state[r][c] = EMPTY;
				inSquare[3*(r/3)+c/3][i] = squareState;
				inRow[r][i] = rowState;
				inColumn[c][i] = columnState;
			}
		}
		
	}
	
	public String toString() {
		String result = "";
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				result += originalGrid[i][j];
			}
			result += "\n";
		}
		return result;
	}
	
	public void printPuzzle() {
		System.out.print(toString());
	}
	
	public String getSolved() {
		if(!solutionFound)
			return "No solution.";
		String result = "";
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				result += solved[i][j];
			}
			result += "\n";
		}
		return result;
	}
	
	public void printSolved() {
		System.out.print(getSolved());
	}

}

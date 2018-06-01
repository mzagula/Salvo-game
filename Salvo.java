package marta.niedziela;

import java.util.Random;
import java.util.Scanner;
public class Salvo {

	public static void main(String[] args) {

	int sideLen = 9;
	int submarine = 3;
	Integer hitAndSunkComp = 0;
	Integer hitAndSunkUser = 0;	
	String[][] compMap =  createCompMap(sideLen);	
	String[][] userMap =  createUserMap(sideLen);	
	String[][] compMapUserView =  createUserMap(sideLen);	
    Scanner scanner = new Scanner(System.in);
	
	displayMap(compMap, sideLen);
	System.out.println("");	
	
    userShipSetting(userMap,scanner,submarine, sideLen);
    compShipSetting(compMap, submarine, sideLen);
	System.out.println("Computer placed its own ships.");
	System.out.println("");	

	while (hitAndSunkComp < submarine && hitAndSunkUser < submarine) {
		int didUserHit = gameplay("User",compMap, userMap, compMapUserView, scanner,sideLen);
		int didCompHit = gameplay("Computer",compMap, userMap, compMapUserView, scanner,sideLen);
		if (didUserHit == 1) {
			hitAndSunkUser++;
		} 
		if (didCompHit == 1) {
			hitAndSunkComp++;
		}
		
		System.out.println("SCOREBOARD: ");
		System.out.println("User - " + hitAndSunkUser + ":" + hitAndSunkComp + " - Computer");
		
		if(hitAndSunkUser == submarine) {
			System.out.println("!!!!!!!!!!!!!!!!You win!!!!!!!!!!!!");
		} else if (hitAndSunkComp==submarine) {
			System.out.println("You lost!");
		}

	}
	
	}
	
	public static String[][]  userShipSetting(String[][] board,  Scanner scanner, int submarine, int sideLen) {

		for (int i=0;i<submarine;i++) {
		int submarineNumber = submarine-i;
		System.out.println("Where do you want place your single ships? You can use "+ submarineNumber +" out of " + submarine);
		System.out.println("Choose row (only numbers).");
		String row = scanner.nextLine();
		int rowNumber = Integer.valueOf(row);
		System.out.println("Choose column (only numbers).");
		String column = scanner.nextLine();
		int colNumber = Integer.valueOf(column);
		board[rowNumber][colNumber] = "O";
		displayMap(board,sideLen);
		System.out.println("");
		}
		System.out.println("It is your board.");
		return board;
	}
	
	
	public static String[][] createCompMap(int row){
		return createBoard(row);
	}
	
	public static String[][] createBoard(int row) {
		String[][] board= new String[row+1][row+1];
		for(int i=0; i<=row; i++) {	
			for(int j=0; j<=row; j++) {
				board[i][j] = "_";	
				}	
			}
		return board;
	}
	
	public static String[][] createUserMap(int row){
		return createBoard(row);

	}
	
	public static void displayMap(String[][] board, int sideLen) {
		sideLen = sideLen+1;
		System.out.print("  ");
		char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUWXVYZ".toCharArray();
		for (int i = 1; i<sideLen; i++) {
			System.out.print(i + " ");
		}
			for (int row=1;row<sideLen;row++) {
			System.out.println("");					
			System.out.print(alphabet[row-1] + "|");				
				for (int col=1; col<sideLen;col++) {
					System.out.print(board[row][col]);
					System.out.print("|");
				}	
			}
	}
	
	
	public static String[][] compShipSetting(String[][] board, int submarine, int sideLen) {
		Random rand = new Random();
		for(int i=1;i<submarine+1;i++) {
		int randRow = rand.nextInt(sideLen-1)+1;	
		int randCol = rand.nextInt(sideLen-1)+1;	
			board[randRow][randCol] = "O";	
		}
		
		return board;		
	}
	
	
	public static int gameplay(String whoPlay, String[][] compMap, String[][]userMap, String[][]compMapUserView, Scanner scanner, int sideLen) {
		Random rand = new Random();
		if(whoPlay.equals("User")) {
			System.out.println("Where do you want to hit - which row (use numbers)?");
			int row = Integer.valueOf(scanner.nextLine());
			System.out.println("Where do you want to hit - which column (use numbers)?");
			int column = Integer.valueOf(scanner.nextLine());
			
			if (compMap[row][column].equals("O") && !compMapUserView[row][column].equals("%")) {
				compMapUserView[row][column] = "%";
				System.out.println("Hit and sunk!");
				displayMap(compMapUserView,sideLen);
				System.out.println("");
				System.out.println("Press enter to continue...");
				scanner.nextLine();
				return 1;
			} else if (compMapUserView[row][column].equals("%") || compMapUserView[row][column].equals("X")) {
				System.out.println("You did those move - try one more time.");
				return gameplay("User",compMap, userMap, compMapUserView, scanner,sideLen);
				
			} else {
				System.out.println("You missed!");
				compMapUserView[row][column] = "X";
				displayMap(compMapUserView,sideLen);	
				System.out.println("");
				System.out.println("Press enter to continue...");
				scanner.nextLine();
				return 0;
			}	

			
		} else if (whoPlay.equals("Computer")){
			System.out.println("Now Computer turn. Press enter to continue...");
			scanner.nextLine();
			int randRow = rand.nextInt(sideLen-1)+1;	
			int randCol = rand.nextInt(sideLen-1)+1;	
			if(userMap[randRow][randCol].equals("O")) {
				System.out.println("Computer hit and sunk your ship!");
				userMap[randRow][randCol] = "%";
				displayMap(userMap,sideLen);
				System.out.println("");
				System.out.println("Press enter to continue...");
				scanner.nextLine();
				return 1;
			} else if (userMap[randRow][randCol].equals("%") || userMap[randRow][randCol].equals("X")) {
				System.out.println("Computer did those move - he is trying one more time.");
				return gameplay("Computer",compMap, userMap, compMapUserView, scanner,sideLen);
			}
			else {
			System.out.println("Computer missed!");
			userMap[randRow][randCol] = "X";
			displayMap(userMap,sideLen);
			System.out.println("");
			System.out.println("Press enter to continue...");
			scanner.nextLine();
			return 0;
			}
		} else {
		return 0; 
		}
	}
	
}
	

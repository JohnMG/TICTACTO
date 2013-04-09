import java.util.Scanner;

public class Board {
//
	private final int ROWCOL = 3;
	private final int PLAYONE = 0;
	private final int PLAYTWO = 1;
	private final int DIAGS = 2;
	private final char BLANKS = '-';
	private final char NOUGHT = 'O';
	private final char CROSS = 'X';
	private final int INITIALPLAYER = 0;
	private final String BOARDFRAME = "+===+===+===+";
	// 
	char[][] titato = new char[ROWCOL][ROWCOL];
	Player[] players = new Player[2];
	Player one;
	Player two;
	
		public Board() {
			String oneName;
			String twoName;
// 
			for(int i = 0; i < ROWCOL; i++) {
				for(int j = 0; j < ROWCOL; j++) {
					titato[i][j] = BLANKS;
				}
					
			}
//
			oneName = AskName(PLAYONE);
			twoName = AskName(PLAYTWO);
			one = new Player(oneName, PLAYONE);
			two = new Player(twoName, PLAYTWO);
			System.out.println(oneName+" You are: Noughts");
			System.out.println(twoName+" You are: Crosses");
			players[PLAYONE] = one;
			players[PLAYTWO] = two;
			
			System.out.println("Board is now ready to play\n");
		}
//
		public String AskName(int playNum) {
			String result = "";
			boolean TEST = false;
//
			while(!TEST) {
				System.out.print("Player "+(playNum+1)+" what is your name: ");
				Scanner input = new Scanner(System.in);
				result = input.nextLine();
				if(!result.isEmpty()) {
					TEST =  true;
				}
			}
			return result;
		}
		
		public void playTTT() {
			int p = INITIALPLAYER;
			int vic = 0;
			int move = 0;
			while(vic == 0) {
				printBoard();
				move = doMove(p);
				p = cyclePlayer(p);
				vic = victoryCond();
			}
			endGame(vic, move);
			printBoard();
		}
		
		public int victoryCond() {
			int result = 0;
			boolean fullCond = false;
//
			if(result == 0) {
				result = checkAcross();
			}
			if(result == 0) {
				result = checkDown();
			}
			if(result == 0) {
				result = checkDiag();
			}
			if(result == 0) {
				fullCond = checkFull();
				if(fullCond) {
					result = 9;
				}
			}
			return result;
		}

		public void printBoard() {
			System.out.println(BOARDFRAME);
			for(int y=0; y<ROWCOL; y++ ) {
				for(int x=0; x<ROWCOL; x++) {
					System.out.print("| "+titato[x][y]+" ");
				}
				System.out.println("|");
				System.out.println(BOARDFRAME);
			}
			System.out.println("\n");
			
		}
		
		public int doMove(int p){
			boolean validMove = false;
			int move = 10;
			int x = 0;
			int y = 0;
			
			while(!validMove) {
				move = players[p].getMove();
				x = detXCoor(move);
				y = detYCoor(move);

				validMove = checkTaken(x,y);
				if(validMove == true) {
					titato[x][y] = players[p].getPiece();
				} else {
					System.out.println("Sorry this square is already taken. Please choose again");
				}
			}
			return move;
		}
		
		public int detXCoor(int move) {
			int result = 0;
			if((move%ROWCOL)>0) {
				result = ((move%ROWCOL)-1);
			} else {
				result = ((ROWCOL)-1);
			}
			return result;
		}
		public int detYCoor(int move) {
			int result = 0;
			if((move%ROWCOL)>0) {
				result = (move/ROWCOL);
			} else {
				result = ((move/ROWCOL)-1);
			}
			return result;
		}
		
		public int cyclePlayer(int p){
			if(p == PLAYONE){
				p = PLAYTWO;
			} else {
				p = PLAYONE;
			}
			return p;
		}
		
		
		public boolean checkTaken(int x, int y) {
			boolean result = false;
			if(titato[x][y] == BLANKS) {
				result = true;
			}
			return result;
		}
		
		public int checkAcross() {
			int found = 0;
			boolean same;
//
			char[] checker = new char[3];
			int x = 0;
			int y = 0;
			
			while((found==0) && (y<3)) {
//
				for(x=0; x<ROWCOL; x++){
					checker[x] = titato[x][y];
				}
//
				same = checkSame(checker);
				if(same == true) {
					found = (y+1);
				}
				y++;
			}
			return found;
		}
		
		public int checkDown() {
			int found = 0;
			boolean same;
//
			char[] checker = new char[3];
			int x = 0;
			int y = 0;
			
			while((found==0) && (x<3)) {
//
				for(y=0; y<ROWCOL; y++){
					checker[y] = titato[x][y];
				}
//
				same = checkSame(checker);
				if(same == true) {
					found = (x+4);
				}
				x++;
			}
			return found;
		}
		
		public int checkDiag() {
			int found = 0;
			boolean same;
			char[][] checker = new char[2][3];
			int x = 0;
			int y = 0;
			
			for(x=0; x<ROWCOL; x++, y++){
				checker[0][x] = titato[x][y];
			}
			for(x=2, y=0; y<ROWCOL; x--,y++) {
				checker[1][y] = titato[x][y];
			}
			for(x=0; ((x<DIAGS) && (found==0)); x++) {
				same = checkSame(checker[x]);
				if(same){
					found = (x+7);
				}
			}
			return found;
		}
		
		public boolean checkSame(char[] ArrOfMove) {
			int x;
			boolean same = true;
			for(x=0; (x<(ROWCOL-1)&&(same)); x++){
				if(ArrOfMove[x] == ArrOfMove[x+1]) {
					if(ArrOfMove[x]!=BLANKS){
						same = true;
					} else {
						same = false;
					}
				} else {
					same = false;
				}
			}
			return same;
		}
		
		public boolean checkFull(){
			boolean result = true;
			for(int x=0; x<ROWCOL; x++) {
				for(int y=0; y<ROWCOL; y++) {
					if(titato[x][y] == BLANKS){
						result = false;
					}
				}
			}
			return result;
		}
		public void endGame(int vic, int move) {

			int x;
			int y;
			String msg;
			char mov;
			int player;
			
			x = detXCoor(move);
			y = detYCoor(move);
			move = titato[x][y];
			player = determinePlayer(x, y);
			msg = ("Player "+players[player].getName()); 
			
			switch(vic) {
				case 1:
					msg = msg+" won on the 1st row";
					break;
				case 2:
					msg = msg+" won on the 2nd row";
					break;
				case 3:
					msg = msg+" won on the 3rd row";
					break;
				case 4:
					msg = msg+" won on the 1st column";
					break;
				case 5:
					msg = msg+" won on the 2nd column";
					break;
				case 6:
					msg = msg+" won on the 3rd column";
					break;
				case 7:
					msg = msg+" won on the upper left diagonal";
					break;
				case 8:
					msg = msg+" won on the upper right diagonal";
					break;
				case 9:
					msg = "The board is full. DRAW!";
					break;
			}
			System.out.println(msg);
		}
		
		public int determinePlayer(int x, int y){
			int result;
			if(titato[x][y] == NOUGHT){
				result = 0;
			} else {
				result = 1;
			}
			return result;
		}
}

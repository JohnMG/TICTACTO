import java.util.Scanner;

public class Player {
	public final char NOUGHT = 'O';
	public final char CROSS = 'X';
	public final int NONUM = 0;
	
	private String name;
	private char piece;
	private int victories;
	
	public Player(String name, int playerCode){
		this.name = name;
		if(playerCode == NONUM) {
			this.piece = NOUGHT;
		} else {
			this.piece = CROSS;
		}
		this.victories = 0;
	}
	
	public int getMove(){
		int result = 10;
		boolean valid = false;
		while(valid == false) {
			System.out.print(this.name+"("+this.piece+")"+", type which square you want to do your move, 1-9: ");
			Scanner input = new Scanner(System.in);
			result = input.nextInt();
			if((result >= 1)&&(result <= 9)) {
				valid = true;
			}
		}
		return result;
	}
	public char getPiece(){
		return this.piece;
	}
	public String getName() {
		return this.name;
	}
}

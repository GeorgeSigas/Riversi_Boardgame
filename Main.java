import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main
{
	public static void main(String[] args)
	{
		int row,col;
		int maxDepth = 0;
		
		Scanner s=new Scanner(System.in);
		
		System.out.print("Select Difficulty Level (Enter number 1-3): \n 1.Easy \n 2.Medium \n 3.Hard \n \n");
		char dif = s.next().charAt(0);
		while(dif != '1' && dif != '2' && dif != '3')
		{
			System.out.println("Invalid answer! Please insert numbers 1, 2 or 3");
		}	
		if(dif == '1')
		{	
			maxDepth = 3;
		}
		else if(dif == '2')
		{
			maxDepth = 4;
		}
		else if(dif == '3')
		{
			maxDepth = 7;
		}	
		
		System.out.println("Choose X or O:");
		
		char player =s.next().charAt(0);
		while(player!='X' && player!='O')
		{
			System.out.println("Invalid answer!");
			System.out.println("Choose X or O:");
			player =s.next().charAt(0);
		}	
		
		char pc;
		if(player=='X')
		{
			pc='O';
		}
		else
		{
			pc='X';
		}
		
		Game game = new Game(maxDepth, player, pc ); 
													
		Board board = new Board();
		
		board.setLastLetterPlayed(Board.X);
	

		board.print();
        //While the game has not finished
		while(!board.isTerminal())
		{
			
			System.out.println();
			if(!board.allowMove(board.getLastLetterPlayed()*(-1)))
			{
				if(board.getLastLetterPlayed()==board.O)
				{
					System.out.println("X has no valid moves!Turn Lost!");
				}
				else
				{
					System.out.println("O has no valid moves!Turn Lost!");
				}
				board.setLastLetterPlayed(board.getLastLetterPlayed()*(-1));
			}
			switch (board.getLastLetterPlayed())
			{
                //If X played last, then O plays now
				case Board.X:
                    System.out.println("O moves");
					if(pc=='O')
					{
						Move OMove = game.MiniMax(board);
						
						board.makeMove(OMove.getRow(),OMove.getCol(), Board.O);
						
					}
					else
					{
						int counter=0;
						do{
							if(counter!=0) System.out.println("Invalid move!Please try again..");
							System.out.println("Row");
							row=s.nextInt();
							System.out.println("Col");
							col=s.nextInt();
							counter++;
						}while(!board.isValidMove(row,col,Board.O));
						board.makeMove(row, col, Board.O);
					}					
					break;
					
                //If O played last, then X plays now
				case Board.O:
                    System.out.println("X moves");
					if(pc == 'X')
					{
						Move XMove = game.MiniMax(board);
						board.makeMove(XMove.getRow(), XMove.getCol(), Board.X);
					}
					else
					{
						int counter=0;
						do{
						if(counter!=0) System.out.println("Invalid move!Please try again..");
						System.out.println("Row");
						row=s.nextInt();
						System.out.println("Col");
						col=s.nextInt();
						counter++;
						}while(!board.isValidMove(row,col,Board.X));
						board.makeMove(row, col, Board.X);	
					}
					break;
				default:
					break;
			}
			board.print();
		}
		board.getscore();
	}
}

import java.util.ArrayList;

public class Board
{
    //Variables for the Boards values
	public static final int X = 1;
	public static final int O = -1;
	public static final int EMPTY = 0;
    

    private Move lastMove;

   
	private int lastLetterPlayed;

	private int [][] gameBoard;
	
	public Board()
	{
		lastMove = new Move();
		lastLetterPlayed = O;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if((i==3 && j==3) || (i==4 && j==4))
				{
					gameBoard[i][j] = O;
				}
				else if((i==3 && j==4) || (i==4 && j==3))
				{
					gameBoard[i][j] = X;
				}
				else
				{	
					gameBoard[i][j] = EMPTY;
				}
			}
		}
	}
	
	public Board(Board board)
	{
		lastMove = board.lastMove;
		lastLetterPlayed = board.lastLetterPlayed;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
	}
		
	public Move getLastMove()
	{
		return lastMove;
	}
	
	public int getLastLetterPlayed()
	{
		return lastLetterPlayed;
	}
	
	public int[][] getGameBoard()
	{
		return gameBoard;
	}
	
	public int getValueAt(int x, int y)
	{
		return gameBoard[x][y];
	}

	public void setLastMove(Move lastMove)
	{
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	public void setLastLetterPlayed(int lastLetterPlayed)
	{
		this.lastLetterPlayed = lastLetterPlayed;
	}
	
	public void setGameBoard(int[][] gameBoard)
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				this.gameBoard[i][j] = gameBoard[i][j];
			}
		}
	}
	
	
	
	
    //Make a move; it places a letter in the board
	public void makeMove(int row, int col, int letter)
	{
		gameBoard[row][col] = letter;
		findAllTurns(row, col ,letter,true);
		lastMove = new Move(row, col);
		lastLetterPlayed = letter;
	}
	
	public ArrayList<Integer> neighbors(int row,int col,int letter)//finds all the coordinates,of the opposite letter, which are around the given letter.For example:
	{															  // xxx
		ArrayList<Integer> coords=new ArrayList<Integer>();		 //  xox
		int j;													//	 xxx returns the coordiantes of Xs in an array list;
		int i=row-1;
		int c=col-1;
		if (i<0) i=0;
		if (c<0) c=0;
		while(i<=row+1 && i<8)
		{
			j=c;
			while (j<=col+1 && j<8)
			{
				
				if(gameBoard[i][j]==((-1)*letter))
				{
					coords.add(i);
					coords.add(j);
				}
				j++;
			}
			i++;
		}
		return coords;
		
	}
	
	public boolean allowMove(int letter)//returns True if the player with letter,has any legal moves
	{
	
		
		for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(gameBoard[row][col] == EMPTY)
				{
					
					if(findAllTurns(row,col,letter,false))return true;
					
				}
            }
        }
		return false;
	}
	
	public boolean findAllTurns(int row,int col,int letter,boolean Turn)
	{
		boolean flag=false;
		ArrayList<Integer> coords=new ArrayList<Integer>();
		coords=neighbors(row,col,letter);
		if(!coords.isEmpty())
		{
			int i=0;
			while(i<coords.size())
			{
				int Nrow=coords.get(i);
				int Ncol=coords.get(i+1);
				if(Check_n_Turn(Nrow,Ncol,Nrow-row,Ncol-col,letter,Turn)) 
				{ 
					flag=true;
					{
						if(!Turn)return true;//if we only need to check,return true when at least 1 leagal move found
					}
				}
				i=i+2;
			}
		}
		return flag;
	}
	
	public boolean Check_n_Turn(int row,int col,int stepRow,int stepCol,int letter,boolean Turn)//calls turnRecursive to check if its legal to change the values on the board
	{
		if(!((row==0 && stepRow==-1)||(col==0 && stepCol==1)||(row==7 && stepRow==1)||(col==7 && stepCol==1)))
		{
			
			return Check_n_TurnRecursive(row,col,stepRow,stepCol,letter,Turn);
		}
		return false;
	}
	private boolean Check_n_TurnRecursive(int row,int col,int stepRow,int stepCol,int letter,boolean Turn)
	{
		if(row>7||row<0||col>7||col<0)	return false;
		if(gameBoard[row][col]==EMPTY)	return false;
		if(gameBoard[row][col]==letter) return true;
		
		if(Check_n_TurnRecursive(row+stepRow,col+stepCol,stepRow,stepCol,letter,Turn))
		{
			
			if(Turn)gameBoard[row][col]=letter;
			return true;
		}
		else
		{
			return false;
		}
	}

    //Checks whether a move is valid
	public boolean isValidMove(int row, int col,int letter)
	{
		int[][] fakeBoard=new int[8][8];
		if ((row == -1) || (col == -1) || (row > 7) || (col > 7))
		{
			
			return false;
		}
		if(gameBoard[row][col] != EMPTY)
		{
			
			return false;
		}
		
		return findAllTurns(row,col,letter,false);
			
	}

    
    public boolean isTerminal()
    {
		boolean flag=false;
        //Checking if there is at least one empty tile
        outerloop:
		for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(gameBoard[row][col] == EMPTY)
				{
					flag=true;
                    break outerloop;
                }
            }
        }
		if(flag==false) return true;
		if(!allowMove(lastLetterPlayed*(-1)))
		{
			if(!allowMove(lastLetterPlayed))
			{
				return true;
			}
		}
		
        return false;
		
    }

    //Prints the board
	public void print()
	{
		System.out.println("   0 1 2 3 4 5 6 7");
		for(int row=0; row<8; row++)
		{
			System.out.print(" "+row+" ");
			for(int col=0; col<8; col++)
			{
				switch (gameBoard[row][col])
				{
					case X:
						System.out.print("X ");
						break;
					case O:
						System.out.print("O ");
						break;
					case EMPTY:
						System.out.print("- ");
						break;
					default:
						break;
				}
			}
			System.out.println(row);
		}
		System.out.println("   **************");
	}
	
	public ArrayList<Board> getChildren(int letter)
	{
		ArrayList<Board> children = new ArrayList<Board>();
		for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(isValidMove(row, col, letter))
				{
					Board child = new Board(this);
					child.makeMove(row, col, letter);
					children.add(child);
				}
			}
		}
		return children;
	}
	
	
	public int Evaluate(int pc_int)
	{
		
		int sum=0;
		if(!allowMove(pc_int*(-1))) sum+=100;
		if(!allowMove(pc_int)) sum-=100;
		
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(gameBoard[i][j]!=EMPTY)
				{
					if( ( ((i==0)||(i==7))&&((j==0)||(j==7)) ) )
					{
						if(gameBoard[i][j]==pc_int)
						{
							sum+=500;
						}
						else
						{
							sum-=500;
						}
					}
					else if((i==0)||(i==7)||(j==0)||(j==7))
					{
						if(gameBoard[i][j]==pc_int)
						{
							sum += 50;
						}
						else
						{
							sum-=50;
						}
					}
					else if((i==3 || i==4)&&(j==3||j==4))
					{
						if(gameBoard[i][j]==pc_int)
						{
							sum += 15;
						}
						else
						{
							sum-=15;
						}
					}
					else
					{
						if(gameBoard[i][j]==pc_int)
						{
							sum ++;
						}
						else
						{
							sum--;
						}
					}
				}
			}
		}
		return sum;
	}
	
	public void	getscore()
	{
		int sumX=0;
		int sumO=0;
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(gameBoard[i][j] == 1)
				{
					sumX ++;
				}
				else if(gameBoard[i][j]==-1)
				{
					sumO++;
				}
			}
		}
		
		if(sumX>sumO)
		{
			System.out.println("X Wins!");
		}
		else if(sumO>sumX)
		{
			System.out.println("O Wins!\n");
		}
		else 
		{
			System.out.println("Tied Game");
		}
		System.out.println("Score: \nX: " + sumX+"\nO: " + sumO);
	}
}

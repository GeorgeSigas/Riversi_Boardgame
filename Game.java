import java.util.ArrayList;
import java.util.Random;


public class Game
{
	int maxDepth;
	char player;
	char pc;
	
	
	public Game(int maxDepth, char player, char pc)
	{
		this.maxDepth=maxDepth;
		this.player=player;
		this.pc=pc;
	}
	
	public int get_pc_int()
	{
		if(pc == 'X')
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	
	
	public Move MiniMax(Board board)//we only need max here,cause we call Minimax only for user's opponent
	{
		
			return max(new Board(board), 0,Integer.MIN_VALUE,Integer.MAX_VALUE);
	
	}

	public Move max(Board board, int depth,int a,int b)
	{
		Random r = new Random();
		
		if((board.isTerminal()) || (depth == maxDepth))
		{
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.Evaluate(get_pc_int()));
			return lastMove;
		}
		
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(get_pc_int()));
		Move maxMove = new Move(Integer.MIN_VALUE);
		for (Board child : children)
		{
			
				Move move = min(child, depth + 1,a,b);
				
			
				if(move.getValue() >= maxMove.getValue())
				{
					if ((move.getValue() == maxMove.getValue()))
					{
					
						if (r.nextInt(2) == 0)
						{
							maxMove.setRow(child.getLastMove().getRow());
							maxMove.setCol(child.getLastMove().getCol());
							maxMove.setValue(move.getValue());
						}
					}
					else
					{
						maxMove.setRow(child.getLastMove().getRow());
						maxMove.setCol(child.getLastMove().getCol());
						maxMove.setValue(move.getValue());
					}
				
				}
				if(maxMove.getValue()>=b) return maxMove;
				if(maxMove.getValue()>a) a=maxMove.getValue();
			
		}
		return maxMove;
	}
	
	 
	public Move min(Board board, int depth,int a,int b)
	{
		Random r = new Random();
		if((board.isTerminal()) || (depth == maxDepth))
		{
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.Evaluate(get_pc_int()));
			return lastMove;
		}
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(get_pc_int()*(-1)));
		Move minMove = new Move(Integer.MAX_VALUE);
		for (Board child : children)
		{
			
				Move move = max(child, depth + 1,a,b);
				if(move.getValue() <= minMove.getValue())
				{
					if ((move.getValue() == minMove.getValue()))
					{
						if (r.nextInt(2) == 0)
						{
							minMove.setRow(child.getLastMove().getRow());
							minMove.setCol(child.getLastMove().getCol());
							minMove.setValue(move.getValue());
						}
					}
					else
					{
						minMove.setRow(child.getLastMove().getRow());
						minMove.setCol(child.getLastMove().getCol());
						minMove.setValue(move.getValue());
					}
				}
				if(minMove.getValue()<=a) return minMove;              
				if(b>minMove.getValue()) b=minMove.getValue();
			
		}
		return minMove;
	}
}
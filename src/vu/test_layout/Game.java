package vu.test_layout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Game extends Activity 
{
	public static final String DIFFICULTY_KEY="vu.test_layout.difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	public static final String easy = "360000000004230800000004200" +
									  "070460003820000014500013020" +
									  "001900000007048300000000045";
	public static final String medium = "650000070000506000014000005" +
									    "007009000002314700000700800" +
									    "500000630000201000030000097";
	public static final String hard = "009000000080605020501078000" +
									  "000000700706040102004000000" +
									  "000720903090301080000000600";
	public int soduku[][] = new int[9][9];
	public int uselog[] = new int[9*9];
	public static final int maxrow = 9;
	public static final int maxcol = 9;
	public SodukuView sodukuView;
	protected static String sudoku_tile = "";
	protected int diff = 0;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		sodukuView = new SodukuView(this);
		Intent intent = getIntent();
		Bundle passData = intent.getExtras();
		diff = passData.getInt("diff");
		switch (diff)
		{
		case DIFFICULTY_EASY:
			sudoku_tile = easy;
			break;
		case DIFFICULTY_MEDIUM:
			sudoku_tile = medium;
			break;
		case DIFFICULTY_HARD:
			sudoku_tile = hard;
			break;
			default:
				sudoku_tile = easy;
		}
		int i = 0, j = 0, begin = 1;
		for (int x = 0; x < sudoku_tile.length(); x++)
		{
			if (x < begin*9)
			{
				soduku[i][j] = sudoku_tile.charAt(x) - '0';
				j++;
			}
			else
			{
				i++;
				begin++;
				j = 0;
				soduku[i][j] = sudoku_tile.charAt(x) - '0';
				j++;
			}
		}
		Log.d("Pass Data ", String.valueOf(diff) + " | " + sudoku_tile);
		setContentView(sodukuView);
		sodukuView.requestFocus();
	}
	public String getTileString(int x, int y)
	{
		if (soduku[x][y] == 0)
			return "";
		return String.valueOf(soduku[x][y]);
	}	
	public int[] getValidOfBox(int x, int y)
	{
		int valid[] = new int[9];
		int beginX = 0, beginY = 0;
		if (x < 3)
			beginX = 0;
		else if (x < 6)
			beginX = 3;
		else if (x < 9)
			beginX = 6;
		if (y < 3)
			beginY = 0;
		else if (y < 6)
			beginY = 3;
		else if (y < 9)
			beginY = 6;
		int rx = 0, ry = 0;
		for (int zx = beginX; zx < beginX + 3; zx++)
		{
			ry = 0;
			for (int zy = beginY; zy < beginY + 3; zy++)
			{				
				valid[rx * 3 + ry++] = soduku[zx][zy];
			}
			rx++;
		}		
		return valid;
	}
	public int[] getValidOfRowCol (int x, int y)
	{
		int valid[] = new int[18];
		int idx = 0;
		//Collect row
		for (int i = 0; i < maxcol; i++)
		{
			valid[idx++] = soduku[x][i];
		}
		for (int i = 0; i < maxrow; i++)
		{
			valid[idx++] = soduku[i][y];
		}
		return valid;
	}
	public boolean checkValidOfColRow (int x, int y, int value)
	{
		//check row
		for (int i = 0; i < maxcol; i++)
		{
			if (soduku[x][i] == value)
				return false;
		}
		//check column
		for (int i = 0; i < maxrow; i++)
		{
			if (soduku[i][y] == value)
				return false;
		}
		return true;
	}
	public void setTile(int x, int y, int value)
	{
		soduku[x][y] = value;
	}
	public boolean checkTileIfValid (int x, int y, int value)
	{
		boolean valid = true;
		int box[] = getValidOfBox(x, y);
		for (int i = 0; i < box.length; i++)
		{
			if (value == box[i])
			{
				valid = false;
			}
		}		
		return valid;
	}
	public void showKeyPad (int x, int y)
	{		
		Dialog bach = new KeyPad(this, this, getValidOfBox(x, y), sodukuView, getValidOfRowCol(x, y));
		bach.show();
	}
}

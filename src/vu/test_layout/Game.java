package vu.test_layout;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity 
{
	public static final String DIFFICULTY_KEY="vu.test_layout.difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	
	public int soduku[] = new int[9*9];
	public SodukuView sodukuView;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		sodukuView = new SodukuView(this);
		setContentView(sodukuView);
		sodukuView.requestFocus();
	}
}

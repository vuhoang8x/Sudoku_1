package vu.test_layout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class KeyPad extends Dialog 
{
	int valid[];
	SodukuView soduku;
	
	public KeyPad (Context context, int used[], SodukuView soduku)
	{
		super (context);
		valid = used;
		this.soduku = soduku;
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Keypad");
		setContentView(R.layout.keypad);
	}
}

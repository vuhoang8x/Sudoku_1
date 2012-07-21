package vu.test_layout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class KeyPad extends Dialog 
{
	int valid[];
	SodukuView soduku;
	View key[] = new View[9];
	
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
		findViews();
		for (int element = 0; element < valid.length; element++)
		{
			if (valid[element] != 0)
				key[element].setVisibility(View.INVISIBLE);
		}
		setListeners();
	}
	protected void findViews ()
	{
		key[0] = findViewById(R.id.keypad_1);
		key[1] = findViewById(R.id.keypad_2);
		key[2] = findViewById(R.id.keypad_3);
		key[3] = findViewById(R.id.keypad_4);
		key[4] = findViewById(R.id.keypad_5);
		key[5] = findViewById(R.id.keypad_6);
		key[6] = findViewById(R.id.keypad_7);
		key[7] = findViewById(R.id.keypad_8);
		key[8] = findViewById(R.id.keypad_9);
	}
	protected void setListeners ()
	{
		for (int i = 0; i < key.length; i++)
		{
			final int value = i + 1;
			key[i].setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isValid(value))
						resultKeyPress(value);
				}
			});
		}
	}
	protected void resultKeyPress(int value)
	{
		soduku.setSelectedTile(value);
		dismiss();
	}
	protected boolean isValid (int value)
	{
		for (int i = 0; i < valid.length;i++)
			if (valid[i] == value)
				return false;
		return true;
	}
}

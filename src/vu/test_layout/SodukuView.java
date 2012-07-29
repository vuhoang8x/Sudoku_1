package vu.test_layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class SodukuView extends View 
{
	private float width;
	private float height;
	private int selX;
	private int selY;
	private final Rect selRect = new Rect();
	public final Game game;
	public SodukuView(Context context)
	{
		super(context);
		game = (Game)context;
		setFocusable(true);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		width = w/9f;
		height = h/9f;
		getRect(selX, selY, selRect);
		super.onSizeChanged(w, h, oldw, oldh);
	}
	private void getRect(int x, int y, Rect rect)
	{
		rect.set((int)(x*width), (int)(y*height), (int)(x*width+width), (int)(y*height + height));
	}
	private void select (int x, int y)
	{
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		getRect(selX, selY, selRect);
		invalidate(selRect);
	}
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
		case KeyEvent.KEYCODE_DPAD_UP:
			select(selX, selY - 1);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			select(selX, selY + 1);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			select(selX - 1, selY);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			select(selX + 1, selY);
			break;
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_SPACE:
			setSelectedTile(0);
			break;
		case KeyEvent.KEYCODE_1:
			setSelectedTile(1);
			break;
		case KeyEvent.KEYCODE_2:
			setSelectedTile(2);
			break;
		case KeyEvent.KEYCODE_3:
			setSelectedTile(3);
			break;
		case KeyEvent.KEYCODE_4:
			setSelectedTile(4);
			break;
		case KeyEvent.KEYCODE_5:
			setSelectedTile(5);
			break;
		case KeyEvent.KEYCODE_6:
			setSelectedTile(6);
			break;
		case KeyEvent.KEYCODE_7:
			setSelectedTile(7);
			break;
		case KeyEvent.KEYCODE_8:
			setSelectedTile(8);
			break;
		case KeyEvent.KEYCODE_9:
			setSelectedTile(9);			
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			game.showKeyPad(selX, selY);
			break;
			default:
				return super.onKeyDown(keyCode, event);
		}
		return true;
	}
	@Override
	protected void onDraw (Canvas canvas)
	{
		Log.d("Soduko", "onDraw...Circle");
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
		Paint dark = new Paint();
		dark.setColor(getResources().getColor(R.color.dark));
		Paint hilite = new Paint();
		hilite.setColor(getResources().getColor(R.color.hilite));
		Paint light = new Paint();
		light.setColor(getResources().getColor(R.color.light));
		
		//Draw the minor grid lines
		for (int i = 0; i < 9; i++)
		{
			canvas.drawLine(0, i*height, getWidth(), i*height, light);
			//canvas.drawLine(0, i*height + 1, getWidth(), i*height + 1, hilite);
			
			canvas.drawLine(i*width, 0, i*width, getHeight(), light);
			//canvas.drawLine(i*width+1, 0, i*width+1, getHeight(), hilite);
		}
		//Draw the major grid lines
		for (int i = 0; i < 9; i++)
		{
			if (i%3 != 0)
				continue;
			canvas.drawLine(0, i*height, getWidth(), i*height, dark);
			canvas.drawLine(0, i*height + 1, getWidth(), i*height + 1, hilite);
			
			canvas.drawLine(i*width, 0, i*width, getHeight(), dark);
			canvas.drawLine(i*width+1, 0, i*width+1, getHeight(), hilite);
		}
		
		//Fill number to grid
		Paint foreground = new Paint (Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(getResources().getColor(R.color.foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height * 0.75f);
		foreground.setTextScaleX(width/height);
		foreground.setTextAlign(Align.CENTER);
		//Draw number center of tile
		FontMetrics fm = foreground.getFontMetrics();
		float x = width/2;
		float y = height/2 - (fm.ascent + fm.descent)/2;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				canvas.drawText(game.getTileString(i, j), i * width + x, j * height + y, foreground);
		
		//Draw selected tile
		Paint selected = new Paint();
		selected.setColor(getResources().getColor(R.color.selected));
		canvas.drawRect(selRect, selected);
	}
	public void setSelectedTile (int value)
	{
		Log.d("Input ", "X: " + String.valueOf(selX) + " Y: " + String.valueOf(selY) + "--" + String.valueOf(value));
		if (value == 0)
		{
			game.setTile(selX, selY, 0);
			invalidate();
		}
		else
		{
			if (game.checkTileIfValid(selX, selY, value) && game.checkValidOfColRow(selX, selY, value))
			{
				game.setTile(selX, selY, value);
				invalidate();
			}
			else
			{
				game.showKeyPad(selX, selY);
			}
		}
	}
}

package vu.test_layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Test_layoutActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnNew = (Button)findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);
        Button btnContinue = (Button)findViewById(R.id.btnContinue);       
        btnContinue.setOnClickListener(this);
        Button btnAbout = (Button)findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);
    }
  
    public void onClick(View view)
    {
    	switch (view.getId())
    	{
    	case R.id.btnContinue:
    		((TextView)findViewById(R.id.lblResult)).setText("Continue");    	
    		break;
    	case R.id.btnNew:
    		openNewGameDialog();
    		break;
    	case R.id.btnAbout:
    		Intent i = new Intent (Test_layoutActivity.this, About.class);
    		startActivity(i);
    		break;
    	}
    }
    
    public void openNewGameDialog()
    {
    	new AlertDialog.Builder(this).setTitle(R.string.new_game_title).setItems(R.array.difficulty
    			, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						startGame(which);
					}
				}).show();
    }
    public void startGame (int dif)
    {
    	String d = "" + dif;
    	((TextView)findViewById(R.id.lblResult)).setText(d);
    	Intent intent = new Intent(Test_layoutActivity.this, Game.class); 
    	Bundle test = new Bundle();
    	test.putInt("diff", dif);
    	intent.putExtras(test);
    	startActivity(intent);
    }
}
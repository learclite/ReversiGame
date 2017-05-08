package com.example.florian.reversi.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.florian.reversi.R;
import com.example.florian.reversi.fragment.RulesFragment;
import com.example.florian.reversi.service.MusicService;

public class MainMenuActivity extends AppCompatActivity {

    private boolean mIsBound = false;
    static MusicService mServ;
    static boolean musicFlag = true;
    private ServiceConnection Scon = new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        doBindService();

        Intent musicServiceIntent = new Intent(this, MusicService.class);
        startService(musicServiceIntent);
        musicFlag = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(MainMenuActivity.musicFlag == true)
            MainMenuActivity.mServ.resumeMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(MainMenuActivity.musicFlag == true)
            MainMenuActivity.mServ.pauseMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent musicServiceIntent = new Intent(this, MusicService.class);
        stopService(musicServiceIntent);
        doUnbindService();

        musicFlag = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.main_menu_sound:
                musicFlag = !musicFlag;
                if(musicFlag){
                    mServ.resumeMusic();
                }else{
                    mServ.pauseMusic();
                }
                break;
            case R.id.game_rules:
                showRules();
                break;
            default:
                break;
        }

        return true;
    }

    public void startGame(View view){
        Intent startGame = new Intent("com.example.florian.reversi.activity.TwoPlayerGameActivity");
        startActivity(startGame);
    }

    public void startGameAI(View view){
        Intent startGameAI = new Intent("com.example.florian.reversi.activity.SinglePlayerGameActivity");
        startActivity(startGameAI);
    }

    public void exitGame(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainMenuActivity.this);
        dialog.setTitle("Close application");
        dialog.setMessage("Are you sure you want to close the application?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("No",null);
        dialog.setCancelable(false);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 4
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainMenuActivity.this);
        dialog.setTitle("Close application");
        dialog.setMessage("Are you sure you want to close the application?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("No",null);
        dialog.setCancelable(false);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public void showRules(){
        android.app.FragmentManager manager = getFragmentManager();
        RulesFragment myRules = new RulesFragment();
        myRules.show(manager,"MyRules");

    }
}

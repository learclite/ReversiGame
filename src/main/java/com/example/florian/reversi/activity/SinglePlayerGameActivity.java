package com.example.florian.reversi.activity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.florian.reversi.gameLogic.GameActions;
import com.example.florian.reversi.R;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SinglePlayerGameActivity extends AppCompatActivity implements OnClickListener {

    private static final int BOARD_SIZE = 8;

    private List<ImageView> cellArray;

    private GameActions game;
    private HashSet<Integer> currentPossibleMoves;
    private int cellSize;

    //true - black; false - white
    private boolean playerTurn;
    private boolean blackCanMove;
    private boolean whiteCanMove;

    private boolean showPossibleMoves;

    //1 - easy, 2 - medium, 3 - hard
    private int gameDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_game);

        initializeLayout();
        startGame();

        final Button hintBtn2 = (Button)findViewById(R.id.hint_button2);
        hintBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPossibleMoves = !showPossibleMoves;
                showHint(playerTurn);
            }
        });

        final Button restartBtn2 = (Button)findViewById(R.id.restart_button2);
        restartBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(SinglePlayerGameActivity.this);
                dialog.setTitle("GAME PAUSED");

                dialog.setMessage("Are you sure you want to restart the game?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                });
                dialog.setNegativeButton("No", null);
                dialog.setCancelable(false);

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        final Button exitBtn2 = (Button)findViewById(R.id.exit_button2);
        exitBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SinglePlayerGameActivity.this);
                dialog.setTitle("Exit Game");
                dialog.setMessage("Are you sure you want to exit game?");
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
        });

        final ImageButton toggle2 = (ImageButton)findViewById(R.id.toggle_sound2);
        toggle2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.musicFlag = !MainMenuActivity.musicFlag;
                if(MainMenuActivity.musicFlag){
                    MainMenuActivity.mServ.resumeMusic();
                    TextView soundText = (TextView)findViewById(R.id.sound_text_2);
                    soundText.setText("Sound On");
                }else{
                    MainMenuActivity.mServ.pauseMusic();
                    TextView soundText = (TextView)findViewById(R.id.sound_text_2);
                    soundText.setText("Sound Off");
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public void onClick(View v) {
        boolean changePlayer = true;
        if (playerTurn) {
            if (hasPossibleMoves(currentPossibleMoves.size())) {
                blackCanMove = true;

                ImageView selectedCell = (ImageView) v;

                //get coordinates of clicked image
                LinearLayout imageLayout = (LinearLayout) selectedCell.getParent();
                //y
                int coordY = imageLayout.indexOfChild(selectedCell);
                //x
                int coordX = ((LinearLayout) imageLayout.getParent()).indexOfChild(imageLayout);

                if (currentPossibleMoves.contains(coordX * BOARD_SIZE + coordY)) {
                    //take pieces
                    game.capturePieces(coordX, coordY, playerTurn);
                    updateBoard();
                    //media sound
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.piece_move_sound);
                    mp.start();
                } else {
                    Toast.makeText(this, "MOVE NOT POSSIBLE!", Toast.LENGTH_SHORT).show();
                    changePlayer = false;
                }
            } else {
                blackCanMove = false;
            }

            if (changePlayer == true) {
                if (endCondition() == false) {
                    playerTurn = !playerTurn;
                    currentPossibleMoves = game.getPossibleMoves(playerTurn);

                    setTurnText(playerTurn);
                    setScoreText(game.getBlackCount(), game.getWhiteCount());
                    setAvailableMovesText(currentPossibleMoves.size());

                    Button hintBtn = (Button) findViewById(R.id.hint_button2);
                    hintBtn.setEnabled(false);

                    Button restartBtn = (Button) findViewById(R.id.restart_button2);
                    restartBtn.setEnabled(false);

                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (hasPossibleMoves(currentPossibleMoves.size())) {
                                whiteCanMove = true;
                                int aiMove = -1;
                                if (gameDifficulty == 1) {
                                    aiMove = game.easyDifficultyMove(playerTurn);
                                } else if (gameDifficulty == 2) {
                                    aiMove = game.mediumDifficultyMove(playerTurn);
                                } else if (gameDifficulty == 3) {
                                    aiMove = game.hardDifficultyMove(playerTurn);
                                }
                                game.capturePieces(aiMove / BOARD_SIZE, aiMove % BOARD_SIZE, playerTurn);
                                updateBoard();
                                //media sound
                                MediaPlayer mp2 = MediaPlayer.create(SinglePlayerGameActivity.this, R.raw.piece_move_sound);
                                mp2.start();

                            } else {
                                whiteCanMove = false;
                            }
                            if (endCondition() == false) {

                                playerTurn = !playerTurn;
                                currentPossibleMoves = game.getPossibleMoves(playerTurn);

                                showHint(playerTurn);
                                setTurnText(playerTurn);
                                setScoreText(game.getBlackCount(), game.getWhiteCount());
                                setAvailableMovesText(currentPossibleMoves.size());

                                Button hintBtn2 = (Button) findViewById(R.id.hint_button2);
                                hintBtn2.setEnabled(true);

                                Button restartBtn2 = (Button) findViewById(R.id.restart_button2);
                                restartBtn2.setEnabled(true);
                            } else {
                                setTurnText(playerTurn);
                                setScoreText(game.getBlackCount(), game.getWhiteCount());
                                setAvailableMovesText(0);
                                endGame();
                            }
                        }
                    }, 1500);
                } else {
                    setTurnText(playerTurn);
                    setScoreText(game.getBlackCount(), game.getWhiteCount());
                    setAvailableMovesText(0);
                    endGame();
                }
            }
        }
    }

    public void initializeLayout(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;

        LinearLayout linear = (LinearLayout)findViewById(R.id.gameBoard2);
        linear.setPadding(0,displayHeight * 10 /100,0,displayHeight * 10 / 100);
        int paddingTop = linear.getPaddingTop();
        int paddingBottom = linear.getPaddingBottom();
        cellSize = (displayHeight - (paddingTop + paddingBottom) )/BOARD_SIZE;
    }

    public void startGame(){
        game = new GameActions(BOARD_SIZE);

        cellArray = new ArrayList<>();

        LinearLayout rowsLayout = (LinearLayout)findViewById(R.id.gameBoard2);

        for(int i=0; i<BOARD_SIZE;i++)
        {
            LinearLayout columnsLayout = (LinearLayout)rowsLayout.getChildAt(i);
            for(int j=0;j<BOARD_SIZE;j++)
            {
                ImageView currentImage = (ImageView)columnsLayout.getChildAt(j);

                switch(game.getBoard()[i][j]) {
                    case 0:
                        currentImage.setImageResource(R.drawable.board_cell);
                        currentImage.setMaxHeight(cellSize);
                        currentImage.setMaxWidth(cellSize);
                        break;
                    case 1:
                        currentImage.setImageResource(R.drawable.black_piece);
                        currentImage.setMaxHeight(cellSize);
                        currentImage.setMaxWidth(cellSize);
                        break;
                    case 2:
                        currentImage.setImageResource(R.drawable.white_piece);
                        currentImage.setMaxHeight(cellSize);
                        currentImage.setMaxWidth(cellSize);
                        break;
                }
                currentImage.setOnClickListener(this);
                cellArray.add(currentImage);
            }
        }
        //black starts
        playerTurn = true;
        blackCanMove = true;
        whiteCanMove = true;
        currentPossibleMoves = game.getPossibleMoves(playerTurn);
        showPossibleMoves = false;

        setTurnText(playerTurn);
        setAvailableMovesText(currentPossibleMoves.size());
        setScoreText(game.getBlackCount(),game.getWhiteCount());

        TextView soundText = (TextView)findViewById(R.id.sound_text_2);
        if(MainMenuActivity.musicFlag){
            soundText.setText("Sound On");
        }else{
            soundText.setText("Sound Off");
        }

        chooseDifficulty();
    }

    public void endGame(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(SinglePlayerGameActivity.this);
        dialog.setTitle("Game over!");
        String message = "";

        if(game.getBlackCount()>game.getWhiteCount()){
            message = "BLACK WINS! Score : " + game.getBlackCount() + " - " + game.getWhiteCount()
                    + "\nPlay again or exit to main menu?";
        }
        else
        if(game.getWhiteCount()>game.getBlackCount()){
            message = "WHITE WINS! Score : " + game.getWhiteCount() + " - " + game.getBlackCount()
                    + "\nPlay again or exit to main menu?";
        }
        else{
            message = "DRAW! Score : " + game.getWhiteCount() + " - " + game.getBlackCount()
                    + "\nPlay again or exit to main menu?";
        }

        dialog.setMessage(message);
        dialog.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startGame();
            }
        });
        dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setCancelable(false);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public void chooseDifficulty(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(SinglePlayerGameActivity.this);
        dialog.setTitle("Choose Difficulty").setCancelable(false).setItems(R.array.gameDifficulty, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameDifficulty = which + 1;
            }
        });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public void updateBoard(){
        LinearLayout rowsLayout = (LinearLayout)findViewById(R.id.gameBoard2);

        for(int i=0; i<BOARD_SIZE;i++)
        {
            LinearLayout columnsLayout = (LinearLayout)rowsLayout.getChildAt(i);
            for(int j=0;j<BOARD_SIZE;j++)
            {
                ImageView currentImage = (ImageView)columnsLayout.getChildAt(j);

                switch(game.getBoard()[i][j]) {
                    case 0:
                        currentImage.setImageResource(R.drawable.board_cell);
                        break;
                    case 1:
                        currentImage.setImageResource(R.drawable.black_piece);
                        break;
                    case 2:
                        currentImage.setImageResource(R.drawable.white_piece);
                        break;
                }
            }
        }
    }

    public boolean hasPossibleMoves(int moveNumber){
        if(moveNumber == 0)
            return false;
        else
            return true;
    }

    public boolean endCondition(){
        if(game.getPiecesCount() == BOARD_SIZE * BOARD_SIZE || (blackCanMove == false && whiteCanMove == false))
            return true;
        return false;
    }

    public void showHint(boolean playerTurn) {
        if (showPossibleMoves == true) {
            if (playerTurn == true && hasPossibleMoves(currentPossibleMoves.size())) {
                for (Integer item : currentPossibleMoves) {
                    cellArray.get(item).setImageResource(R.drawable.black_piece_hint);
                }
            }
        } else {
            for (Integer item : currentPossibleMoves) {
                cellArray.get(item).setImageResource(R.drawable.board_cell);
            }
        }
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

        AlertDialog.Builder dialog = new AlertDialog.Builder(SinglePlayerGameActivity.this);
        dialog.setTitle("Alert!");
        dialog.setMessage("Are you sure you want to exit game?");
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

    public void setTurnText(boolean playerTurn){
        if(playerTurn){
            TextView turnText = (TextView)findViewById(R.id.playerTurn2);
            turnText.setTextAppearance(SinglePlayerGameActivity.this,R.style.BlackTurnTextStyle);
            turnText.setText(R.string.black_turn);
        }
        else{
            TextView turnText = (TextView)findViewById(R.id.playerTurn2);
            turnText.setTextAppearance(SinglePlayerGameActivity.this,R.style.WhiteTurnTextStyle);
            turnText.setText(R.string.white_turn);
        }
    }

    public void setAvailableMovesText(int availableMoves){
        if(availableMoves > 0){

            String text = "Available moves - " + availableMoves;
            TextView movesText = (TextView)findViewById(R.id.numberPossibleMoves2);
            movesText.setTextAppearance(SinglePlayerGameActivity.this,R.style.MovesAvailableStyle);
            movesText.setText(text);
        }
        else{
            TextView movesText = (TextView)findViewById(R.id.numberPossibleMoves2);
            movesText.setTextAppearance(SinglePlayerGameActivity.this,R.style.NoMovesAvailableStyle);
            movesText.setText(R.string.no_moves_available);

        }
    }

    public void setScoreText(int blackPoints, int whitePoints){
        String text = "Score\n" + "B : " + blackPoints + " / " + "W : " + whitePoints;

        TextView scoreText = (TextView)findViewById(R.id.currentScore2);
        scoreText.setTextAppearance(SinglePlayerGameActivity.this,R.style.ScoreStyle);
        scoreText.setText(text);
    }
}

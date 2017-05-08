package com.example.florian.reversi.gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Florian on 20-Apr-17.
 */

public class GameActions {

    private int boardSize;
    private int[][] board;

    private int whiteCount;
    private int blackCount;

    //0-blank 1-black 2-white

    public GameActions(int boardSize){
        this.boardSize=boardSize;
        this.board=new int[boardSize][boardSize];

        board[boardSize/2-1][boardSize/2-1] = 2;
        board[boardSize/2][boardSize/2] = 2;
        board[boardSize/2][boardSize/2-1] = 1;
        board[boardSize/2-1][boardSize/2] = 1;

        this.whiteCount = 2;
        this.blackCount = 2;
    }

    public int[][] getBoard(){
        return this.board;
    }

    public int getWhiteCount(){
        return this.whiteCount;
    }

    public int getBlackCount(){
        return this.blackCount;
    }

    public int getPiecesCount(){
        return this.whiteCount + this.blackCount;
    }

    public void setBoardCell(int x, int y, int value){
        this.board[x][y] = value;
    }

    public HashSet<Integer> getPossibleMoves(boolean playerTurn){
        HashSet<Integer> movesList = new HashSet<Integer>();
        //black turn
        if(playerTurn == true){
            for(int i=0 ; i<boardSize; i++)
                for(int j=0;j<boardSize;j++)
                {
                    if(board[i][j] == 1)
                    {
                        int northWest = verifyNorthWest(i,j,playerTurn);
                        int north = verifyNorth(i,j,playerTurn);
                        int northEast = verifyNorthEast(i,j,playerTurn);
                        int east = verifyEast(i,j,playerTurn);
                        int southEast = verifySouthEast(i,j,playerTurn);
                        int south = verifySouth(i,j,playerTurn);
                        int southWest = verifySouthWest(i,j,playerTurn);
                        int west = verifyWest(i,j,playerTurn);

                        if(northWest != -1)
                            movesList.add(northWest);
                        if(north != -1)
                            movesList.add(north);
                        if(northEast != -1)
                            movesList.add(northEast);
                        if(east != -1)
                            movesList.add(east);
                        if(southEast != -1)
                            movesList.add(southEast);
                        if(south != -1)
                            movesList.add(south);
                        if(southWest != -1)
                            movesList.add(southWest);
                        if(west != -1)
                            movesList.add(west);
                    }
                }
        }
        else{
            for(int i=0 ; i<boardSize; i++)
                for(int j=0;j<boardSize;j++)
                {
                    if(board[i][j] == 2)
                    {
                        int northWest = verifyNorthWest(i,j,playerTurn);
                        int north = verifyNorth(i,j,playerTurn);
                        int northEast = verifyNorthEast(i,j,playerTurn);
                        int east = verifyEast(i,j,playerTurn);
                        int southEast = verifySouthEast(i,j,playerTurn);
                        int south = verifySouth(i,j,playerTurn);
                        int southWest = verifySouthWest(i,j,playerTurn);
                        int west = verifyWest(i,j,playerTurn);

                        if(northWest != -1)
                            movesList.add(northWest);
                        if(north != -1)
                            movesList.add(north);
                        if(northEast != -1)
                            movesList.add(northEast);
                        if(east != -1)
                            movesList.add(east);
                        if(southEast != -1)
                            movesList.add(southEast);
                        if(south != -1)
                            movesList.add(south);
                        if(southWest != -1)
                            movesList.add(southWest);
                        if(west != -1)
                            movesList.add(west);

                    }
                }
        }
        return movesList;
    }

    public int verifyNorthWest(int x, int y, boolean playerTurn) {
        if (playerTurn == true) {
            int x1 = x - 1;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0 && y1 >= 0) {
                    if (board[x1][y1] == 2) {
                        x1 = x1 - 1;
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
        else {
            int x1 = x - 1;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0 && y1 >= 0) {
                    if (board[x1][y1] == 1) {
                        x1 = x1 - 1;
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
    }

    public int verifyNorth(int x, int y, boolean playerTurn){
        if (playerTurn == true) {
            int x1 = x - 1;
            int y1 = y;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0) {
                    if (board[x1][y1] == 2) {
                        x1 = x1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
        else {
            int x1 = x - 1;
            int y1 = y;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0) {
                    if (board[x1][y1] == 1) {
                        x1 = x1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
    }

    public int verifyNorthEast(int x, int y, boolean playerTurn)
    {
        if (playerTurn == true) {
            int x1 = x - 1;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0 && y1 < boardSize) {
                    if (board[x1][y1] == 2) {
                        x1 = x1 - 1;
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
        else {
            int x1 = x - 1;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0 && y1 < boardSize) {
                    if (board[x1][y1] == 1) {
                        x1 = x1 - 1;
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
    }

    public int verifyEast(int x, int y, boolean playerTurn)
    {
        if (playerTurn == true) {
            int x1 = x ;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (y1 < boardSize) {
                    if (board[x1][y1] == 2) {
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
        else {
            int x1 = x;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (y1 < boardSize) {
                    if (board[x1][y1] == 1) {
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
    }

    public int verifySouthEast(int x, int y, boolean playerTurn){
        if (playerTurn == true) {
            int x1 = x + 1;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize && y1 < boardSize) {
                    if (board[x1][y1] == 2) {
                        x1 = x1 + 1;
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
        else {
            int x1 = x + 1;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize && y1 < boardSize) {
                    if (board[x1][y1] == 1) {
                        x1 = x1 + 1;
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
    }

    public int verifySouth(int x, int y, boolean playerTurn){
        if (playerTurn == true) {
            int x1 = x + 1;
            int y1 = y ;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize) {
                    if (board[x1][y1] == 2) {
                        x1 = x1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
        else {
            int x1 = x + 1;
            int y1 = y ;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize) {
                    if (board[x1][y1] == 1) {
                        x1 = x1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
    }

    public int verifySouthWest(int x, int y, boolean playerTurn){
        if (playerTurn == true) {
            int x1 = x + 1;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize && y1 >= 0) {
                    if (board[x1][y1] == 2) {
                        x1 = x1 + 1;
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
        else {
            int x1 = x + 1;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize && y1 >= 0) {
                    if (board[x1][y1] == 1) {
                        x1 = x1 + 1;
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
    }

    public int verifyWest(int x, int y, boolean playerTurn){
        if (playerTurn == true) {
            int x1 = x ;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (y1 >= 0) {
                    if (board[x1][y1] == 2) {
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
        else {
            int x1 = x ;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (y1 >= 0) {
                    if (board[x1][y1] == 1) {
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1)
                return -1;
            else if (board[x1][y1] != 0)
                return -1;
            else
            if(capturedPieces == 0)
                return -1;
            else
                return x1 * boardSize + y1;
        }
    }

    public void capturePieces(int x, int y, boolean playerTurn){
        if(playerTurn) {
            int switchedPieces = 0;

            List<Integer> northWest = captureNorthWest(x, y, playerTurn);
            List<Integer> north = captureNorth(x, y, playerTurn);
            List<Integer> northEast = captureNorthEast(x, y, playerTurn);
            List<Integer> east = captureEast(x, y, playerTurn);
            List<Integer> southEast = captureSouthEast(x, y, playerTurn);
            List<Integer> south = captureSouth(x, y, playerTurn);
            List<Integer> southWest = captureSouthWest(x, y, playerTurn);
            List<Integer> west = captureWest(x, y, playerTurn);

            if (!northWest.isEmpty()) {
                for (Integer item : northWest) {
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,1);
                }
                switchedPieces += northWest.size();
            }

            if(!north.isEmpty()){
                for(Integer item : north){
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,1);
                }
                switchedPieces += north.size();
            }

            if (!northEast.isEmpty()) {
                for (Integer item : northEast) {
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,1);
                }
                switchedPieces += northEast.size();
            }
            if(!east.isEmpty()){
                for(Integer item : east){
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,1);
                }
                switchedPieces += east.size();
            }
            if (!southEast.isEmpty()) {
                for (Integer item : southEast) {
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,1);
                }
                switchedPieces += southEast.size();
            }
            if(!south.isEmpty()){
                for(Integer item : south){
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,1);
                }
                switchedPieces += south.size();
            }
            if (!southWest.isEmpty()) {
                for (Integer item : southWest) {
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,1);
                }
                switchedPieces += southWest.size();
            }
            if(!west.isEmpty()){
                for(Integer item : west){
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,1);
                }
                switchedPieces += west.size();
            }
            setBoardCell(x,y,1);
            this.whiteCount = this.whiteCount - switchedPieces;
            this.blackCount = this.blackCount + switchedPieces + 1;
        }
        else{
            int switchedPieces = 0;

            List<Integer> northWest = captureNorthWest(x, y, playerTurn);
            List<Integer> north = captureNorth(x, y, playerTurn);
            List<Integer> northEast = captureNorthEast(x, y, playerTurn);
            List<Integer> east = captureEast(x, y, playerTurn);
            List<Integer> southEast = captureSouthEast(x, y, playerTurn);
            List<Integer> south = captureSouth(x, y, playerTurn);
            List<Integer> southWest = captureSouthWest(x, y, playerTurn);
            List<Integer> west = captureWest(x, y, playerTurn);

            if (!northWest.isEmpty()) {
                for (Integer item : northWest) {
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,2);
                }
                switchedPieces += northWest.size();
            }

            if(!north.isEmpty()){
                for(Integer item : north){
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,2);
                }
                switchedPieces += north.size();
            }

            if (!northEast.isEmpty()) {
                for (Integer item : northEast) {
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,2);
                }
                switchedPieces += northEast.size();
            }
            if(!east.isEmpty()){
                for(Integer item : east){
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,2);
                }
                switchedPieces += east.size();
            }
            if (!southEast.isEmpty()) {
                for (Integer item : southEast) {
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,2);
                }
                switchedPieces += southEast.size();
            }
            if(!south.isEmpty()){
                for(Integer item : south){
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,2);
                }
                switchedPieces += south.size();
            }
            if (!southWest.isEmpty()) {
                for (Integer item : southWest) {
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,2);
                }
                switchedPieces += southWest.size();
            }
            if(!west.isEmpty()){
                for(Integer item : west){
                    int i = item / boardSize;
                    int j = item % boardSize;
                    setBoardCell(i,j,2);
                }
                switchedPieces += west.size();
            }
            setBoardCell(x,y,2);
            this.blackCount = this.blackCount - switchedPieces;
            this.whiteCount = this.whiteCount + switchedPieces + 1;
        }
    }

    public List<Integer> captureNorthWest(int x, int y, boolean playerTurn){
        List<Integer> capturedPiecesList = new ArrayList<>();

        if (playerTurn == true) {
            int x1 = x - 1;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0 && y1 >= 0) {
                    if (board[x1][y1] == 2) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 - 1;
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
 //           else
//            if(capturedPieces == 0)
  //              return -1;
            else
                return capturedPiecesList;
        }
        else {
            int x1 = x - 1;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0 && y1 >= 0) {
                    if (board[x1][y1] == 1) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 - 1;
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 2) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
    }

    public List<Integer> captureNorth(int x, int y, boolean playerTurn){
        List<Integer> capturedPiecesList = new ArrayList<>();

        if (playerTurn == true) {
            int x1 = x - 1;
            int y1 = y;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0) {
                    if (board[x1][y1] == 2) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
        else {
            int x1 = x - 1;
            int y1 = y;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0) {
                    if (board[x1][y1] == 1) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 2) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
    }

    public List<Integer> captureNorthEast(int x, int y, boolean playerTurn){
        List<Integer> capturedPiecesList = new ArrayList<>();

        if (playerTurn == true) {
            int x1 = x - 1;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0 && y1 < boardSize) {
                    if (board[x1][y1] == 2) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 - 1;
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
        else {
            int x1 = x - 1;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 >= 0 && y1 < boardSize) {
                    if (board[x1][y1] == 1) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 - 1;
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 2) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
    }

    public List<Integer> captureEast (int x, int y, boolean playerTurn){
        List<Integer> capturedPiecesList = new ArrayList<>();
        if (playerTurn == true) {
            int x1 = x ;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (y1 < boardSize) {
                    if (board[x1][y1] == 2) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
        else {
            int x1 = x;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (y1 < boardSize) {
                    if (board[x1][y1] == 1) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 2) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
    }

    public List<Integer> captureSouthEast(int x, int y, boolean playerTurn){
        List<Integer> capturedPiecesList = new ArrayList<>();
        if (playerTurn == true) {
            int x1 = x + 1;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize && y1 < boardSize) {
                    if (board[x1][y1] == 2) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 + 1;
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
        else {
            int x1 = x + 1;
            int y1 = y + 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize && y1 < boardSize) {
                    if (board[x1][y1] == 1) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 + 1;
                        y1 = y1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 2) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
    }

    public List<Integer> captureSouth(int x, int y, boolean playerTurn){
        List<Integer> capturedPiecesList = new ArrayList<>();
        if (playerTurn == true) {
            int x1 = x + 1;
            int y1 = y ;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize) {
                    if (board[x1][y1] == 2) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
        else {
            int x1 = x + 1;
            int y1 = y ;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize) {
                    if (board[x1][y1] == 1) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 + 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 2) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
    }

    public List<Integer> captureSouthWest(int x, int y, boolean playerTurn){
        List<Integer> capturedPiecesList = new ArrayList<>();
        if (playerTurn == true) {
            int x1 = x + 1;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize && y1 >= 0) {
                    if (board[x1][y1] == 2) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 + 1;
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
        else {
            int x1 = x + 1;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (x1 < boardSize && y1 >= 0) {
                    if (board[x1][y1] == 1) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        x1 = x1 + 1;
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 2) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
    }

    public List<Integer> captureWest(int x, int y, boolean playerTurn){
        List<Integer> capturedPiecesList = new ArrayList<>();
        if (playerTurn == true) {
            int x1 = x ;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (y1 >= 0) {
                    if (board[x1][y1] == 2) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
        else {
            int x1 = x ;
            int y1 = y - 1;
            int outOfBounds;
            int canContinue;
            int capturedPieces = 0;

            do {
                outOfBounds = 0;
                canContinue = 1;
                if (y1 >= 0) {
                    if (board[x1][y1] == 1) {
                        capturedPiecesList.add(x1 * boardSize + y1);
                        y1 = y1 - 1;
                        capturedPieces++;
                    } else {
                        canContinue = 0;
                    }
                } else {
                    outOfBounds = 1;
                }

            } while (outOfBounds == 0 && canContinue == 1);

            if (outOfBounds == 1) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            else if (board[x1][y1] != 2) {
                capturedPiecesList.clear();
                return capturedPiecesList;
            }
            //           else
//            if(capturedPieces == 0)
            //              return -1;
            else
                return capturedPiecesList;
        }
    }

    ///////////////////////////////FOR A.I.//////////////////////////////////////

    public Map<Integer, Integer> capturedPiecesCountByMove(boolean playerTurn){

        HashSet<Integer> possibleMoves = getPossibleMoves(playerTurn);
        Map<Integer, Integer> moveCaptures = new HashMap<>();

        int capturedPieces = 0;

        for(Integer move : possibleMoves) {
            int row = move / boardSize;
            int column = move % boardSize;

            List<Integer> northWest = captureNorthWest(row, column, playerTurn);
            List<Integer> north = captureNorth(row, column, playerTurn);
            List<Integer> northEast = captureNorthEast(row, column, playerTurn);
            List<Integer> east = captureEast(row, column, playerTurn);
            List<Integer> southEast = captureSouthEast(row, column, playerTurn);
            List<Integer> south = captureSouth(row, column, playerTurn);
            List<Integer> southWest = captureSouthWest(row, column, playerTurn);
            List<Integer> west = captureWest(row, column, playerTurn);

            capturedPieces = northWest.size() + north.size() + northEast.size() + east.size() +
                    southEast.size() + south.size() + southWest.size() + west.size();

            moveCaptures.put(move,capturedPieces);
        }
        return moveCaptures;
    }

    public int easyDifficultyMove(boolean playerTurn){
        Map<Integer,Integer> moveCaptures = capturedPiecesCountByMove(playerTurn);
        List<Integer> lowCaptureMoves = new ArrayList<>();
        List<Integer> orderedPosCaptures = new ArrayList<>();

        int maxCaptures = 0;

        for(Map.Entry<Integer,Integer> entry : moveCaptures.entrySet()){
            if(!orderedPosCaptures.contains(entry.getValue())){
                orderedPosCaptures.add(entry.getValue());
            }
        }

        Collections.sort(orderedPosCaptures);

        if(orderedPosCaptures.size()%2 == 0){
            maxCaptures = orderedPosCaptures.get(orderedPosCaptures.size() /2 - 1);
        }else{
            maxCaptures = orderedPosCaptures.get(orderedPosCaptures.size() / 2);
        }

        for(Map.Entry<Integer,Integer> entry : moveCaptures.entrySet()){
            if(entry.getValue() <= maxCaptures){
                lowCaptureMoves.add(entry.getKey());
            }
        }

        if(lowCaptureMoves.size() > 1){
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(lowCaptureMoves.size());
            return lowCaptureMoves.get(index);
        }else{
            return lowCaptureMoves.get(0);
        }

    }

    public int mediumDifficultyMove(boolean playerTurn){
        Map<Integer,Integer> moveCaptures = capturedPiecesCountByMove(playerTurn);
        List<Integer> mediumCaptureMoves = new ArrayList<>();
        List<Integer> orderedPosCaptures = new ArrayList<>();

        int minCaptures = 0;

        for(Map.Entry<Integer,Integer> entry : moveCaptures.entrySet()){
            if(!orderedPosCaptures.contains(entry.getValue())){
                orderedPosCaptures.add(entry.getValue());
            }
        }

        Collections.sort(orderedPosCaptures);

        if(orderedPosCaptures.size()%2 == 0){
            minCaptures = orderedPosCaptures.get(orderedPosCaptures.size() /2 - 1);
        }else{
            minCaptures = orderedPosCaptures.get(orderedPosCaptures.size() / 2);
        }

        for(Map.Entry<Integer,Integer> entry : moveCaptures.entrySet()){
            if(entry.getValue() >= minCaptures){
                mediumCaptureMoves.add(entry.getKey());
            }
        }

        if(mediumCaptureMoves.size() > 1){
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(mediumCaptureMoves.size());
            return mediumCaptureMoves.get(index);
        }else{
            return mediumCaptureMoves.get(0);
        }
    }

    public int hardDifficultyMove(boolean playerTurn){
        Map<Integer,Integer> moveCaptures = capturedPiecesCountByMove(playerTurn);
        List<Integer> bestMoves = new ArrayList<>();

        int maxCaptures = 0;

        for(Map.Entry<Integer,Integer> entry : moveCaptures.entrySet()){
            if(entry.getValue() > maxCaptures){
                maxCaptures = entry.getValue();
            }
        }

        for(Map.Entry<Integer,Integer> entry : moveCaptures.entrySet()){
            if(entry.getValue() == maxCaptures){
                bestMoves.add(entry.getKey());
            }
        }

        if(bestMoves.size() > 1){
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(bestMoves.size());
            return bestMoves.get(index);
        }
        else{
            return bestMoves.get(0);
        }
    }
}

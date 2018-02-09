/*
 * Arjun Khare Pd.2 AP Computer Science
 */

/**
 *
 * @author Arjun
 */

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class ChessGame {
    public String currentPlayer;
    public int[] pieceCoords;
    public ArrayList<String[]> chessBoard;
    public String[] whitePieces;
    public String[] blackPieces;
    public ArrayList<String> whiteCaptured;
    public ArrayList<String> blackCaptured;
    public float totalWhiteTime;
    public float totalBlackTime;
    public int[] numbers;
    public int[] moveCoords;
    public ChessGame() {
        /*
        Unicode for Chess Pieces:
        White Pieces:
        King:\u2654
        Queen:\u2655
        Rook:\u2656
        Bishop:\u2657
        Knight:\u2658
        Pawn:\u2659
        Black Pieces:
        King:\u265A
        Queen:\u265B
        Rook:\u265C
        Bishop:\u265D
        Knight:\u265E
        Pawn:\u265F
        */
        ArrayList<String[]> myBoard = new ArrayList<>();
        numbers = new int[]{8, 7, 6, 5, 4, 3, 2, 1};
        String[] row1 = {"\u265C", "\u265E", "\u265D", "\u265B", "\u265A", "\u265D", "\u265E", "\u265C"};
        String[] row2 = {"\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F"};
        String[] row3 = {" ", " ", " ", " ", " ", " ", " ", " "};
        String[] row4 = {" ", " ", " ", " ", " ", " ", " ", " "};
        String[] row5 = {" ", " ", " ", " ", " ", " ", " ", " "};
        String[] row6 = {" ", " ", " ", " ", " ", " ", " ", " "};
        String[] row7 = {"\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659"};
        String[] row8 = {"\u2656", "\u2658", "\u2657", "\u2655", "\u2654", "\u2657", "\u2658", "\u2656"};
        myBoard.add(row1);
        myBoard.add(row2);
        myBoard.add(row3);
        myBoard.add(row4);
        myBoard.add(row5);
        myBoard.add(row6);
        myBoard.add(row7);
        myBoard.add(row8);
        chessBoard = myBoard;
        blackPieces = new String[]{"\u265C", "\u265E", "\u265D", "\u265B", "\u265A", "\u265D", "\u265E", "\u265C", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F"};
        whitePieces = new String[]{"\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2656", "\u2658", "\u2657", "\u2654", "\u2655", "\u2657", "\u2658", "\u2656"};
        currentPlayer = "White";
        startGame();
        // System.out.println(" |A|B|C|D|E|F|G|H");
        // System.out.println("8|R|N|B|K|Q|B|N|R");
        // System.out.println("7|P|P|P|P|P|P|P|P");
        // System.out.println("6| | | | | | | | ");
        // System.out.println("5| | | | | | | | ");
        // System.out.println("4| | | | | | | | ");
        // System.out.println("3| | | | | | | | ");
        // System.out.println("2|P|P|P|P|P|P|P|P");
        // System.out.println("1|R|N|B|K|Q|B|N|R");
    }
    
    public void switchPlayer(){
        currentPlayer = (currentPlayer.equals("White")) ? "Black" : "White";
    }
    
    public Boolean checkValidPieceCoords(int[] pieceCoords, ArrayList<String[]> chessBoard){
        return (checkValidMoveCoords(pieceCoords) && checkValidPlayerPiece());
    }

    public Boolean checkValidMoveCoords(int[] moveCoords){
        Boolean isValid = false;
        if(moveCoords[0] >= 0 && moveCoords[0] < 8 && moveCoords[1] >= 0 && moveCoords[1] < 8){
            isValid = true;
        }
        return isValid;
    }
    
    public Boolean checkValidPlayerPiece(){
        if(currentPlayer.equals("White")){
            return !((chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u265A") ||
                   (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u265B") ||
                   (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u265C") ||
                   (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u265D") ||
                   (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u265E") ||
                   (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u265F") ||
                   (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals(" "));
        } 
        return !((chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u2654") ||
               (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u2655") ||
               (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u2656") ||
               (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u2657") ||
               (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u2658") ||
               (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals("\u2659") ||
               (chessBoard.get(pieceCoords[0]))[pieceCoords[1]].equals(" "));
    }
    
    public int[] stringToCoords(String stringPieceCoords){
        char[] stringPieceCoordsBreak = stringPieceCoords.toCharArray();
        String boardLetters = "ABCDEFGH";
        int col = boardLetters.indexOf(stringPieceCoordsBreak[0]);
        int row = 8 - (Character.getNumericValue(stringPieceCoordsBreak[1]));
        int[] pieceCoords = {row, col};
        return pieceCoords;
    }
    
    public ArrayList<int[]> calculatePieceMoves(){
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        switch(chessBoard.get(pieceCoords[0])[pieceCoords[1]]){
            case "\u2659":
            case "\u265F":
                possibleMoves = getPawnMoves();
                break;
            case "\u2656":
            case "\u265C":
                possibleMoves = getRookMoves();
                break;
            case "\u2658":
            case "\u265E":
                possibleMoves = getKnightMoves();
                break;
            case "\u2657":
            case "\u265D":
                possibleMoves = getBishopMoves();
                break;
            case "\u265A":
            case "\u2654":
                possibleMoves = getKingMoves();
                break;
            case "\u265B":
            case "\u2655":
                possibleMoves = getQueenMoves();
                break;
        }
        return possibleMoves;
    }
    
    public ArrayList<int[]> getPawnMoves(){
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        if(currentPlayer.equals("White")){
            int[] normalMove = {pieceCoords[0] - 1, pieceCoords[1]};
            if(checkValidMoveCoords(normalMove) && chessBoard.get(normalMove[0])[normalMove[1]] == " "){
                possibleMoves.add(normalMove);
                int[] startMove = {pieceCoords[0] - 2, pieceCoords[1]};
                if(pieceCoords[0] == 6 && chessBoard.get(startMove[0])[startMove[1]] == " "){
                    possibleMoves.add(startMove);
                }
            }
            int[] checkKill = {pieceCoords[0] - 1, pieceCoords[1] + 1};
            if(checkValidMoveCoords(checkKill) && chessBoard.get(checkKill[0])[checkKill[1]] != " " && checkKillColor(checkKill)){
                possibleMoves.add(checkKill);
            }
            int[] checkKill2 = {pieceCoords[0] - 1, pieceCoords[1] - 1};
            if(checkValidMoveCoords(checkKill2) && chessBoard.get(checkKill2[0])[checkKill2[1]] != " " && checkKillColor(checkKill2)){
                possibleMoves.add(checkKill2);
            }
        } else {
            int[] normalMove = {pieceCoords[0] + 1, pieceCoords[1]};
            if(checkValidMoveCoords(normalMove) && chessBoard.get(normalMove[0])[normalMove[1]] == " "){
                possibleMoves.add(normalMove);   
                int[] startMove = {pieceCoords[0] + 2, pieceCoords[1]}; 
                if(pieceCoords[0] == 1 && chessBoard.get(startMove[0])[startMove[1]] == " "){
                    possibleMoves.add(startMove);
                }
            }
            int[] checkKill = {pieceCoords[0] + 1, pieceCoords[1] + 1};
            if(checkValidMoveCoords(checkKill) && chessBoard.get(checkKill[0])[checkKill[1]] != " " && checkKillColor(checkKill)){
                possibleMoves.add(checkKill);
            }
            int[] checkKill2 = {pieceCoords[0] + 1, pieceCoords[1] - 1};
            if(checkValidMoveCoords(checkKill2) && chessBoard.get(checkKill2[0])[checkKill2[1]] != " " && checkKillColor(checkKill2)){
                possibleMoves.add(checkKill2);
            }
        }
        return possibleMoves;
    }
    
    public ArrayList<int[]> getRookMoves(){
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        alongRowLoop:
        for(int row = pieceCoords[1] + 1; row < 8; row++){
            int[] rowMoves = {pieceCoords[0], row};
            if(chessBoard.get(rowMoves[0])[rowMoves[1]] != " "){
                possibleMoves.add(rowMoves);
                break alongRowLoop;
            } else {
                possibleMoves.add(rowMoves);
            }
        }
        alongRowLoop2:
        for(int row = pieceCoords[1] - 1; row > -1; row--){
            int[] rowMoves = {pieceCoords[0], row};
            if(chessBoard.get(rowMoves[0])[rowMoves[1]] != " "){
                possibleMoves.add(rowMoves);
                break alongRowLoop2;
            } else {
                possibleMoves.add(rowMoves);
            }
        }
        alongColLoop:
        for(int col = pieceCoords[0] + 1; col < 8; col++){
            int[] colMoves = {col, pieceCoords[1]};
            if(chessBoard.get(colMoves[0])[colMoves[1]] != " "){
                possibleMoves.add(colMoves);
                break alongColLoop;
            } else {
                possibleMoves.add(colMoves);
            }
        }
        alongColLoop2:
        for(int col = pieceCoords[0] - 1; col > -1; col--){
            int[] colMoves = {col, pieceCoords[1]};
            if(chessBoard.get(colMoves[0])[colMoves[1]] != " "){
                possibleMoves.add(colMoves);
                break alongColLoop2;
            } else {
                possibleMoves.add(colMoves);
            }
        }
        ArrayList<int[]> toRemove = new ArrayList<>();
        for(int[] move : possibleMoves){
            if(chessBoard.get(move[0])[move[1]] != " " && !checkKillColor(move)){
                toRemove.add(move);
            }
        }
        possibleMoves.removeAll(toRemove);
        return possibleMoves;
    }
    
    public ArrayList<int[]> getKnightMoves(){
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        int[] normalMove1 = {pieceCoords[0] - 2, pieceCoords[1] + 1}; possibleMoves.add(normalMove1);
        int[] normalMove2 = {pieceCoords[0] - 2, pieceCoords[1] - 1}; possibleMoves.add(normalMove2);
        int[] normalMove3 = {pieceCoords[0] + 2, pieceCoords[1] + 1}; possibleMoves.add(normalMove3);
        int[] normalMove4 = {pieceCoords[0] + 2, pieceCoords[1] - 1}; possibleMoves.add(normalMove4);
        int[] normalMove5 = {pieceCoords[0] - 1, pieceCoords[1] + 2}; possibleMoves.add(normalMove5);
        int[] normalMove6 = {pieceCoords[0] + 1, pieceCoords[1] + 2}; possibleMoves.add(normalMove6);
        int[] normalMove7 = {pieceCoords[0] - 1, pieceCoords[1] - 2}; possibleMoves.add(normalMove7);
        int[] normalMove8 = {pieceCoords[0] + 1, pieceCoords[1] - 2}; possibleMoves.add(normalMove8);
        ArrayList<int[]> toRemove = new ArrayList<>();
        for(int[] move : possibleMoves){
            if(!checkValidMoveCoords(move) || (chessBoard.get(move[0])[move[1]] != " " && !checkKillColor(move))){
                toRemove.add(move);
            }
        }
        possibleMoves.removeAll(toRemove);
        return possibleMoves;
    }
    
    public ArrayList<int[]> getBishopMoves(){
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        int row = pieceCoords[0] + 1;
        int col = pieceCoords[1] + 1;
        alongURBL:
        while(row < 8 && col < 8){
            int[] URBLMoves = {row, col};
            if(chessBoard.get(URBLMoves[0])[URBLMoves[1]] != " "){
                possibleMoves.add(URBLMoves);
                break alongURBL;
            } else {
                possibleMoves.add(URBLMoves);
            }
            row++; col++;
        }
        row = pieceCoords[0] - 1;
        col = pieceCoords[1] - 1;
        alongURBL2:
        while(row > -1 && col > -1){
            int[] URBLMoves = {row, col};
            if(chessBoard.get(URBLMoves[0])[URBLMoves[1]] != " "){
                possibleMoves.add(URBLMoves);
                break alongURBL2;
            } else {
                possibleMoves.add(URBLMoves);
            }
            row--; col--;
        }
        row = pieceCoords[0] + 1;
        col = pieceCoords[1] - 1;
        alongBLUR:
        while(row < 8 && col > -1){
            int[] BLURMoves = {row, col};
            if(chessBoard.get(BLURMoves[0])[BLURMoves[1]] != " "){
                possibleMoves.add(BLURMoves);
                break alongBLUR;
            } else {
                possibleMoves.add(BLURMoves);
            }
            row++; col--;
        }
        row = pieceCoords[0] - 1;
        col = pieceCoords[1] + 1;
        alongBLUR2:
        while(row > -1 && col < 8){
            int[] BLURMoves = {row, col};
            if(chessBoard.get(BLURMoves[0])[BLURMoves[1]] != " "){
                possibleMoves.add(BLURMoves);
                break alongBLUR2;
            } else {
                possibleMoves.add(BLURMoves);
            }
            row--; col++;
        }
        ArrayList<int[]> toRemove = new ArrayList<>();
        for(int[] move : possibleMoves){
            if(chessBoard.get(move[0])[move[1]] != " " && !checkKillColor(move)){
                toRemove.add(move);
            }
        }
        possibleMoves.removeAll(toRemove);
        return possibleMoves;
    }
    
    public ArrayList<int[]> getKingMoves(){
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        int[] normalMove1 = {pieceCoords[0] - 1, pieceCoords[1]}; possibleMoves.add(normalMove1);
        int[] normalMove2 = {pieceCoords[0] + 1, pieceCoords[1]}; possibleMoves.add(normalMove2);
        int[] normalMove3 = {pieceCoords[0], pieceCoords[1] + 1}; possibleMoves.add(normalMove3);
        int[] normalMove4 = {pieceCoords[0], pieceCoords[1] - 1}; possibleMoves.add(normalMove4);
        int[] normalMove5 = {pieceCoords[0] + 1, pieceCoords[1] + 1}; possibleMoves.add(normalMove5);
        int[] normalMove6 = {pieceCoords[0] - 1, pieceCoords[1] - 1}; possibleMoves.add(normalMove6);
        int[] normalMove7 = {pieceCoords[0] - 1, pieceCoords[1] + 1}; possibleMoves.add(normalMove7);
        int[] normalMove8 = {pieceCoords[0] + 1, pieceCoords[1] - 1}; possibleMoves.add(normalMove8);
        ArrayList<int[]> toRemove = new ArrayList<>();
        for(int[] move : possibleMoves){
            if(!checkValidMoveCoords(move) || (chessBoard.get(move[0])[move[1]] != " " && !checkKillColor(move))){
                toRemove.add(move);
            }
        }
        possibleMoves.removeAll(toRemove);
        return possibleMoves;
    }
    
    public ArrayList<int[]> getQueenMoves(){
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        ArrayList<int[]> rookMoves = getRookMoves();
        ArrayList<int[]> bishopMoves = getBishopMoves();
        possibleMoves.addAll(rookMoves); 
        possibleMoves.addAll(bishopMoves);
        return possibleMoves;
    }

    public Boolean checkKillColor(int[] killCoords){
        String killPiece = chessBoard.get(killCoords[0])[killCoords[1]];
        Boolean isGood = true;
        if(currentPlayer.equals("White")){
            if(Arrays.asList(whitePieces).contains(killPiece)){
                isGood = false;
            }
        } else {
            if(Arrays.asList(blackPieces).contains(killPiece)){
                isGood = false;
            }
        }
        return isGood;
    }
    
    public void startGame(){
        whiteCaptured = new ArrayList<>();
        blackCaptured = new ArrayList<>();
        totalWhiteTime = 0;
        totalBlackTime = 0;
        Boolean gameIsGoingOn = true;
        while(gameIsGoingOn){
            Scanner move = new Scanner(System.in);
            System.out.println("Current Player: " + currentPlayer);
            Boolean moveIsBad = true;
            long playerStartTime = System.currentTimeMillis();
            while(moveIsBad){
                Boolean pieceCoordsBad = true;
                while(pieceCoordsBad){
                    System.out.println("  A  B  C  D  E  F  G  H");
                    System.out.println("8" + Arrays.toString(chessBoard.get(0)) + " Total Time Taken: " + totalBlackTime);
                    System.out.println("7" + Arrays.toString(chessBoard.get(1)) + " Captured Pieces: " + blackCaptured.toString());
                    System.out.println("6" + Arrays.toString(chessBoard.get(2)));
                    System.out.println("5" + Arrays.toString(chessBoard.get(3)));
                    System.out.println("4" + Arrays.toString(chessBoard.get(4)));
                    System.out.println("3" + Arrays.toString(chessBoard.get(5)));
                    System.out.println("2" + Arrays.toString(chessBoard.get(6)) + " Captured Pieces: " + whiteCaptured.toString());
                    System.out.println("1" + Arrays.toString(chessBoard.get(7)) + " Total Time Taken: " + totalWhiteTime);
                    System.out.println("Enter the coordinates of the piece you want to move (e.g. A5, B3, C8): ");
                    String stringPieceCoords = move.nextLine();
                    pieceCoords = stringToCoords(stringPieceCoords);
                    System.out.println((!checkValidPieceCoords(pieceCoords, chessBoard) ? "Invalid coordinates. Please try again." : "Valid coordinates entered.")); 
                    pieceCoordsBad = !checkValidPieceCoords(pieceCoords, chessBoard);
                }
                String pieceChosen = "";
                switch(chessBoard.get(pieceCoords[0])[pieceCoords[1]]){
                    case "\u2659":
                    case "\u265F":
                        pieceChosen = "Pawn";
                        break;
                    case "\u2656":
                    case "\u265C":
                        pieceChosen = "Rook";
                        break;
                    case "\u2658":
                    case "\u265E":
                        pieceChosen = "Knight";
                        break;
                    case "\u2657":
                    case "\u265D":
                        pieceChosen = "Bishop";
                        break;
                    case "\u265A":
                    case "\u2654":
                        pieceChosen = "King";
                        break;
                    case "\u2655":
                    case "\u265B":
                        pieceChosen = "Queen";
                        break;
                }
                System.out.print("You chose your " + currentPlayer + " " + pieceChosen + ". ");
                ArrayList<int[]> possibleMoves = calculatePieceMoves();
                if(possibleMoves.size() != 0){
                    moveIsBad = false;
                } else {
                    System.out.println("This is not able to move to any location. Please change your selection.");
                }
            }
            ArrayList<int[]> possibleMoves = calculatePieceMoves();
            Boolean moveCoordsBad = true;
            while(moveCoordsBad){    
                System.out.println("Enter the coordinates of the place you want to move to: ");
                String stringMoveCoords = move.nextLine();
                moveCoords = stringToCoords(stringMoveCoords);
                if(checkValidMoveCoords(moveCoords)){
                    checkLoop:
                    for(int i = 0; i < possibleMoves.size(); i++){
                        if(Arrays.equals(possibleMoves.get(i), moveCoords)){
                            System.out.println("Valid coordinates entered.");
                            moveCoordsBad = false;
                            break checkLoop;
                        }
                    } 
                } 
                if(moveCoordsBad){
                    System.out.println("Invalid coordinates. Please try again.");
                }
            }
            long playerEndTime = System.currentTimeMillis();;
            if(chessBoard.get(moveCoords[0])[moveCoords[1]] != " "){
                if(currentPlayer == "White"){
                    whiteCaptured.add(chessBoard.get(moveCoords[0])[moveCoords[1]]);
                } else {
                    blackCaptured.add(chessBoard.get(moveCoords[0])[moveCoords[1]]);
                }
            }
            if(currentPlayer == "White"){
                totalWhiteTime += (float)(playerEndTime - playerStartTime)/1000;
            } else {
                totalBlackTime += (float)(playerEndTime - playerStartTime)/1000;
            }
            chessBoard.get(moveCoords[0])[moveCoords[1]] = chessBoard.get(pieceCoords[0])[pieceCoords[1]];
            chessBoard.get(pieceCoords[0])[pieceCoords[1]] = " ";
            switchPlayer();
        }
    }

    public static void main(String[] args){
        ChessGame newGame = new ChessGame();
    }
        
}

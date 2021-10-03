import java.util.Scanner;
import java.util.HashMap;
import java.lang.Character; 
// import javafx.util.Pair;
// USER : 1 -> X
// COMP : -1 -> O
// DRAW : 0
class TicTacToe{
    public static void main(String[] args) {
        // to get input from user 
        Scanner sc = new Scanner(System.in);
        
        // board
        char[][] board = new char[3][3];
        
        // filled with empty charater
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                board[i][j] = ' ';
            }
        }

        // map to keep track of positions and assign 
        // numbers to the positions so that user can play game
        HashMap<Integer, Integer[]> map  = fillMap();

        // to keep track of moves 
        int countMoves = 0;

        // to keep track of turn
        boolean user = true;

        // to print board instructions
        printBoardInstruntion();

        while( countMoves <= 9 ){

            // printBoard
            printBoard(board);

            // check weather someone is won 
            int winner = checkWinner(board);
            if(winner == 1){
                System.out.println("You Won!");
                break;
            }
            else if(winner == -1){
                System.out.println("You Lose!");
                break;
            }

            // check if DRAW
            if( checkIfDraw(countMoves) ){
                System.out.println("DRAW!");
                break;
            }

            if(user){
                System.out.print("Enter the position number: ");
                int choice = sc.nextInt();
                if(choice > 9){
                    System.out.println("Invalid Place ");
                    continue;
                }
                if(checkIfPlaceIsFilled(choice, board, map)){
                    System.out.println("The Place is Already Filled");
                    continue;
                }

                Integer[] userChoice = map.get(choice);
                int row = userChoice[0];
                int col = userChoice[1];
                board[row][col] = 'X';
                user = false;
            }
            else{
                int bestMove = Integer.MAX_VALUE;
                int level = Integer.MAX_VALUE;
                int bestRow = -1; 
                int bestCol = -1;
                for(int i=0; i<3;i++){
                    for(int j=0; j<3; j++){
                        if(board[i][j] == ' '){
                            board[i][j] = 'O';
                            Pair currentBest = findBestMove(map, board, true, countMoves + 1, 0);
                            if( currentBest.key() == bestMove && currentBest.value() < level ){
                                level =  currentBest.value();
                                bestRow = i;
                                bestCol = j;
                            }
                            else if(currentBest.key() < bestMove){
                                bestMove = currentBest.key();
                                level = currentBest.value();
                                bestRow = i;
                                bestCol = j;
                            }
                            board[i][j] = ' ';
                        }
                    }
                }
                board[bestRow][bestCol] = 'O';
                user = true;
            }
            countMoves++;
        }

    }
    // Pair
    // first value stand as score
    // second value stand as level
    public static Pair findBestMove(HashMap<Integer, Integer[]> mapping,
                                                     char[][] board,
                                                     boolean user, 
                                                     int moves,
                                                     int level){
        int winner = checkWinner(board);
        // checking if someone is won
        if(winner != 2){
            return new Pair(winner, level);
        }

        // check if match is DRAW 
        if(checkIfDraw(moves)){
            return new Pair(0,level);
        }   

        if(user){
            //  as user we want to maximize the result
            int bestMove = Integer.MIN_VALUE;
            int bestLevel = Integer.MAX_VALUE;
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    if(board[i][j] == ' '){
                        board[i][j] = 'X';
                        Pair currentBest = findBestMove(mapping, board, false, moves+1, level + 1);
                        if(currentBest.key() == bestMove && currentBest.value() < level){
                            bestLevel = currentBest.value();
                        }
                        else if( currentBest.key() >= bestMove ){
                            bestMove = currentBest.key();
                            bestLevel = currentBest.value();
                        }
                        board[i][j] = ' ';
                    }
                }
            }
            return new Pair(bestMove,bestLevel);
        }
        else{
            // as computer i want to minimise the result
            int bestMove = Integer.MAX_VALUE;
            int bestLevel = Integer.MAX_VALUE;
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    if(board[i][j] == ' '){
                        board[i][j] = 'O';
                        Pair currentBest = findBestMove(mapping, board, true, moves+1, level + 1);
                        if(currentBest.key() == bestMove && currentBest.value() < level){
                            bestLevel = currentBest.value();
                        }
                        else if( currentBest.key() < bestMove ){
                            bestMove = currentBest.key();
                            bestLevel = currentBest.value();
                        }
                        board[i][j] = ' ';
                    }
                }
            }
            return new Pair(bestMove,bestLevel);
        }
    }

    public static HashMap<Integer, Integer[]> fillMap(){
        HashMap<Integer, Integer[]> map = new HashMap<>();
        int cnt = 1;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                map.put(cnt++, new Integer[]{i,j});
            }
        }
        return map;
    }

    public static boolean checkIfDraw(int moves){
        return moves == 9 ? true : false;
    }

    public static int checkWinner(char[][] board){

        // row 1
        if(board[0][0] == board[0][1] && board[0][1] == board[0][2]){
            if( board[0][0] == 'X' ){
                return 1;
            }
            else if(board[0][0] == 'O'){
                return -1;
            }
        }

        // row 2
        if(board[1][0] == board[1][1] && board[1][1] == board[1][2]){
            if( board[1][0] == 'X' ){
                return 1;
            }
            else if(board[1][0] == 'O'){
                return -1;
            }
        }
        // row 3
        if(board[2][0] == board[2][1] && board[2][1] == board[2][2]){
            if( board[2][0] == 'X' ){
                return 1;
            }
            else if(board[2][0] == 'O'){
                return -1;
            }
        }
        // col 1
        if(board[0][0] == board[1][0] && board[1][0] == board[2][0]){
            if( board[0][0] == 'X' ){
                return 1;
            }
            else if(board[0][0] == 'O'){
                return -1;
            }
        }
        // col 2
        if(board[0][1] == board[1][1] && board[1][1] == board[2][1]){
            if( board[0][1] == 'X' ){
                return 1;
            }
            else if(board[0][1] == 'O'){
                return -1;
            }
        }
        // col 3
        if(board[0][2] == board[1][2] && board[1][2] == board[2][2]){
            if( board[0][2] == 'X' ){
                return 1;
            }
            else if(board[0][2] == 'O'){
                return -1;
            }
        }
        // first diagonal
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2]){
            if( board[0][0] == 'X' ){
                return 1;
            }
            else if(board[0][0] == 'O'){
                return -1;
            }
        }
        // second diagonal
        if(board[0][2] == board[1][1] && board[1][1] == board[2][0]){
            if( board[0][2] == 'X' ){
                return 1;
            }
            else if(board[0][2] == 'O'){
                return -1;
            }
        }

        // there is no winner found so return any other number than 0, 1, -1
        return 2;
    }

    public static boolean checkIfPlaceIsFilled(int choice, char[][] board, HashMap<Integer, Integer[]> mapping){
        Integer ar[] = mapping.get(choice);
        int row = ar[0];
        int col = ar[1];
        if( board[row][col] != ' ' ){
            return true;
        }
        return false;
    }

    public static void printBoard(char[][] board){
        for(char rows[] : board){
            for(char col : rows){
                System.out.print((col==' ' ? '-' : col)+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printBoardInstruntion(){
        int cnt = 1;
        System.out.println("Select Numbers To Choose Your Positions");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(cnt + " ");
                cnt++;
            }
            System.out.println();
        }
    }
}
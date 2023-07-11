package Application;

import Chess.ChessPiece;
import Chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

    public static ChessPosition readChessPosition(Scanner sc){
        try{
            String s = sc.nextLine();
            char colunmn = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(colunmn,row);
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Erro ao ler a ChessPosition. Valores validos apenas de a1 ate h8");
        }
    }

    public static void printBoard(ChessPiece[][] pieces){
        for (int i=0; i< pieces.length; i++){
            System.out.print((8 - i) + " ");
            for(int j=0; j< pieces.length; j++){
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.printf("  a b c d e f g h");
    }
    private static void printPiece(ChessPiece piece){
        if(piece == null){
            System.out.print("-");
        }
        else{
            System.out.print(piece);
        }
        System.out.printf(" ");
    }
}

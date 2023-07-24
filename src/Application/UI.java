package Application;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;
import Chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece>captured){
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turno : " + chessMatch.getTurn());
        System.out.println("Esperando jogador: " + chessMatch.getCurrentplayer());
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

    private static void printCapturedPieces(List<ChessPiece> captured){
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
        System.out.println("Pecas caputuradas:");
        System.out.print("Brancas: ");
        System.out.println(Arrays.toString(white.toArray()));

        System.out.println("Pecas caputuradas:");
        System.out.print("Preta: ");
        System.out.println(Arrays.toString(black.toArray()));
    }

}

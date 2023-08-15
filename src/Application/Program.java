package Application;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessException;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[]arg){
        Scanner sc = new Scanner(System.in);

        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while(!chessMatch.getCheckmate()) {
            try {
                UI.printMatch(chessMatch, captured);
                System.out.println();
                System.out.printf("Origem: ");
                ChessPosition source = UI.readChessPosition(sc);

                System.out.println();
                System.out.printf("Destino: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

                if(chessMatch.getPromoted() != null){
                    System.out.printf("Coloque a peca para promocao (B/N/R/Q)");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")){
                        System.out.printf("Valor invalido coloque a peca para promocao (B/N/R/Q)");
                        type = sc.nextLine().toUpperCase();
                    }
                    chessMatch.replacePormotedPiece(type);
                }
            }
            catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.printMatch(chessMatch, captured);
    }
}

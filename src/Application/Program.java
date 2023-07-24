package Application;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

import java.util.Scanner;

public class Program {
    public static void main(String[]arg){
        Scanner sc = new Scanner(System.in);

        ChessMatch chessMatch = new ChessMatch();

        while(true) {
            UI.printMatch(chessMatch);
            System.out.println();
            System.out.printf("Origem: ");
            ChessPosition source = UI.readChessPosition(sc);

            System.out.println();
            System.out.printf("Destino: ");
            ChessPosition target = UI.readChessPosition(sc);

            ChessPiece capturedPiece = chessMatch.performChessMove(source,target);
        }
    }
}

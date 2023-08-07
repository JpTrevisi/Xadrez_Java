package Application;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[]arg){
        Scanner sc = new Scanner(System.in);

        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while(!chessMatch.getCheckmate()) {
            UI.printMatch(chessMatch, captured);
            System.out.println();
            System.out.printf("Origem: ");
            ChessPosition source = UI.readChessPosition(sc);

            System.out.println();
            System.out.printf("Destino: ");
            ChessPosition target = UI.readChessPosition(sc);

            ChessPiece capturedPiece = chessMatch.performChessMove(source,target);

            if(capturedPiece != null){
                captured.add(capturedPiece);
            }
        }
        UI.printMatch(chessMatch, captured);
    }
}

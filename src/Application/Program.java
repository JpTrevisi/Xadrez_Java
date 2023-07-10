package Application;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessMatch;

public class Program {
    public static void main(String[]arg){

        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
    }
}

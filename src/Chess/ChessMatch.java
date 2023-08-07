package Chess;

import Boardgame.Board;
import Boardgame.Piece;
import Boardgame.Position;
import Chess.Pieces.King;
import Chess.Pieces.Pawn;
import Chess.Pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private Board board;
    private int turn;
    private Color currentplayer;
    private boolean check;
    private boolean checkMate;


    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public int getTurn() {
        return turn;
    }

    public boolean getCheck(){
        return check;
    }

    public Color getCurrentplayer() {
        return currentplayer;
    }

    public boolean getCheckmate(){
        return checkMate;
    }

    public ChessMatch(){
        board = new Board(8,8);
        turn =1;
        currentplayer = Color.WHITE;
        initialSetup();
    }
    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for(int i=0; i< board.getRows(); i++){
            for(int j=0; j< board.getColumns(); j++){
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }

    private void validateSourcePosition(Position position){
        if(!board.thereIsAPiece(position)){
            throw new ChessException("Nao existe uma peca na posicao de origem");
        }
        if(currentplayer != ((ChessPiece)board.piece(position)).getColor()){
            throw new ChessException("A peca escolhida nao e sua");
        }
        if(!board.piece(position).isThereAnyPossibleMoves()){
            throw new ChessException("Nao existe movimentos possiveis para peca escolhida");
        }
    }

    private Piece makeMove(Position source, Position target){
        ChessPiece p = (ChessPiece)board.removePiece(source);
        p.increaseMOveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        if(capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece){
        ChessPiece p =(ChessPiece)board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);
        if(capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void validateTargetPosition(Position source, Position target){
        if(!board.piece(source).possibleMoves(target)){
            throw new ChessException("A peca escolhida nao pode se mover para posicao de destino");
        }
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source,target);

        if(testCheck(currentplayer)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("Voce nao pode se colocar em Check");
        }

        check = (testCheck(opponent(currentplayer))) ? true : false;

        if(testCheckMate(opponent(currentplayer))){
            checkMate = true;
        }else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    private void nextTurn(){
        turn ++;
        currentplayer = (currentplayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : list){
            if (p instanceof King){
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("Nao existe o rei da cor " + color + "no tabuleiro");
    }

    private boolean testCheck(Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece>opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces){
            boolean[][] mat = p.possibleMoves();
            if(mat[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color){
        if(!testCheck(color)){
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : list){
            boolean[][] mat = p.possibleMoves();
            for(int i=0; i<board.getRows(); i++){
                for(int j=0; j<board.getColumns(); j++){
                    if(mat[i][j]){
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheack = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if(!testCheack){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup(){
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a',8,new Rook(board, Color.BLACK));
        placeNewPiece('e',8,new King(board, Color.BLACK));
        placeNewPiece('h',8,new Rook(board, Color.BLACK));
        placeNewPiece('a',7,new Pawn(board, Color.BLACK));
        placeNewPiece('b',7,new Pawn(board, Color.BLACK));
        placeNewPiece('c',7,new Pawn(board, Color.BLACK));
        placeNewPiece('d',7,new Pawn(board, Color.BLACK));
        placeNewPiece('e',7,new Pawn(board, Color.BLACK));
        placeNewPiece('f',7,new Pawn(board, Color.BLACK));
        placeNewPiece('g',7,new Pawn(board, Color.BLACK));
        placeNewPiece('h',7,new Pawn(board, Color.BLACK));
    }
}

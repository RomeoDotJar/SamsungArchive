package GameBase.Chess.Figures;

import GameBase.Chess.ChessFigure;
import GameBase.Base.Coordinate;

public class Rook extends ChessFigure {
    public Rook(boolean colorIsWhite, Coordinate coordinateFrom) {
        super(colorIsWhite, colorIsWhite ? '\u2656' : '\u265c', coordinateFrom);
    }

    @Override
    public boolean canMove(Coordinate coordinateTo) {
        //TODO: how does Rook can move on chess board?
        return from.getX() == coordinateTo.getX() || from.getY() == coordinateTo.getY();
    }
}

package GameBase.Chess.Figures;

import GameBase.Chess.ChessFigure;
import GameBase.Base.Coordinate;

public class King extends ChessFigure {
    public King(boolean colorIsWhite, Coordinate coordinateFrom) {
        super(colorIsWhite, colorIsWhite ? '\u2654' : '\u265a', coordinateFrom);
    }

    @Override
    public boolean canMove(Coordinate to) {
        return to.getX() >= from.getX()-1 && to.getX() <= from.getX()+1 && to.getY() >= from.getY()-1 && to.getY() <= from.getY()+1;
    }
}

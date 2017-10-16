import javafx.scene.paint.Color;

public class GoalPiece extends HorizontalGamePiece {
    public GoalPiece(int x, int y) {
        super(2, Color.RED, x, y);
    }

    public boolean canMoveRightIn(GameBoard b) {
        //think in range
        int desirecX = getTopLeftX() + width;
        int desiredy = getTopLeftY();


            if(desirecX > 6 || desiredy > 5 || desiredy<0 || desirecX < 0) {
                return false;
            }

            for (int i = 0; i < b.getNumGamePieces(); i++) {
                GamePiece piecefromArray = b.getGamePieces()[i];

                int initialX = piecefromArray.topLeftX;
                int finalX = piecefromArray.topLeftX + piecefromArray.width - 1;
                int initialy = piecefromArray.getTopLeftY();
                int finalY = piecefromArray.getTopLeftY() + piecefromArray.height - 1;

                if (desirecX >= initialX  && desirecX <= finalX) {
                    if (initialy <= desiredy && desiredy <= finalY) {
                        return false;
                    }
                }
            }

        return true;
    }
}
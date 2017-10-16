import javafx.scene.paint.Color;

public class HorizontalGamePiece extends GamePiece {
    public HorizontalGamePiece(int w, Color c, int x, int y) {
        super(w, 1, c, x, y);
    }

    public boolean canMoveLeftIn(GameBoard b) {
        int desiredXposition, desiredYposition;
        //get the desired x position

        desiredXposition = getTopLeftX() - 1;
        desiredYposition = getTopLeftY();

        if (desiredXposition > 5 || desiredYposition > 5 || desiredYposition < 0 || desiredXposition < 0) {
            return false;
        }


        for (int i = 0; i < b.getNumGamePieces(); i++) {
            GamePiece piecefromArray = b.getGamePieces()[i];

            int initialX = piecefromArray.topLeftX;
            int finalX = piecefromArray.topLeftX + piecefromArray.width - 1;
            int initialy = piecefromArray.getTopLeftY();
            int finalY = piecefromArray.getTopLeftY() + piecefromArray.height - 1;

            if (desiredXposition >= initialX && desiredXposition <= finalX) {
                if (initialy <= desiredYposition && desiredYposition <= finalY) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMoveRightIn(GameBoard b) {
        //find the desired xpostion && find the desired Yposition

        int desiredXposition, desiredYposition;
        //get the desired x position
        desiredXposition = this.getTopLeftX() + (this.width);
        desiredYposition = this.getTopLeftY();


        if (desiredXposition > 5 || desiredYposition > 5 || desiredYposition < 0 || desiredXposition < 0) {
            return false;
        }


        for (int i = 0; i < b.getNumGamePieces(); i++) {
            GamePiece piecefromArray = b.getGamePieces()[i];

            int initialX = piecefromArray.topLeftX;
            int finalX = piecefromArray.topLeftX + piecefromArray.width - 1;
            int initialy = piecefromArray.getTopLeftY();
            int finalY = piecefromArray.getTopLeftY() + piecefromArray.height - 1;

            if (desiredXposition >= initialX && desiredXposition <= finalX) {
                if (initialy <= desiredYposition && desiredYposition <= finalY) {
                    return false;
                }
            }
        }
        return true;
    }
}











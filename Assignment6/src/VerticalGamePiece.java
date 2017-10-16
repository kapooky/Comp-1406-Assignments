import javafx.scene.paint.Color;

public class VerticalGamePiece extends GamePiece {
    public VerticalGamePiece(int h, Color c, int x, int y) {
        super(1, h, c, x, y);
    }

    public boolean canMoveDownIn(GameBoard b) {

        int desiredXposition, desiredYposition;

        desiredXposition = this.getTopLeftX();
        desiredYposition = this.getTopLeftY() + getHeight();


        //make sure it's in the grid, I skipped that part
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

    public boolean canMoveUpIn(GameBoard b) {
        int desiredXposition, desiredYposition;

        desiredXposition = this.getTopLeftX();
        desiredYposition = this.getTopLeftY() - 1;


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


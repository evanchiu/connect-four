import java.awt.Color;
import java.awt.Graphics;

public class TempPiece extends Piece
{
    public TempPiece(Board newBoard, int newcol, Color newColor)
    {
        super(newBoard, newcol, newColor);
        this.temp = true;
    }

    public void draw(Graphics g, int x, int y, int diameter)
    {
        Etool.drawshape(g, this.my2Color, "circle", x, y, diameter, diameter, true);
        Etool.drawshape(g, new Color(149, 194, 251), "circle", x + 10, y + 10, diameter - 20, diameter - 20, true);
    }
}

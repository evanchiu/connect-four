import java.awt.Color;
import java.awt.Graphics;

public class Piece
    implements Cloneable
{
    protected Board myBoard;
    protected Position myPos;
    protected Color myColor;
    protected Color my2Color;
    protected boolean blank = false;
    protected boolean winner = false;
    protected boolean temp = false;
    protected final String shape = "circle";

    public Piece(Board newBoard, Position newPos, boolean putpiece)
    {
        this.myBoard = newBoard;
        this.myPos = newPos;
        this.blank = true;
        if (putpiece)
            this.myBoard.add(this);
    }

    public Piece(Piece copy, Board newBoard)
    {
        this.myBoard = newBoard;
        this.myPos = copy.getPosition();
        if (copy.isblank())
            this.blank = true;
        else
            this.myColor = copy.getColor();
        this.myBoard.add(this);
    }

    public Piece(Board newBoard, int newcol, Color newColor)
    {
        this.myBoard = newBoard;
        this.myPos = new Position();
        this.myPos.col = newcol;
        this.myColor = newColor;
        if (this.myColor.equals(Color.red))
            this.my2Color = new Color(255, 50, 50);
        else if (this.myColor.equals(Color.black))
            this.my2Color = new Color(50, 50, 50);
        else {
            this.my2Color = this.myColor;
        }
        if (this.myBoard.isblank(new Position(0, newcol)))
        {
            boolean placed = false;
            int row = 5;
            while (!placed)
            {
                if (this.myBoard.isblank(new Position(row, newcol)))
                {
                    this.myPos.row = row;
                    placed = true;
                }
                row--;
            }
        }

        this.myBoard.add(this);
    }

    public boolean equals(Object obj)
    {
        if ((this.blank) || (((Piece)obj).isblank()) || (this.temp) || (((Piece)obj).istemp())) {
            return false;
        }
        return this.myColor.equals(((Piece)obj).getColor());
    }

    public Board getBoard()
    {
        return this.myBoard;
    }

    public Color getColor()
    {
        return this.myColor;
    }

    public Position getPosition()
    {
        return this.myPos;
    }

    public boolean isblank()
    {
        return this.blank;
    }

    public void won()
    {
        this.winner = true;
    }

    public boolean iswinner()
    {
        return this.winner;
    }

    public void makeblank()
    {
        this.blank = true;
        this.myBoard.add(this);
    }

    public boolean istemp()
    {
        return this.temp;
    }

    public void draw(Graphics g, int x, int y, int diameter)
    {
        if (this.blank)
        {
            Etool.drawshape(g, new Color(149, 194, 251), "circle", x, y, diameter, diameter, true);
        }
        else
        {
            Etool.drawshape(g, this.myColor, "circle", x, y, diameter, diameter, true);
            Etool.drawshape(g, this.my2Color, "circle", x + 10, y + 10, diameter - 20, diameter - 20, true);
            if (this.winner)
                Etool.drawshape(g, Color.green, "circle", x + 10, y + 10, diameter - 20, diameter - 20, true);
            Etool.drawshape(g, this.myColor, "crown", x + 20, y + 20, diameter - 40, diameter - 40, true);
        }
    }
}

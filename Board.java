import java.awt.Color;
import java.awt.Graphics;

public class Board
    implements Cloneable
{
    private Piece[][] theBoard;
    private boolean won = false;
    public boolean changed = true;
    private Color whowon;

    public Board()
    {
        this.theBoard = new Piece[6][7];
        for (int row = 0; row < 6; row++)
        {
            for (int col = 0; col < 7; col++)
            {
                this.theBoard[row][col] = new Piece(this, new Position(row, col), false);
            }
        }
    }

    public Board(Board copy)
    {
        this.theBoard = new Piece[6][7];
        for (int row = 0; row < 6; row++)
        {
            for (int col = 0; col < 7; col++)
            {
                this.theBoard[row][col] = new Piece(copy.atPos(new Position(row, col)), this);
            }
        }
    }

    public void add(Piece piece)
    {
        Position pos = piece.getPosition();
        this.theBoard[pos.row][pos.col] = piece;
        this.changed = true;
    }

    public boolean isopen(int col)
    {
        return this.theBoard[0][col].isblank();
    }

    public boolean gameover()
    {
        return (checkwin()) || (isfull());
    }

    public Color winner()
    {
        if (checkwin()) {
            return this.whowon;
        }
        return null;
    }

    public Piece atPos(Position p)
    {
        return this.theBoard[p.row][p.col];
    }

    public Piece atPos(int row, int col)
    {
        return atPos(new Position(row, col));
    }

    public boolean isfull()
    {
        boolean full = true;
        for (int row = 0; row < 6; row++)
        {
            for (int col = 0; col < 7; col++)
            {
                if (this.theBoard[row][col].isblank())
                    full = false;
            }
        }
        return full;
    }

    public void display(Graphics g, int WIDTH, int HEIGHT)
    {
        if (this.changed)
        {
            int width = WIDTH / 7;
            int height = HEIGHT / 6;
            g.setColor(Color.yellow);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            for (int row = 0; row < 6; row++)
            {
                for (int col = 0; col < 7; col++)
                {
                    this.theBoard[row][col].draw(g, width * col + 5, height * row + 5, width - 10);
                }
            }
        }
        this.changed = false;
    }

    public boolean isblank(Position pos)
    {
        return this.theBoard[pos.row][pos.col].isblank();
    }

    public String toString()
    {
        String str = new String("");
        for (int row = 0; row < 6; row++)
        {
            for (int col = 0; col < 7; col++)
            {
                if (this.theBoard[row][col].isblank())
                    str = str + "- ";
                else if (this.theBoard[row][col].getColor().equals(Color.red))
                    str = str + "R ";
                else
                    str = str + "B ";
            }
            str = str + "\n";
        }
        return str;
    }

    public boolean checkwin()
    {
        this.changed = true;
        Position pos = new Position();

        for (pos.row = 0; pos.row < 6; pos.row += 1)
        {
            for (pos.col = 0; pos.col < 4; pos.col += 1)
            {
                if ((this.theBoard[pos.row][pos.col].equals(this.theBoard[pos.row][(pos.col + 1)])) && (this.theBoard[pos.row][pos.col].equals(this.theBoard[pos.row][(pos.col + 2)])) && (this.theBoard[pos.row][pos.col].equals(this.theBoard[pos.row][(pos.col + 3)])))
                {
                    this.theBoard[pos.row][pos.col].won();
                    this.theBoard[pos.row][(pos.col + 1)].won();
                    this.theBoard[pos.row][(pos.col + 2)].won();
                    this.theBoard[pos.row][(pos.col + 3)].won();
                    this.whowon = this.theBoard[pos.row][pos.col].getColor();
                    return true;
                }
            }
        }

        for (pos.row = 0; pos.row < 3; pos.row += 1)
        {
            for (pos.col = 0; pos.col < 4; pos.col += 1)
            {
                if ((this.theBoard[pos.row][pos.col].equals(this.theBoard[(pos.row + 1)][(pos.col + 1)])) && (this.theBoard[pos.row][pos.col].equals(this.theBoard[(pos.row + 2)][(pos.col + 2)])) && (this.theBoard[pos.row][pos.col].equals(this.theBoard[(pos.row + 3)][(pos.col + 3)])))
                {
                    this.theBoard[pos.row][pos.col].won();
                    this.theBoard[(pos.row + 1)][(pos.col + 1)].won();
                    this.theBoard[(pos.row + 2)][(pos.col + 2)].won();
                    this.theBoard[(pos.row + 3)][(pos.col + 3)].won();
                    this.whowon = this.theBoard[pos.row][pos.col].getColor();
                    return true;
                }
            }

        }

        for (pos.row = 0; pos.row < 3; pos.row += 1)
        {
            for (pos.col = 0; pos.col < 7; pos.col += 1)
            {
                if ((this.theBoard[pos.row][pos.col].equals(this.theBoard[(pos.row + 1)][pos.col])) && (this.theBoard[pos.row][pos.col].equals(this.theBoard[(pos.row + 2)][pos.col])) && (this.theBoard[pos.row][pos.col].equals(this.theBoard[(pos.row + 3)][pos.col])))
                {
                    this.theBoard[pos.row][pos.col].won();
                    this.theBoard[(pos.row + 1)][pos.col].won();
                    this.theBoard[(pos.row + 2)][pos.col].won();
                    this.theBoard[(pos.row + 3)][pos.col].won();
                    this.whowon = this.theBoard[pos.row][pos.col].getColor();
                    return true;
                }
            }
        }

        for (pos.row = 3; pos.row < 6; pos.row += 1)
        {
            for (pos.col = 0; pos.col < 4; pos.col += 1)
            {
                if ((this.theBoard[pos.row][pos.col].equals(this.theBoard[(pos.row - 1)][(pos.col + 1)])) && (this.theBoard[pos.row][pos.col].equals(this.theBoard[(pos.row - 2)][(pos.col + 2)])) && (this.theBoard[pos.row][pos.col].equals(this.theBoard[(pos.row - 3)][(pos.col + 3)])))
                {
                    this.theBoard[pos.row][pos.col].won();
                    this.theBoard[(pos.row - 1)][(pos.col + 1)].won();
                    this.theBoard[(pos.row - 2)][(pos.col + 2)].won();
                    this.theBoard[(pos.row - 3)][(pos.col + 3)].won();
                    this.whowon = this.theBoard[pos.row][pos.col].getColor();
                    return true;
                }
            }

        }

        return false;
    }
}

public class Position
{
    public int row;
    public int col;

    public Position()
    {
    }

    public Position(int newRow, int newCol)
    {
        this.row = newRow;
        this.col = newCol;
    }

    public boolean equals(Object obj)
    {
        return (this.row == ((Position)obj).row) && (this.col == ((Position)obj).col);
    }
}

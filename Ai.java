import java.awt.Color;
import java.util.ArrayList;

public class Ai
{
    public static int move(Board b, Color myColor)
    {
        int col = 0;

        int errorcheck = 0;
        boolean done = false;
        Color opponent;
        if (myColor.equals(Color.red))
            opponent = Color.black;
        else {
            opponent = Color.red;
        }

        Ai.Move[] options = new Ai.Move[7];
        for (int c = 0; c < 7; c++) {
            options[c] = new Ai.Move(c, b, myColor, opponent);
        }
        int hival = options[0].getval();

        for (int index = 1; index < 7; index++) {
            if (options[index].getval() > hival)
                hival = options[index].getval();
        }
        ArrayList bestchoices = new ArrayList();
        for (int index = 0; index < 7; index++) {
            if (options[index].getval() == hival) {
                bestchoices.add(options[index]);
            }
        }
        int index = Etool.randint(0, bestchoices.size() - 1);
        col = ((Ai.Move)bestchoices.get(index)).getcol();

        Etool.debug(((Ai.Move)bestchoices.get(index)).toString());
        return col;
    }

    private static class Move
    {
        int column;
        int value;
        String strat;

        public Move(int col, Board b, Color mcolor, Color ocolor) {
            this.column = col;
            Board board = new Board(b);
            this.value = 0;
            this.strat = new String("");

            if (!board.isopen(this.column)) {
                this.value -= 100000;
            }

            if (wouldwin(board, this.column, mcolor))
            {
                this.value += 10000;
                this.strat += " win ";
            }

            if (wouldwin(board, this.column, ocolor))
            {
                this.value += 4000;
                this.strat += " block ";
            }

            if (wouldwin2(board, this.column, mcolor, ocolor)) {
                this.value -= 2500;
            }

            if (wouldtrap(board, this.column, mcolor))
            {
                this.value += 200;
                this.strat += " set_trap ";
            }

            if (wouldtrap(board, this.column, ocolor))
            {
                this.value += 150;
                this.strat += " block_trap ";
            }

            if (wouldtrap2(board, this.column, mcolor, ocolor)) {
                this.value -= 140;
            }

            if (wouldthree(board, this.column, mcolor))
            {
                this.value += 50;
                this.strat += " connect_three ";
            }

            if (wouldthree(board, this.column, ocolor))
            {
                this.value += 50;
                this.strat += " block_three ";
            }
        }

        public int getval()
        {
            return this.value;
        }

        public int getcol()
        {
            return this.column;
        }

        public String getstrategy()
        {
            return this.strat;
        }

        public String toString()
        {
            return "Column:" + this.column + "\tValue:" + this.value + "\nStrategies:" + this.strat + "\n";
        }

        private static boolean wouldwin(Board b, int col, Color piece)
        {
            if (!b.isopen(col)) {
                return false;
            }
            Board z = new Board(b);
            new Piece(z, col, piece);

            return (z.checkwin()) && (z.winner().equals(piece));
        }

        private static boolean wouldwin2(Board b, int col, Color piece, Color opponent)
        {
            Board z = new Board(b);

            if (wouldwin(z, col, piece)) {
                return false;
            }
            if (!z.isopen(col))
                return false;
            new Piece(z, col, piece);

            if (z.isfull()) {
                return false;
            }
            return wouldwin(z, col, opponent);
        }

        private static boolean wouldtrap(Board b, int col, Color piece)
        {
            Board z = new Board(b);

            if (!z.isopen(col))
                return false;
            new Piece(z, col, piece);

            return countwin(z, piece) >= 2;
        }

        private static boolean wouldtrap2(Board b, int col, Color piece, Color opponent)
        {
            Board z = new Board(b);

            if (!z.isopen(col))
                return false;
            new Piece(z, col, piece);
            if (z.isfull()) {
                return false;
            }
            return wouldtrap(z, col, opponent);
        }

        private static int countwin(Board b, Color piece)
        {
            int num = 0;
            for (int c = 0; c < 7; c++)
            {
                if (wouldwin(b, c, piece))
                    num++;
            }
            return num;
        }

        private static int randcol(Board b)
        {
            int c = Etool.randint(0, 6);
            while (!b.isopen(c))
            {
                c = Etool.randint(0, 6);
                Etool.debug("Blocked to: " + c);
            }
            return c;
        }

        public static boolean wouldthree(Board b, int col, Color mycolor)
        {
            if (!b.isopen(col)) {
                return false;
            }
            Board z = new Board(b);
            Piece thispiece = new Piece(z, col, mycolor);

            Position pos = new Position();

            for (pos.row = 0; pos.row < 6; pos.row += 1)
            {
                for (pos.col = 0; pos.col < 5; pos.col += 1)
                {
                    if ((z.atPos(pos.row, pos.col).equals(z.atPos(pos.row, pos.col + 1))) && (z.atPos(pos.row, pos.col).equals(z.atPos(pos.row, pos.col + 2))))
                    {
                        if (z.atPos(pos.row, pos.col).getPosition().equals(thispiece.getPosition()))
                            return true;
                        if (z.atPos(pos.row, pos.col + 1).getPosition().equals(thispiece.getPosition()))
                            return true;
                        if (z.atPos(pos.row, pos.col + 2).getPosition().equals(thispiece.getPosition())) {
                            return true;
                        }
                    }
                }
            }
            for (pos.row = 0; pos.row < 4; pos.row += 1)
            {
                for (pos.col = 0; pos.col < 5; pos.col += 1)
                {
                    if ((z.atPos(pos.row, pos.col).equals(z.atPos(pos.row + 1, pos.col + 1))) && (z.atPos(pos.row, pos.col).equals(z.atPos(pos.row + 2, pos.col + 2))))
                    {
                        if (z.atPos(pos.row, pos.col).getPosition().equals(thispiece.getPosition()))
                            return true;
                        if (z.atPos(pos.row + 1, pos.col + 1).getPosition().equals(thispiece.getPosition()))
                            return true;
                        if (z.atPos(pos.row + 2, pos.col + 2).getPosition().equals(thispiece.getPosition())) {
                            return true;
                        }
                    }
                }
            }
            for (pos.row = 0; pos.row < 4; pos.row += 1)
            {
                for (pos.col = 0; pos.col < 7; pos.col += 1)
                {
                    if ((z.atPos(pos.row, pos.col).equals(z.atPos(pos.row + 1, pos.col))) && (z.atPos(pos.row, pos.col).equals(z.atPos(pos.row + 2, pos.col))))
                    {
                        if (z.atPos(pos.row, pos.col).getPosition().equals(thispiece.getPosition()))
                            return true;
                        if (z.atPos(pos.row + 1, pos.col).getPosition().equals(thispiece.getPosition()))
                            return true;
                        if (z.atPos(pos.row + 2, pos.col).getPosition().equals(thispiece.getPosition())) {
                            return true;
                        }
                    }
                }
            }
            for (pos.row = 2; pos.row < 6; pos.row += 1)
            {
                for (pos.col = 0; pos.col < 5; pos.col += 1)
                {
                    if ((z.atPos(pos.row, pos.col).equals(z.atPos(pos.row - 1, pos.col + 1))) && (z.atPos(pos.row, pos.col).equals(z.atPos(pos.row - 2, pos.col + 2))))
                    {
                        if (z.atPos(pos.row, pos.col).getPosition().equals(thispiece.getPosition()))
                            return true;
                        if (z.atPos(pos.row - 1, pos.col + 1).getPosition().equals(thispiece.getPosition()))
                            return true;
                        if (z.atPos(pos.row - 2, pos.col + 2).getPosition().equals(thispiece.getPosition())) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }
}

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardPanel extends JPanel
{
    private final int WIDTH = 1000;
    private final int HEIGHT = 857;
    private Board b;
    private boolean redturn;
    private boolean twoplayer;
    private Piece temppiece;
    private int redscore = 0;
    private int blackscore = 0;
    private boolean scorechange = false;

    public BoardPanel(boolean twoP)
    {
        this.b = new Board();
        BoardPanel.BoardListener ear = new BoardPanel.BoardListener(null);
        addMouseListener(ear);
        addMouseMotionListener(ear);
        setBackground(Color.yellow);
        setPreferredSize(new Dimension(1000, 857));
        this.twoplayer = twoP;
        if (!twoP)
            this.redturn = true;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.b.display(g, 1000, 857);
    }
    private class BoardListener implements MouseListener, MouseMotionListener {
        private BoardListener() {
        }

        public void mousePressed(MouseEvent event) {
            if (!BoardPanel.this.b.gameover())
            {
                if (BoardPanel.this.temppiece != null) {
                    new Piece(BoardPanel.this.b, BoardPanel.this.temppiece.getPosition(), true);
                }
                Point click = event.getPoint();
                Position pos = new Position(6 * click.y / 857, 7 * click.x / 1000);
                if (BoardPanel.this.twoplayer)
                {
                    Color turn;
                    if (BoardPanel.this.redturn)
                        turn = Color.red;
                    else
                        turn = Color.black;
                    if (BoardPanel.this.b.isopen(pos.col))
                    {
                        new Piece(BoardPanel.this.b, pos.col, turn);
                        BoardPanel.this.temppiece = null;
                        BoardPanel.this.redturn = (!BoardPanel.this.redturn);
                    }

                }
                else if (BoardPanel.this.b.isopen(pos.col))
                {
                    new Piece(BoardPanel.this.b, pos.col, Color.red);
                    BoardPanel.this.temppiece = null;
                    if (!BoardPanel.this.b.gameover())
                    {
                        int comcol = Ai.move(BoardPanel.this.b, Color.black);
                        new Piece(BoardPanel.this.b, comcol, Color.black);
                    }
                }

            }
            else
            {
                BoardPanel.this.repaint();

                if ((BoardPanel.this.b.checkwin()) && (!BoardPanel.this.scorechange))
                {
                    BoardPanel.this.scorechange = true;
                    if (BoardPanel.this.b.winner().equals(Color.red))
                        BoardPanel.this.redscore++;
                    else
                        BoardPanel.this.blackscore++;
                }
                String scores = new String("Red has won " + BoardPanel.this.redscore + " times.\n");
                scores = scores + "Black has won " + BoardPanel.this.blackscore + " times.\n";
                int choice = JOptionPane.showConfirmDialog(null, scores + "Play Again?");
                if (choice == 0)
                {
                    BoardPanel.this.b = new Board();
                    BoardPanel.this.scorechange = false;
                }
            }
            BoardPanel.this.b.checkwin();
            BoardPanel.this.repaint();
        }

        public void mouseMoved(MouseEvent event)
        {
            if (!BoardPanel.this.b.gameover())
            {
                if (BoardPanel.this.temppiece != null)
                {
                    new Piece(BoardPanel.this.b, BoardPanel.this.temppiece.getPosition(), true);
                }
                Point click = event.getPoint();
                Position pos = new Position(6 * click.y / 857, 7 * click.x / 1000);
                Color turn;
                if (BoardPanel.this.redturn)
                    turn = Color.red;
                else
                    turn = Color.black;
                if (BoardPanel.this.b.isopen(pos.col))
                    BoardPanel.this.temppiece = new TempPiece(BoardPanel.this.b, pos.col, turn);
            }
            BoardPanel.this.repaint();
        }

        public void mouseClicked(MouseEvent event)
        {
        }

        public void mouseDragged(MouseEvent event)
        {
        }

        public void mouseReleased(MouseEvent event)
        {
        }

        public void mouseEntered(MouseEvent event)
        {
        }

        public void mouseExited(MouseEvent event)
        {
        }

        BoardListener(BoardPanel x1)
        {
            this();
        }
    }
}

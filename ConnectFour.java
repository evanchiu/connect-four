import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JApplet;
import javax.swing.JOptionPane;

public class ConnectFour extends JApplet
{
    private final int WIDTH = 700;
    private final int HEIGHT = 600;
    private Board b;
    private boolean redturn;
    private boolean twoplayer;
    private Piece temppiece;
    private int redscore;
    private int blackscore;
    private boolean scorechange;
    Image offscreen;

    public ConnectFour()
    {
        this.redscore = 0;
        this.blackscore = 0;
        this.scorechange = false;
    }

    public void init()
    {
        this.b = new Board();
        ConnectFour.BoardListener ear = new ConnectFour.BoardListener(null);
        addMouseListener(ear);
        addMouseMotionListener(ear);
        setBackground(Color.yellow);
        setSize(700, 600);
        this.twoplayer = false;
        if (!this.twoplayer)
            this.redturn = true;
    }

    public void update(Graphics g)
    {
        this.offscreen = createImage(700, 600);

        Graphics gg = this.offscreen.getGraphics();

        paint(gg);

        gg.dispose();

        g.drawImage(this.offscreen, 0, 0, this);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        this.b.display(g, 700, 600);
    }
    private class BoardListener implements MouseListener, MouseMotionListener {
        private BoardListener() {
        }

        public void mousePressed(MouseEvent event) {
            if (!ConnectFour.this.b.gameover())
            {
                if (ConnectFour.this.temppiece != null) {
                    new Piece(ConnectFour.this.b, ConnectFour.this.temppiece.getPosition(), true);
                }
                Point click = event.getPoint();
                Position pos = new Position(6 * click.y / 600, 7 * click.x / 700);
                if (ConnectFour.this.twoplayer)
                {
                    Color turn;
                    if (ConnectFour.this.redturn)
                        turn = Color.red;
                    else
                        turn = Color.black;
                    if (ConnectFour.this.b.isopen(pos.col))
                    {
                        new Piece(ConnectFour.this.b, pos.col, turn);
                        ConnectFour.this.temppiece = null;
                        ConnectFour.this.redturn = (!ConnectFour.this.redturn);
                    }

                }
                else if (ConnectFour.this.b.isopen(pos.col))
                {
                    new Piece(ConnectFour.this.b, pos.col, Color.red);
                    ConnectFour.this.temppiece = null;
                    if (!ConnectFour.this.b.gameover())
                    {
                        int comcol = Ai.move(ConnectFour.this.b, Color.black);
                        new Piece(ConnectFour.this.b, comcol, Color.black);
                    }
                }

            }
            else
            {
                if (ConnectFour.this.b.changed) ConnectFour.this.repaint();

                if ((ConnectFour.this.b.checkwin()) && (!ConnectFour.this.scorechange))
                {
                    ConnectFour.this.scorechange = true;
                    if (ConnectFour.this.b.winner().equals(Color.red))
                        ConnectFour.this.redscore++;
                    else
                        ConnectFour.this.blackscore++;
                }
                String scores = new String("Red has won " + ConnectFour.this.redscore + " times.\n");
                scores = scores + "Black has won " + ConnectFour.this.blackscore + " times.\n";
                int choice = JOptionPane.showConfirmDialog(null, scores + "Play Again?");
                ConnectFour.this.b.changed = true;
                if (choice == 0)
                {
                    ConnectFour.this.b = new Board();
                    ConnectFour.this.scorechange = false;
                }
            }
            ConnectFour.this.b.checkwin();
            if (ConnectFour.this.b.changed) ConnectFour.this.repaint();
        }

        public void mouseMoved(MouseEvent event)
        {
            if (!ConnectFour.this.b.gameover())
            {
                if (ConnectFour.this.temppiece != null)
                {
                    new Piece(ConnectFour.this.b, ConnectFour.this.temppiece.getPosition(), true);
                }
                Point click = event.getPoint();
                Position pos = new Position(6 * click.y / 600, 7 * click.x / 700);
                Color turn;
                if (ConnectFour.this.redturn)
                    turn = Color.red;
                else
                    turn = Color.black;
                if (ConnectFour.this.b.isopen(pos.col))
                    ConnectFour.this.temppiece = new TempPiece(ConnectFour.this.b, pos.col, turn);
            }
            if (ConnectFour.this.b.changed) ConnectFour.this.repaint();
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

        BoardListener(ConnectFour x1)
        {
            this();
        }
    }
}

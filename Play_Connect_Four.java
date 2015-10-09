import java.awt.Container;
import javax.swing.JFrame;

public class Play_Connect_Four
{
    public static void main(String[] args)
    {
        int twop = 1;

        JFrame boardFrame = new JFrame("Connect Four");
        boardFrame.setDefaultCloseOperation(3);

        if (twop == 0)
            boardFrame.getContentPane().add(new BoardPanel(true));
        else {
            boardFrame.getContentPane().add(new BoardPanel(false));
        }
        boardFrame.pack();
        boardFrame.show();
    }
}

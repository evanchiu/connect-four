import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.io.PrintStream;
import javax.swing.JOptionPane;

public class Etool
{
    public static boolean debug_is_on = false;

    public static final Color aqua = new Color(95, 247, 254);

    public static void debug(String message)
    {
        if (debug_is_on)
            System.out.println(message);
    }

    public static int randint(int min, int max)
    {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public static double randnum(double min, double max)
    {
        return Math.random() * (max - min) + min;
    }

    public static double rounder(double value, int places)
    {
        double truncated = (int)(value * Math.pow(10.0D, places));
        double leftover = value * Math.pow(10.0D, places) - truncated;
        double rounded;
        if (leftover >= 0.5D)
            rounded = truncated + 1.0D;
        else
            rounded = truncated;
        rounded /= Math.pow(10.0D, places);

        return rounded;
    }

    public static double getdouble(String name)
    {
        String str = JOptionPane.showInputDialog("Please enter the " + name);
        double number = Double.parseDouble(str);
        return number;
    }

    public static int getint(String name)
    {
        String str = JOptionPane.showInputDialog("Please enter the " + name);
        int number = Integer.parseInt(str);
        return number;
    }

    public static void wait(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (Exception e)
        {
        }
    }

    public boolean isinregion(Point p, int x, int y, int w, int h)
    {
        return (p.x >= x) && (p.x <= x + h) && (p.y >= y) && (p.y <= y + h);
    }

    public static Color randcolor()
    {
        return new Color(randint(0, 255), randint(0, 255), randint(0, 255));
    }

    public static void drawshape(Graphics g, Color color, String figure, int x, int y, int width, int height, boolean fill)
    {
        g.setColor(color);
        if (figure.equals("circle"))
        {
            if (fill)
                g.fillOval(x, y, width, height);
            else {
                g.drawOval(x, y, width, height);
            }
        }
        else if (figure.equals("triangle"))
        {
            int[] xpoints = { x + width / 2, x, x + width };
            int[] ypoints = { y, y + height, y + height };
            if (fill)
                g.fillPolygon(new Polygon(xpoints, ypoints, 3));
            else {
                g.drawPolygon(new Polygon(xpoints, ypoints, 3));
            }
        }
        else if (figure.equals("square"))
        {
            if (fill)
                g.fillRect(x, y, width, height);
            else {
                g.drawRect(x, y, width, height);
            }
        }
        if (figure.equals("hexagon"))
        {
            int[] xpoints = { x + width / 3, x + 2 * (width / 3), x + width, x + 2 * (width / 3), x + width / 3, x };
            int[] ypoints = { y, y, y + height / 2, y + height, y + height, y + height / 2 };
            if (fill)
                g.fillPolygon(new Polygon(xpoints, ypoints, 6));
            else {
                g.drawPolygon(new Polygon(xpoints, ypoints, 6));
            }
        }
        else if (figure.equals("cross"))
        {
            int[] xpoints = { x + width / 3, x + 2 * (width / 3), x + 2 * (width / 3), x + width, x + width, x + 2 * (width / 3), x + 2 * (width / 3), x + width / 3, x + width / 3, x, x, x + width / 3 };

            int[] ypoints = { y, y, y + height / 3, y + height / 3, y + 2 * (height / 3), y + 2 * (height / 3), y + height, y + height, y + 2 * (height / 3), y + 2 * (height / 3), y + height / 3, y + height / 3 };

            if (fill)
                g.fillPolygon(new Polygon(xpoints, ypoints, 12));
            else {
                g.drawPolygon(new Polygon(xpoints, ypoints, 12));
            }
        }
        else if (figure.equals("star"))
        {
            int[] xpoints = { x, x + width / 3, x + width / 2, x + 2 * (width / 3), x + width, x + 3 * (width / 4), x + 4 * (width / 5), x + width / 2, x + width / 5, x + width / 4 };

            int[] ypoints = { y + height / 3, y + height / 3, y, y + height / 3, y + height / 3, y + 3 * (height / 5), y + height, y + 4 * (height / 5), y + height, y + 3 * (height / 5) };

            if (fill)
                g.fillPolygon(new Polygon(xpoints, ypoints, 10));
            else
                g.drawPolygon(new Polygon(xpoints, ypoints, 10));
        }
        else if (figure.equals("jstar"))
        {
            int[] xpoints = { x, x + width / 3, x + width / 2, x + 2 * width / 3, x + width, x + 4 * width / 5, x + width, x + 2 * width / 3, x + width / 2, x + width / 3, x, x + width / 5 };

            int[] ypoints = { y + height / 4, y + height / 4, y, y + height / 4, y + height / 4, y + height / 2, y + 3 * height / 4, y + 3 * height / 4, y + height, y + 3 * height / 4, y + 3 * height / 4, y + height / 2 };

            if (fill)
                g.fillPolygon(new Polygon(xpoints, ypoints, 12));
            else
                g.drawPolygon(new Polygon(xpoints, ypoints, 12));
        }
        else if (figure.equals("crown"))
        {
            int[] xpoints = { x, x + width / 3, x + width / 2, x + 2 * width / 3, x + width, x + 3 * width / 4, x + width / 4 };

            int[] ypoints = { y + height / 5, y + height / 2, y, y + height / 2, y + height / 5, y + height, y + height };

            if (fill)
                g.fillPolygon(new Polygon(xpoints, ypoints, 7));
            else
                g.drawPolygon(new Polygon(xpoints, ypoints, 7));
        }
    }
}

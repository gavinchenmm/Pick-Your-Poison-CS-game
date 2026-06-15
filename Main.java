import javax.swing.*;
import java.awt.*;

class Main
{
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        GamePanel panel = new GamePanel(frame);

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        frame.setContentPane(panel);
        frame.setSize(1920,1080);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
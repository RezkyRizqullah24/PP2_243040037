import java.awt.BorderLayout;
import javax.swing.*;

public class Latihan2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Latihan 2");
            frame.add(new JLabel("Judul Atas(NORTH)", JLabel.CENTER), BorderLayout.NORTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setVisible(true);
        });
    }
}

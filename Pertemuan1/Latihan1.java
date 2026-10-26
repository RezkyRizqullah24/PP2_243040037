import javax.swing.*;

public class Latihan1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hello World");
            frame.add(new JLabel("Hello, PP2", SwingConstants.CENTER));

            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // tampil di tengah layar
            frame.setVisible(true);
        });
    }
}
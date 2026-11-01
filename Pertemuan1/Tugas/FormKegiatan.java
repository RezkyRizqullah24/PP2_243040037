package Tugas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * Tugas 1 - Form pendaftaran kegiatan (Java Swing).
 *
 * Susunan layout:
 * - panel utama      : BorderLayout (isi di CENTER, tombol + status di SOUTH)
 * - panel isi        : GridBagLayout (3 kelompok: identitas, kegiatan, catatan)
 * - panel identitas  : GridBagLayout (label di kiri, input di kanan)
 * - panel kegiatan   : GridBagLayout + BoxLayout untuk radio dan checkbox
 * - panel catatan    : BorderLayout (JTextArea di dalam JScrollPane)
 * - panel tombol     : BorderLayout + FlowLayout
 */
public class FormKegiatan {

    private static final String STATUS_AWAL = "Isi data lalu klik Daftar.";

    // komponen dijadikan field supaya bisa dipakai di tombol Daftar dan Reset
    private JFrame frame;
    private JTextField namaField;
    private JTextField npmField;
    private JTextField emailField;
    private JComboBox<String> kegiatanCombo;
    private JRadioButton luringRadio;
    private JRadioButton daringRadio;
    private JCheckBox sertifikatCheck;
    private JCheckBox konsumsiCheck;
    private JCheckBox setujuCheck;
    private JTextArea catatanArea;
    private JLabel statusLabel;
    private Color warnaStatusAwal;

    public static void main(String[] args) {
        // GUI dibuat lewat EDT, bukan langsung di thread main
        SwingUtilities.invokeLater(() -> new FormKegiatan().buatDanTampilkanGUI());
    }

    private void buatDanTampilkanGUI() {
        frame = new JFrame("Form Pendaftaran Kegiatan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(buatPanelUtama());
        frame.pack();
        // ukuran hasil pack() jadi batas minimum biar isi tidak terpotong
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        namaField.requestFocusInWindow();
    }

    // menyusun seluruh isi jendela (belum ada JFrame di sini)
    private JPanel buatPanelUtama() {
        // panel isi: tiga kelompok disusun dari atas ke bawah
        JPanel isiPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);

        // identitas dan kegiatan tingginya tetap (weighty 0)
        gbc.gridy = 0;
        gbc.weighty = 0;
        isiPanel.add(buatPanelIdentitas(), gbc);

        gbc.gridy = 1;
        isiPanel.add(buatPanelKegiatan(), gbc);

        // catatan menyerap tambahan tinggi saat jendela diperbesar
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        isiPanel.add(buatPanelCatatan(), gbc);

        JPanel utamaPanel = new JPanel(new BorderLayout(10, 10));
        utamaPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        utamaPanel.add(isiPanel, BorderLayout.CENTER);
        utamaPanel.add(buatPanelTombol(), BorderLayout.SOUTH);
        return utamaPanel;
    }

    // ---------- panel-panel ----------

    private JPanel buatPanelIdentitas() {
        namaField = new JTextField(25);
        npmField = new JTextField(25);
        emailField = new JTextField(25);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(buatBorderGrup("Identitas"));
        tambahBaris(panel, 0, "Nama lengkap:", 'N', namaField);
        tambahBaris(panel, 1, "NPM:", 'P', npmField);
        tambahBaris(panel, 2, "Email:", 'E', emailField);
        return panel;
    }

    private JPanel buatPanelKegiatan() {
        kegiatanCombo = new JComboBox<>(new String[] {
            "Seminar Teknologi",
            "Workshop Java",
            "Lomba Pemrograman",
            "Bakti Sosial",
            "Pelatihan Kepemimpinan"
        });

        // dua radio button dalam satu ButtonGroup supaya saling eksklusif
        // (BoxLayout dipakai biar rata kiri sejajar dengan combo dan field)
        luringRadio = new JRadioButton("Luring (datang langsung)", true);
        daringRadio = new JRadioButton("Daring");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(luringRadio);
        modeGroup.add(daringRadio);
        JPanel modePanel = new JPanel();
        modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.X_AXIS));
        modePanel.add(luringRadio);
        modePanel.add(Box.createHorizontalStrut(16));
        modePanel.add(daringRadio);
        modePanel.add(Box.createHorizontalGlue());

        sertifikatCheck = new JCheckBox("Sertifikat");
        konsumsiCheck = new JCheckBox("Konsumsi");
        JPanel butuhPanel = new JPanel();
        butuhPanel.setLayout(new BoxLayout(butuhPanel, BoxLayout.X_AXIS));
        butuhPanel.add(sertifikatCheck);
        butuhPanel.add(Box.createHorizontalStrut(16));
        butuhPanel.add(konsumsiCheck);
        butuhPanel.add(Box.createHorizontalGlue());

        setujuCheck = new JCheckBox("Data yang saya isi sudah benar");

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(buatBorderGrup("Pilihan Kegiatan"));
        tambahBaris(panel, 0, "Kegiatan:", 'K', kegiatanCombo);
        tambahBaris(panel, 1, "Mode hadir:", 0, modePanel);
        tambahBaris(panel, 2, "Kebutuhan:", 0, butuhPanel);
        tambahBaris(panel, 3, "Persetujuan:", 0, setujuCheck);
        return panel;
    }

    private JPanel buatPanelCatatan() {
        catatanArea = new JTextArea(5, 30);
        catatanArea.setLineWrap(true);
        catatanArea.setWrapStyleWord(true);
        // biar Tab di JTextArea pindah fokus, bukan menyisipkan karakter tab
        catatanArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        catatanArea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(buatBorderGrup("Catatan"));
        panel.add(new JScrollPane(catatanArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buatPanelTombol() {
        statusLabel = new JLabel(STATUS_AWAL);
        warnaStatusAwal = statusLabel.getForeground();

        JButton resetButton = new JButton("Reset");
        JButton daftarButton = new JButton("Daftar");
        resetButton.addActionListener(event -> resetForm());
        daftarButton.addActionListener(event -> prosesDaftar());

        JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        tombolPanel.add(resetButton);
        tombolPanel.add(daftarButton);

        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.add(statusLabel, BorderLayout.CENTER);
        panel.add(tombolPanel, BorderLayout.EAST);
        return panel;
    }


    private Border buatBorderGrup(String judul) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(judul),
            BorderFactory.createEmptyBorder(2, 6, 6, 6));
    }

    // menambah satu baris "label + isian" ke panel ber-GridBagLayout
    private void tambahBaris(JPanel panel, int baris, String teks, int mnemonic, JComponent isian) {
        JLabel label = new JLabel(teks);
        if (mnemonic != 0) {
            label.setDisplayedMnemonic(mnemonic);
            label.setLabelFor(isian);
        }

        GridBagConstraints kiri = new GridBagConstraints();
        kiri.gridx = 0;
        kiri.gridy = baris;
        kiri.anchor = GridBagConstraints.WEST;
        kiri.insets = new Insets(3, 4, 3, 10);
        panel.add(label, kiri);

        GridBagConstraints kanan = new GridBagConstraints();
        kanan.gridx = 1;
        kanan.gridy = baris;
        kanan.weightx = 1;
        kanan.fill = GridBagConstraints.HORIZONTAL;
        kanan.insets = new Insets(3, 0, 3, 4);
        panel.add(isian, kanan);
    }

    // ---------- aksi tombol ----------

    private void prosesDaftar() {
        String nama = namaField.getText().trim();
        String npm = npmField.getText().trim();
        String email = emailField.getText().trim();

        if (nama.isEmpty()) {
            tampilkanError("Nama wajib diisi.", namaField);
            return;
        }
        if (!npm.matches("\\d+")) {
            tampilkanError("NPM harus berupa angka.", npmField);
            return;
        }
        if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            tampilkanError("Format email belum benar.", emailField);
            return;
        }
        if (!setujuCheck.isSelected()) {
            tampilkanError("Centang persetujuan dulu.", setujuCheck);
            return;
        }

        // belum menyimpan apa-apa, cuma validasi
        statusLabel.setForeground(new Color(0, 110, 0));
        statusLabel.setText("Data valid (belum disimpan).");
    }

    private void tampilkanError(String pesan, JComponent target) {
        statusLabel.setForeground(new Color(180, 0, 0));
        statusLabel.setText(pesan);
        target.requestFocusInWindow();
    }

    private void resetForm() {
        namaField.setText("");
        npmField.setText("");
        emailField.setText("");
        kegiatanCombo.setSelectedIndex(0);
        luringRadio.setSelected(true);
        sertifikatCheck.setSelected(false);
        konsumsiCheck.setSelected(false);
        setujuCheck.setSelected(false);
        catatanArea.setText("");
        statusLabel.setForeground(warnaStatusAwal);
        statusLabel.setText(STATUS_AWAL);
        namaField.requestFocusInWindow();
    }
}

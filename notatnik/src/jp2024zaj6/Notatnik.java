package jp2024zaj6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class Notatnik extends JFrame implements ActionListener {

    private JTextArea textArea;

    public Notatnik() {

        super();
        setTitle("Notatnik");

        Toolkit zestaw = Toolkit.getDefaultToolkit();
        Dimension rozmiarEkranu = zestaw.getScreenSize();
        int szerEkranu = rozmiarEkranu.width;
        int wysEkranu = rozmiarEkranu.height;
        setBounds(szerEkranu / 4, wysEkranu / 4, szerEkranu / 2, wysEkranu / 2); //1/4 ekranu 
        setResizable(false);   //nie mozna rozciagac okienka

        JMenuBar pasekMenu = new JMenuBar();   //tworzenie paska menu
        JMenu mPlik = new JMenu("Plik");
        mPlik.setMnemonic('P');
        //podelementy
        JMenuItem otworz = new JMenuItem("Otworz");
        otworz.addActionListener(this);
        otworz.setActionCommand("11");
        otworz.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        JMenuItem zapisz = new JMenuItem("Zapisz");
        zapisz.addActionListener(this);
        zapisz.setActionCommand("12");
        zapisz.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        JMenuItem zakoncz = new JMenuItem("Zakoncz");
        zakoncz.setAccelerator(KeyStroke.getKeyStroke("ctrl K"));
        zakoncz.addActionListener(this);
        zakoncz.setActionCommand("13");

        mPlik.add(otworz);
        mPlik.add(zapisz);
        mPlik.addSeparator();
        mPlik.add(zakoncz);

        JMenu mEdycja = new JMenu("Edycja");
        mEdycja.setMnemonic('E');

        JRadioButtonMenuItem pomniejszCz = new JRadioButtonMenuItem("Pomniejsz czcionkę");
        JRadioButtonMenuItem normalnaCz = new JRadioButtonMenuItem("Normalna czcionka", true);
        JRadioButtonMenuItem powiekszCz = new JRadioButtonMenuItem("Powiększ czcionkę");

        JMenuItem wyczysc = new JMenuItem("Wyczysc");
        wyczysc.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        wyczysc.addActionListener(this);
        wyczysc.setActionCommand("24");

        ButtonGroup bg = new ButtonGroup();

        bg.add(pomniejszCz);
        bg.add(normalnaCz);
        bg.add(powiekszCz);

        mEdycja.add(pomniejszCz);
        pomniejszCz.addActionListener(this);
        pomniejszCz.setActionCommand("21");
        mEdycja.add(normalnaCz);
        normalnaCz.addActionListener(this);
        normalnaCz.setActionCommand("22");
        mEdycja.add(powiekszCz);
        powiekszCz.addActionListener(this);
        powiekszCz.setActionCommand("23");
        mEdycja.addSeparator();
        mEdycja.add(wyczysc);

        JMenu mPomoc = new JMenu("Pomoc");
        mPomoc.setMnemonic('o');

        JMenuItem info = new JMenuItem("O autorze");
        info.addActionListener(this);
        info.setActionCommand("31");
        mPomoc.add(info);

        //dodawanie do paska menu; wazna jest kolejnosc
        pasekMenu.add(mPlik);
        pasekMenu.add(mEdycja);
        pasekMenu.add(mPomoc);

        setJMenuBar(pasekMenu);     //dodawanie paska menu

        textArea = new JTextArea(); //tworzenie obszaru tekstowego

        //tworzenie panelu przewijalnego z obszarem tekstowym
        JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout());    //ustawienie layoutu dla calego okna
        add(sp, BorderLayout.CENTER);   //dodanie panelu przewijania na centrum okna
        Border obramowanieA = BorderFactory.createEtchedBorder();
        Border tytulObramowaniA = BorderFactory.createTitledBorder(obramowanieA, "Pisanie");
        sp.setBorder(tytulObramowaniA);

        JPanel panelLewy = new JPanel(new FlowLayout(FlowLayout.CENTER));  //stworzenie paneli na przyciski
        JButton tytul = new JButton("Tytul");
        tytul.addActionListener(this);
        tytul.setActionCommand("41");
        JButton podpis = new JButton("Podpis");
        podpis.addActionListener(this);
        podpis.setActionCommand("42");
        panelLewy.add(tytul);
        panelLewy.add(podpis);
        Border obramowanieE = BorderFactory.createEtchedBorder();
        Border tytulObramowaniaE = BorderFactory.createTitledBorder(obramowanieE, "Wstawki");
        panelLewy.setBorder(tytulObramowaniaE);

        JPanel panelSrodkowy = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] czKolory = {"czerwona", "zielona", "niebieska", "czarna", "biala"};
        JLabel etyKolory = new JLabel("Kolory:  ");
        JComboBox kolorList = new JComboBox(czKolory);
        kolorList.setSelectedIndex(3);
        kolorList.addActionListener(this);
        kolorList.setActionCommand("51");
        panelSrodkowy.add(etyKolory);
        panelSrodkowy.add(kolorList);
        Border obramowanieD = BorderFactory.createEtchedBorder();
        Border tytulObramowaniaD = BorderFactory.createTitledBorder(obramowanieD, "Kolory czcionki");
        panelSrodkowy.setBorder(tytulObramowaniaD);

        JPanel panelPrawy = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JRadioButton bi = new JRadioButton("bialy", true);
        JRadioButton zo = new JRadioButton("zolty");
        JRadioButton zi = new JRadioButton("zielony");

        //dodanie nasluchu
        bi.addActionListener(this);
        zo.addActionListener(this);
        zi.addActionListener(this);
        //przypisanie numerow do przyciskow
        bi.setActionCommand("61");
        zo.setActionCommand("62");
        zi.setActionCommand("63");

        ButtonGroup bg1 = new ButtonGroup();

        bg1.add(bi);
        bg1.add(zo);
        bg1.add(zi);

        panelPrawy.add(bi);
        panelPrawy.add(zo);
        panelPrawy.add(zi);

        Border obramowanieF = BorderFactory.createEtchedBorder();
        Border tytulObramowaniaF = BorderFactory.createTitledBorder(obramowanieF, "Kolory tla");
        panelPrawy.setBorder(tytulObramowaniaF);

        JPanel panelPrzyciski = new JPanel(new GridLayout(1, 3)); //panel zbiorczy
        panelPrzyciski.add(panelLewy);
        panelPrzyciski.add(panelSrodkowy);
        panelPrzyciski.add(panelPrawy);

        add(panelPrzyciski, BorderLayout.SOUTH); //dodanie panelu na dole okna
        
        //Popup menu
        JPopupMenu menuPopUP = new JPopupMenu();
        JMenuItem metal = new JMenuItem("metal look-and-feel");
	JMenuItem windows = new JMenuItem("windows look-and-feel");
	JMenuItem motif = new JMenuItem("motif look-and-feel");
        menuPopUP.add(metal);
        menuPopUP.add(windows);
        menuPopUP.add(motif);
        textArea.setComponentPopupMenu(menuPopUP);
        
        
        metal.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        windows.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        motif.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        Notatnik nt = new Notatnik();
        ImageIcon ikona = new ImageIcon("C:/Users/K/Desktop/notatnik.png");
        nt.setIconImage(ikona.getImage());
        nt.setVisible(true);
        nt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //sprawdzenie ktory przycisk zostal wcisniety i wykonanie odpowiedniej akcji
        switch (Integer.parseInt(e.getActionCommand())) {
            case 11: {
                JFileChooser pliki = new JFileChooser(".");
                if (JFileChooser.APPROVE_OPTION == pliki.showOpenDialog(this)) {
                    try {
                        File f = pliki.getSelectedFile();
                        setTitle(f.getAbsolutePath() + " Notatnik");
                        BufferedReader br = new BufferedReader(new FileReader(f));
                        String temp = "";
                        while (br.ready()) {
                            temp += br.readLine() + "\n";
                        }
                        textArea.setText(temp);
                    } catch (IOException ex) {
                        System.out.println("Brak pliku");
                    }
                }
                break;
            }
            case 12: {
                JFileChooser pliki = new JFileChooser(".");
                if (JFileChooser.APPROVE_OPTION == pliki.showSaveDialog(this)) {
                    try {
                        File f = pliki.getSelectedFile();
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                        bw.write(textArea.getText());
                        bw.flush();
                        bw.close();
                    } catch (IOException ee) {
                        System.out.println("Problemy z zapisem");
                    }
                }
                break;
            }
            case 13: {
                System.exit(0);
                break;
            }
            case 21: {
                Font f = new Font("Arial", Font.PLAIN, 10);
                textArea.setFont(f);
                break;
            }
            case 22: {
                Font f = new Font("Arial", Font.PLAIN, 12);
                textArea.setFont(f);
                break;
            }
            case 23: {
                Font f = new Font("Arial", Font.PLAIN, 18);
                textArea.setFont(f);
                break;
            }
            case 24: {
                textArea.setText("");
                break;
            }
            case 31: {
                JOptionPane.showMessageDialog(this, "Autor: Kinga Lubecka");
                break;
            }
            case 41: {
                textArea.setText("Szanowny Panie \n\n" + textArea.getText());
                break;
            }
            case 42: {
                textArea.setText(textArea.getText() + "\n\n Z powazaniem");
                break;
            }
            case 51: {
                JComboBox komboBox = (JComboBox) (e.getSource());
                switch (komboBox.getSelectedIndex()) {
                    case 0: {
                        textArea.setForeground(Color.RED);
                        break;
                    }
                    case 1: {
                        textArea.setForeground(Color.GREEN);
                        break;
                    }
                    case 2: {
                        textArea.setForeground(Color.BLUE);
                        break;
                    }
                    case 3: {
                        textArea.setForeground(Color.BLACK);
                        break;
                    }
                    case 4: {
                        textArea.setForeground(Color.WHITE);
                        break;
                    }

                }
            }
            case 61: {
                textArea.setBackground(Color.WHITE);
                break;
            }
            case 62: {
                textArea.setBackground(Color.YELLOW);
                break;
            }
            case 63: {
                textArea.setBackground(Color.GREEN);
                break;
            }
            
        }
    }
}

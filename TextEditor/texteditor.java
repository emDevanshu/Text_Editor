package TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class texteditor implements ActionListener {

    JFrame frame;
    JMenuBar menuBar;
    JMenu file,edit,themes;
    JTextArea textArea;
    JScrollPane scroll;
    JMenuItem darkTheme, purpleMoonTheme, defaultTheme, save, open, close, cut, copy, paste, New, selectAll, fontSize;
    JPanel saveFileOptionWindow;
    JLabel fileLabel, dirLabel;
    JTextField fileName, dirName;

    public texteditor() {
        frame = new JFrame("Untitled_Document-1");

        Image img = Toolkit.getDefaultToolkit().getImage("/Users/devanshusharma/IdeaProjects/CodeClause/src/main/java/TextEditor/logo_texteditor.png");
        frame.setIconImage(img);

        menuBar = new JMenuBar();

        // CREATING ALL THE MENUS
        file = new JMenu("File");
        edit = new JMenu("Edit");
        themes = new JMenu("Themes");

        // ADDING MENUS TO MENUBAR
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(themes);
        frame.setJMenuBar(menuBar);

        // ADDING SUB-MENUS IN File
        New = new JMenuItem("New");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        close = new JMenuItem("Close");
        file.add(New);
        file.add(open);
        file.add(save);
        file.add(close);

        // ADDING SUB-MENUS IN Edit
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select all");
        fontSize = new JMenuItem("Font size");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(fontSize);

        // ADDING SUB-MENUS IN Themes
        darkTheme = new JMenuItem("Dark Theme");
        defaultTheme = new JMenuItem("Default Theme");
        purpleMoonTheme = new JMenuItem("Purple Moon Theme");
        themes.add(darkTheme);
        themes.add(purpleMoonTheme);
        themes.add(defaultTheme);

        // TEXT AREA
        textArea = new JTextArea(32,83);
        frame.add(textArea);

        // SCROLL PANE
        scroll = new JScrollPane(textArea);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scroll);


        // adding event listeners for cut, copy, paste
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        fontSize.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        New.addActionListener(this);
        darkTheme.addActionListener(this);
        purpleMoonTheme.addActionListener(this);
        defaultTheme.addActionListener(this);
        close.addActionListener(this);

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame,"Do you want to exit?", "Confirm Before Saving", JOptionPane.YES_NO_OPTION);

                if(confirm == JOptionPane.YES_OPTION)
                    frame.dispose();
                else if(confirm == JOptionPane.NO_OPTION)
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });


        // KEYBOARD FUNCTIONS/LISTERNERS
        KeyListener k = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_S && e.isMetaDown())
                    saveTheFile();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        textArea.addKeyListener(k);


        frame.setSize(1000,596);
        frame.setResizable(false);
        frame.setLocation(250,100);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new texteditor();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // COPY PASTE FUNCTIONS
        if(e.getSource() == cut) textArea.cut();
        if(e.getSource() == copy) textArea.copy();
        if(e.getSource() == paste) textArea.paste();
        if(e.getSource() == selectAll) textArea.selectAll();


        // CHANGING THE FONT SIZE
        if(e.getSource() == fontSize) {
            String sizeoffont = JOptionPane.showInputDialog(frame,"Enter font size : ", JOptionPane.OK_CANCEL_OPTION);

            if(sizeoffont != null)
            {
                int val = Integer.parseInt(sizeoffont);

                Font font = new Font(Font.SANS_SERIF, Font.PLAIN, val);

                textArea.setFont(font);
            }
        }

        // OPENING A FILE
        if(e.getSource() == open) {

            JFileChooser fileChooser = new JFileChooser();
            int i = fileChooser.showOpenDialog(frame);

            if(i == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                String filepath = file.getPath();
                String fileNameToShow = file.getName();

                frame.setTitle(fileNameToShow);


                try {
                    BufferedReader readFile = new BufferedReader(new FileReader(filepath));

                    String singleLine = "";
                    String content = "";

                    while ((singleLine = readFile.readLine()) != null){
                        content += singleLine + "\n";
                    }

                    textArea.setText(content);
                    readFile.close();
                }
                catch (Exception excep){
                    excep.printStackTrace();
                }
            }
        }


        // SAVING A FILE
        if(e.getSource() == save) {
            saveTheFile();
        }

        // CREATING A NEW FILE
        if(e.getSource() == New) {
            textArea.setText("");
        }

        // EXIT FROM THE WINDOW
        if(e.getSource() == close) {
            int confirm = JOptionPane.showConfirmDialog(frame,"Do you want to exit?", "Confirm Before Saving", JOptionPane.YES_NO_OPTION);

            if(confirm == JOptionPane.YES_OPTION)
                frame.dispose();
            else if(confirm == JOptionPane.NO_OPTION)
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }

        // SETTING THE THEMES
        if(e.getSource() == darkTheme) {
            textArea.setBackground(new Color(43,43,43));
            textArea.setForeground(Color.WHITE);
        }
        if(e.getSource() == defaultTheme) {
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
        }
        if(e.getSource() == purpleMoonTheme) {
            textArea.setBackground(new Color(81,83,107));
            textArea.setForeground(Color.WHITE);
        }


    }

    private void saveTheFile() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = fileChooser.showSaveDialog(frame);
        if(res == JFileChooser.CANCEL_OPTION) {
            return;
        }

        saveFileOptionWindow = new JPanel(new FlowLayout());
        saveFileOptionWindow.setSize(20,20);

        fileLabel = new JLabel("FileName        :");
        fileName = new JTextField(20);

        saveFileOptionWindow.add(fileLabel);
        saveFileOptionWindow.add(fileName);

        JOptionPane.showMessageDialog(frame, saveFileOptionWindow);
        String filecontent = textArea.getText();
        String filePath = fileChooser.getCurrentDirectory() + "/" + fileName.getText();
//        String temp = fileChooser.getSelectedFile().getName();

        try {
            BufferedWriter writeContent = new BufferedWriter(new FileWriter(filePath));

            writeContent.write(filecontent);
            writeContent.close();

            JOptionPane.showMessageDialog(frame, "File Successfully saved!");

        }
        catch (Exception excep)
        {
            excep.printStackTrace();
        }
    }
}

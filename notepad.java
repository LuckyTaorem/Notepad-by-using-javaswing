import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

public class notepad {
    
    static String text="Word Count: 0         Character Count: 0      ";
    static String [] words;
    static File selectedFile;
    static int wcount =0;
    static JFileChooser fileChooser;
    public static void main(String[] args) {
        JFrame f = new JFrame("NotePad");
        f.setSize(1200,800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        JTextArea jt = new JTextArea();
        jt.setFont(new Font("Times new roman", 1, 20));
        JScrollPane jsp = new JScrollPane(jt);
        f.add(jsp);

        JMenuBar jmb = new JMenuBar();
        f.setJMenuBar(jmb);
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Edit");
        JMenu m3 = new JMenu("Font");
        JMenu m311 = new JMenu("Font Type");
        m3.add(m311);
        JMenu m312 = new JMenu("Font Size");
        m3.add(m312);
        JMenu m4 = new JMenu("Help");
        jmb.add(m1); jmb.add(m2); jmb.add(m3); jmb.add(m4); 

        JMenuItem m11 = new JMenuItem("New      ");
        JMenuItem m12 = new JMenuItem("Open      ");
        JMenuItem m13 = new JMenuItem("Save      ");
        JMenuItem m14 = new JMenuItem("Save As      ");
        JMenuItem m15 = new JMenuItem("Print      ");
        JMenuItem m16 = new JMenuItem("Exit      ");
        
        m11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        m12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        m13.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        m14.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        m15.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));

        m1.add(m11); m1.add(m12); 
        m1.addSeparator();
        m1.add(m13);
        m1.add(m14);
        m1.addSeparator();
        m1.add(m15);
        m1.addSeparator();
        m1.add(m16);

        JMenuItem m21 = new JMenuItem("Cut      ");
        JMenuItem m22 = new JMenuItem("Copy      ");
        JMenuItem m23 = new JMenuItem("Paste      ");
        JMenuItem m24 = new JMenuItem("Undo      ");
        JMenuItem m25 = new JMenuItem("Redo      ");
        
        m21.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        m22.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        m23.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        m24.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        m25.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));

        m2.add(m21); m2.add(m22); m2.add(m23); 
        m1.addSeparator();
        m1.add(m24);
        m1.add(m25);

        JCheckBoxMenuItem m31 = new JCheckBoxMenuItem("Bold",true);
        m311.add(m31);
        JCheckBoxMenuItem m32 = new JCheckBoxMenuItem("Plain");
        m311.add(m32);
        JCheckBoxMenuItem m33 = new JCheckBoxMenuItem("Italic");
        m311.add(m33);
        JCheckBoxMenuItem m34 = new JCheckBoxMenuItem("Italic + Bold");
        m311.add(m34);

        JCheckBoxMenuItem f20 = new JCheckBoxMenuItem("20",true);
        m312.add(f20);

        ButtonGroup bg = new ButtonGroup();
        bg.add(m31);
        bg.add(m32);
        bg.add(m33);
        bg.add(m34);

        JMenuItem m41 = new JMenuItem("About      ");
        m4.add(m41);

        JLabel bottom = new JLabel(text,JLabel.RIGHT);
        f.add(bottom,BorderLayout.SOUTH);

        Thread wc = new Thread(
        ()->{
            while(true){
                text = jt.getText();
                words = jt.getText().split("\\s+");
                wcount = words.length -1;
                bottom.setText("Word Count: "+wcount + "         Character Count: "+text.length()+"      ");
            }
        }
        );
        wc.start();
        class ItemListen implements ItemListener{
            public void itemStateChanged(ItemEvent ie) {
               if(m31.isSelected()){
                // String Text = jt.getText();
                jt.setFont(new Font("Times new roman",Font.BOLD,20));
               }
               else if(m32.isSelected()){
                jt.setFont(new Font("Times new roman",Font.PLAIN,20));
               }
               else if(m33.isSelected()){
                jt.setFont(new Font("Times new roman",Font.ITALIC,20));
               }else if(m34.isSelected()){
                jt.setFont(new Font("Times new roman",3,20));
               }
            //    else if(f20.isSelected()){
            //     jt.setFont(new Font("Times new roman",3,20));
            //    }
            }
        }
        ItemListen il = new ItemListen();
        m31.addItemListener(il);
        m32.addItemListener(il);
        m33.addItemListener(il);
        m34.addItemListener(il);

        class MyListener implements ActionListener{
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()== m11){
                    fileChooser = new JFileChooser();

                    fileChooser.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text documents (*.txt)", "txt");
                    fileChooser.addChoosableFileFilter(filter);

                    int result = fileChooser.showSaveDialog(f);
                    if (result == 0) {
                        selectedFile = fileChooser.getSelectedFile();

                        if (!selectedFile.getName().endsWith(".txt")) {
                            selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                        }
                        
                        try {
                            boolean success = selectedFile.createNewFile();
                            if (success) {
                                f.setTitle(selectedFile.getName());
                            } else {
                                JOptionPane.showMessageDialog(f, "Error creating new file");
                            }
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }
                }
                else if(e.getSource() == m12){

                    fileChooser = new JFileChooser();

                    fileChooser.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text documents (*.txt)", "txt");
                    fileChooser.addChoosableFileFilter(filter);

                    int result = fileChooser.showOpenDialog(f);

                    if (result == 0) {
                        selectedFile = fileChooser.getSelectedFile();
                        String fileName = selectedFile.getName();
                        f.setTitle(fileName);
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                jt.append(line + "\n");
                            }
                        reader.close();
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }
                }
                else if(e.getSource()==m13){

                    if (selectedFile != null) {
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                            writer.write(jt.getText());
                            writer.close();
                            JOptionPane.showMessageDialog(f, "File saved successfully.");
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(f, "Please open a file first.");
                    }
                }
                else if(e.getSource()==m14){
                    fileChooser = new JFileChooser();

                    fileChooser.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text documents (*.txt)", "txt");
                    fileChooser.addChoosableFileFilter(filter);

                    int returnValue = fileChooser.showSaveDialog(f);

                    if (returnValue == 0) {
                        // Get the selected file
                        selectedFile = fileChooser.getSelectedFile();
                        if (!selectedFile.getName().endsWith(".txt")) {
                            selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                        }
                        String fileName = selectedFile.getName();
                        f.setTitle(fileName);

                        try {
                            FileWriter writer = new FileWriter(selectedFile);
                            writer.write(jt.getText());
                            writer.close();
                        } catch (IOException ex) {
                        JOptionPane.showMessageDialog(f, "Error writing file: " + ex.getMessage());
                        }
                    }
                }else if(e.getSource()== m15){
                        PrinterJob job = PrinterJob.getPrinterJob();
                        job.setPrintable(new Printable() {
                          public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                            if (pageIndex > 0) {
                              return 1;
                            }
                            return 0;
                          }
                        });
                        if (job.printDialog()) {
                          try {
                            job.print();
                          } catch (Exception ep) {
                            System.out.println(ep);
                          }
                        }
                }else if(e.getSource() == m41){
                    JFrame fa = new JFrame("About");
                    fa.setSize(600,600);
                    fa.setResizable(false);
                    fa.setLocationRelativeTo(f);
                    fa.setLayout(null);

                    ImageIcon ic = new ImageIcon("lucky.png");
                    JLabel l = new JLabel(ic);
                    l.setBounds(50, 50, 100, 100);
                    
                    JLabel name = new JLabel("Name: Taorem Lucky Singh");
                    JLabel regd = new JLabel("Regd.no: 12203018");
                    JLabel made = new JLabel("Made with \u2764 By: ");
                    JLabel desc = new JLabel("Student of B.Tech (CSE)");
                    JLabel sec = new JLabel("Section: K21LE");
                    
                    fa.add(l);
                    fa.add(name);
                    fa.add(regd);
                    fa.add(made);
                    fa.add(desc);
                    fa.add(sec);

                    Font serif = new Font("Serif",1,30);
                    Font serif_bold = new Font("Serif",Font.BOLD,30);
                    name.setBounds(50, 160,500,50);
                    name.setFont(serif);
                    regd.setBounds(50, 210,300,50);
                    regd.setFont(serif);
                    made.setBounds(170, 45,300,50);
                    made.setFont(serif_bold);
                    desc.setBounds(170, 80,500,50);
                    desc.setFont(serif_bold);
                    sec.setBounds(50,260,300,50);
                    sec.setFont(serif_bold);


                    fa.setVisible(true);
                }else if(e.getSource()==m16){
                    System.exit(0);
                }else if(e.getSource()==m21){
                    jt.cut();
                }else if(e.getSource()==m22){
                    jt.copy();
                }else if(e.getSource()==m23){
                    jt.paste();
                }else if(e.getSource()==m24){
                    // jt.
                }else if(e.getSource()==m25){

                }
            }
        }
        
        MyListener ml = new MyListener();
        m11.addActionListener(ml);
        m12.addActionListener(ml);
        m13.addActionListener(ml);
        m14.addActionListener(ml);
        m15.addActionListener(ml);
        m16.addActionListener(ml);

        m41.addActionListener(ml);

        m21.addActionListener(ml);
        m22.addActionListener(ml);
        m23.addActionListener(ml);
        m24.addActionListener(ml);
        m25.addActionListener(ml);
        // font_size.addActionListener(ml);

        f.setVisible(true);
    }
}

package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;

public class Editor extends JFrame implements ActionListener {
JTextArea textArea;
JLabel fontlable;
JScrollPane scrollPane;
JSpinner fontsizespinner;
JButton fontColorButton;
JComboBox fontbox;

JMenuBar menuBar;
    Editor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setTitle("text_Editor");
        this.setSize(500 ,500);
        this.setLayout(new FlowLayout());

       this.setLocationRelativeTo(null);

       //textarea

        textArea =new JTextArea();

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arila",Font.PLAIN,20));

        //scrollpanel
        scrollPane =new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450,450));

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
   //Jspinner
        fontlable=new JLabel("Font");
        fontsizespinner=new JSpinner();
        fontsizespinner.setPreferredSize(new Dimension(50,25));
        fontsizespinner.setValue(20);
        fontsizespinner.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontsizespinner.getValue()));
            }
        });
        //Jbuttons
        fontColorButton=new JButton("color");
        fontColorButton.addActionListener(this);

        //font style
        String fonts[] =GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontbox=new JComboBox(fonts);
        fontbox.addActionListener(this);
        fontbox.setSelectedItem("Arial");

        //menubar
        menuBar=new JMenuBar();
        JMenu m1=new JMenu("File");
        JMenuItem mi1  =new JMenuItem("New");
        JMenuItem mi2  =new JMenuItem("Open");
        JMenuItem mi3  =new JMenuItem("save");
        JMenuItem mi4  =new JMenuItem("print");
mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi4.addActionListener(this);
     m1.add(mi1);
        m1.add(mi2);

        m1.add(mi3);

        m1.add(mi4);

        //edit menu
        JMenu m2=new JMenu("Edit");
        //menuiteam
        JMenuItem mi5  =new JMenuItem("cut");
        JMenuItem mi6  =new JMenuItem("copy");
        JMenuItem mi7  =new JMenuItem("paste");

        mi5.addActionListener(this);
        mi6.addActionListener(this);
        mi7.addActionListener(this);

        m2.add(mi5);
        m2.add(mi6);
        m2.add(mi7);

        JMenuItem mc=new JMenuItem("close");

        menuBar.add(m1);
        menuBar.add(m2);
        menuBar.add(mc);

        this.setJMenuBar(menuBar);
        this.add(fontlable);
        this.add(fontsizespinner);
        this.add(fontColorButton);
        this.add(fontbox);
       this.add(scrollPane);

        this.setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        //fontcolor
       if (e.getSource()==fontColorButton){
           JColorChooser colorChooser=new JColorChooser();

           Color color=colorChooser.showDialog(null,"choose a color",Color.BLACK);
           textArea.setForeground(color);
       }
       //fontbox
        if (e.getSource()==fontbox){
            textArea.setFont(new Font((String) fontbox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }

        String s =e.getActionCommand();
        if(s.equals("cut")){
            textArea.cut();

        }
        else if(s.equals("copy")){
            textArea.copy();
        }
        else if(s.equals("paste")){
            textArea.paste();
        }
        else if(s.equals("save")){
            // create object filechooser class
            JFileChooser j=new JFileChooser("D:");
            int r=j.showSaveDialog(null);
            if (r==JFileChooser.APPROVE_OPTION){
                File fi =new File(j.getSelectedFile().getAbsolutePath());
                try{
                    FileWriter wr =new FileWriter(fi,false);

                    BufferedWriter w=new BufferedWriter(wr);
                    w.write(textArea.getText());
                    w.flush();
                    w.close();

                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,ex.getMessage());
                }
            }
            else{
                JOptionPane.showMessageDialog(this,"the user cancelled the operation");
            }

        }
        else if(s.equals("Open")){
            JFileChooser j=new JFileChooser("D:");
            int r=j.showSaveDialog(null);
            if (r== JFileChooser.APPROVE_OPTION){
                File fi =new File(j.getSelectedFile().getAbsolutePath());

                try{
                    String s1=" ",s2=" ";
                    FileReader fr =new FileReader(fi);
                    BufferedReader br =new BufferedReader(fr);

                    s1=br.readLine();

                    while ((s1=br.readLine()) !=null){
                        s1=s1+"\n"+s1;
                    }
                    textArea.setText(s1);

                }catch (Exception ev){
                    JOptionPane.showMessageDialog(this,ev.getMessage());
                }
            }
            else {
                JOptionPane.showMessageDialog(this,"the user cancelled the option");
            }

        }
        else if(s.equals("print")){
            try{

                textArea.print();
            } catch (Exception ex) {
               JOptionPane.showMessageDialog(this,ex.getMessage());
            }

        }
        else if(s.equals("New")){
            textArea.setText(" ");
        }
        else if(s.equals("close")){
          textArea.setVisible(false);
        }

    }
}

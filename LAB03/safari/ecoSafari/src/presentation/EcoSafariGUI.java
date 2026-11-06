package presentation;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EcoSafariGUI extends JFrame{  
    public static final int SIDE=20;

    public final int SIZE;
    private JButton ticTacButton;
    private JPanel  controlPanel;
    private PhotoEcoSafari photo;
    private EcoSafari theEcoSafari;
    
    //JMenuItems
    JMenuItem nuevo;
    JMenuItem abrir;
    JMenuItem salvar;
    JMenuItem importar;
    JMenuItem exportar;
    JMenuItem salir;
    
    private EcoSafariGUI() {
        theEcoSafari=new EcoSafari();
        SIZE=theEcoSafari.getSize();
        prepareElements();
        prepareActions();
    }
    
    private void prepareElements() {
        setTitle("EcoSafari");
        photo=new PhotoEcoSafari(this);
        ticTacButton=new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo,BorderLayout.NORTH);
        add(ticTacButton,BorderLayout.SOUTH);
        setSize(new Dimension(SIDE*SIZE+15,SIDE*SIZE+90)); 
        setResizable(false);
        photo.repaint();
        setJMenuBar(prepareViewMenu());
    }

    private JMenuBar prepareViewMenu(){
        JMenuBar barra = new JMenuBar();
        JMenu archivo = new JMenu("Archivo");
        nuevo = new JMenuItem("Nuevo");
        abrir = new JMenuItem("Abrir");
        salvar = new JMenuItem("Salvar como");
        importar = new JMenuItem("Importar");
        exportar = new JMenuItem("Exportar como");
        salir = new JMenuItem("Salir");

        archivo.add(nuevo);
        archivo.addSeparator();
        archivo.add(abrir);
        archivo.add(salvar);
        archivo.addSeparator();
        archivo.add(importar);
        archivo.add(exportar);
        archivo.addSeparator();
        archivo.add(salir);

        barra.add(archivo);
        return barra;

        
    }

    private void prepareActions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        ticTacButton.addActionListener(e-> ticTacButtonAction());
        prepareControllerMenu();
    }

    private void prepareControllerMenu(){
        salir.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                optionExit();
            }
        });

        nuevo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                optionNew();
            }
        });
        salvar.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                saveAction();
            }
        });
        abrir.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                openAction();
            }
        });
        importar.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                importAction();
            }
        });

        exportar.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                exportAction();
            }
        });
    }

    private void optionExit(){
        System.exit(0);
    }

    private void optionNew(){
        this.setVisible(false);
        main(new String[] {});
        
    }

    private void saveAction(){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        File archivo = chooser.getSelectedFile();
        if(archivo == null){
            return;
        }
        try{
            theEcoSafari.saveAs(archivo);
        }catch(EcoSafariException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Salvar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void openAction(){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        File archivo = chooser.getSelectedFile();
        if(archivo == null){
            return;
        }
        try{
            theEcoSafari.open(archivo);
        }catch(EcoSafariException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Abrir", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void importAction(){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        File archivo = chooser.getSelectedFile();
        if(archivo == null){
            return;
        }
        try{
            theEcoSafari.import_(archivo);
        }catch(EcoSafariException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Importar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void exportAction(){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        File archivo = chooser.getSelectedFile();
        if(archivo == null){
            return;
        }
        try{
            theEcoSafari.exportAs(archivo);
        }catch(EcoSafariException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Exportar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void ticTacButtonAction() {
        theEcoSafari.ticTac();
        photo.repaint();
    }

    public EcoSafari gettheEcoSafari(){
        return theEcoSafari;
    }
    
    public static void main(String[] args) {
        EcoSafariGUI cg=new EcoSafariGUI();
        cg.setVisible(true);
    }  


    class PhotoEcoSafari extends JPanel{
        
        private EcoSafariGUI gui;
    
        public PhotoEcoSafari(EcoSafariGUI gui) {
            this.gui=gui;
            setBackground(Color.white);
            setPreferredSize(new Dimension(gui.SIDE*gui.SIZE+10, gui.SIDE*gui.SIZE+10));         
        }
    
    
        public void paintComponent(Graphics g){
            EcoSafari theEcoSafari=gui.gettheEcoSafari();
            super.paintComponent(g);
            for (int c=0;c<=theEcoSafari.getSize();c++){      
                g.drawLine(c*gui.SIDE,0,c*gui.SIDE,theEcoSafari.getSize()*gui.SIDE);
            } 
            for (int f=0;f<=theEcoSafari.getSize();f++){
                g.drawLine(0,f*gui.SIDE,theEcoSafari.getSize()*gui.SIDE,f*gui.SIDE);
            }  
            for (int c=4;c<=theEcoSafari.getSize();c++){      
                g.setColor(Color.GREEN);
                g.drawLine(c*gui.SIDE,240,c*gui.SIDE,theEcoSafari.getSize()*gui.SIDE);
            }
            for (int f=12;f<=theEcoSafari.getSize();f++){
                g.setColor(Color.GREEN);
                g.drawLine(80,f*gui.SIDE,theEcoSafari.getSize()*gui.SIDE,f*gui.SIDE);
            }    
            for (int f=0;f<theEcoSafari.getSize();f++){
                for(int c=0;c<theEcoSafari.getSize();c++){
                    g.setColor(new Color(181, 138, 99));
                    g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);   

                    if(theEcoSafari.get(f,c) instanceof Ovni){
                        paintOvni(g, f, c);
                    }
                    else if(theEcoSafari.get(f,c) instanceof Zebra){
                        paintZebra(g, f, c);
                        g.setColor(Color.red);    
                        if (((Organism)theEcoSafari.get(f,c)).getEnergy()>=50){
                            g.drawString("+",gui.SIDE*c+7,gui.SIDE*f+15);
                        } else {
                            g.drawString("~",gui.SIDE*c+7,gui.SIDE*f+17);
                        }
                    }else if(theEcoSafari.get(f,c) instanceof Lyon){
                        paintLyon(g, f, c);
                        g.setColor(Color.red);    
                        if (((Organism)theEcoSafari.get(f,c)).getEnergy()>=50){
                            g.drawString("+",gui.SIDE*c+7,gui.SIDE*f+15);
                        } else {
                            g.drawString("~",gui.SIDE*c+7,gui.SIDE*f+17);
                        }
                    }
                    
                    else if(theEcoSafari.get(f,c) instanceof Grass){
                        paintGrass(g, f, c);
                    }
                    else if (theEcoSafari.get(f,c)!=null){
                        if(theEcoSafari.getAffectedEntities()[f][c]){
                            g.setColor(Color.GRAY);
                        }
                        else{
                            g.setColor(theEcoSafari.get(f,c).getColor());
                        }
                        if (theEcoSafari.get(f,c).shape()==Entity.SQUARE){                  
                            g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);   
                        }else {
                            g.fillOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        }
                        
                        if (theEcoSafari.get(f,c).isOrganism()){
                            if(theEcoSafari.get(f,c) instanceof AngryElephant){
                                g.setColor(Color.BLACK);
                            }
                            else{
                                g.setColor(Color.red);
                            }
                            if (((Organism)theEcoSafari.get(f,c)).getEnergy()>=50){
                                g.drawString("+",gui.SIDE*c+6,gui.SIDE*f+15);
                            } else {
                                g.drawString("~",gui.SIDE*c+6,gui.SIDE*f+17);
                            }
                        }    
                    } 
                }
            }
        }
        
        private void paintOvni(Graphics g, int f, int c){
            if(theEcoSafari.getAffectedEntities()[f][c]){
                g.setColor(Color.GRAY);
            }
            else{
                g.setColor(Color.GREEN);
            }
            g.fillOval(gui.SIDE*c+5,gui.SIDE*f+1,gui.SIDE-10,gui.SIDE-10);
            
            g.setColor(Color.GRAY);
            g.fillOval(gui.SIDE*c+1,gui.SIDE*f+6,gui.SIDE-2,gui.SIDE-8);
        }
        
        private void paintZebra(Graphics g, int f, int c){
            if(theEcoSafari.getAffectedEntities()[f][c]){
                g.setColor(Color.GRAY);
                g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2); 
            }
            else{
                g.setColor(theEcoSafari.get(f,c).getColor());
                g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2); 
                g.setColor(Color.WHITE);
                g.fillRoundRect(gui.SIDE*c+5,gui.SIDE*f+1,gui.SIDE-17,gui.SIDE-2,5,0); 
                g.fillRoundRect(gui.SIDE*c+12,gui.SIDE*f+1,gui.SIDE-17,gui.SIDE-2,5,0); 
            }
        }
        
        private void paintLyon(Graphics g, int f, int c){
            if(theEcoSafari.getAffectedEntities()[f][c]){
                g.setColor(Color.GRAY);
                g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2); 
            }
            else{
                g.setColor(theEcoSafari.get(f,c).getColor());
                g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2); 
                g.setColor(Color.YELLOW);
                g.fillRoundRect(gui.SIDE*c+5,gui.SIDE*f+5,gui.SIDE-10,gui.SIDE-10,2,2); 
            }
        }
        
        private void paintGrass(Graphics g, int f, int c){
            boolean affected = theEcoSafari.getAffectedEntities()[f][c];
            if(affected){
                g.setColor(Color.GRAY);
                g.fillRoundRect(gui.SIDE*c,gui.SIDE*f+6,gui.SIDE-18,gui.SIDE-6,5,0);
                g.fillRoundRect(gui.SIDE*c+2,gui.SIDE*f+3,gui.SIDE-18,gui.SIDE-2,5,0);
                g.fillRoundRect(gui.SIDE*c+4,gui.SIDE*f+10,gui.SIDE-18,gui.SIDE-10,5,0);
                g.fillRoundRect(gui.SIDE*c+6,gui.SIDE*f+2,gui.SIDE-18,gui.SIDE-2,5,0);
                g.fillRoundRect(gui.SIDE*c+8,gui.SIDE*f+6,gui.SIDE-18,gui.SIDE-6,5,0);
                g.fillRoundRect(gui.SIDE*c+10,gui.SIDE*f+3,gui.SIDE-18,gui.SIDE-2,5,0);
                g.fillRoundRect(gui.SIDE*c+12,gui.SIDE*f+10,gui.SIDE-18,gui.SIDE-10,5,0);
                g.fillRoundRect(gui.SIDE*c+14,gui.SIDE*f+2,gui.SIDE-18,gui.SIDE-2,5,0);
                g.fillRoundRect(gui.SIDE*c+16,gui.SIDE*f+6,gui.SIDE-18,gui.SIDE-6,5,0);
                g.fillRoundRect(gui.SIDE*c+18,gui.SIDE*f+3,gui.SIDE-18,gui.SIDE-2,5,0);
           
            }
            else{
                g.setColor(new Color(0,100,0));
                g.fillRoundRect(gui.SIDE*c,gui.SIDE*f+6,gui.SIDE-18,gui.SIDE-6,5,0);    
                g.setColor(Color.GREEN);
                g.fillRoundRect(gui.SIDE*c+2,gui.SIDE*f+3,gui.SIDE-18,gui.SIDE-2,5,0);  
                g.setColor(new Color(0,100,0));
                g.fillRoundRect(gui.SIDE*c+4,gui.SIDE*f+10,gui.SIDE-18,gui.SIDE-10,5,0);      
                g.setColor(Color.GREEN);
                g.fillRoundRect(gui.SIDE*c+6,gui.SIDE*f+2,gui.SIDE-18,gui.SIDE-2,5,0);
                g.setColor(new Color(0,100,0));
                g.fillRoundRect(gui.SIDE*c+8,gui.SIDE*f+6,gui.SIDE-18,gui.SIDE-6,5,0);
                g.setColor(Color.GREEN);
                g.fillRoundRect(gui.SIDE*c+10,gui.SIDE*f+3,gui.SIDE-18,gui.SIDE-2,5,0);
                g.setColor(new Color(0,100,0));
                g.fillRoundRect(gui.SIDE*c+12,gui.SIDE*f+10,gui.SIDE-18,gui.SIDE-10,5,0);
                g.setColor(Color.GREEN);
                g.fillRoundRect(gui.SIDE*c+14,gui.SIDE*f+2,gui.SIDE-18,gui.SIDE-2,5,0);
                g.setColor(new Color(0,100,0));
                g.fillRoundRect(gui.SIDE*c+16,gui.SIDE*f+6,gui.SIDE-18,gui.SIDE-6,5,0);
                g.setColor(Color.GREEN);
                g.fillRoundRect(gui.SIDE*c+18,gui.SIDE*f+3,gui.SIDE-18,gui.SIDE-2,5,0);
            }
            
        }
    }
}
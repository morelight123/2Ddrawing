/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ddrawing;

/**
 *
 * @author KaixuanM
 */
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import javax.swing.JColorChooser;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.abs;
import static java.lang.Math.min;

public class M extends JFrame {


    private Color color1 = Color.BLACK;
    private Color color2 = Color.WHITE;
    private JButton undo;
    private JButton clear;
    private JButton fcolor;
    private JButton scolor;
    private JCheckBox filled;
    private JCheckBox useGradient;
    private JCheckBox Dashed;
    private JComboBox<String> ComboBox;
    private JLabel Lwidth;
    private JLabel Ldash;
    private JLabel statusBar;
    private JLabel lShape;
    private JTextField TF_LW;
    private JTextField TF_DL;
    private int ox;
    private int oy;
    private int nx;
    private int ny;
    List<Shape> shapes = new ArrayList<>();
    
    private static final String[] names
            = {"line", "oval", "rectangle"};
    
    public M() {
        super("2D Drawing");
        setLayout(new BorderLayout());
       
        DrawPanel drawPanel = new DrawPanel();
        
        DrawPanel3 panel3 = new DrawPanel3();
        add(BorderLayout.NORTH, panel3);
        add(BorderLayout.CENTER, drawPanel);
        statusBar = new JLabel("Out of Panel");
        add(BorderLayout.SOUTH, statusBar);
      
         scolor.setVisible(false);
         Ldash.setVisible(false);    
         TF_DL.setVisible(false);
         
         //add all the handlers 
        ButtonHandler btn_handler = new ButtonHandler();  
        MouseHandler ms_handler = new MouseHandler();
        ComboBoxHandler cb_handler = new ComboBoxHandler();
        CheckBoxHandler chk_handler = new CheckBoxHandler();
        undo.addActionListener(btn_handler);
        clear.addActionListener(btn_handler);
        fcolor.addActionListener(btn_handler);
        scolor.addActionListener(btn_handler);
        TF_LW.addMouseListener(ms_handler);
        TF_DL.addMouseListener(ms_handler);
        ComboBox.addActionListener(cb_handler);
        filled.addActionListener(chk_handler);
        useGradient.addActionListener(chk_handler);
        Dashed.addActionListener(chk_handler);
       
    }
    
    public class DrawPanel1 extends JPanel{
        public DrawPanel1() {
            super();
            setLayout(new FlowLayout());
            undo=new JButton("undo");
            add(undo);
            clear=new JButton("clear");
            add(clear);
            lShape=new JLabel ("shape:");
            add(lShape);
            ComboBox=new JComboBox(names);
            add(ComboBox);
            filled=new JCheckBox("Filled");
        }
    }
    public class DrawPanel2 extends JPanel{
        public DrawPanel2() {
            super();
            setLayout(new FlowLayout());
            useGradient=new JCheckBox("Use Gradient");
            add(useGradient);
            fcolor=new JButton("1st Color");
            add(fcolor);
            scolor=new JButton("2nd Color");
            add(scolor);
            Lwidth=new JLabel("Line Width:");
            add(Lwidth);
            TF_LW=new JTextField("", 1);
            add(TF_LW);
            Ldash=new JLabel("Dash Length");
            add(Ldash);
            TF_DL=new JTextField("", 1);
            add(TF_DL);
            Dashed=new JCheckBox("Dashed");
            add(Dashed);
            
            
        }
    }
    public class DrawPanel3 extends JPanel {
        public DrawPanel3() {
            setLayout(new BorderLayout());
            DrawPanel1 panel1=new DrawPanel1();
            DrawPanel2 panel2=new DrawPanel2();
            add(BorderLayout.NORTH, panel1);
            add(BorderLayout.SOUTH, panel2);
        }
    }
    
    public class DrawPanel extends JPanel {
        public DrawPanel() {
            MouseHandler pms_handler=new MouseHandler();
            addMouseMotionListener(pms_handler);
            addMouseListener(pms_handler);
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            repaint();
            setLayout(new FlowLayout());
            this.setBackground(Color.white);
            //cast g to 2D
            Graphics2D g2d = (Graphics2D) g;
            for (int i = 0; i < shapes.size(); i++) {
                shapes.get(i).draw(g);
            }
        }
    }
    public abstract class Shape {
        private float newx;
        private float newy;
        private float oldx;
        private float oldy;
        private float dashedL;
        private float unfillwidth;
        private Color firstcolor;
        private Color secondcolor;
        public float getNewx() {
            return newx;}
        public void setNewx(float x) {
            newx = x;}
        public float getNewy() {
            return newy;}
        public void setNewy(float y) {
           newy = y;}
        public float getOldx() {
            return oldx;}
        public void setOldx(float x) {
            oldx = x;}
        public float getOldy() {
            return oldy;}
        public void setOldy(float y) {
            oldy = y;}
        public Color getColor1() {
            return firstcolor;}
        public void setColor1(Color color) {
            firstcolor = color;}
        public Color getColor2() {
            return secondcolor;}
        public void setColor2(Color color) {
            secondcolor = color;}
        public float getDashedL() {
            return dashedL;}
        public void setDashedL(float dash) {
            dashedL = dash;}
        public float getUnfillwidth() {
            return unfillwidth;}
        public void setUnfillwidth(float width) {
            unfillwidth = width;}
        public abstract void draw(Graphics g);
    }
    public class line extends Shape {
        public line(float oldx, float oldy, Color color1, Color color2, float dashedL, float newx, float newy, float width) {
            this.setNewx(newx);
            this.setOldx(oldx);
            this.setNewy(newy);
            this.setOldy(oldy); 
            this.setColor1(color1);
            this.setColor2(color2);  
            this.setUnfillwidth(width);
            this.setDashedL(dashedL);
        }
        @Override
        public void draw(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            if (this.getColor2() != Color.WHITE || this.getColor1() != Color.BLACK) {
                    g2d.setPaint(new GradientPaint(this.getOldx() / 4, 5, this.getColor1(), this.getOldx(), 20, this.getColor2(), true));
            } 
            if (this.getDashedL()== 0) {
                float[] dashes = {this.getDashedL()};
                g2d.setStroke(new BasicStroke(this.getUnfillwidth()));
                g2d.draw(new Line2D.Float(this.getOldx(), this.getOldy(), this.getNewx() , this.getNewy() ));
            } 
            else {
                float[] dashes = {this.getDashedL()};
                g2d.setStroke(new BasicStroke(this.getUnfillwidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10,dashes,0));
                g2d.draw(new Line2D.Float(this.getOldx(), this.getOldy(), this.getNewx() , this.getNewy() ));
                }
            }
    }
        public class oval extends Shape {

            public oval(float oldx, float oldy, Color color1, Color color2, float dashedL, float newx, float newy, float width) {
                this.setNewx(newx);
                this.setOldx(oldx);
                this.setNewy(newy);
                this.setOldy(oldy); 
                this.setColor1(color1);
                this.setColor2(color2);  
                this.setUnfillwidth(width);
                this.setDashedL(dashedL);
            }
            @Override
            public void draw(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                if (this.getColor2() != Color.WHITE || this.getColor1() != Color.BLACK) {
                    g2d.setPaint(new GradientPaint(this.getOldx() / 4, 5, this.getColor1(), this.getOldx(), 20, this.getColor2(), true));
                }
                if (this.getUnfillwidth() != 0) {
                    if(this.getDashedL()!=0)
                    {
                    float[] dashes = {this.getDashedL()};
                    g2d.setStroke(new BasicStroke(this.getUnfillwidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashes, 0));
                    }
                    else {
                        g2d.setStroke(new BasicStroke(this.getUnfillwidth()));}
                    g2d.draw(new Arc2D.Float(min(this.getOldx(),this.getNewx()), min(this.getOldy(),
                        this.getNewy()), abs(this.getNewx() - this.getOldx()), abs(this.getNewy() - this.getOldy()),0,360,Arc2D.OPEN));
                } 
                else {
                    g2d.fill(new Arc2D.Float(min(this.getOldx(),this.getNewx()), min(this.getOldy(),
                        this.getNewy()), abs(this.getNewx() - this.getOldx()), abs(this.getNewy() - this.getOldy()),0,360,Arc2D.OPEN));
                } 
            }
        }
    
    public class rectangle extends Shape {

            public rectangle(float oldx, float oldy, Color color1, Color color2, float dashedL, float newx, float newy, float width) {
                this.setNewx(newx);
                this.setOldx(oldx);
                this.setNewy(newy);
                this.setOldy(oldy); 
                this.setColor1(color1);
                this.setColor2(color2);  
                this.setUnfillwidth(width);
                this.setDashedL(dashedL);
            }

        @Override
            public void draw(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                if (this.getColor2() != Color.white|| this.getColor1() != Color.BLACK) {
                    g2d.setPaint(new GradientPaint(this.getOldx() / 4, 5, this.getColor1(), this.getOldx(), 20, this.getColor2(), true));
                } 
                else 
                if (this.getUnfillwidth() != 0) {
                    if(this.getDashedL()!=0)
                    {
                    float[] dashes = {this.getDashedL()};
                    g2d.setStroke(new BasicStroke(this.getUnfillwidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 5, dashes, 1));
                    }
                        else {
                    g2d.setStroke(new BasicStroke(this.getUnfillwidth()));}
                    g2d.draw(new Rectangle2D.Float(min(this.getOldx(),this.getNewx()), min(this.getOldy(),
                            this.getNewy()), abs(this.getNewx() - this.getOldx()), abs(this.getNewy() - this.getOldy())));
                } 
                else {
                    g2d.fill(new Rectangle2D.Float(min(this.getOldx(),this.getNewx()), min(this.getOldy(),
                            this.getNewy()), abs(this.getNewx() - this.getOldx()), abs(this.getNewy() - this.getOldy())));
                }
            }
            }
        
    
    private class MouseHandler implements MouseListener, MouseMotionListener {
        int temp1=0;
        int temp2=0;
        
        @Override
        public void mousePressed(MouseEvent me) {
           
            try
             {

            if (me.getSource() == TF_DL) {
                TF_DL.setText("");
            } else if (me.getSource() == TF_LW) {
                TF_LW.setText("");
            }else{
                ox = me.getPoint().x;
                oy = me.getPoint().y;

                temp1 = 0;
                temp2 = 0;
                if (Dashed.isSelected()) {
                    temp1 = Integer.parseInt(TF_DL.getText());
                }
                if (!filled.isSelected() ||  ComboBox.getSelectedItem() == "line") {
                    temp2 = Integer.parseInt(TF_LW.getText());
                }
                if ( ComboBox.getSelectedItem() == "line") {
                    shapes.add(new line(me.getPoint().x, me.getPoint().y, color1, color2, temp1, me.getPoint().x, me.getPoint().y, temp2));
                } 
                else if ( ComboBox.getSelectedItem() == "oval") {
                    shapes.add(new oval(ox, oy, color1, color2, temp1, ox, oy, temp2));
                } 
                else if ( ComboBox.getSelectedItem() == "rectangle") {
                    shapes.add(new rectangle(ox, oy, color1, color2, temp1, ox, oy, temp2));
                }
            }}
            catch(Exception e)
             {
                 String string="";
           
            string=String.format("enter input for line width or dash length");
            JOptionPane.showMessageDialog(null, string);
             }

        }
        @Override
        public void mouseDragged(MouseEvent me) {
           
            statusBar.setText("(" + me.getPoint().x + ", " + me.getPoint().y + ")");
            nx = me.getPoint().x;
            ny = me.getPoint().y;
            temp1 = 0;
            temp2 = 0;
            if (Dashed.isSelected()) {
                temp1 = Integer.parseInt(TF_DL.getText());
            }
            if (!filled.isSelected() ||  ComboBox.getSelectedItem() == "line") {
                temp2 = Integer.parseInt(TF_LW.getText());
            }
            if ( ComboBox.getSelectedItem() == "line") {
                shapes.set(shapes.size() - 1, new line(ox, oy, color1, color2, temp1, nx, ny, temp2));
            } 
            else if ( ComboBox.getSelectedItem() == "oval") {
                shapes.set(shapes.size() - 1, new oval(ox, oy, color1, color2, temp1, nx, ny, temp2));
            } 
            else if ( ComboBox.getSelectedItem() == "rectangle") {
                shapes.set(shapes.size() - 1, new rectangle(ox, oy, color1, color2, temp1, nx, ny, temp2));
            }
        }
        @Override
        public void mouseMoved(MouseEvent me) {
          
            {
                     statusBar.setText("(" + me.getPoint().x + ", " + me.getPoint().y + ")");
              
            }

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == undo) {
                shapes.remove(shapes.size()-1);
            } 
            else if (event.getSource() == clear) {
                shapes.clear();
            } 
            else if (event.getSource() == fcolor) {
                color1 = JColorChooser.showDialog(M.this, "choose color", color1);
               
                
            } else if (event.getSource() == scolor) {
                color2 = JColorChooser.showDialog(M.this, "choose color", color2);
               
                
            }
        }
    }
    private class ComboBoxHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if ( ComboBox.getSelectedItem() == "line") {
              
                TF_LW.setVisible(true);
                Lwidth.setVisible(true);
            } else if ( ComboBox.getSelectedItem() == "oval") {
             
                filled.setVisible(true);
                TF_LW.setVisible(!filled.isSelected());
                Lwidth.setVisible(!filled.isSelected());
            } else if ( ComboBox.getSelectedItem() == "rectangle") {
              
                filled.setVisible(true);
                TF_LW.setVisible(!filled.isSelected());
                Lwidth.setVisible(!filled.isSelected());
            }

        }

    }
    private class CheckBoxHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fcolor.setVisible(useGradient.isSelected());  
            if ( ComboBox.getSelectedItem() == "line") {
                
                if(Dashed.isSelected()) {
                TF_DL.setVisible(true);
                Ldash.setVisible(true);
                }
                else {TF_DL.setVisible(false);
                Ldash.setVisible(false);}
            }
            else {
                if(Dashed.isSelected()&&!filled.isSelected())
                {
                TF_LW.setVisible(!filled.isSelected());
               Lwidth.setVisible(!filled.isSelected());    
                TF_DL.setVisible(true);
                Ldash.setVisible(true);
                
            }
                else if(filled.isSelected())
                {
                Lwidth.setVisible(false);
                TF_LW.setVisible(false);
                Dashed.setVisible(false);
                Dashed.setSelected(false);
                TF_DL.setVisible(false);
                Ldash.setVisible(false);
                                 
                }
                else if(!filled.isSelected())
                {
                TF_DL.setVisible(Dashed.isSelected());
                Ldash.setVisible(Dashed.isSelected());
                TF_LW.setVisible(true);
                Lwidth.setVisible(true);
                Dashed.setVisible(true);
                Dashed.setSelected(false);}
            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        M test;
        test = new M();
        test.setVisible(true);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setSize(600, 500);

    }
    
}







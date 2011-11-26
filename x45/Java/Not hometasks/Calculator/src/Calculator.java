import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: denison
 * Date: 22.10.2008
 * Time: 18:11:15
 * To change this template use File | Settings | File Templates.
 */
public class Calculator {
    private JButton but_off;
    private JButton but_min;
    private JButton but_del;
    private JButton but_hello;
    private JButton but_c;
    private JButton but_umn;
    private JButton but_4;
    private JButton but_calc;
    private JButton but_plus;
    private JButton but_9;
    private JButton but_6;
    private JButton but_3;
    private JButton but_pi;
    private JButton but_8;
    private JButton but_5;
    private JButton but_2;
    private JButton but_point;
    private JButton but_7;
    private JButton but_1;
    private JButton but_nul;
    private JButton but_mem;
    private JButton but_mr;
    private JButton but_clear_mem;
    private JButton but_sin;
    private JButton but_cos;
    private JButton but_tan;
    private JButton but_lg;
    private JButton but_ln;
    private JButton but_sqrt;
    private JButton but_abs;
    private JButton but_sk_1;
    private JButton but_sk_2;
    private JTextField text_1;
    private JPanel main_panel;
    private JTextField text_er;
    private JButton but_fak;
    private JButton but_step;
    private JButton but_kv;

    private static final double Pi = Math.PI;
    private double cur1;
    private double cur2;
    private double cur_ud; // dlya poryadka umn/del
    private double rez;
    private double memory;
    private boolean plus=false , min=false, del=false, umn=false,
            sin = false, cos = false, tan = false, lg = false,
            ln = false, abs = false, fak = false, kv =false, step = false, sqrt = false;
    private double a;

    private double Calc(double cur1, double cur2){
        double i = 1;
        int j;
        if(plus){
            i = cur1 + cur2;
            return i;
        }
        if(min){
            i = cur1 - cur2;
            return i;
        }
        if(del){
            if(cur2 != 0) i = cur1 / cur2;
            else i = 0;
            return i;
        }
        if(umn){
            i = cur1 * cur2;
            return i;
        }
        if(sin) return Math.sin(cur1);
        if(cos) return Math.cos(cur1);
        if(tan) return Math.tan(cur1);
        if(lg)  return Math.log10(cur1);
        if(ln)  return Math.log(cur1);
        if(abs) return (-cur1);
        if(fak){               // cur1 - integer
            if(cur1 == (int)cur1){
                for(j=0;j<cur1;j++){
                    i *= cur1 - j;
                }
            }
            else i = 0;
            return i;
        }
        if(kv)  return cur1 * cur1;
        if(step){              //cur1, cur2 - integer
            if(cur1 == (int)cur1 && cur2 == (int)cur2 ){
                for(j=0;j<cur2;j++){
                    i *= cur1;
                }
            }
            else i = 0;
            return i;
        }
        if(sqrt) return Math.sqrt(cur1);
        else
            return 0;
    }

    public Calculator(){

        but_sqrt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                   cur1 = Double.parseDouble(text_1.getText());
                   sqrt = true;
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_er.setText(/*text_1.getText() + */"sqrt( " + cur1 + " )");
            }
        });
        but_kv.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                   cur1 = Double.parseDouble(text_1.getText());
                   kv = true;
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_er.setText(/*text_1.getText() + */cur1 + " ^2 ");
            }
        });
        but_fak.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                   cur1 = Double.parseDouble(text_1.getText());
                   fak = true;
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_er.setText(/*text_1.getText() + */cur1 + "!");
            }
        });
        but_abs.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!abs){
                    text_er.setText("-" + text_1.getText());
                    text_1.setText("-" + text_1.getText());
                    abs = true;
                }
                else{
                    text_er.setText( (text_1.getText()).substring(1) );
                    text_1.setText( (text_1.getText()).substring(1) );
                    abs = false;
                }
            }
        });
        but_tan.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                   cur1 = Double.parseDouble(text_1.getText());
                   tan = true;
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_er.setText(/*text_1.getText() + */"tan( " + cur1 + " )");
            }
        });
        but_cos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                   cur1 = Double.parseDouble(text_1.getText());
                   cos = true;
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_er.setText(/*text_1.getText() + */"cos( " + cur1 + " ) ");
            }
        });
        but_sin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                   cur1 = Double.parseDouble(text_1.getText());
                   sin = true;
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_er.setText(/*text_1.getText() + */"sin( " + cur1 + " ) ");
            }
        });
        but_hello.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                text_1.setText(text_1.getText() + "Hello!");
            }
        });

        but_1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "1");
                text_er.setText(text_er.getText() + "1");
            }
        });
        but_2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "2");
                text_er.setText(text_er.getText() + "2");
            }
        });
        but_3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "3");
                text_er.setText(text_er.getText() + "3");
            }
        });
        but_4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "4");
                text_er.setText(text_er.getText() + "4");
            }
        });
        but_5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "5");
                text_er.setText(text_er.getText() + "5");
            }
        });
        but_6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "6");
                text_er.setText(text_er.getText() + "6");
            }
        });
        but_7.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "7");
                text_er.setText(text_er.getText() + "7");
            }
        });
        but_8.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "8");
                text_er.setText(text_er.getText() + "8");
            }
        });
        but_9.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "9");
                text_er.setText(text_er.getText() + "9");
            }
        });
        but_nul.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + "0");
                text_er.setText(text_er.getText() + "0");
            }
        });
        but_pi.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + Pi);
                text_er.setText(text_er.getText() + Pi);
            }
        });
        but_plus.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    if(!plus && !min && !umn && !del){
                        cur1 = Double.parseDouble(text_1.getText());
                    }
                    if(plus){
                        cur1 += Double.parseDouble(text_1.getText());
                        plus = false;
                    }
                    if(min){
                        cur1 -= Double.parseDouble(text_1.getText());
                        min = false;
                    }
                    if(umn){
                        cur1 *= Double.parseDouble(text_1.getText());
                        umn = false;
                    }
                    if(del){
                        if(Calc(cur1, Double.parseDouble(text_1.getText())) == 0){
                            text_1.setText("");
                            text_er.setText("Error - divide by 0");
                            cur1 = 0;
                        }
                        else  cur1 /= Double.parseDouble(text_1.getText());
                        del = false;
                    }
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_1.setText("");
                text_er.setText(cur1 + " + ");
                plus = true;
            }
        });
        but_min.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    if(!plus && !min && !umn && !del){
                        cur1 = Double.parseDouble(text_1.getText());
                    }
                    if(plus){
                        cur1 += Double.parseDouble(text_1.getText());
                        plus = false;
                    }
                    if(min){
                        cur1 -= Double.parseDouble(text_1.getText());
                        min = false;
                    }
                    if(umn){
                        cur1 *= Double.parseDouble(text_1.getText());
                        umn = false;
                    }
                    if(del){
                        if(Calc(cur1, Double.parseDouble(text_1.getText())) == 0){
                            text_1.setText("");
                            text_er.setText("Error - divide by 0");
                            cur1 = 0;
                        }
                        else  cur1 /= Double.parseDouble(text_1.getText());
                        del = false;
                    }
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_1.setText("");
                text_er.setText(cur1 + " - ");
                min = true;
            }
        });
        but_umn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    if(!plus && !min && !umn && !del){
                        cur1 = Double.parseDouble(text_1.getText());
                    }
                    if(plus){
                        cur_ud = cur1;
                        cur1 = Double.parseDouble(text_1.getText());

                    }
                    if(min){
                        cur_ud = cur1;
                        cur1 = Double.parseDouble(text_1.getText());
                    }
                    if(umn){
                        cur1 *= Double.parseDouble(text_1.getText());
                        umn = false;
                    }
                    if(del){
                        if(Calc(cur1, Double.parseDouble(text_1.getText())) == 0){
                            text_1.setText("");
                            text_er.setText("Error - divide by 0");
                            cur1 = 0;
                        }
                        else  cur1 /= Double.parseDouble(text_1.getText());
                        del = false;
                    }
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_1.setText("");
                text_er.setText( cur1 + " * ");
                umn = true;
            }
        });
        but_step.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                        cur1 = Double.parseDouble(text_1.getText());
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_1.setText("");
                text_er.setText(text_er.getText() + " ^ ");
                step = true;
            }
        });
        but_del.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    if(!plus && !min && !umn && !del){
                        cur1 = Double.parseDouble(text_1.getText());
                    }
                    if(plus){
                        cur_ud = cur1;
                        cur1 = Double.parseDouble(text_1.getText());

                    }
                    if(min){
                        cur_ud = cur1;
                        cur1 = Double.parseDouble(text_1.getText());
                    }
                    if(umn){
                        cur1 *= Double.parseDouble(text_1.getText());
                        umn = false;
                    }
                    if(del){
                        if(Calc(cur1, Double.parseDouble(text_1.getText())) == 0){
                            text_1.setText("");
                            text_er.setText("Error - divide by 0");
                            cur1 = 0;
                        }
                        else  cur1 /= Double.parseDouble(text_1.getText());
                        del = false;
                    }
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_1.setText("");
                text_er.setText(cur1 + " / ");
                del = true;
            }
        });

        but_calc.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(umn){
                    try{
                        cur2 = Double.parseDouble(text_1.getText());
                    }
                    catch (NumberFormatException oo){
                        text_er.setText("Not a number");
                    }
                    if(plus){
                        rez = cur1 * cur2 + cur_ud;
                        cur_ud = 0;
                    }
                    if(min){
                        rez = cur_ud - cur1 * cur2;
                        cur_ud = 0;
                    }
                    if(!min && !plus) rez = Calc(cur1, cur2);
                    text_1.setText(rez + "");
                    cur1 = rez;
                    cur2 = 0;
                    //text_er.setText(rez + "");
                    umn = false;
                    plus = false;
                    min = false;
                }
                if(del){
                    try{
                        cur2 = Double.parseDouble(text_1.getText());
                    }
                    catch (NumberFormatException oo){
                        text_er.setText("Not a number");
                    }
                    if(plus){
                        rez = cur1 / cur2 + cur_ud;
                        cur_ud = 0;
                    }
                    if(min){
                        rez = cur_ud - cur1 / cur2;
                        cur_ud = 0;
                    }
                    if(!min && !plus){
                        rez = Calc(cur1, cur2);
                        if(rez != 0){
                            text_1.setText(rez + "");
                            cur1 = rez;
                            cur2 = 0;
                        }
                        else{
                            text_er.setText("Error - divide by 0");
                            text_1.setText("");
                            cur1 = 0;
                            cur2 = 0;
                        }
                    }
                    text_1.setText(rez + "");
                    cur1 = rez;
                    cur2 = 0;
                    //text_er.setText(rez + "");
                    del = false;
                    plus = false;
                    min = false;
                }
                
                if(plus || min ){
                    try{
                        cur2 = Double.parseDouble(text_1.getText());
                    }
                    catch (NumberFormatException oo){
                        text_er.setText("Not a number");
                    }
                    rez = Calc(cur1, cur2);
                    text_1.setText(rez + "");
                    cur1 = rez;
                    cur2 = 0;
                    //text_er.setText(rez + "");
                    plus = false;
                    min = false;
                }
                if(step){
                    try{
                        cur2 = Double.parseDouble(text_1.getText());
                    }
                    catch (NumberFormatException oo){
                        text_er.setText("Not a number");
                    }
                    rez = Calc(cur1, cur2);
                    text_1.setText(rez + "");
                    cur1 = rez;
                    cur2 = 0;
                    step = false;
                }

                if(sin || cos || tan || lg || ln || abs || fak || kv || sqrt){
                    rez = Calc(cur1, cur2);
                    text_1.setText(rez + "");
                    cur1 = rez;
                    cur2 = 0;
                    sin = false;
                    cos = false;
                    tan = false;
                    lg = false;
                    ln = false;
                    abs = false;
                    fak = false;
                    kv =false;
                    sqrt = false;

                }
            }
        });
        but_mem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    memory = Double.parseDouble(text_1.getText());
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
            }
        });
        but_c.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText("");
            }
        });
        but_clear_mem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                memory = 0;
            }
        });
        but_mr.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(memory + "");
                text_er.setText(text_1.getText() + " MR ");
            }
        });

        but_off.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText("");
                text_er.setText("");
                cur1 = 0;
                cur2 = 0;
                rez = 0;
                plus = false;
                min = false;
                umn = false;
                del = false;
            }
        });
        but_point.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                text_1.setText(text_1.getText() + ".");
            }
        });
        but_ln.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                   cur1 = Double.parseDouble(text_1.getText());
                   ln = true; 
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_er.setText(text_1.getText() + "ln( " + cur1 + " ) ");
            }
        });
        but_lg.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                   cur1 = Double.parseDouble(text_1.getText());
                   lg = true;
                }
                catch (NumberFormatException oo){
                    text_er.setText("Not a number");
                }
                text_er.setText(text_1.getText() + "lg( " + cur1 + " ) ");
            }
        });
    }

    public JPanel getForm(){
        return main_panel;
    }

}

/* Zagotovki (na budushee)
                    if(kv){
                        cur1 = Calc(Double.parseDouble(text_1.getText()), 0);
                        kv = false;
                    }
                    if(step){
                        if( Calc(Double.parseDouble(text_1.getText()), 0) == 0){
                            text_er.setText("Not integer (step)");
                            text_1 .setText("");
                            cur1 = 0;
                        }
                        else cur1 = Calc(cur1, Double.parseDouble(text_1.getText()));
                        step = false;
                    }
                    if(fak){
                        if( Calc(Double.parseDouble(text_1.getText()), 0) == 0){
                            text_er.setText("Not integer (fak)");
                            text_1 .setText("");
                            cur1 = 0;
                        }
                        else cur1 = Calc(Double.parseDouble(text_1.getText()), 0);
                        fak = false;
                    }
                    if(sqrt){
                        cur1 = Calc(Double.parseDouble(text_1.getText()), 0);
                        sqrt = false;
                    }
                    if(tan){
                        cur1 = Calc(Double.parseDouble(text_1.getText()), 0);
                        tan = false;
                    }
                    if(cos){
                        cur1 = Calc(Double.parseDouble(text_1.getText()), 0);
                        cos = false;
                    }
                    if(sin){
                        cur1 = Calc(Double.parseDouble(text_1.getText()),0);
                        sin = false;
                    }
*/
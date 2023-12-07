import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlContrastIJTheme;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;


public class TextArea {
    
    static JTextArea area(JPanel panel, JTextArea[][] areas) {
        
        JTextArea area = new JTextArea();
        area.setFont(new Font(null, Font.BOLD, 26));
        area.setFocusable(true);
        area.setCaretColor(new Color(0x010E1A));
        area.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        area.setForeground(Color.white);
        area.setSelectionColor(new Color(0x010E1A));
        //Making the areas only accept one character
        area.addKeyListener(new KeyListener() {
            int row = 0;
            int col = 0;
            final char[] keys = keyDisabler(area.getInputMap());
            @Override
            public void keyTyped(KeyEvent e) {
                    area.setText(String.valueOf((char) e.getKeyCode()).trim());
            }
            
            @Override
            /*
            Adding the functionality to move the focus from each area to its neighbor
            by pressing the arrow keys
            */
            public void keyPressed(KeyEvent e) {
                //Find indexes of the FocusOwner
                for(int i = 0; i < areas.length; i++){
                    for (int j = 0; j < areas.length; j++) {
                        if(areas[i][j].isFocusOwner()) {
                            row = i;
                            col = j;
                        }
                    }
                }
                //Movements
                if(e.getKeyCode() == 38) {
                    if(row > 0 && row < areas.length) {
                        areas[--row][col].grabFocus();
                    }
                } //Up
                if(e.getKeyCode() == 40) {
                    if(row >= 0 && row < areas.length - 1) {
                        areas[++row][col].grabFocus();
                    }
                } //Down
                if(e.getKeyCode() == 39) {
                    if(col >= 0 && col < areas.length - 1) {
                        areas[row][++col].grabFocus();
                    }
                } //Right
                if(e.getKeyCode() == 37) {
                    if(col > 0 && col < areas.length) {
                        areas[row][--col].grabFocus();
                    }
                } //Left
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
            
            }
        });
        //Setting focus properties
        area.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                area.setBackground(new Color(0x063A67));
                area.setCaretColor(new Color(0x063A67));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                area.setBackground(new Color(0x010E1A));
                area.setCaretColor(new Color(0x010E1A));
            }
        });
        //Disabling the unnecessary keys that might create problems
        keyDisabler(area.getInputMap());
        panel.add(area);
        return area;
    }
    private static char[] keyDisabler(InputMap inputMap) {
        //Disables all keys except the numbers from 1 to 9 (49 - 57 in ascii)
        char[] keys = new char[95];
        for (int i = 32; i < 127; i++) {
            if (i == 49) {
                i = 58;
            }
            keys[i - 32] = (char) i;
        }
        for (char disableThis : keys) {
            inputMap.put(KeyStroke.getKeyStroke(disableThis), "none");
        }
        return keys;
    }
    static JButton number(JPanel panel) {
        JButton number = new JButton();
        number.setFont(new Font(null, Font.BOLD, 26));
        number.setBackground(new Color(0x010E1A));
        number.setForeground(Color.white);
        number.setFocusable(false);
        panel.add(number);
        return number;
    }
}

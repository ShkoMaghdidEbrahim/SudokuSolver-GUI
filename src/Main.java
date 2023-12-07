import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlContrastIJTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        FlatNightOwlContrastIJTheme.install(new FlatNightOwlContrastIJTheme());
        UIManager.put("Button.arc", 20);
    
        ImageIcon icon = new ImageIcon("src/LOGO.ico");
        
        JFrame frame = frame();
        JPanel panel = panel(frame);
        JButton youSolveIt = youSolveIt(panel);
        JButton solver = solver(panel);
        
        youSolveIt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameX = frame.getX();
                frameY = frame.getY();
                new youSolveIt();
                frame.dispose();
            }
        });
        solver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameX = frame.getX();
                frameY = frame.getY();
                new solver();
                frame.dispose();
            }
        });
        
        frame.setVisible(true);
    }
    
    public static int frameX = 490;
    public static int frameY = 100;
    
    static JPanel panel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        return panel;
    }
    static JFrame frame() {
        ImageIcon icon = new ImageIcon("src/LOGO.ico");
        JFrame frame = new JFrame("Sudoku!");
        frame.setBackground(new Color(0x2C2C2C));
        frame.setSize(385, 500);
        frame.setLocation(frameX, frameY);
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        return frame;
    }
    static JButton youSolveIt(JPanel panel) {
        ImageIcon youSolve = new ImageIcon("images/yousolve.png");
        ImageIcon youSolveAnimated = new ImageIcon("images/yousolveAnimated.gif");
        
        JButton solve = new JButton(youSolve);
        solve.setBounds(10, 10, 350, 210);
        solve.setText("You Solve It!");
        solve.setToolTipText("You're given a sudoku board \nand you have to try to fill it in \nwith the correct numbers");
        solve.setFocusable(false);
        solve.setRolloverIcon(youSolveAnimated);
        solve.setFont(new Font("Segoe UI", Font.BOLD, 30));
        solve.setForeground(Color.white);
        panel.add(solve);
        return solve;
    }
    static JButton solver(JPanel panel) {
        ImageIcon solver = new ImageIcon("images/solver.png");
        ImageIcon solverAnimated = new ImageIcon("images/solverAnimated.gif");
        
        JButton solve = new JButton(solver);
        solve.setBounds(10, 240, 350, 210);
        solve.setText("Solver!");
        solve.setToolTipText("This time you give it a board \nand it will try to solve it ;)");
        solve.setFocusable(false);
        solve.setRolloverIcon(solverAnimated);
        solve.setFont(new Font("Segoe UI", Font.BOLD, 30));
        solve.setForeground(Color.white);
        panel.add(solve);
        return solve;
    }
    
    
}


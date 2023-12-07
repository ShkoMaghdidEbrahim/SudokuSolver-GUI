import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlContrastIJTheme;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class solver {
    public solver() {
        FlatNightOwlContrastIJTheme.install(new FlatNightOwlContrastIJTheme());
        UIManager.put("Button.arc", 999);
        
        int[][] sudoku = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}};
        
        JFrame frame = frame();
        JPanel panel = panel(frame);
        //Border methods each describing itself
        MatteBorder RightBorder = RightBorder();
        MatteBorder LeftBorder = LeftBorder();
        MatteBorder TopBorder = TopBorder();
        MatteBorder BottomBorder = BottomBorder();
        LineBorder normalBorder = normalBorder();
        
        JButton[] buttons = new JButton[9];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = TextArea.number(panel);
            buttons[i].setText(String.valueOf((char) i + 1));
        }
        /*
        Initializing the Array that will hold all the TextAreas
        which makes everything much easier, rather than writing 81 lines of code to:
        initialize each area, position it and give it a border
        which in total will need 243 lines
        */
        JTextArea[][] areas = new JTextArea[9][9];
        /*
        Initialize each TextArea and position it accordingly
        row and col are used to track the rows and columns
        and the positions start from 5 to 325 increasing by 40 each time
        which will make a checkered board of a size of 9x9
        */
        int row = 0;
        for (int l = 5; l <= 325; l += 40) {
            int col = 0;
            for (int k = 5; k <= 325; k += 40) {
                areas[row][col] = TextArea.area(panel, areas);
                areas[row][col].setBounds(k, l, 40, 40);
                int finalRow = row;
                int finalCol = col;
                areas[row][col].addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }
        
                    @Override
                    public void keyPressed(KeyEvent e) {
            
                        for(int i = 49; i < 59; i++) {
                            if(e.getKeyCode() == i) {
                                areas[finalRow][finalCol].setForeground(new Color(0x00FF2A));
                                areas[finalRow][finalCol].setSelectedTextColor(new Color(0x00FD2A));
                            }
                        }
                    }
        
                    @Override
                    public void keyReleased(KeyEvent e) {
            
                    }
                });
                if(l == 325) {
                    buttons[col].setBounds(k, l + 45, 40, 40); //Set position of the buttons right below the board
                }
                
                col++;
            }
            row++;
        }
        //set the color of your input as green
        for (int i = 49; i < 58; i++) {
            int finalI = i;
            buttons[i - 49].addActionListener(e -> {
                for (JTextArea[] area : areas) {
                    for (int k = 0; k < areas[0].length; k++) {
                        if (area[k].isFocusOwner()) {
                            area[k].setText(String.valueOf((char) finalI));
                            area[k].setForeground(new Color(0x00FF2A));
                        }
                    }
                }
            });
        }
        /*
        Giving each area a border according to their location
        some must have a thicker side to emulate the blocks
        */
        for (int i = 0; i < areas.length; i++) {
            for (int j = 0; j < areas[i].length; j++) {
                //Areas that have their right side thicker
                if (i != 2 && i != 6 && j == 2) {
                    areas[i][j].setBorder(new CompoundBorder(RightBorder, new EmptyBorder(0, 10, 0, 0)));
                }
                //Areas that have their left side thicker
                else if (i != 2 && i != 6 && j == 6) {
                    areas[i][j].setBorder(new CompoundBorder(LeftBorder, new EmptyBorder(0, 10, 0, 0)));
                }
                //Areas that have their bottom side thicker
                else if (i == 2 && j != 2 && j != 6) {
                    areas[i][j].setBorder(new CompoundBorder(BottomBorder, new EmptyBorder(0, 10, 0, 0)));
                }
                //Areas that have their top side thicker
                else if (i == 6 && j != 2 && j != 6) {
                    areas[i][j].setBorder(new CompoundBorder(TopBorder, new EmptyBorder(0, 10, 0, 0)));
                }
                //Special areas that must have two thick sides
                else if (i == 2 && j == 2) {
                    areas[i][j].setBorder(new CompoundBorder(new MatteBorder(1, 1, 4, 4, Color.WHITE), new EmptyBorder(0, 10, 0, 0)));
                }
                else if (i == 2) {
                    areas[i][j].setBorder(new CompoundBorder(new MatteBorder(1, 4, 4, 1, Color.WHITE), new EmptyBorder(0, 10, 0, 0)));
                }
                else if (i == 6 && j == 2) {
                    areas[i][j].setBorder(new CompoundBorder(new MatteBorder(4, 1, 1, 4, Color.WHITE), new EmptyBorder(0, 10, 0, 0)));
                }
                else if (i == 6) {
                    areas[i][j].setBorder(new CompoundBorder(new MatteBorder(4, 4, 1, 1, Color.WHITE), new EmptyBorder(0, 10, 0, 0)));
                }
                else {
                    areas[i][j].setBorder(new CompoundBorder(normalBorder, new EmptyBorder(0, 10, 0, 0)));
                }
                
            }
        }
        
        JButton solve = solve(panel);
        JButton goBack = goBack(panel);
        
        solve.addActionListener(e -> {
            /*
            Get all user inputs and put them
            in their respective index in the array
            */
            for (int i = 0; i < areas.length; i++) {
                for (int j = 0; j < areas[0].length; j++) {
                    if (areas[i][j].getText().length() > 0) {
                        sudoku[i][j] = Integer.parseInt(String.valueOf(areas[i][j].getText().charAt(0)));
                    }
                    if(sudoku[i][j] == 0) {
                        areas[i][j].setForeground(Color.white);
                    }
                }
            }
            
            
            //if it is possible to solve:
            if (checker(areas, sudoku)) {
                solve(sudoku, 0, 0); //solve it
                /*
                Put back the results in the respected area
                but with a delay of 75 milliseconds for each row
                */
                Runnable timedExecution = new Runnable() {
                    int k = 0;
                    public void run() {
                        for (int l = 0; l < areas[0].length; l++) {
                            areas[k][l].setText(String.valueOf(sudoku[k][l]));
                        }
                        k++;
                    }
                };
                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(timedExecution, 0, 75, TimeUnit.MILLISECONDS);
            }
            //if not:
            else {
                JOptionPane.showMessageDialog(null, "Theres no possible solution, \ninputs are intersecting");
                Main.frameX = frame.getX();
                Main.frameY = frame.getY();
                frame.dispose();
                new solver();
            }
            
            solve.setText("OK");
            solve.setFocusable(true);
            solve.requestFocus();
            solve.removeActionListener(solve.getActionListeners()[0]);
            solve.addActionListener(e1 -> {
                Main.frameX = frame.getX();
                Main.frameY = frame.getY();
                frame.dispose();
                new solver();
            });
            
        });
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frameX = frame.getX();
                Main.frameY = frame.getY();
                frame.dispose();
                Main.main(new String[]{});
            
            }
        });
        
        frame.setVisible(true);
    }
    
    static boolean checker(JTextArea[][] areas, int[][] sudoku) {
        //Check the rows for any duplicates
        for (int l = 0; l < areas[0].length; l++) {
            for (int i = 0; i < areas[l].length; i++) {
                for (int j = 0; j < areas.length; j++) {
                    if (sudoku[l][i] == sudoku[l][j] && i != j && sudoku[l][i] != 0) {
                        return false;
                    }
                }
            }
        }
        //Check the columns for any duplicates
        for (int l = 0; l < areas[0].length; l++) {
            for (int i = 0; i < areas[l].length; i++) {
                for (int j = 0; j < areas.length; j++) {
                    if (sudoku[i][l] == sudoku[j][l] && i != j && sudoku[i][l] != 0) {
                        return false;
                    }
                }
            }
        }
        //Check the blocks for any duplicates
        for (int l = 0; l < 3; l++) {
            for (int k = 0; k < 3; k++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        //Check the left side blocks
                        if (sudoku[l][k] == sudoku[i][j] && l != i && k != j && sudoku[l][k] != 0) {
                            return false;
                        } //Block 0,0
                        if (sudoku[l + 3][k] == sudoku[i + 3][j] && l != i && k != j && sudoku[l + 3][k] != 0) {
                            return false;
                        }//Block 1,0
                        if (sudoku[l + 6][k] == sudoku[i + 6][j] && l != i && k != j && sudoku[l + 6][k] != 0) {
                            return false;
                        }//Block 2,0
                        //Middle row of blocks
                        if (sudoku[l][k + 3] == sudoku[i][j + 3] && l != i && k != j && sudoku[l][k + 3] != 0) {
                            return false;
                        }//Block 0,1
                        if (sudoku[l + 3][k + 3] == sudoku[i + 3][j + 3] && l != i && k != j && sudoku[l + 3][k + 3] != 0) {
                            return false;
                        }//Block 1,1
                        if (sudoku[l + 6][k + 3] == sudoku[i + 6][j + 3] && l != i && k != j && sudoku[l + 6][k + 3] != 0) {
                            return false;
                        }//Block 2,1
                        //Right Side Blocks
                        if (sudoku[l][k + 6] == sudoku[i][j + 6] && l != i && k != j && sudoku[l][k + 6] != 0) {
                            return false;
                        }//Block 0,2
                        if (sudoku[l + 3][k + 6] == sudoku[i + 3][j + 6] && l != i && k != j && sudoku[l + 3][k + 6] != 0) {
                            return false;
                        }//Block 1,2
                        if (sudoku[l + 6][k + 6] == sudoku[i + 6][j + 6] && l != i && k != j && sudoku[l + 6][k + 6] != 0) {
                            return false;
                        }//Block 2,2
                    }
                }
            }
        }
        
        return true;
    }
    static boolean isThisNumberSafe(int[][] sudoku, int col, int row, int num) {
        
        for (int x = 0; x <= 8; x++) {
            if (sudoku[row][x] == num) {
                return false;
            }
        }
        for (int x = 0; x <= 8; x++) {
            if (sudoku[x][col] == num) {
                return false;
            }
        }
        
        int startRow = row - (row % 3), startColumn = col - (col % 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (sudoku[i + startRow][j + startColumn] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    static boolean solve(int[][] sudoku, int row, int col) {
        
        if (row == 9 - 1 && col == 9) {
            return true;
        }
        if (col == 9) {
            row++;
            col = 0;
        }
        if (sudoku[row][col] != 0) {
            return solve(sudoku, row, col + 1);
        }
        for (int num = 1; num < 10; num++) {
            if (isThisNumberSafe(sudoku, col, row, num)) {
                sudoku[row][col] = num;
                if (solve(sudoku, row, col + 1)) {
                    return true;
                }
            }
            sudoku[row][col] = 0;
        }
        return false;
    }
    
    static JFrame frame() {
        ImageIcon icon = new ImageIcon("src/LOGO.ico");
        JFrame frame = new JFrame("Sudoku Solver");
        frame.setBackground(new Color(0x2C2C2C));
        frame.setSize(385, 500);
        frame.setLocation(Main.frameX, Main.frameY);
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        return frame;
    }
    static JPanel panel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        return panel;
    }
    static JButton solve(JPanel panel) {
        JButton solve = new JButton();
        solve.setBounds(110, 415, 150, 40);
        solve.setText("SOLVE!");
        solve.setFocusable(false);
        solve.setFont(new Font(null, Font.BOLD, 26));
        solve.setForeground(Color.white);
        panel.add(solve);
        return solve;
    }
    static JButton goBack(JPanel panel) {
        ImageIcon back = new ImageIcon("images/back.png");
        ImageIcon backAnimated = new ImageIcon("images/backAnimated.gif");
        JButton goBack = new JButton(back);
        goBack.setBounds(5, 415, 40, 40);
        goBack.setRolloverIcon(backAnimated);
        goBack.setFocusable(false);
        panel.add(goBack);
    
        return goBack;
    }
    
    public static MatteBorder TopBorder() {
        return new MatteBorder(4, 1, 1, 1, Color.WHITE);
    }
    public static MatteBorder BottomBorder() {
        return new MatteBorder(1, 1, 4, 1, Color.WHITE);
    }
    public static MatteBorder LeftBorder() {
        return new MatteBorder(1, 4, 1, 1, Color.WHITE);
    }
    public static MatteBorder RightBorder() {
        return new MatteBorder(1, 1, 1, 4, Color.WHITE);
    }
    public static LineBorder normalBorder() {
        return new LineBorder(Color.WHITE, 1);
    }
}

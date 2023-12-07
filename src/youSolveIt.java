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
import java.util.Objects;
import java.util.Random;

public class youSolveIt {
    public youSolveIt() {
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
    
        File[] soundFiles = new File[2];
        soundFiles[0] = new File("sounds/typingCorrectSound.wav");
        soundFiles[1] = new File("sounds/typingErrorSound.wav");
        AudioInputStream[] inputStream = new AudioInputStream[2];
        Clip[] player = new Clip[2];
        for (int i = 0; i < soundFiles.length; i++) {
            try {
                inputStream[i] = AudioSystem.getAudioInputStream(soundFiles[i]);
            }
            catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            try {
                player[i] = AudioSystem.getClip();
                player[i].open(inputStream[i]);
            }
            catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
        
        JFrame frame = frame();
        JPanel panel = panel(frame);
        JLabel chancesLeft = chancesLeft(panel);
        JButton goBack = goBack(panel);
        //Border methods each describing itself
        MatteBorder RightBorder = RightBorder();
        MatteBorder LeftBorder = LeftBorder();
        MatteBorder TopBorder = TopBorder();
        MatteBorder BottomBorder = BottomBorder();
        LineBorder normalBorder = normalBorder();
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
                col++;
            }
            row++;
        }
        //input some random numbers into the array
        for (int i = sudoku.length - 1; i > 0; i--) {
            sudoku[i][(int) (Math.random() * 9)] = i + 1;
        }
        //solve and shuffle a nearly empty board
        Random random = new Random();
        solver(sudoku, 0, 0);
        for (JTextArea[] area : areas) {
            for (int j = 0; j < area.length; j++) {
                int blocLength = random.nextInt(0, 3);
                int blockWidth = random.nextInt(0, 3);
                int blocLength1 = random.nextInt(0, 3);
                int blockWidth1 = random.nextInt(0, 3);
                sudoku[blocLength][blockWidth] = sudoku[blocLength][blockWidth] ^ sudoku[blocLength1][blockWidth1] ^ (sudoku[blocLength1][blockWidth1] = sudoku[blocLength][blockWidth]);
                sudoku[blocLength][blockWidth + 3] = sudoku[blocLength][blockWidth + 3] ^ sudoku[blocLength1][blockWidth1 + 3] ^ (sudoku[blocLength1][blockWidth1 + 3] = sudoku[blocLength][blockWidth + 3]);
                sudoku[blocLength][blockWidth + 6] = sudoku[blocLength][blockWidth + 6] ^ sudoku[blocLength1][blockWidth1 + 6] ^ (sudoku[blocLength1][blockWidth1 + 6] = sudoku[blocLength][blockWidth + 6]);
            
                sudoku[blocLength + 3][blockWidth] = sudoku[blocLength + 3][blockWidth] ^ sudoku[blocLength1 + 3][blockWidth1] ^ (sudoku[blocLength1 + 3][blockWidth1] = sudoku[blocLength + 3][blockWidth]);
                sudoku[blocLength + 3][blockWidth + 3] = sudoku[blocLength + 3][blockWidth + 3] ^ sudoku[blocLength1 + 3][blockWidth1 + 3] ^ (sudoku[blocLength1 + 3][blockWidth1 + 3] = sudoku[blocLength + 3][blockWidth + 3]);
                sudoku[blocLength + 3][blockWidth + 6] = sudoku[blocLength + 3][blockWidth + 6] ^ sudoku[blocLength1 + 3][blockWidth1 + 6] ^ (sudoku[blocLength1 + 3][blockWidth1 + 6] = sudoku[blocLength + 3][blockWidth + 6]);
            
                sudoku[blocLength + 6][blockWidth] = sudoku[blocLength + 6][blockWidth] ^ sudoku[blocLength1 + 6][blockWidth1] ^ (sudoku[blocLength1 + 6][blockWidth1] = sudoku[blocLength + 6][blockWidth]);
                sudoku[blocLength + 6][blockWidth + 3] = sudoku[blocLength + 6][blockWidth + 3] ^ sudoku[blocLength1 + 6][blockWidth1 + 3] ^ (sudoku[blocLength1 + 6][blockWidth1 + 3] = sudoku[blocLength + 6][blockWidth + 3]);
                sudoku[blocLength + 6][blockWidth + 6] = sudoku[blocLength + 6][blockWidth + 6] ^ sudoku[blocLength1 + 6][blockWidth1 + 6] ^ (sudoku[blocLength1 + 6][blockWidth1 + 6] = sudoku[blocLength + 6][blockWidth + 6]);
                if (!checker(areas, sudoku)) {
                    j--;
                }
            }
        }
        //Put some shuffled numbers into some random cells
        for (int i = 0; i < 35; i++) {
            int randomRow = random.nextInt(0, 9);
            int randomCol = random.nextInt(0, 9);
            areas[randomRow][randomCol].setText(String.valueOf(sudoku[randomRow][randomCol]));
            areas[randomRow][randomCol].setFocusable(false);
        }
        /*
        Giving each area a border according to their location
        some must have a thicker side to emulate the 3x3 blocks
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
        /*
        Add a function to the focus owner so that
        the color of text changes based on the input
        */
        for (int i = 0; i < areas.length; i++) {
            for (int j = 0; j < areas[i].length; j++) {
                int finalI = i;
                int finalJ = j;
                areas[i][j].addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    
                    }
                    
                    @Override
                    public void keyPressed(KeyEvent e) {
                    }
                    
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (areas[finalI][finalJ].getText().length() > 0) {
                            Color correctOrIncorrectColor = (Integer.parseInt(String.valueOf(areas[finalI][finalJ].getText())) == sudoku[finalI][finalJ]) ? Color.green : Color.red;
                            Clip correctOrIncorrectClip = (Integer.parseInt(String.valueOf(areas[finalI][finalJ].getText())) == sudoku[finalI][finalJ]) ? player[0] : player[1];
                            correctOrIncorrectClip.setFramePosition(0);
                            int chancesChange = (Integer.parseInt(String.valueOf(areas[finalI][finalJ].getText())) == sudoku[finalI][finalJ]) ? chances : --chances;
                            chancesLeft.setText("Remaining Chances: " + chances);
                            areas[finalI][finalJ].setForeground(correctOrIncorrectColor);
                            //if input is correct disable focus
                            if (correctOrIncorrectColor == Color.green) {
                                areas[finalI][finalJ].setFocusable(false);
                                solvedCorrectly++;
                                if (solvedCorrectly == 81) {
                                    JOptionPane.showMessageDialog(null, "Congrats! \nYou Won!");
                                    Main.frameX = frame.getX();
                                    Main.frameY = frame.getY();
                                    frame.dispose();
                                    solvedCorrectly = 0;
                                    chances = 5;
                                    new youSolveIt();
                                }
                            }
                            //
                            correctOrIncorrectClip.start();
                            //if chances reach zero stop the game
                            if (chancesChange == 0) {
                                JOptionPane.showMessageDialog(null, "You lost :(");
                                Main.frameX = frame.getX();
                                Main.frameY = frame.getY();
                                frame.dispose();
                                chances = 5;
                                solvedCorrectly = 0;
                                new youSolveIt();
                            }
                        }
                    }
                });
            }
        }
        //Count the already solved cells
        for (JTextArea[] area : areas) {
            for (JTextArea jTextArea : area) {
                if (!Objects.equals(jTextArea.getText(), "0") && jTextArea.getText() != null && jTextArea.getText().length() > 0) {
                    solvedCorrectly++;
                }
            }
        }
        
        
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frameX = frame.getX();
                Main.frameY = frame.getY();
                chances = 5;
                solvedCorrectly = 0;
                frame.dispose();
                Main.main(new String[]{});
        
            }
        });
        frame.setVisible(true);
    }
    
    static int chances = 5;
    static int solvedCorrectly = 0;
    
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
    static boolean solver(int[][] sudoku, int row, int col) {
        
        if (row == 9 - 1 && col == 9) {
            return true;
        }
        if (col == 9) {
            row++;
            col = 0;
        }
        if (sudoku[row][col] != 0) {
            return solver(sudoku, row, col + 1);
        }
        for (int num = 1; num < 10; num++) {
            if (isThisNumberSafe(sudoku, col, row, num)) {
                sudoku[row][col] = num;
                if (solver(sudoku, row, col + 1)) {
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
        frame.setSize(385, 450);
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
    static JLabel chancesLeft(JPanel panel) {
        JLabel chancesLeft = new JLabel();
        chancesLeft.setBounds(80, 365, 300, 40);
        chancesLeft.setText("Remaining Chances: " + chances);
        chancesLeft.setFont(new Font(null, Font.BOLD, 20));
        chancesLeft.setForeground(Color.white);
        panel.add(chancesLeft);
        return chancesLeft;
    }
    static JButton goBack(JPanel panel) {
        ImageIcon back = new ImageIcon("images/back.png");
        ImageIcon backAnimated = new ImageIcon("images/backAnimated.gif");
        JButton goBack = new JButton(back);
        goBack.setBounds(5, 369, 40, 40);
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
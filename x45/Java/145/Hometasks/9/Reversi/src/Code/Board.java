package Code;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: Volkman
 * Date: 28.04.2010
 * Time: 22:34:08
 */
public class Board {
    private class Listener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!gameIsRunning) {
                status.setText("Please start a new game.");
                return;
            }
            if (newGame.getGameType() == 2)
                return;
            Cell p = null;
            if (newGame.getGameType() == 0 || (newGame.getGameType() == 1 && logic.getCurPlayerCourse() == 1)) {
                if (logic.checkCourseAvailable()) {
                    p = getCell((JLabel) (e.getComponent()));
                    if (!logic.checkCourse(p)) {
                        status.setText("Please choose correct cell");
                        return;
                    }
                } else {
                    logic.setCurPlayerCourse((logic.getCurPlayerCourse() * 2) % 3);
                    status.setText("No available course on the board. The course returned to another player.");
                }
            }
            logic.setCourse(p);
            update(logic.getBoard());
            if ((newGame.getGameType() == 1 && logic.getCurPlayerCourse() == 2)) {
                p = Logic.botCourse(logic, newGame.getBotName(1), logic.getCurPlayerCourse());
                if (p.x != -1 && p.y != -1) {
                    logic.setCourse(p);
                } else {
                    logic.setCurPlayerCourse((logic.getCurPlayerCourse() * 2) % 3);
                    status.setText("No available course to bot. The course returned to player.");
                }
                update(logic.getBoard());
            }
            switch (logic.checkWin()) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Player1 wins!!!");
                    gameIsRunning = false;
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Player2 wins!!!");
                    gameIsRunning = false;
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Draw in this game!!!");
                    gameIsRunning = false;
                    break;
                case 0:
                    break;
            }
        }

    }

    private Color defaultBackground;
    private static int ROWS = 0;
    private static int COLUMNS = 0;
    private JPanel mainPanel;
    private JPanel board;
    private JLabel status;
    private JLabel[][] map;
    private boolean gameIsRunning = false;
    private Logic logic;

    public Board(int ROWS, int COLUMNS) {
        Board.ROWS = ROWS;
        Board.COLUMNS = COLUMNS;
        defaultBackground = new Color(173, 216, 230);

        // new Logic(ROWS, COLUMNS);
        status.setText("Welcome to my game");
        board.removeAll();
        board.setLayout(new GridLayout(ROWS, COLUMNS));
        map = new JLabel[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                JLabel l = new JLabel("");
                l.setBackground(defaultBackground);
                l.setOpaque(true);
                l.setHorizontalAlignment(JLabel.CENTER);
                l.setFont(new Font(null, Font.BOLD, 30));
                l.setMinimumSize(new Dimension(50, 50));
                l.setBorder(new LineBorder(Color.GRAY));
                Listener listener = new Listener();
                l.addMouseListener(listener);
                map[i][j] = l;
                board.add(l);
            }
        }
        board.setFocusable(true);
        board.requestFocus();
    }

    public void showForm(final String[] botList) {
        final JFrame frame;
        frame = new JFrame("Reversi by Volkman");
        frame.setBackground(Color.lightGray);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(mainPanel.getWidth() + 15, mainPanel.getHeight() + 65));


        JMenuBar menuBar = new JMenuBar();
        JMenu game_menu = new JMenu("Game");
        JMenuItem newgame = new JMenuItem("New Game");
        game_menu.add(newgame);
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logic = new Logic(ROWS, COLUMNS);
                update(logic.getBoard());
                new newGame(botList, Board.this, defaultBackground).showForm();
                gameIsRunning = true;
                update(logic.getBoard());
                status.setText("Score: Player1 - " + logic.getSumPlayer(1) + ", Player2 - " + logic.getSumPlayer(2));

            }
        });
        menuBar.add(game_menu);

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private Cell getCell(JLabel l) {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++)
                if (map[i][j] == l)
                    return new Cell(i, j);
        throw new IllegalArgumentException("Cannot find this JLabel object in map[][]");
    }

    private void update(int[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                switch (board[i][j]) {
                    case 0:
                        map[i][j].setBackground(defaultBackground);
                        break;
                    case 1:
                        map[i][j].setBackground(Color.BLACK);
                        break;
                    case 2:
                        map[i][j].setBackground(Color.WHITE);
                        break;
                }
            }
        }
        status.setText("Score: Player1 - " + logic.getSumPlayer(1) + ", Player2 - " + logic.getSumPlayer(2));
    }

    public void startComputerGame() {
        Timer timer = new Timer(2000, null);
        while (logic.checkWin() == 0) {
            timer.start();
            Cell p = Logic.botCourse(logic, newGame.getBotName(logic.getCurPlayerCourse()), logic.getCurPlayerCourse());
            logic.setCourse(p);
            update(logic.getBoard());
        }
        switch (logic.checkWin()) {
            case 1:
                JOptionPane.showMessageDialog(null, "Bot1 wins!!!");
                gameIsRunning = false;
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Bot2 wins!!!");
                gameIsRunning = false;
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Draw in this game!!!");
                gameIsRunning = false;
                break;
            case 0:
                break;
        }
    }


}

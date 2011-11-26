package Code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * User: Volkman
 * Date: 28.04.2010
 * Time: 4:12:52
 */
public class newGame {
    private JPanel mainPanel;
    private JComboBox type;
    private JComboBox player1;
    private JComboBox player2;
    private JLabel p1;
    private JLabel p2;
    private JButton OK;
    private JFrame frame;
    private static String[] botName;
    private static int gameType;

    public newGame(final String[] botList, final Board board, Color defaultBackground) {
        final int WIDTH = 315;
        final int HEIGHT = 45;
        botName = new String[3];

        p1.setVisible(false);
        p2.setVisible(false);
        player1.setVisible(false);
        player2.setVisible(false);
        mainPanel.setBackground(defaultBackground);

        type.addItem("Player vs Player");
        type.addItem("Player vs Computer");
        type.addItem("Computer vs Computer");
        type.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                switch (type.getSelectedIndex()) {
                    case 0:
                        frame.setSize(WIDTH, 2 * HEIGHT + 15);
                        p1.setVisible(false);
                        p2.setVisible(false);
                        player1.setVisible(false);
                        player2.setVisible(false);
                        break;
                    case 1:
                        frame.setSize(WIDTH, 3 * HEIGHT);
                        player1.removeAllItems();
                        for (int i = 0; i < botList.length; i++) {
                            player1.addItem(botList[i]);
                        }
                        p1.setText(" IBot");
                        p1.setVisible(true);
                        p2.setVisible(false);
                        player1.setEnabled(true);
                        player1.setVisible(true);
                        player2.setVisible(false);
                        break;
                    case 2:
                        frame.setSize(WIDTH, 4 * HEIGHT);
                        player1.removeAllItems();
                        player2.removeAllItems();
                        for (int i = 0; i < botList.length; i++) {
                            player1.addItem(botList[i]);
                            player2.addItem(botList[i]);
                        }
                        p1.setText(" IBot 1");
                        p2.setText(" IBot 2");
                        p1.setVisible(true);
                        p2.setVisible(true);
                        player1.setEnabled(true);
                        player1.setVisible(true);
                        player2.setEnabled(true);
                        player2.setVisible(true);
                        break;
                }
            }
        });

        OK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                gameType = type.getSelectedIndex();
                botName[1] = (String) player1.getSelectedItem();
                botName[2] = (String) player2.getSelectedItem();
                if(gameType == 2){
                    board.startComputerGame();
                }
            }
        });
    }

    public void showForm() {
        frame = new JFrame("New Game");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocation(150, 150); 
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }

    public static String getBotName(int curPlayer) {
        return botName[curPlayer];
    }

    public static int getGameType() {
        return gameType;
    }
}

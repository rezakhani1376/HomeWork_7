package TicTacToe;



import javax.swing.*;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Component;

public class TicTacToeMain {
    public static void main(String[] args) {
        GUI g = new GUI();
        // g.setLayout(null);
        g.setTitle("TicTacToe");
        g.setSize(500, 475);
        g.setVisible(true);
        g.setLocationRelativeTo(null);
    }
}


class BoardButton extends JButton {
    //private String sign;
    private boolean pressed;
    private int xPos, yPos, value;

    public BoardButton(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        value = 0;
    }

    public void setSign(String sign) {
        setText(sign);
    }

    /*public String getSign() {
        return getText();
    }*/

    public boolean getState() {
        return pressed;
    }

    public void setState(boolean pressed) {
        this.pressed = pressed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

class GUI extends JFrame {
    private JPanel mainPanel, scoreBoard, board, timerPanel;
    private JButton newGame, reset;
    private JLabel circle, cross, tie, scores, turn;
    //private String winner;
    private BoardButton btn[][];
    private int clicks, circleCount, crossCount, tieCount;
    //private Timer timer = new Timer();

    public GUI() {
        board = new JPanel();
        mainPanel = new JPanel();
        scoreBoard = new JPanel();
        timerPanel = new JPanel();

        newGame = new JButton("New Game");
        reset = new JButton("Reset Scores");

        cross = new JLabel("Cross: 0 wins");
        circle = new JLabel("Circle: 0 wins");
        tie = new JLabel("Ties: 0");
        scores = new JLabel("Scores");
        turn = new JLabel("Circles turn");

        scores.setFont(new Font("Arial", Font.BOLD, 20));
        scores.setForeground(Color.darkGray);

        cross.setFont(new Font("Arial", Font.BOLD, 15));
        cross.setForeground(Color.lightGray);

        circle.setFont(new Font("Arial", Font.BOLD, 15));
        circle.setForeground(Color.lightGray);

        tie.setFont(new Font("Arial", Font.BOLD, 15));
        tie.setForeground(Color.lightGray);

        scoreBoard.setLayout(new BoxLayout(scoreBoard, BoxLayout.Y_AXIS));

        scoreBoard.add(Box.createVerticalStrut(10));
        scoreBoard.add(scores);
        scoreBoard.add(circle);
        scoreBoard.add(Box.createVerticalStrut(20));
        scoreBoard.add(cross);
        scoreBoard.add(Box.createVerticalStrut(20));

        scoreBoard.add(tie);
        scoreBoard.add(reset);
        scoreBoard.add(turn);

        //timerPanel.add(timer);
        timerPanel.add(newGame);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(board, BorderLayout.CENTER);
        mainPanel.add(scoreBoard, BorderLayout.EAST);
        mainPanel.add(timerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        newGame.addActionListener(new BtnListener());
        reset.addActionListener(new BtnListener());

        btn = new BoardButton[3][3];
        board.setLayout(new GridLayout(3,3));
        //timer.setRunning(true);

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++) {
                btn[i][j]=new BoardButton(j,i);
                btn[i][j].setFont(new Font("Arial", Font.BOLD, 70));
                btn[i][j].setForeground(Color.blue);
                btn[i][j].addActionListener(new BoardListener());
                board.add(btn[i][j]);
            }
        }
    }

    //Checks for winner
    public void checkWin() {
        int diagSum1 = 0;
        int diagSum2 = 0;
        int colSum = 0;
        int rowSum = 0;
        String winner = "";

        diagSum1 = btn[0][2].getValue() + btn[1][1].getValue() + btn[2][0].getValue();
        diagSum2 = btn[0][0].getValue() + btn[1][1].getValue() + btn[2][2].getValue();

        if(diagSum1 == 3 || diagSum2 == 3) {
            winner = "Cross";
            crossCount++;
        }
        else if(diagSum1 == -3 || diagSum2 == -3) {
            winner = "Circle";
            circleCount++;
        }

        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                rowSum += btn[i][j].getValue();
                colSum += btn[j][i].getValue();
            }
            if(rowSum == 3 || colSum == 3 && winner.equals("")) {
                winner = "Cross";
                crossCount++;
            }
            else if(rowSum == -3 || colSum == -3 && winner.equals("")) {
                winner = "Circle";
                circleCount++;
            }
            rowSum = 0;
            colSum = 0;
        }
        if(clicks == 9 && winner.equals("")) {
            winner = "No one";
            tieCount++;
        }
        if(!winner.equals("")) {
            setPanelEnabled(board, false);
            //timer.setRunning(false);
            turn.setText("");
            JOptionPane.showMessageDialog(null, winner + " is the winner!","Results",-1);
        }
        updateScores();
    }

    public void reset() {
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++) {
                btn[i][j].setSign("");
                btn[i][j].setState(false);
                btn[i][j].setValue(0);
            }
        }
    }

    public void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);
        Component[] components = panel.getComponents();

        for(int i = 0; i < components.length; i++) {
            if(components[i].getClass().getName() == "javax.swing.JPanel") {
                setPanelEnabled((JPanel) components[i], isEnabled);
            }

            components[i].setEnabled(isEnabled);
        }
    }

    public void updateScores() {
        circle.setText("Circle: " + Integer.toString(circleCount) + " wins");
        cross.setText("Cross: " + Integer.toString(crossCount) + " wins");
        tie.setText("Ties: " + Integer.toString(tieCount));
    }

    class BoardListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            BoardButton buttonClicked = (BoardButton)e.getSource();
            if(buttonClicked.getState()==false) {
                clicks++;
                if(clicks%2==0) {
                    buttonClicked.setText("X");
                    buttonClicked.setForeground(Color.blue);
                    buttonClicked.setValue(1);
                    turn.setText("Circles turn");
                    checkWin();
                }
                else {
                    buttonClicked.setText("O");
                    buttonClicked.setValue(-1);
                    buttonClicked.setForeground(Color.red);
                    turn.setText("Cross turn");
                    checkWin();
                }
            }
            buttonClicked.setState(true);
        }
    }

    class BtnListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if(e.getSource() == newGame) {
                reset();
                setPanelEnabled(board, true);
//                timer.setRunning(true);
//                timer.getClock().resetTime();
//                timer.getDisplay().setText(timer.getClock().getCurrentDisplayTime());
                turn.setText("Circles turn");
                clicks = 0;
            }
            else if(e.getSource() == reset) {
                circleCount = 0;
                crossCount = 0;
                tieCount = 0;
                updateScores();
            }
        }
    }
}





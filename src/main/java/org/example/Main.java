package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiPredicate;

public class Main {

    private static int sec = 0;
    private static Timer timer = new Timer();
    private static JLabel lblTime;
    private static Board board;
    private static JButton btnStart;

    public static void main(String[] args) {
        //container: JPanel, JFrame
        //component: JButton, JLabel, JCheckBox

        //Boxlayout, Flowlayout, ... giup quan ly bo cuc giao dien

//        Timer timer = new Timer();

        //timer.cancel();


        //Hoi ai di truoc
        //Board board = (choice == 0) ? new Board(Cell.X_VALUE) : new Board(Cell.O_VALUE);

        board = new Board();
        board.setEndGame(new EndGame() {
            @Override
            public void end(String player, int st) {
                if (st == Board.WIN) {
                    //System.out.println("Nguoi choi " + currentPlayer + " thang");
                    JOptionPane.showMessageDialog(null, "Nguoi choi " + player + " thang");
                    stopGame();
                } else if (st == Board.DRAW){
                    //System.out.println("Hoa");
                    JOptionPane.showMessageDialog(null, "Hoa");
                    stopGame();
                }

            }
        });

        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);

        board.setPreferredSize(new Dimension(600, 600));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0 , 0);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(flowLayout);
        //bottomPanel.setPreferredSize(new Dimension(300, 50));
        //bottomPanel.setBackground(Color.YELLOW);

        btnStart = new JButton("Start");

        //Timer va TimerTask
        lblTime = new JLabel("0:0");
        bottomPanel.add(lblTime);
        bottomPanel.add(btnStart);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(btnStart.getText().equals("Start")) {
                    startGame();
                } else {
                    stopGame();
                }

            }
        });

        jPanel.add(board);
        jPanel.add(bottomPanel);


        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        //Cho cua so ra giua man hinh

        JFrame jFrame = new JFrame("Game co caro"); //tao kich co
//        jFrame.setSize(600, 600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false); //ko cho thay doi kich thuoc
        jFrame.add(jPanel);

        int x = (int) dimension.getWidth() / 2 - 300;
        int y = (int) dimension.getHeight() / 2 - 300;

        jFrame.setLocation(x, y);

        jFrame.pack();
        //show giao dien
        jFrame.setVisible(true);


    }
    private static void startGame() {
        //Hoi ai di truoc
        //int choice = JOptionPane.showConfirmDialog(null, "Nguoi choi X di truoc dung khong?", "Ai la nguoi di truoc", JOptionPane.YES_NO_OPTION);

        board.reset();
        String currentPlayer = Cell.X_VALUE;
        board.setCurrentPlayer(currentPlayer);

        //Dem nguoc
        //Neu moi mo chuong trình

        //Neu game dang start
        sec = 0;
        lblTime.setText("0:0");
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sec++;
                String value = ( sec / 60 + ":" + sec % 60);
                lblTime.setText(value);
            }
        }, 1000, 1000);

        //doi nut Start thanh Stop
        btnStart.setText("Stop");
    }

    private static void stopGame() {
        btnStart.setText("Start");
        sec = 0;
        lblTime.setText("0:0");

        timer.cancel();
        timer = new Timer();

        board.reset();

    }
}
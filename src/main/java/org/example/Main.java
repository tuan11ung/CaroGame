package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //container: JPanel, JFrame
        //component: JButton, JLabel, JCheckBox

        Board board = new Board();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        //Cho cua so ra giua man hinh

        JFrame jFrame = new JFrame("Game co caro"); //tao kich co
        jFrame.setSize(600, 600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false); //ko cho thay doi kich thuoc
        jFrame.add(board);

        int x = (int) dimension.getWidth() / 2 - (jFrame.getWidth() / 2);
        int y = (int) dimension.getHeight() / 2 - (jFrame.getHeight() / 2);

        jFrame.setLocation(x, y);
        jFrame.setVisible(true); //tao cua so

    }
}
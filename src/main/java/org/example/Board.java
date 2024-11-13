package org.example;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

//dung component chua
public class Board extends JPanel {
    private static final int N = 24;
    private static final int M = 24;

    public static final int DRAW = 0;
    public static final int WIN = 1;
    public static final int NORMAL = 2;

    private EndGame endGame;

    private Image imgX;
    private Image imgO;

    private Cell matrix[][] = new Cell[N][M];

    private String currentPlayer = Cell.EMPTY_VALUE;

    public Board(String player) {
        this();
        this.currentPlayer = player;
    }

    public Board() {

        this.initMatrix();

        //khoi tao matrix

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();

                if(currentPlayer.equals(Cell.EMPTY_VALUE)) {
                    return;
                }

                //phat ra am thanh
                soundClick();

                //Tinh toan xem x, y roi vao o nao trong board, sau do ve hinh X hoac O
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        Cell cell = matrix[i][j];

                        int cXStart = cell.getX();
                        int cYStart = cell.getY();

                        int cXEnd = cXStart + cell.getW(); //100 + 100 = 200
                        int cYEnd = cYStart + cell.getH();

                        if (x >= cXStart && x <= cXEnd && y >= cYStart && y <= cYEnd) {
                            if (cell.getValue().equals(Cell.EMPTY_VALUE)) {
                                cell.setValue(currentPlayer);

                                repaint();

                                int result = checkWin(currentPlayer, i, j);
                                if (endGame != null) {
                                    endGame.end(currentPlayer, result);
                                }

                                if(result == NORMAL) {
                                //Dao luot
                                    currentPlayer = currentPlayer.equals(Cell.O_VALUE) ? Cell.X_VALUE : Cell.O_VALUE;
                                }

                            }

                        }
                    }
                }

            }
        });

        try {
            imgX = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/X.png")));
            imgO = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/O.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private synchronized void soundClick() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/Mousclik.wav"));
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void initMatrix() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Cell cell = new Cell();
                matrix[i][j] = cell;

            }
        }
    }

    public void reset() {
        this.initMatrix();
        this.setCurrentPlayer(Cell.EMPTY_VALUE);
        repaint();
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setEndGame(EndGame endGame) {
        this.endGame = endGame;
    }

    //Hoa, Thang, Thua
    //0: Hoa, 1: Player hien tai thang, 2: Player hien tai chua thang (con nuoc danh tiep)
    public int checkWin(String player, int i, int j) {

//        //Duong cheo thu nhat
//        if (this.matrix[0][0].getValue().equals(player) && this.matrix[1][1].getValue().equals(player) && this.matrix[2][2].getValue().equals(player)) {
//            return WIN;
//        }
//
//        //Duong cheo thu hai
//        if (this.matrix[0][2].getValue().equals(player) && this.matrix[1][1].getValue().equals(player) && this.matrix[2][0].getValue().equals(player)) {
//            return WIN;
//        }
//
//        //Dong thu 1
//        if (this.matrix[0][0].getValue().equals(player) && this.matrix[0][1].getValue().equals(player) && this.matrix[0][2].getValue().equals(player)) {
//            return WIN;
//        }
//
//        //Dong thu 2
//        if (this.matrix[1][0].getValue().equals(player) && this.matrix[1][1].getValue().equals(player) && this.matrix[1][2].getValue().equals(player)) {
//            return WIN;
//        }
//
//        //Dong thu 3
//        if (this.matrix[1][0].getValue().equals(player) && this.matrix[2][1].getValue().equals(player) && this.matrix[2][2].getValue().equals(player)) {
//            return WIN;
//        }
//
//        //Cot thu 1
//        if (this.matrix[0][0].getValue().equals(player) && this.matrix[1][0].getValue().equals(player) && this.matrix[2][0].getValue().equals(player)) {
//            return WIN;
//        }
//
//        //Cot thu 2
//        if (this.matrix[0][1].getValue().equals(player) && this.matrix[1][1].getValue().equals(player) && this.matrix[2][1].getValue().equals(player)) {
//            return WIN;
//        }
//
//        //Cot thu 3
//        if (this.matrix[0][2].getValue().equals(player) && this.matrix[1][2].getValue().equals(player) && this.matrix[2][2].getValue().equals(player)) {
//            return WIN;
//        }

        int count = 0;

        //Chieu ngang
        for (int col = 0; col < M; col ++) {
            Cell cell = matrix[i][col];
            if (cell.getValue().equals(currentPlayer)) {
                count++;
                if (count == 5) {
                    System.out.println("Ngang");
                    return WIN;
                }
            } else {
                count = 0;
            }
        }

        //Chieu doc
        count = 0;
        for (int row = 0; row < N; row++) {
            Cell cell = matrix[row][j];
            if (cell.getValue().equals(currentPlayer)) {
                count++;
                if (count == 5) {
                    System.out.println("Doc");
                    return WIN;
                }
            } else {
                count = 0;
            }
        }

        //Cheo trai
        int min = Math.min(i, j);
        int topI = i - min;
        int topJ = j - min;
        count = 0;
        for (;topI < N && topJ < M; topI++, topJ++) {
            Cell cell = matrix[topI][topJ];
            if (cell.getValue().equals(currentPlayer)) {
                count++;
                if (count == 5) {
                    System.out.println("Cheo trai");
                    return WIN;
                }
            } else {
                count = 0;
            }
        }

        //Cheo phai
        min = Math.min(i, j);
        topI = i - min;
        topJ = j + min;
        count = 0;

        if (topJ >= M) {
            int du = topJ - (M - 1);
            topJ = M - 1;
        }

        for (;topI < N && topJ >= 0; topI++, topJ--) {
            Cell cell = matrix[topI][topJ];
            if (cell.getValue().equals(currentPlayer)) {
                count++;
                if (count == 5) {
                    System.out.println("Cheo phai");
                    return WIN;
                }
            } else {
                count = 0;
            }
        }

        if (this.isFull()) {
            return DRAW;
        }
        return NORMAL;

    }

    private boolean isFull() {
        int number = N * M;
        int k = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Cell cell = matrix[i][j];
                if(!cell.getValue().equals(Cell.EMPTY_VALUE)) {
                    k++;
                }

            }
        }
        return k == number;

    }

    @Override
    public void paint(Graphics g) {
        int w = getWidth() / M;
        int h = getHeight() / N;

        Graphics2D graphics2D = (Graphics2D) g;

        for (int i = 0; i < N; i++) {
            int k = i;
            for (int j = 0; j < M; j++) {
                int x = j * w; //0 * 200 = 0; 1*200 = 200; 2*200 = 400
                int y = i * h;

                //Cap nhat lai matrix
                Cell cell = matrix[i][j];
                cell.setY(y);
                cell.setX(x);
                cell.setW(w);
                cell.setH(h);


                Color color = k % 2 == 0 ? Color.WHITE : Color.GRAY;
                graphics2D.setColor(color);
                graphics2D.fillRect(x, y, w, h);

                if (cell.getValue().equals(Cell.X_VALUE)){
                    Image img = imgX;
                    graphics2D.drawImage(img, x, y, w, h, this);

                } else if (cell.getValue().equals(Cell.O_VALUE)) {
                    Image img = imgO;
                    graphics2D.drawImage(img, x, y, w, h, this);

                }


                k++;
            }
        }
    }

}

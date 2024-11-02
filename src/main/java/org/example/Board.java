package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

//dung component chua
public class Board extends JPanel {
    private static final int N = 3;
    private static final int M = 3;

    private Image imgX;
    private Image imgO;

    public Board() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();
            }
        });

        try {
            imgX = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/X.png")));
            imgO = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/O.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        int w = getWidth() / 3;
        int h = getHeight() / 3;

        System.out.println(w);
        Graphics2D graphics2D = (Graphics2D) g;

        int k = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int x = i * w; //0 * 200 = 0; 1*200 = 200; 2*200 = 400
                int y = j * h;

                Color color = k % 2 == 0 ? Color.BLUE : Color.RED;
                graphics2D.setColor(color);
                graphics2D.fillRect(x, y, w, h);

                Image img = k % 2 == 0 ? imgX : imgO;

                graphics2D.drawImage(img, x, y, w, h, this);

                k++;
            }
        }
    }
}

package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Timer;

public class UI implements ActionListener {

    GamePanel gp;
    Font stuffFont;
    Font messageFont;
    public boolean messageOn = false;
    Timer alphaTimer = new Timer(800, this);
    float alphaValue = 1f;
    int messageCounter = 0;
    String message;

    public UI(GamePanel gp) {
        this.gp = gp;
        stuffFont = new Font("Times New Roman", stuffFont.ITALIC, 40);
        this.message = "";

        try {
            InputStream is = getClass()
                    .getResourceAsStream("res/font/x12y16pxMaruMonica.ttf");
            messageFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNumStuff() {
        alphaTimer.start();
        messageOn = true;
        alphaValue = 1f;
    }

    public void showMessage(String message) {
        this.message = message;
    }

    public void draw(Graphics2D g2) {
        if (messageOn) {
            g2.setFont(stuffFont);
            if (gp.getMapName().equals("inter")) {
                g2.setColor(Color.WHITE);
            } else {
                g2.setColor(Color.BLACK);
            }
            g2.setComposite(
                    AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
            g2.drawString("Stuffs: " + gp.getMainCharacter().getNumStuff(), 25, 50);
        }

        if (message.length() > 0) {
            g2.setFont(messageFont);
            g2.setColor(Color.BLACK);
            message = "";
        }

    }

    public void dialogueBlock(Graphics2D g2) {
        int x = gp.getTileSize() * 2, y = gp.getTileSize() / 2,
                width = gp.getScreenWidth() - (gp.getTileSize() * 4),
                height = gp.getTileSize() * 4, arcHW = 35;

        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, arcHW, arcHW);

        x += 5;
        y += 5;
        width -= 10;
        height -= 10;
        arcHW -= 10;

        c = new Color(255, 255, 255);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, arcHW, arcHW);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        alphaValue -= 0.2f;

        if (alphaValue < 0) {
            alphaValue = 0;
            alphaTimer.stop();
            messageOn = false;
        }
        gp.repaint();
    }
}

package com.librasys.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImageAutoFitJPanel extends JPanel {

    private Image background;

    public ImageAutoFitJPanel(Image background) {
        this.background = background;
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Image full size
        // g.drawImage(background, 0, 0, null);

        // Image scaled
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(background.getWidth(this), background.getHeight(this));
    }

}

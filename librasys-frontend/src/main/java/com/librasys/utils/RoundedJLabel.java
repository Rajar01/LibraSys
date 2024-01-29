package com.librasys.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedJLabel extends JLabel {
    private Color backgroundColor;
    private int cornerRadius;

    public RoundedJLabel(String text, Icon icon, int cornerRadius, Color backgroundColor, int padding) {
        super(text, icon, JLabel.CENTER);
        this.backgroundColor = backgroundColor;
        this.cornerRadius = cornerRadius;
        this.setBorder(new EmptyBorder(padding, padding, padding, padding));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        int width = getWidth();
        int height = getHeight();

        // Create a rounded rectangle for the background
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);

        // Set the background color
        g2d.setColor(backgroundColor);
        g2d.fill(roundedRectangle);

        // Paint the text and icon
        super.paintComponent(g2d);

        g2d.dispose();
    }
}

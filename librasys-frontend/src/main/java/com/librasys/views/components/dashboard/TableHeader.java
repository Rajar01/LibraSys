package com.librasys.views.components.dashboard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.border.Border;

public class TableHeader extends JLabel {
    public TableHeader(String text, Font font, Color foregroundColor, Border border) {
        super(text);
        setOpaque(true);
        setBackground(Color.WHITE);
        setFont(font);
        setForeground(foregroundColor);
        setBorder(border);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.setColor(new Color(230, 230, 230));
        grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
}

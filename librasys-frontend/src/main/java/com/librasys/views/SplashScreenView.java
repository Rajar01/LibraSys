package com.librasys.views;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import net.miginfocom.swing.MigLayout;

public class SplashScreenView extends BaseView {
    @Override
    public void initComponents() {
        setLayout(new MigLayout(
                "gap 0, ins 0, fill",
                "[fill]",
                "[fill]"));

        JPanel jPanel = new JPanel(new MigLayout(
                "ins 0, fill",
                "[fill]",
                "[]"));

        jPanel.setBackground(Color.decode("#FFFFFF"));

        FlatSVGIcon svgIconLogo = new FlatSVGIcon(getClass().getResource("/logo.svg"));
        svgIconLogo = svgIconLogo.derive(3);
        JLabel jLabelLogoImage = new JLabel();
        jLabelLogoImage.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelLogoImage.setIcon(svgIconLogo);

        jPanel.add(jLabelLogoImage);

        add(jPanel);
    }
}

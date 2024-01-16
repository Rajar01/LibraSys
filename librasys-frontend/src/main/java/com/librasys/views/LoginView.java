package com.librasys.views;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.librasys.core.FontManager;
import com.librasys.utils.JPasswordFieldWithPlaceholder;
import com.librasys.utils.JTextFieldWithPlaceholder;
import com.librasys.utils.RoundedJPanel;

import net.miginfocom.swing.MigLayout;

public class LoginView extends BaseView {
        private JPanel jPanelImageContainer;
        private RoundedJPanel jPanelLoginFormContainer;
        private JLabel jLabelHeading, jLabelSubHeading, jLabelLogoImage, jLabelHeroImage;
        private JTextFieldWithPlaceholder jTextFieldWithPlaceholderUsername;
        private JPasswordFieldWithPlaceholder jPasswordFieldWithPlaceholder;
        private JButton jButtonLogin;

        @Override
        public void initComponents() {
                setLayout(new MigLayout(
                                "flowx, gap 0, ins 0, fill",
                                "[grow, 60%, align center][grow, 40%, align center]",
                                "[grow, 100%]"));

                jPanelImageContainer = new JPanel(new MigLayout(
                                "flowx, aligny center, ins 0",
                                "[]",
                                "[]"));

                jPanelLoginFormContainer = new RoundedJPanel(new MigLayout(
                                "flowy, aligny center, ins 0 80 0 80",
                                "[100%, align center]",
                                "[]48[]8[]48[]16[]32[]"), 16);

                setBackground(Color.decode("#8DBDFF"));
                jPanelImageContainer.setBackground(Color.BLUE);
                jPanelLoginFormContainer.setBackground(Color.decode("#FFFFFF"));

                add(jPanelImageContainer);
                add(jPanelLoginFormContainer, "w 100%, h 100%, gapx 16 16, gapy 16 16");

                jLabelHeading = new JLabel("Selamat Datang");
                jLabelSubHeading = new JLabel("Masukkan username dan password Anda");

                jTextFieldWithPlaceholderUsername = new JTextFieldWithPlaceholder("Username");
                jTextFieldWithPlaceholderUsername
                                .setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));

                jPasswordFieldWithPlaceholder = new JPasswordFieldWithPlaceholder("Password");
                jPasswordFieldWithPlaceholder
                                .setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));

                jButtonLogin = new JButton("Log In");
                jButtonLogin.setBackground(Color.decode("#8DBDFF"));
                jButtonLogin.setForeground(Color.WHITE);

                FlatSVGIcon svgIconLogo = new FlatSVGIcon(getClass().getResource("/logo.svg"));
                jLabelLogoImage = new JLabel();
                jLabelLogoImage.setHorizontalAlignment(SwingConstants.CENTER);
                jLabelLogoImage.setIcon(svgIconLogo);

                jLabelHeading.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(32f));
                jLabelSubHeading.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
                jButtonLogin.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(20f));

                jPanelLoginFormContainer.add(jLabelLogoImage);
                jPanelLoginFormContainer.add(jLabelHeading);
                jPanelLoginFormContainer.add(jLabelSubHeading);
                jPanelLoginFormContainer.add(jTextFieldWithPlaceholderUsername, "w 100%, h 16*3");
                jPanelLoginFormContainer.add(jPasswordFieldWithPlaceholder, "w 100%, h 16*3");
                jPanelLoginFormContainer.add(jButtonLogin, "w 100%, h 16*3");

                FlatSVGIcon svgLoginHero = new FlatSVGIcon(getClass().getResource("/login-hero.svg"));
                svgLoginHero = svgLoginHero.derive(2.5f);
                jLabelHeroImage = new JLabel();
                jLabelHeroImage.setBackground(new Color(0, 0, 0, 0));
                jLabelHeroImage.setOpaque(true);
                jLabelHeroImage.setHorizontalAlignment(SwingConstants.CENTER);
                jLabelHeroImage.setIcon(svgLoginHero);

                jPanelImageContainer.add(jLabelHeroImage);
        }

        public JTextFieldWithPlaceholder getJTextFieldWithPlaceholderUsername() {
                return this.jTextFieldWithPlaceholderUsername;
        }

        public JPasswordFieldWithPlaceholder getJPasswordFieldWithPlaceholder() {
                return this.jPasswordFieldWithPlaceholder;
        }

        public JButton getJButtonLogin() {
                return this.jButtonLogin;
        }
}
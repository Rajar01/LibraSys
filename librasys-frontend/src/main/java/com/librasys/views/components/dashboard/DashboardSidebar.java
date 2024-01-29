package com.librasys.views.components.dashboard;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.librasys.core.FontManager;
import com.librasys.views.BaseView;

import net.miginfocom.swing.MigLayout;

public class DashboardSidebar extends BaseView {
    private JLabel jLabelOverviewMenu, jLabelBookMenu, jLabelMemberMenu, jLabelAboutMenu, jLabelExitMenu;
    JButton jButtonLendAndReturnBookCTA;

    @Override
    protected void initComponents() {
        setLayout(new MigLayout(
                "flowy, gap 0, ins 32 16, fill",
                "[]",
                "[grow 0]48[grow 0]24[align top]"));
        setBackground(Color.WHITE);

        FlatSVGIcon svgIconLogo = new FlatSVGIcon(getClass().getResource("/logo.svg"));
        JLabel jLabelLogoImage = new JLabel();
        jLabelLogoImage.setIcon(svgIconLogo);
        jLabelLogoImage.setHorizontalAlignment(SwingConstants.CENTER);

        jButtonLendAndReturnBookCTA = new JButton("Pinjam/Kembali");
        jButtonLendAndReturnBookCTA.setBackground(Color.decode("#8DBDFF"));
        jButtonLendAndReturnBookCTA.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));
        jButtonLendAndReturnBookCTA.setFocusable(false);
        jButtonLendAndReturnBookCTA.setForeground(Color.WHITE);

        JPanel jPanelSidebarMenuContainer = new JPanel(new MigLayout(
                "flowy, gap 0 16, ins 0, fill",
                "",
                ""));

        jPanelSidebarMenuContainer.setOpaque(false);

        FlatSVGIcon svgIconGridOutline = new FlatSVGIcon(
                getClass().getResource("/icons/grid-outline-icon.svg"));
        jLabelOverviewMenu = new JLabel("Overview");
        jLabelOverviewMenu.setIcon(svgIconGridOutline);
        jLabelOverviewMenu.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));

        FlatSVGIcon svgIconBookOutline = new FlatSVGIcon(
                getClass().getResource("/icons/book-outline-icon.svg"));
        jLabelBookMenu = new JLabel("Buku");
        jLabelBookMenu.setIcon(svgIconBookOutline);
        jLabelBookMenu.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));

        FlatSVGIcon svgIconUsersGroupOutline = new FlatSVGIcon(
                getClass().getResource("/icons/users-group-outline-icon.svg"));
        jLabelMemberMenu = new JLabel("Anggota");
        jLabelMemberMenu.setIcon(svgIconUsersGroupOutline);
        jLabelMemberMenu.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));

        FlatSVGIcon svgIconInfoCircleOutline = new FlatSVGIcon(
                getClass().getResource("/icons/info-circle-outline-icon.svg"));
        jLabelAboutMenu = new JLabel("Tentang");
        jLabelAboutMenu.setIcon(svgIconInfoCircleOutline);
        jLabelAboutMenu.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));

        FlatSVGIcon svgIconCloseCirlcleOutline = new FlatSVGIcon(
                getClass().getResource("/icons/close-circle-outline-icon.svg"));
        jLabelExitMenu = new JLabel("Keluar");
        jLabelExitMenu.setIcon(svgIconCloseCirlcleOutline);
        jLabelExitMenu.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));

        jPanelSidebarMenuContainer.add(jLabelOverviewMenu);
        jPanelSidebarMenuContainer.add(jLabelBookMenu);
        jPanelSidebarMenuContainer.add(jLabelMemberMenu);
        jPanelSidebarMenuContainer.add(jLabelAboutMenu);
        jPanelSidebarMenuContainer.add(jLabelExitMenu);

        add(jLabelLogoImage, "width 100%");
        add(jButtonLendAndReturnBookCTA, "width 100%, height 48");
        add(jPanelSidebarMenuContainer, "width 100%");
    }

    public JLabel getJLabelOverviewMenu() {
        return this.jLabelOverviewMenu;
    }

    public JLabel getJLabelBookMenu() {
        return this.jLabelBookMenu;
    }

    public JLabel getJLabelMemberMenu() {
        return this.jLabelMemberMenu;
    }

    public JLabel getJLabelAboutMenu() {
        return this.jLabelAboutMenu;
    }

    public JLabel getJLabelExitMenu() {
        return this.jLabelExitMenu;
    }

    public JButton getJButtonLendAndReturnBookCTA() {
        return this.jButtonLendAndReturnBookCTA;
    }
}
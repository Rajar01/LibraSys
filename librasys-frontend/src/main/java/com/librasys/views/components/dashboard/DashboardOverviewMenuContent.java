package com.librasys.views.components.dashboard;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.librasys.core.FontManager;
import com.librasys.utils.ImageAutoFitJPanel;
import com.librasys.utils.RoundedJLabel;
import com.librasys.utils.RoundedJPanel;
import com.librasys.views.BaseView;

import net.miginfocom.swing.MigLayout;

public class DashboardOverviewMenuContent extends BaseView {
    private BookLoanTable jTableBookLoanTable;

    @Override
    protected void initComponents() {
        setLayout(new MigLayout(
                "flowy, gap 0 24, ins 32, fillx",
                "[]",
                "[][][]"));

        JPanel jPanelOneContainer = new JPanel(new MigLayout(
                "flowy, gap 0, ins 0",
                "[]",
                "[]8[]"));

        JPanel jPanelGreeting = new JPanel(new MigLayout("ins 0, gap 0"));
        JLabel jLabelGreetingOne = new JLabel("Hello, ");
        jLabelGreetingOne.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(32f));
        JLabel jLabelGreetingTwo = new JLabel("Admin!");
        jLabelGreetingTwo.setFont(FontManager.INSTANCE.getFont("Roboto-Black.ttf").deriveFont(32f));
        jLabelGreetingTwo.setForeground(Color.decode("#8DBDFF"));
        jPanelGreeting.add(jLabelGreetingOne);
        jPanelGreeting.add(jLabelGreetingTwo);
        JLabel jLabelDateNow = new JLabel("29 Januari 2024 | Senin, 19:00 WIB");
        jLabelDateNow.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(20f));

        jPanelOneContainer.add(jPanelGreeting);
        jPanelOneContainer.add(jLabelDateNow);

        JPanel jPanelTwoContainer = new JPanel(new MigLayout(
                "gap 0, ins 0, fillx",
                "[fill]24[fill]24[fill]24[fill]",
                "[]"));

        RoundedJPanel jPanelTotalMemberCard = new RoundedJPanel(new MigLayout(
                "ins 24, gap 0 16, fill",
                "",
                ""), 24);

        jPanelTotalMemberCard.setBackground(Color.WHITE);

        JLabel jLabelTotalMemberNumber = new JLabel("1223");
        jLabelTotalMemberNumber.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(32f));

        JLabel jLabelTotalMemberText = new JLabel("Total Anggota");
        jLabelTotalMemberText.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));

        FlatSVGIcon svgIconUsersGroupWhiteOutline = new FlatSVGIcon(
                getClass().getResource("/icons/users-group-white-outline-icon.svg"));
        RoundedJLabel jLabelTotalMemberIcon = new RoundedJLabel(
                "", svgIconUsersGroupWhiteOutline, Integer.MAX_VALUE, Color.decode("#8DBDFF"), 12);

        jPanelTotalMemberCard.add(jLabelTotalMemberNumber);
        jPanelTotalMemberCard.add(jLabelTotalMemberIcon, "wrap, align right");
        jPanelTotalMemberCard.add(jLabelTotalMemberText);

        RoundedJPanel jPanelTotalBookCard = new RoundedJPanel(new MigLayout(
                "ins 24, gap 0 16, fill",
                "",
                ""), 24);
        jPanelTotalBookCard.setBackground(Color.WHITE);

        JLabel jLabelTotalBookNumber = new JLabel("720");
        jLabelTotalBookNumber.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(32f));

        JLabel jLabelTotalBookText = new JLabel("Total Buku");
        jLabelTotalBookText.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));

        FlatSVGIcon svgIconBookWhiteOutline = new FlatSVGIcon(
                getClass().getResource("/icons/book-white-outline-icon.svg"));
        RoundedJLabel jLabelTotalBookIcon = new RoundedJLabel(
                "", svgIconBookWhiteOutline, Integer.MAX_VALUE, Color.decode("#8DBDFF"), 12);

        jPanelTotalBookCard.add(jLabelTotalBookNumber);
        jPanelTotalBookCard.add(jLabelTotalBookIcon, "wrap, align right");
        jPanelTotalBookCard.add(jLabelTotalBookText);

        RoundedJPanel jPanelBorrowedBookCard = new RoundedJPanel(new MigLayout(
                "ins 24, gap 0 16, fill",
                "",
                ""), 24);
        jPanelBorrowedBookCard.setBackground(Color.WHITE);

        JLabel jLabelBorrowedBookNumber = new JLabel("22");
        jLabelBorrowedBookNumber.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(32f));

        JLabel jLabelBorrowedBookText = new JLabel("Buku Dipinjam");
        jLabelBorrowedBookText.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));

        FlatSVGIcon svgIconSwatchBookWhiteOutline = new FlatSVGIcon(
                getClass().getResource("/icons/swatchbook-white-outline.svg"));
        RoundedJLabel jLabelBorrowedBookIcon = new RoundedJLabel(
                "", svgIconSwatchBookWhiteOutline, Integer.MAX_VALUE, Color.decode("#8DBDFF"), 12);

        jPanelBorrowedBookCard.add(jLabelBorrowedBookNumber);
        jPanelBorrowedBookCard.add(jLabelBorrowedBookIcon, "wrap, align right");
        jPanelBorrowedBookCard.add(jLabelBorrowedBookText);

        RoundedJPanel jPanelOverdueBookCard = new RoundedJPanel(new MigLayout(
                "ins 24, gap 0 16, fill",
                "",
                ""), 24);
        jPanelOverdueBookCard.setBackground(Color.WHITE);

        JLabel jLabelOverdueBookNumber = new JLabel("60");
        jLabelOverdueBookNumber.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(32f));

        JLabel jLabelOverdueBookText = new JLabel("Buku Terlambat");
        jLabelOverdueBookText.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));

        FlatSVGIcon svgIconClockTimeWhiteOutline = new FlatSVGIcon(
                getClass().getResource("/icons/clock-time-white-outline.svg"));
        RoundedJLabel jLabelOverdueBookIcon = new RoundedJLabel(
                "", svgIconClockTimeWhiteOutline, Integer.MAX_VALUE, Color.decode("#8DBDFF"), 12);

        jPanelOverdueBookCard.add(jLabelOverdueBookNumber);
        jPanelOverdueBookCard.add(jLabelOverdueBookIcon, "wrap, align right");
        jPanelOverdueBookCard.add(jLabelOverdueBookText);

        jPanelTwoContainer.add(jPanelTotalMemberCard);
        jPanelTwoContainer.add(jPanelTotalBookCard);
        jPanelTwoContainer.add(jPanelBorrowedBookCard);
        jPanelTwoContainer.add(jPanelOverdueBookCard);

        JPanel jPanelThreeContainer = new JPanel(new MigLayout(
                "flowy, gap 0, ins 0, fillx",
                "[fill]",
                "[]16[]"));

        JLabel jLabelTopChoice = new JLabel("Pilihan Teratas");
        jLabelTopChoice.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(24f));
        JPanel jPanelTopChoiceBookCardContainer = new JPanel(new MigLayout(
                "gap 0, ins 0, fillx",
                "[fill]push[fill]push[fill]push[fill]push[fill]",
                "[]"));

        String[] topChoiceBooks = { "The Critique of Pure Reason", "The Critique of Pure Reason",
                "The Critique of Pure Reason", "The Critique of Pure Reason",
                "The Critique of Pure Reason" };

        for (String book : topChoiceBooks) {
            JPanel jPanelCard = new JPanel(new MigLayout(
                    "flowy, gap 0, ins 0, fillx",
                    "[]",
                    "[]8[]4[]"));

            String bookTitle = book;
            if (bookTitle.length() > 29) {
                bookTitle = bookTitle.substring(0, 26) + "...";
            }

            try {
                Image bookCoverImage = ImageIO.read(getClass().getResource("/200x320.png"));
                ImageAutoFitJPanel imageAutoFitJPanelBookCover = new ImageAutoFitJPanel(bookCoverImage);
                JLabel jLabelBookTitle = new JLabel(bookTitle);
                jLabelBookTitle.setFont(
                        FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(14f));
                JLabel jLabelBookAuthor = new JLabel("Immanuel Kant");
                jLabelBookAuthor.setFont(
                        FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(12f));

                jPanelCard.add(imageAutoFitJPanelBookCover);
                jPanelCard.add(jLabelBookTitle);
                jPanelCard.add(jLabelBookAuthor);
                jPanelTopChoiceBookCardContainer.add(jPanelCard);
            } catch (IOException e) {
                Logger.getLogger(DashboardOverviewMenuContent.class.getName()).log(Level.SEVERE,
                        e.getMessage());
            }
        }

        jPanelThreeContainer.add(jLabelTopChoice, "grow");
        jPanelThreeContainer.add(jPanelTopChoiceBookCardContainer, "grow");

        RoundedJPanel jPanelFourContainer = new RoundedJPanel(new MigLayout(
                "ins 24, gap 0 16, fillx",
                "[fill]",
                "[][]"), 24);
        jPanelFourContainer.setBackground(Color.WHITE);

        JLabel jLabelBookLoanTableTitle = new JLabel("Daftar Peminjaman Buku");
        jLabelBookLoanTableTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(20f));

        JScrollPane jScrollPaneBookLoanTable = new JScrollPane();
        jScrollPaneBookLoanTable.getViewport().setBorder(null);
        jScrollPaneBookLoanTable.setBorder(new EmptyBorder(0, 0, 0, 0));
        jScrollPaneBookLoanTable.getViewport().setBackground(Color.WHITE);
        jScrollPaneBookLoanTable.setVerticalScrollBar(new JScrollBar());
        jScrollPaneBookLoanTable.getVerticalScrollBar().setBackground(Color.WHITE);

        jTableBookLoanTable = new BookLoanTable();
        jTableBookLoanTable.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "Nama", "ID Buku", "Judul",
                        "Tanggal Peminjaman", "Status" }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        jScrollPaneBookLoanTable.setViewportView(jTableBookLoanTable);

        jPanelFourContainer.add(jLabelBookLoanTableTitle, "wrap, grow");
        jPanelFourContainer.add(jScrollPaneBookLoanTable, "grow");

        add(jPanelOneContainer, "grow");
        add(jPanelTwoContainer, "grow");
        // add(jPanelThreeContainer, "grow");
        add(jPanelFourContainer, "grow");
    }

    public BookLoanTable getJTableBookLoanTable() {
        return this.jTableBookLoanTable;
    }
}

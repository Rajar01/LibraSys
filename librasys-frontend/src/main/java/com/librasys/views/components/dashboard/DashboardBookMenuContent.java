package com.librasys.views.components.dashboard;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.librasys.core.FontManager;
import com.librasys.utils.RoundedJPanel;
import com.librasys.views.BaseView;

import net.miginfocom.swing.MigLayout;

public class DashboardBookMenuContent extends BaseView {

    JButton jButtonAddBook, jButtonEditBook, jButtonDeleteBook;

    BookTable jTableBooksTable;

    @Override
    protected void initComponents() {
        setLayout(new MigLayout(
                "flowy, gap 0 24, ins 32, fill, aligny top",
                "[]",
                "[grow 0][fill]"));

        JPanel jPanelOneContainer = new JPanel(new MigLayout(
                "flowy, gap 0, ins 0, fillx",
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

        jPanelOneContainer.add(jPanelGreeting, "grow");
        jPanelOneContainer.add(jLabelDateNow, "grow");

        RoundedJPanel jPanelTwoContainer = new RoundedJPanel(new MigLayout(
                "ins 24, gap 0 16, fillx",
                "[fill]",
                "[][]"), 24);
        jPanelTwoContainer.setBackground(Color.WHITE);

        JPanel jPanelTitleAndAddBookButton = new JPanel(new MigLayout(
                "gap 0, ins 0, aligny center, fill",
                "[][align right]",
                "[align center]"));
        jPanelTitleAndAddBookButton.setOpaque(false);

        JLabel jLabelBooksTableTitle = new JLabel("Daftar Buku");
        jLabelBooksTableTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(20f));

        JPanel jPanelCrudButtonContainer = new JPanel(new MigLayout(
                "gap 0, ins 0, aligny center, fill",
                "[]4[]4[]",
                "[align center]"));

        jButtonAddBook = new JButton("Tambahkan Buku");
        jButtonAddBook.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));
        jButtonAddBook.setBackground(Color.decode("#8DBDFF"));
        jButtonAddBook.setForeground(Color.WHITE);
        jButtonAddBook.setFocusable(false);

        jButtonEditBook = new JButton("Edit Buku");
        jButtonEditBook.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));
        jButtonEditBook.setBackground(Color.decode("#8DBDFF"));
        jButtonEditBook.setForeground(Color.WHITE);
        jButtonEditBook.setFocusable(false);

        jButtonDeleteBook = new JButton("Hapus Buku");
        jButtonDeleteBook.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));
        jButtonDeleteBook.setBackground(Color.decode("#8DBDFF"));
        jButtonDeleteBook.setForeground(Color.WHITE);
        jButtonDeleteBook.setFocusable(false);

        jPanelCrudButtonContainer.add(jButtonAddBook, "height 48");
        jPanelCrudButtonContainer.add(jButtonEditBook, "height 48");
        jPanelCrudButtonContainer.add(jButtonDeleteBook, "height 48");

        jPanelTitleAndAddBookButton.add(jLabelBooksTableTitle);
        jPanelTitleAndAddBookButton.add(jPanelCrudButtonContainer);

        JScrollPane jScrollPaneBooksTable = new JScrollPane();
        jScrollPaneBooksTable.getViewport().setBorder(null);
        jScrollPaneBooksTable.setBorder(new EmptyBorder(0, 0, 0, 0));
        jScrollPaneBooksTable.getViewport().setBackground(Color.WHITE);
        jScrollPaneBooksTable.setVerticalScrollBar(new JScrollBar());
        jScrollPaneBooksTable.getVerticalScrollBar().setBackground(Color.WHITE);

        jTableBooksTable = new BookTable();
        jTableBooksTable.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "ISBN", "Judul", "Penulis", "Penerbit" }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        jScrollPaneBooksTable.setViewportView(jTableBooksTable);

        jPanelTwoContainer.add(jPanelTitleAndAddBookButton, "wrap, grow");
        jPanelTwoContainer.add(jScrollPaneBooksTable, "grow");

        add(jPanelOneContainer);
        add(jPanelTwoContainer, "grow");
    }

    public JButton getJButtonAddBook() {
        return this.jButtonAddBook;
    }


    public JButton getJButtonEditBook() {
        return this.jButtonEditBook;
    }

    public JButton getJButtonDeleteBook() {
        return this.jButtonDeleteBook;
    }


    public BookTable getJTableBooksTable() {
        return this.jTableBooksTable;
    }

}

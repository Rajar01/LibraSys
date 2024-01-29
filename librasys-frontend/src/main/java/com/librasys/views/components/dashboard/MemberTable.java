package com.librasys.views.components.dashboard;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.librasys.core.FontManager;
import com.librasys.utils.RoundedJLabel;

import net.miginfocom.swing.MigLayout;

public class MemberTable extends JTable {
    public MemberTable() {
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setFocusable(false);
        setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(48);
        getTableHeader().setEnabled(false);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i,
                    int i1) {
                TableHeader header = new TableHeader(
                        o + "",
                        FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(14f),
                        Color.BLACK,
                        new EmptyBorder(16, 0, 16, 0));
                return header;
            }
        });
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1,
                    int i, int i1) {
                Component comp = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                comp.setBackground(Color.WHITE);
                setBorder(new EmptyBorder(0, 0, 0, 0));
                if (i1 == 6) {
                    JPanel jPanelBookActionContainer = new JPanel(new MigLayout(
                            "gap 0, ins 0, align left center",
                            "[]8[]",
                            "[]"));
                    jPanelBookActionContainer.setOpaque(false);

                    FlatSVGIcon svgIconEditWhiteOutline = new FlatSVGIcon(
                            getClass().getResource("/icons/edit-white-outline-icon.svg"));
                    RoundedJLabel jLabelUpdateBookIcon = new RoundedJLabel(
                            "", svgIconEditWhiteOutline, 0, Color.decode("#8DBDFF"), 4);

                    FlatSVGIcon svgIconTrashbinWhiteOutline = new FlatSVGIcon(
                            getClass().getResource("/icons/trashbin-white-outline-icon.svg"));
                    RoundedJLabel jLabelDeleteBookIcon = new RoundedJLabel(
                            "", svgIconTrashbinWhiteOutline, 0, Color.decode("#8DBDFF"), 4);

                    jPanelBookActionContainer.add(jLabelUpdateBookIcon);
                    jPanelBookActionContainer.add(jLabelDeleteBookIcon);

                    return jPanelBookActionContainer;
                } else {
                    if (selected) {
                        comp.setForeground(Color.decode("#8DBDFF"));
                        comp.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(14f));
                    } else {
                        comp.setForeground(new Color(102, 102, 102));
                        comp.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(14f));
                    }
                    return comp;
                }
            }
        });
    }

    public void addData(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
}

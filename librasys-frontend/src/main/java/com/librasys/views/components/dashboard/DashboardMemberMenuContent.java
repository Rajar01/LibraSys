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

public class DashboardMemberMenuContent extends BaseView {
	private MemberTable jTableMembersTable;
	private JButton jButtonAddMember, jButtonEditMember, jButtonDeleteMember;

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

		JLabel jLabelBooksTableTitle = new JLabel("Daftar Anggota");
		jLabelBooksTableTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(20f));

		JPanel jPanelCrudButtonContainer = new JPanel(new MigLayout(
				"gap 0, ins 0, aligny center, fill",
				"[]4[]4[]",
				"[align center]"));

		jButtonAddMember = new JButton("Tambahkan Anggota");
		jButtonAddMember.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));
		jButtonAddMember.setBackground(Color.decode("#8DBDFF"));
		jButtonAddMember.setForeground(Color.WHITE);
		jButtonAddMember.setFocusable(false);

		jButtonEditMember = new JButton("Edit Data Anggota");
		jButtonEditMember.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));
		jButtonEditMember.setBackground(Color.decode("#8DBDFF"));
		jButtonEditMember.setForeground(Color.WHITE);
		jButtonEditMember.setFocusable(false);

		jButtonDeleteMember = new JButton("Hapus Data Anggota");
		jButtonDeleteMember.setFont(FontManager.INSTANCE.getFont("Roboto-Medium.ttf").deriveFont(16f));
		jButtonDeleteMember.setBackground(Color.decode("#8DBDFF"));
		jButtonDeleteMember.setForeground(Color.WHITE);
		jButtonDeleteMember.setFocusable(false);

		jPanelCrudButtonContainer.add(jButtonAddMember, "height 48");
		jPanelCrudButtonContainer.add(jButtonEditMember, "height 48");
		jPanelCrudButtonContainer.add(jButtonDeleteMember, "height 48");

		jPanelTitleAndAddBookButton.add(jLabelBooksTableTitle);
		jPanelTitleAndAddBookButton.add(jPanelCrudButtonContainer, "height 48");

		JScrollPane jScrollPaneMembersTable = new JScrollPane();
		jScrollPaneMembersTable.getViewport().setBorder(null);
		jScrollPaneMembersTable.setBorder(new EmptyBorder(0, 0, 0, 0));
		jScrollPaneMembersTable.getViewport().setBackground(Color.WHITE);
		jScrollPaneMembersTable.setVerticalScrollBar(new JScrollBar());
		jScrollPaneMembersTable.getVerticalScrollBar().setBackground(Color.WHITE);

		jTableMembersTable = new MemberTable();
		jTableMembersTable.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] { "ID", "Username", "Nama", "Email", "No.Handphone",
						"Alamat" }) {
			boolean[] canEdit = new boolean[] {
					false, false, false, false, false, false
			};

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		jScrollPaneMembersTable.setViewportView(jTableMembersTable);

		jPanelTwoContainer.add(jPanelTitleAndAddBookButton, "wrap, grow");
		jPanelTwoContainer.add(jScrollPaneMembersTable, "grow");

		add(jPanelOneContainer);
		add(jPanelTwoContainer, "grow");
	}

	public JButton getJButtonAddMember() {
		return this.jButtonAddMember;
	}

	public JButton getJButtonEditMember() {
		return this.jButtonEditMember;
	}

	public JButton getJButtonDeleteMember() {
		return this.jButtonDeleteMember;
	}

	public MemberTable getJTableMembersTable() {
		return this.jTableMembersTable;
	}
}

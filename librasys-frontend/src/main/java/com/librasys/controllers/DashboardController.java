package com.librasys.controllers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.google.gson.Gson;
import com.librasys.core.FontManager;
import com.librasys.core.ViewManager;
import com.librasys.models.Book;
import com.librasys.models.BookList;
import com.librasys.models.Loan;
import com.librasys.models.LoanDetail;
import com.librasys.models.LoanList;
import com.librasys.models.User;
import com.librasys.models.UserList;
import com.librasys.utils.JTextFieldWithPlaceholder;
import com.librasys.views.DashboardView;
import com.librasys.views.components.dashboard.BookLoanTable;
import com.librasys.views.components.dashboard.BookTable;
import com.librasys.views.components.dashboard.DashboardBookMenuContent;
import com.librasys.views.components.dashboard.DashboardMemberMenuContent;
import com.librasys.views.components.dashboard.DashboardOverviewMenuContent;
import com.librasys.views.components.dashboard.MemberTable;

import net.miginfocom.swing.MigLayout;

enum SideBarMenu {
    OVERVIEW_MENU, BOOK_MENU, MEMBER_MENU, ABOUT_MENU, EXIT_MENU
}

public class DashboardController extends BaseController {
    FlatSVGIcon svgIconGridOutline = new FlatSVGIcon(
            getClass().getResource("/icons/grid-outline-icon.svg"));
    FlatSVGIcon svgIconBookOutline = new FlatSVGIcon(
            getClass().getResource("/icons/book-outline-icon.svg"));
    FlatSVGIcon svgIconUsersGroupOutline = new FlatSVGIcon(
            getClass().getResource("/icons/users-group-outline-icon.svg"));
    FlatSVGIcon svgIconInfoCircleOutline = new FlatSVGIcon(
            getClass().getResource("/icons/info-circle-outline-icon.svg"));
    FlatSVGIcon svgIconCloseCirlcleOutline = new FlatSVGIcon(
            getClass().getResource("/icons/close-circle-outline-icon.svg"));

    FlatSVGIcon svgIconGridLightblueOutline = new FlatSVGIcon(
            getClass().getResource("/icons/grid-lightblue-outline-icon.svg"));
    FlatSVGIcon svgIconBookLightblueOutline = new FlatSVGIcon(
            getClass().getResource("/icons/book-lightblue-outline-icon.svg"));
    FlatSVGIcon svgIconUsersGroupLightblueOutline = new FlatSVGIcon(
            getClass().getResource("/icons/users-group-lightblue-outline-icon.svg"));
    FlatSVGIcon svgIconInfoCircleLightblueOutline = new FlatSVGIcon(
            getClass().getResource("/icons/info-circle-lightblue-outline-icon"));
    FlatSVGIcon svgIconCloseCirlcleLightblueOutline = new FlatSVGIcon(
            getClass().getResource("/icons/close-circle-lightblue-outline-icon.svg"));

    private DashboardView dashboardView;

    private SideBarMenu sidebarActiveMenu;

    DashboardBookMenuContent jPanelBookMenuContent;
    DashboardMemberMenuContent jPanelMemberMenuContent;
    DashboardOverviewMenuContent jPanelOverviewMenuContent;

    BookTable jTableBooksTable;
    MemberTable jTableMembersTable;
    BookLoanTable jTableBookLoanTable;

    @Override
    public void initController() {
        this.dashboardView = (DashboardView) ViewManager.INSTANCE.getView(DashboardView.class.getName());

        this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setForeground(Color.decode("#8DBDFF"));
        this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setIcon(svgIconGridLightblueOutline);

        this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        sidebarOverviewMenuClicked();
                    }
                });

        this.dashboardView.getDashboardSidebar().getJLabelBookMenu()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        sidebarBookMenuClicked();
                    }
                });

        this.dashboardView.getDashboardSidebar().getJLabelMemberMenu()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        sidebarMemberMenuClicked();
                    }
                });

        this.dashboardView.getDashboardSidebar().getJLabelAboutMenu()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // TODO change this code below
                        JOptionPane.showMessageDialog(null, "In Progress!");
                    }
                });

        this.dashboardView.getDashboardSidebar().getJLabelExitMenu()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        exit();
                    }
                });

        // Book
        jPanelBookMenuContent = new DashboardBookMenuContent();

        jTableBooksTable = jPanelBookMenuContent.getJTableBooksTable();

        for (Book book : getBookData()) {
            jTableBooksTable.addData(new Object[] {
                    book.getId(), book.getISBN(),
                    book.getTitle(), book.getAuthor(),
                    book.getPublisher()
            });
        }

        jPanelBookMenuContent.getJButtonAddBook()
                .addActionListener(e -> showBookForm("Form Penambahan Data Buku", new Book(), jTableBooksTable));
        jPanelBookMenuContent.getJButtonDeleteBook().addActionListener(e -> deleteBook());
        jPanelBookMenuContent.getJButtonEditBook().addActionListener(e -> {
            int selectedRow = jTableBooksTable.getSelectedRow();
            int bookId = Integer.parseInt(jTableBooksTable.getValueAt(selectedRow, 0).toString());

            if (selectedRow >= 0) {
                Gson gson = new Gson();

                try {
                    HttpRequest getRequest = HttpRequest.newBuilder()
                            .uri(new URI(String.format("http://localhost:8080/books/%s", bookId)))
                            .GET()
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());

                    if (getResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                        Book book = gson.fromJson(getResponse.body(), Book.class);
                        showBookForm("Form Pengeditan Data Buku", book,
                                jTableBooksTable);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, err.getMessage());
                }

            }

        });

        // Member
        jPanelMemberMenuContent = new DashboardMemberMenuContent();

        jTableMembersTable = jPanelMemberMenuContent.getJTableMembersTable();

        for (User user : getMemberData()) {
            jTableMembersTable.addData(new Object[] {
                    user.getId(), user.getUsername(),
                    user.getName(), user.getEmail(),
                    user.getPhoneNumber(), user.getAddress()
            });
        }

        jPanelMemberMenuContent.getJButtonAddMember()
                .addActionListener(e -> showMemberForm("Form Penambahan Data Anggota", new User(), jTableMembersTable));
        jPanelMemberMenuContent.getJButtonDeleteMember().addActionListener(e -> deleteMember());
        jPanelMemberMenuContent.getJButtonEditMember().addActionListener(e -> {
            int selectedRow = jTableMembersTable.getSelectedRow();
            int userId = Integer.parseInt(jTableMembersTable.getValueAt(selectedRow, 0).toString());

            if (selectedRow >= 0) {
                Gson gson = new Gson();

                try {
                    HttpRequest getRequest = HttpRequest.newBuilder()
                            .uri(new URI(String.format("http://localhost:8080/users/%s", userId)))
                            .GET()
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());

                    if (getResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                        User user = gson.fromJson(getResponse.body(), User.class);
                        showMemberForm("Form Pengeditan Data Anggota", user,
                                jTableMembersTable);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, err.getMessage());
                }

            }

        });

        // Loan
        jPanelOverviewMenuContent = new DashboardOverviewMenuContent();

        jTableBookLoanTable = jPanelOverviewMenuContent.getJTableBookLoanTable();

        for (Loan loan : getLoanData()) {
            User user = getUserById(loan.getUserId());
            for (LoanDetail loanDetail : loan.getLoanDetail()) {
                jTableBookLoanTable.addData(new Object[] {
                        user.getName(), loanDetail.getBook().getId(),
                        loanDetail.getBook().getTitle(),
                        loan.getLoanDate(), loanDetail.getStatus().getStatusName()
                });
            }
        }

        this.dashboardView.getDashboardSidebar().getJButtonLendAndReturnBookCTA()
                .addActionListener(e -> showLoanAndReturnForm("Form Peminjaman & Pengembalian Buku",
                        jTableBookLoanTable));
    }

    public void showLoanAndReturnForm(String title, BookLoanTable bookLoanTable) {
        JDialog dialog = new JDialog(ViewManager.INSTANCE.getFrame(), true);
        dialog.setSize(400, 600);
        dialog.setLayout(new MigLayout(
                "flowy, gap 0, ins 16, fillx",
                "[fill]",
                "[]16[]8[]8[]8[]16[]"));

        JLabel jLabelDialogTitle = new JLabel(title);
        jLabelDialogTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelDialogTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(24f));

        JPanel jPanelAction = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelAction = new JLabel("Aksi");
        jLabelAction.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JComboBox<String> jComboBoxAction = new JComboBox<>();
        jComboBoxAction.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jComboBoxAction.addItem("Peminjaman");
        jComboBoxAction.addItem("Pengembalian");
        jPanelAction.add(jLabelAction);
        jPanelAction.add(jComboBoxAction, "w 100%, h 16*3");

        JPanel jPanelFullname = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelFullname = new JLabel("Nama Lengkap");
        jLabelFullname.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JComboBox<String> jComboBoxFullname = new JComboBox<>();
        jComboBoxFullname.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        for (User user : getUserData()) {
            jComboBoxFullname.addItem(user.getName());
        }
        jPanelFullname.add(jLabelFullname);
        jPanelFullname.add(jComboBoxFullname, "w 100%, h 16*3");

        JPanel jPanelBookTitle = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelBookTitle = new JLabel("Judul Buku");
        jLabelBookTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JComboBox<String> jComboBoxBookTitle = new JComboBox<>();
        jComboBoxBookTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        for (Book book : getBookData()) {
            jComboBoxBookTitle.addItem(book.getTitle());
        }
        jPanelBookTitle.add(jLabelBookTitle);
        jPanelBookTitle.add(jComboBoxBookTitle, "w 100%, h 16*3");

        JPanel jPanelStatus = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelStatus = new JLabel("Status");
        jLabelStatus.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JComboBox<String> jComboBoxStatus = new JComboBox<>();
        jComboBoxStatus.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jComboBoxStatus.addItem("Dipinjamkan");
        jComboBoxStatus.addItem("Dikembalikan");
        jComboBoxStatus.addItem("Terlambat");
        jPanelStatus.add(jLabelStatus);
        jPanelStatus.add(jComboBoxStatus, "w 100%, h 16*3");

        JButton jButtonLoanOrReturn = new JButton("Ok");

        jButtonLoanOrReturn.addActionListener(e -> {
            Gson gson = new Gson();
            Loan loan = new Loan();

            if (jComboBoxAction.getModel().getSelectedItem().equals("Peminjaman")) {
                for (User user : getUserData()) {
                    if (user.getName().equals(jComboBoxFullname.getModel().getSelectedItem())) {
                        loan.setUserId(user.getId());
                        break;
                    }
                }

                LocalDateTime loanDate = LocalDateTime.now();
                OffsetDateTime offsetDateTimeLoanDate = loanDate.atOffset(ZoneOffset.UTC);
                DateTimeFormatter formatterLoanDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX");
                String formattedLoanDate = offsetDateTimeLoanDate.format(formatterLoanDate);
                loan.setLoanDate(formattedLoanDate);

                LoanDetail loanDetail = new LoanDetail();

                LocalDateTime returnDate = LocalDateTime.now().plusDays(3);
                OffsetDateTime offsetDateTimeReturnDate = returnDate.atOffset(ZoneOffset.UTC);
                DateTimeFormatter formatterReturnDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX");
                String formattedReturnDate = offsetDateTimeReturnDate.format(formatterReturnDate);
                loanDetail.setReturnDate(formattedReturnDate);

                for (Book book : getBookData()) {
                    if (book.getTitle().equals(jComboBoxBookTitle.getModel().getSelectedItem())) {
                        loanDetail.setBookId(book.getId());
                        break;
                    }
                }

                if (jComboBoxStatus.getModel().getSelectedItem().equals("Dipinjamkan")) {
                    loanDetail.setStatusId(1);
                } else if (jComboBoxStatus.getModel().getSelectedItem().equals("Dikembalikan")) {
                    loanDetail.setStatusId(2);
                } else {
                    loanDetail.setStatusId(3);
                }

                loan.setLoanDetail(new ArrayList<LoanDetail>(Arrays.asList(loanDetail)));

                try {
                    HttpRequest postRequest = HttpRequest.newBuilder()
                            .uri(new URI("http://localhost:8080/loans"))
                            .POST(BodyPublishers.ofString(gson.toJson(loan)))
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> postResponse = httpClient.send(postRequest,
                            BodyHandlers.ofString());

                    if (postResponse.statusCode() == HttpURLConnection.HTTP_CREATED) {
                        User user = getUserById(loan.getUserId());

                        jTableBookLoanTable.addData(new Object[] {
                                user.getName(),
                                getLoanData().getLast().getLoanDetail().getLast().getBookId(),
                                getLoanData().getLast().getLoanDetail().getLast().getBook().getTitle(),
                                getLoanData().getLast().getLoanDate(),
                                getLoanData().getLast().getLoanDetail().getLast().getStatus().getStatusName()
                        });

                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(
                                dashboardView.getParent(),
                                "Gagal melakukan peminjaman buku",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE,
                            err.getMessage());
                }
            } else {
                // Return
                for (User user : getUserData()) {
                    if (user.getName().equals(jComboBoxFullname.getModel().getSelectedItem())) {
                        loan.setUserId(user.getId());
                        break;
                    }
                }

                LoanDetail loanDetail = new LoanDetail();

                for (Book book : getBookData()) {
                    if (book.getTitle().equals(jComboBoxBookTitle.getModel().getSelectedItem())) {
                        loanDetail.setBookId(book.getId());
                        break;
                    }
                }

                loan.setLoanDetail(new ArrayList<>(Arrays.asList(loanDetail)));

                for (Loan l : getLoanData()) {
                    if (l.getUserId() == loan.getUserId() &&
                            l.getLoanDetail().getLast().getBookId() == loan.getLoanDetail().getLast().getBookId()) {
                        loan = l;
                        break;
                    }
                }

                if (jComboBoxStatus.getModel().getSelectedItem().equals("Dipinjamkan")) {
                    loan.getLoanDetail().getLast().setStatusId(1);
                } else if (jComboBoxStatus.getModel().getSelectedItem().equals("Dikembalikan")) {
                    loan.getLoanDetail().getLast().setStatusId(2);
                } else {
                    loan.getLoanDetail().getLast().setStatusId(3);
                }

                try {
                    HttpRequest postRequest = HttpRequest.newBuilder()
                            .uri(new URI(String.format("http://localhost:8080/loans/%s", loan.getId())))
                            .PUT(BodyPublishers.ofString(gson.toJson(loan)))
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> postResponse = httpClient.send(postRequest,
                            BodyHandlers.ofString());

                    if (postResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                        User user = getUserById(loan.getUserId());
                        Book book = loan.getLoanDetail().getLast().getBook();

                        for (int i = 0; i < ((DefaultTableModel) jTableBookLoanTable.getModel()).getRowCount(); i++) {
                            if (((DefaultTableModel) jTableBookLoanTable.getModel()).getValueAt(i, 0)
                                    .equals(user.getName()) &&
                                    ((DefaultTableModel) jTableBookLoanTable.getModel()).getValueAt(i, 1)
                                            .equals(book.getId())) {
                                ((DefaultTableModel) jTableBookLoanTable.getModel())
                                        .setValueAt(loan.getLoanDetail().getLast().getStatus().getStatusName(), i, 4);
                            }
                        }

                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(
                                dashboardView.getParent(),
                                "Gagal melakukan pengembalian buku",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE,
                            err.getMessage());
                }
            }
        });

        jButtonLoanOrReturn.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(20f));
        jButtonLoanOrReturn.setBackground(Color.decode("#8DBDFF"));
        jButtonLoanOrReturn.setForeground(Color.WHITE);

        dialog.add(jLabelDialogTitle);
        dialog.add(jPanelAction);
        dialog.add(jPanelFullname);
        dialog.add(jPanelBookTitle);
        dialog.add(jPanelStatus);
        dialog.add(jButtonLoanOrReturn, "w 100%, h 16*3");

        dialog.setLocationRelativeTo(ViewManager.INSTANCE.getFrame());
        dialog.pack();
        dialog.setVisible(true);
    }

    public List<User> getUserData() {
        List<User> users = new ArrayList<>();
        Gson gson = new Gson();

        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/users"))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());

            if (getResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                for (User user : gson.fromJson(getResponse.body(), UserList.class).getUsers()) {
                    if (user.getRoleId() == 2) {
                        users.add(user);
                    }
                }

                return users;
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return null;
    }

    public User getUserById(long userId) {
        Gson gson = new Gson();

        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(String.format("http://localhost:8080/users/%s", userId)))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());

            if (getResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                return gson.fromJson(getResponse.body(), User.class);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return null;
    }

    public List<Loan> getLoanData() {
        Gson gson = new Gson();

        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/loans"))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());

            if (getResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                return gson.fromJson(getResponse.body(), LoanList.class).getLoans();
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return null;
    }

    public void deleteMember() {
        int selectedRow = jTableMembersTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = Integer.parseInt(jTableMembersTable.getValueAt(selectedRow, 0).toString());

            int respond = JOptionPane.showConfirmDialog(
                    dashboardView,
                    "Apakah anda yakin ingin menghapus?",
                    "Konfirmasi Penghapusan",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (respond == JOptionPane.YES_OPTION) {
                try {
                    HttpRequest deleteRequest = HttpRequest.newBuilder()
                            .uri(new URI(String.format("http://localhost:8080/users/%s", userId)))
                            .DELETE()
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, BodyHandlers.ofString());

                    if (deleteResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                        ((DefaultTableModel) jTableMembersTable.getModel()).removeRow(selectedRow);

                    } else {
                        JOptionPane.showMessageDialog(
                                dashboardView.getParent(),
                                "Gagal menghapus data anggota",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, err.getMessage());
                }
            }
        }
    }

    public void showMemberForm(String title, User user, MemberTable memberTable) {
        JDialog dialog = new JDialog(ViewManager.INSTANCE.getFrame(), true);
        dialog.setSize(400, 600);
        dialog.setLayout(new MigLayout(
                "flowy, gap 0, ins 16, fillx",
                "[fill]",
                "[]16[]8[]8[]8[]8[]8[]16[]"));

        JLabel jLabelDialogTitle = new JLabel(title);
        jLabelDialogTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelDialogTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(24f));

        JPanel jPanelUsername = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelUsername = new JLabel("Username");
        jLabelUsername.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JTextFieldWithPlaceholder jTextFieldWithPlaceholderUsername = new JTextFieldWithPlaceholder("Username");
        jTextFieldWithPlaceholderUsername.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jPanelUsername.add(jLabelUsername);
        jPanelUsername.add(jTextFieldWithPlaceholderUsername, "w 100%, h 16*3");

        JPanel jPanelName = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelName = new JLabel("Nama");
        jLabelName.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JTextFieldWithPlaceholder jTextFieldWithPlaceholderNama = new JTextFieldWithPlaceholder("Nama");
        jTextFieldWithPlaceholderNama.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jPanelName.add(jLabelName);
        jPanelName.add(jTextFieldWithPlaceholderNama, "w 100%, h 16*3");

        JPanel jPanelEmail = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelEmail = new JLabel("Email");
        jLabelEmail.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JTextFieldWithPlaceholder jTextFieldWithPlaceholderEmail = new JTextFieldWithPlaceholder("Email");
        jTextFieldWithPlaceholderEmail.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jPanelEmail.add(jLabelEmail);
        jPanelEmail.add(jTextFieldWithPlaceholderEmail, "w 100%, h 16*3");

        JPanel jPanelPhoneNumber = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelPhoneNumber = new JLabel("Nomor Handphone");
        jLabelPhoneNumber.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JTextFieldWithPlaceholder jTextFieldWithPlaceholderPhoneNumber = new JTextFieldWithPlaceholder(
                "Nomor Handphone");
        jTextFieldWithPlaceholderPhoneNumber
                .setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jPanelPhoneNumber.add(jLabelPhoneNumber);
        jPanelPhoneNumber.add(jTextFieldWithPlaceholderPhoneNumber, "w 100%, h 16*3");

        JPanel jPanelAddress = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelAddress = new JLabel("Alamat");
        jLabelAddress.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JTextFieldWithPlaceholder jTextFieldWithPlaceholderAddress = new JTextFieldWithPlaceholder(
                "Alamat");
        jTextFieldWithPlaceholderAddress
                .setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jPanelAddress.add(jLabelAddress);
        jPanelAddress.add(jTextFieldWithPlaceholderAddress, "w 100%, h 16*3");

        JButton jButtonAddMember = new JButton("Tambahkan Anggota");

        if (title == "Form Pengeditan Data Anggota") {
            jTextFieldWithPlaceholderUsername.setText(user.getUsername());
            jTextFieldWithPlaceholderNama.setText(user.getName());
            jTextFieldWithPlaceholderEmail.setText(user.getEmail());
            jTextFieldWithPlaceholderPhoneNumber.setText(user.getPhoneNumber());
            jTextFieldWithPlaceholderAddress.setText(user.getAddress());
            jButtonAddMember.setText("Simpan Perubahan");

            jButtonAddMember.addActionListener(e -> {
                user.setUsername(jTextFieldWithPlaceholderUsername.getText());
                user.setName(jTextFieldWithPlaceholderNama.getText());
                user.setEmail(jTextFieldWithPlaceholderEmail.getText());
                user.setPhoneNumber(jTextFieldWithPlaceholderPhoneNumber.getText());
                user.setAddress(jTextFieldWithPlaceholderAddress.getText());

                Gson gson = new Gson();

                try {
                    HttpRequest postRequest = HttpRequest.newBuilder()
                            .uri(new URI(String.format("http://localhost:8080/users/%s", user.getId())))
                            .PUT(BodyPublishers.ofString(gson.toJson(user)))
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());

                    if (postResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                        for (int i = 0; i < ((DefaultTableModel) jTableMembersTable.getModel()).getRowCount(); i++) {
                            if (((DefaultTableModel) jTableMembersTable.getModel()).getValueAt(i, 0)
                                    .equals(user.getId())) {
                                ((DefaultTableModel) jTableMembersTable.getModel()).setValueAt(user.getUsername(), i,
                                        1);
                                ((DefaultTableModel) jTableMembersTable.getModel()).setValueAt(user.getName(), i, 2);
                                ((DefaultTableModel) jTableMembersTable.getModel()).setValueAt(user.getEmail(), i, 3);
                                ((DefaultTableModel) jTableMembersTable.getModel()).setValueAt(user.getPhoneNumber(), i,
                                        4);
                                ((DefaultTableModel) jTableMembersTable.getModel()).setValueAt(user.getAddress(), i, 5);
                            }
                        }

                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(
                                dashboardView.getParent(),
                                "Gagal menyimpan perubahan data anggota",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, err.getMessage());
                }
            });
        } else {
            jButtonAddMember.addActionListener(e -> {
                user.setUsername(jTextFieldWithPlaceholderUsername.getText());
                user.setName(jTextFieldWithPlaceholderNama.getText());
                user.setEmail(jTextFieldWithPlaceholderEmail.getText());
                user.setPhoneNumber(jTextFieldWithPlaceholderPhoneNumber.getText());
                user.setAddress(jTextFieldWithPlaceholderAddress.getText());
                user.setRoleId(2);

                Gson gson = new Gson();

                try {
                    HttpRequest postRequest = HttpRequest.newBuilder()
                            .uri(new URI("http://localhost:8080/users"))
                            .POST(BodyPublishers.ofString(gson.toJson(user)))
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());

                    if (postResponse.statusCode() == HttpURLConnection.HTTP_CREATED) {
                        jTableMembersTable.addData(new Object[] {
                                getMemberData().getLast().getId(), getMemberData().getLast().getUsername(),
                                getMemberData().getLast().getName(), getMemberData().getLast().getEmail(),
                                getMemberData().getLast().getPhoneNumber(), getMemberData().getLast().getAddress()
                        });

                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(
                                dashboardView.getParent(),
                                "Gagal menambahkan data anggota",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, err.getMessage());
                }
            });
        }

        jButtonAddMember.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(20f));
        jButtonAddMember.setBackground(Color.decode("#8DBDFF"));
        jButtonAddMember.setForeground(Color.WHITE);

        dialog.add(jLabelDialogTitle);
        dialog.add(jPanelUsername);
        dialog.add(jPanelName);
        dialog.add(jPanelEmail);
        dialog.add(jPanelPhoneNumber);
        dialog.add(jPanelAddress);
        dialog.add(jButtonAddMember, "w 100%, h 16*3");

        dialog.setLocationRelativeTo(ViewManager.INSTANCE.getFrame());
        dialog.pack();
        dialog.setVisible(true);
    }

    public List<User> getMemberData() {
        Gson gson = new Gson();

        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/users"))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());

            if (getResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                return gson.fromJson(getResponse.body(), UserList.class).getUsers();
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return null;
    }

    public void deleteBook() {
        int selectedRow = jTableBooksTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bookId = Integer.parseInt(jTableBooksTable.getValueAt(selectedRow, 0).toString());

            int respond = JOptionPane.showConfirmDialog(
                    dashboardView,
                    "Apakah anda yakin ingin menghapus?",
                    "Konfirmasi Penghapusan",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (respond == JOptionPane.YES_OPTION) {
                try {
                    HttpRequest deleteRequest = HttpRequest.newBuilder()
                            .uri(new URI(String.format("http://localhost:8080/books/%s", bookId)))
                            .DELETE()
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, BodyHandlers.ofString());

                    if (deleteResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                        ((DefaultTableModel) jTableBooksTable.getModel()).removeRow(selectedRow);

                    } else {
                        JOptionPane.showMessageDialog(
                                dashboardView.getParent(),
                                "Gagal menghapus data buku",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, err.getMessage());
                }
            }
        }
    }

    public void showBookForm(String title, Book book, BookTable bookTable) {
        JDialog dialog = new JDialog(ViewManager.INSTANCE.getFrame(), true);
        dialog.setSize(400, 600);
        dialog.setLayout(new MigLayout(
                "flowy, gap 0, ins 16, fillx",
                "[fill]",
                "[]16[]8[]8[]8[]16[]"));

        JLabel jLabelDialogTitle = new JLabel(title);
        jLabelDialogTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelDialogTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(24f));

        JPanel jPanelISBN = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelISBN = new JLabel("ISBN");
        jLabelISBN.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JTextFieldWithPlaceholder jTextFieldWithPlaceholderISBN = new JTextFieldWithPlaceholder("ISBN");
        jTextFieldWithPlaceholderISBN.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jPanelISBN.add(jLabelISBN);
        jPanelISBN.add(jTextFieldWithPlaceholderISBN, "w 100%, h 16*3");

        JPanel jPanelTitle = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelTitle = new JLabel("Judul Buku");
        jLabelTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JTextFieldWithPlaceholder jTextFieldWithPlaceholderTitle = new JTextFieldWithPlaceholder("Judul buku");
        jTextFieldWithPlaceholderTitle.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jPanelTitle.add(jLabelTitle);
        jPanelTitle.add(jTextFieldWithPlaceholderTitle, "w 100%, h 16*3");

        JPanel jPanelAuthor = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelAuthor = new JLabel("Penulis Buku");
        jLabelAuthor.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JTextFieldWithPlaceholder jTextFieldWithPlaceholderAuthor = new JTextFieldWithPlaceholder("Penulis buku");
        jTextFieldWithPlaceholderAuthor.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jPanelAuthor.add(jLabelAuthor);
        jPanelAuthor.add(jTextFieldWithPlaceholderAuthor, "w 100%, h 16*3");

        JPanel jPanelPublisher = new JPanel(new MigLayout(
                "flowy, gap 0 8, ins 0, fillx",
                "",
                ""));
        JLabel jLabelPublisher = new JLabel("Penerbit Buku");
        jLabelPublisher.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(16f));
        JTextFieldWithPlaceholder jTextFieldWithPlaceholderPublisher = new JTextFieldWithPlaceholder("Penulis buku");
        jTextFieldWithPlaceholderPublisher.setFont(FontManager.INSTANCE.getFont("Roboto-Regular.ttf").deriveFont(16f));
        jPanelPublisher.add(jLabelPublisher);
        jPanelPublisher.add(jTextFieldWithPlaceholderPublisher, "w 100%, h 16*3");

        JButton jButtonAddBook = new JButton("Tambahkan Buku");

        if (title == "Form Pengeditan Data Buku") {
            jTextFieldWithPlaceholderISBN.setText(book.getISBN());
            jTextFieldWithPlaceholderTitle.setText(book.getTitle());
            jTextFieldWithPlaceholderAuthor.setText(book.getAuthor());
            jTextFieldWithPlaceholderPublisher.setText(book.getPublisher());
            jButtonAddBook.setText("Simpan Perubahan");

            jButtonAddBook.addActionListener(e -> {
                book.setISBN(jTextFieldWithPlaceholderISBN.getText());
                book.setTitle(jTextFieldWithPlaceholderTitle.getText());
                book.setAuthor(jTextFieldWithPlaceholderAuthor.getText());
                book.setPublisher(jTextFieldWithPlaceholderPublisher.getText());

                Gson gson = new Gson();

                try {
                    HttpRequest postRequest = HttpRequest.newBuilder()
                            .uri(new URI(String.format("http://localhost:8080/books/%s", book.getId())))
                            .PUT(BodyPublishers.ofString(gson.toJson(book)))
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());

                    if (postResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                        for (int i = 0; i < ((DefaultTableModel) jTableBooksTable.getModel()).getRowCount(); i++) {
                            if (((DefaultTableModel) jTableBooksTable.getModel()).getValueAt(i, 0)
                                    .equals(book.getId())) {
                                ((DefaultTableModel) jTableBooksTable.getModel()).setValueAt(book.getISBN(), i, 1);
                                ((DefaultTableModel) jTableBooksTable.getModel()).setValueAt(book.getTitle(), i, 2);
                                ((DefaultTableModel) jTableBooksTable.getModel()).setValueAt(book.getAuthor(), i, 3);
                                ((DefaultTableModel) jTableBooksTable.getModel()).setValueAt(book.getPublisher(), i, 4);
                            }
                        }

                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(
                                dashboardView.getParent(),
                                "Gagal menyimpan perubahan data buku",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, err.getMessage());
                }
            });
        } else {
            jButtonAddBook.addActionListener(e -> {
                book.setISBN(jTextFieldWithPlaceholderISBN.getText());
                book.setTitle(jTextFieldWithPlaceholderTitle.getText());
                book.setAuthor(jTextFieldWithPlaceholderAuthor.getText());
                book.setPublisher(jTextFieldWithPlaceholderPublisher.getText());

                Gson gson = new Gson();

                try {
                    HttpRequest postRequest = HttpRequest.newBuilder()
                            .uri(new URI("http://localhost:8080/books"))
                            .POST(BodyPublishers.ofString(gson.toJson(book)))
                            .build();

                    HttpClient httpClient = HttpClient.newHttpClient();
                    HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());

                    if (postResponse.statusCode() == HttpURLConnection.HTTP_CREATED) {
                        jTableBooksTable.addData(new Object[] {
                                getBookData().getLast().getId(), getBookData().getLast().getISBN(),
                                getBookData().getLast().getTitle(), getBookData().getLast().getAuthor(),
                                getBookData().getLast().getPublisher()
                        });

                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(
                                dashboardView.getParent(),
                                "Gagal menambahkan data buku",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (URISyntaxException | IOException | InterruptedException err) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, err.getMessage());
                }
            });
        }

        jButtonAddBook.setFont(FontManager.INSTANCE.getFont("Roboto-Bold.ttf").deriveFont(20f));
        jButtonAddBook.setBackground(Color.decode("#8DBDFF"));
        jButtonAddBook.setForeground(Color.WHITE);

        dialog.add(jLabelDialogTitle);
        dialog.add(jPanelISBN);
        dialog.add(jPanelTitle);
        dialog.add(jPanelAuthor);
        dialog.add(jPanelPublisher);
        dialog.add(jButtonAddBook, "w 100%, h 16*3");

        dialog.setLocationRelativeTo(ViewManager.INSTANCE.getFrame());
        dialog.pack();
        dialog.setVisible(true);
    }

    public List<Book> getBookData() {
        Gson gson = new Gson();

        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/books"))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());

            if (getResponse.statusCode() == HttpURLConnection.HTTP_OK) {
                return gson.fromJson(getResponse.body(), BookList.class).getBooks();
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return null;
    }

    public void sidebarOverviewMenuClicked() {
        this.dashboardView.getJScrollPaneContentContainer().setViewportView(null);
        this.dashboardView.getJScrollPaneContentContainer().setViewportView(jPanelOverviewMenuContent);
        setSidebarActiveMenu(SideBarMenu.OVERVIEW_MENU);
        this.dashboardView.repaint();
        this.dashboardView.revalidate();
    }

    public void sidebarBookMenuClicked() {
        this.dashboardView.getJScrollPaneContentContainer().setViewportView(null);
        this.dashboardView.getJScrollPaneContentContainer().setViewportView(jPanelBookMenuContent);
        setSidebarActiveMenu(SideBarMenu.BOOK_MENU);
        this.dashboardView.revalidate();
        this.dashboardView.repaint();
    }

    public void sidebarMemberMenuClicked() {
        this.dashboardView.getJScrollPaneContentContainer().setViewportView(null);
        this.dashboardView.getJScrollPaneContentContainer().setViewportView(jPanelMemberMenuContent);
        setSidebarActiveMenu(SideBarMenu.MEMBER_MENU);
        this.dashboardView.revalidate();
        this.dashboardView.repaint();
    }

    public void exit() {
        this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setForeground(Color.BLACK);
        this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setForeground(Color.BLACK);
        this.dashboardView.getDashboardSidebar().getJLabelMemberMenu().setForeground(Color.BLACK);
        this.dashboardView.getDashboardSidebar().getJLabelAboutMenu().setForeground(Color.BLACK);
        this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setForeground(Color.decode("#8DBDFF"));

        this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setIcon(svgIconGridOutline);
        this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setIcon(svgIconBookOutline);
        this.dashboardView.getDashboardSidebar().getJLabelMemberMenu()
                .setIcon(svgIconUsersGroupOutline);
        this.dashboardView.getDashboardSidebar().getJLabelAboutMenu()
                .setIcon(svgIconInfoCircleOutline);
        this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setIcon(svgIconCloseCirlcleLightblueOutline);

        int respond = JOptionPane.showConfirmDialog(
                dashboardView,
                "Apakah anda yakin ingin menutup",
                "Konfirmasi Tutup",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (respond == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            setSidebarActiveMenu(getSidebarActiveMenu());
        }
    }

    public SideBarMenu getSidebarActiveMenu() {
        return this.sidebarActiveMenu;
    }

    public void setSidebarActiveMenu(SideBarMenu sideBarMenu) {
        switch (sideBarMenu) {
            case OVERVIEW_MENU:
                sidebarActiveMenu = SideBarMenu.OVERVIEW_MENU;
                this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setForeground(Color.decode("#8DBDFF"));
                this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelMemberMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelAboutMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setForeground(Color.BLACK);

                this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setIcon(svgIconGridLightblueOutline);
                this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setIcon(svgIconBookOutline);
                this.dashboardView.getDashboardSidebar().getJLabelMemberMenu().setIcon(svgIconUsersGroupOutline);
                this.dashboardView.getDashboardSidebar().getJLabelAboutMenu().setIcon(svgIconInfoCircleOutline);
                this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setIcon(svgIconCloseCirlcleOutline);
                break;
            case BOOK_MENU:
                sidebarActiveMenu = SideBarMenu.BOOK_MENU;
                this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setForeground(Color.decode("#8DBDFF"));
                this.dashboardView.getDashboardSidebar().getJLabelMemberMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelAboutMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setForeground(Color.BLACK);

                this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setIcon(svgIconGridOutline);
                this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setIcon(svgIconBookLightblueOutline);
                this.dashboardView.getDashboardSidebar().getJLabelMemberMenu().setIcon(svgIconUsersGroupOutline);
                this.dashboardView.getDashboardSidebar().getJLabelAboutMenu().setIcon(svgIconInfoCircleOutline);
                this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setIcon(svgIconCloseCirlcleOutline);
                break;
            case MEMBER_MENU:
                sidebarActiveMenu = SideBarMenu.MEMBER_MENU;
                this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelMemberMenu().setForeground(Color.decode("#8DBDFF"));
                this.dashboardView.getDashboardSidebar().getJLabelAboutMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setForeground(Color.BLACK);

                this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setIcon(svgIconGridOutline);
                this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setIcon(svgIconBookOutline);
                this.dashboardView.getDashboardSidebar().getJLabelMemberMenu()
                        .setIcon(svgIconUsersGroupLightblueOutline);
                this.dashboardView.getDashboardSidebar().getJLabelAboutMenu().setIcon(svgIconInfoCircleOutline);
                this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setIcon(svgIconCloseCirlcleOutline);
                break;
            case ABOUT_MENU:
                sidebarActiveMenu = SideBarMenu.ABOUT_MENU;
                this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelMemberMenu().setForeground(Color.BLACK);
                this.dashboardView.getDashboardSidebar().getJLabelAboutMenu().setForeground(Color.decode("#8DBDFF"));
                this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setForeground(Color.BLACK);

                this.dashboardView.getDashboardSidebar().getJLabelOverviewMenu().setIcon(svgIconGridOutline);
                this.dashboardView.getDashboardSidebar().getJLabelBookMenu().setIcon(svgIconBookOutline);
                this.dashboardView.getDashboardSidebar().getJLabelMemberMenu()
                        .setIcon(svgIconUsersGroupOutline);
                this.dashboardView.getDashboardSidebar().getJLabelAboutMenu()
                        .setIcon(svgIconInfoCircleLightblueOutline);
                this.dashboardView.getDashboardSidebar().getJLabelExitMenu().setIcon(svgIconCloseCirlcleOutline);
                break;
            default:
                break;
        }
    }
}

package com.librasys.views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.librasys.views.components.dashboard.DashboardOverviewMenuContent;
import com.librasys.views.components.dashboard.DashboardSidebar;

import net.miginfocom.swing.MigLayout;

public class DashboardView extends BaseView {
    private DashboardSidebar dashboardSidebar;
    private JPanel jPanelContentContainer;
    private JScrollPane jScrollPaneContentContainer;

    @Override
    protected void initComponents() {
        setLayout(new MigLayout(
                "flowx, gap 0, ins 0",
                "[15%][85%]",
                "[100%]"));

        dashboardSidebar = new DashboardSidebar();

        jScrollPaneContentContainer = new JScrollPane(new DashboardOverviewMenuContent());
        jScrollPaneContentContainer.setBorder(new EmptyBorder(0, 0, 0, 0));
        jScrollPaneContentContainer.getVerticalScrollBar().setUnitIncrement(32);

        add(dashboardSidebar, "width 100%, height 100%");
        add(jScrollPaneContentContainer, "width 100%, height 100%");
    }

    public DashboardSidebar getDashboardSidebar() {
        return this.dashboardSidebar;
    }

    public JPanel getJPanelContentContainer() {
        return this.jPanelContentContainer;
    }

    public void setJPanelContentContainer(JPanel jPanelContentContainer) {
        this.jPanelContentContainer = jPanelContentContainer;
    }

    public JScrollPane getJScrollPaneContentContainer() {
        return this.jScrollPaneContentContainer;
    }
}

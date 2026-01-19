package com.beyza.earthquake.ui;

import com.beyza.earthquake.model.*;
import com.beyza.earthquake.service.BagService;
import com.beyza.earthquake.repository.BagRepository;
import com.beyza.earthquake.repository.TxtBagRepository;
import com.beyza.earthquake.exception.OverweightBagException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MainGUI extends JFrame {

    private BagService bagService;
    private BagRepository bagRepository;
    
    private EmergencyBag individualBag;
    private List<FamilyMember> currentFamilyMembers;
    private FamilyMember selectedFamilyMember;
    private DefaultListModel<SavedRecord> historyListModel;
    
    private int currentLang = 0; 
    private Map<String, String[]> dict;

    private JTabbedPane tabbedPane;
    private JLabel lblBrand;
    private JLabel lblName, lblProfile, lblDoluluk, lblProdName, lblProdWeight, lblProdCost;
    private JButton btnApplyProfile, btnAdd, btnDel, btnBasic, btnSave;
    private JLabel indStatusLabel, famHeaderLabel, famTotalWeightLabel;
    private JButton btnLanguage;
    
    private JTextField indNameField;
    private JComboBox<String> indProfileBox;
    private JComboBox<String> indRiskBox;
    private JTextField indItemNameField, indItemWeightField, indItemCostField;
    private JProgressBar indProgressBar;
    private DefaultTableModel indTableModel;
    private JTable indTable;
    private JTextArea indMissingArea;
    private JProgressBar survivalProgressBar;
    private JPanel indScorePanel;

    private JTextField familyNameField;
    private JButton btnSaveFamily;
    private DefaultListModel<String> familyMemberListModel;
    private JList<String> familyMemberList;
    private JTextField famMemberNameField;
    private JComboBox<String> famMemberTypeBox;
    private JComboBox<String> famRiskBox;
    private JButton btnAddMem, btnRemMem;
    
    private JPanel famEditorPanel;
    private JPanel leftMemberPanel; 
    private JTextField famItemNameField, famItemWeightField, famItemCostField;
    private DefaultTableModel famBagTableModel;
    private JTable famTable;
    private JProgressBar famMemberProgressBar;
    private JLabel famMemberStatusLabel;
    private JTextArea famMissingArea;
    private JButton btnFamAdd, btnFamDel, btnFamBasic;
    
    private JList<SavedRecord> historyList;
    private JTextArea historyDetailsArea;
    private JButton btnClearHistory, btnExportHistory;
    private JPanel histListPanel, histDetailPanel;
    private JPanel indEditorPanel;
    
    private JLabel lblFamNameInput, lblFamTypeInput, lblFamRisk;
    private JLabel lblFamProd, lblFamKg, lblFamCost;
    
    private TitledBorder borderIndEditor, borderScore, borderFamMembers, borderFamBag, borderHistList, borderHistDetail;

    private final Color CLR_BG_MAIN = new Color(236, 240, 241);
    private final Color CLR_PANEL_BG = Color.WHITE;
    private final Color CLR_BLUE = new Color(52, 152, 219);
    private final Color CLR_GREEN = new Color(46, 204, 113);
    private final Color CLR_RED = new Color(231, 76, 60);
    private final Color CLR_ORANGE = new Color(241, 196, 15);
    private final Color CLR_DARK = new Color(44, 62, 80);
    private final Color CLR_TEXT = new Color(30, 30, 30);
    
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13);

    public MainGUI() {
        bagService = new BagService();
        bagRepository = new TxtBagRepository();
        
        initDictionary();
        individualBag = new EmergencyBag(15.0);
        currentFamilyMembers = new ArrayList<>();
        historyListModel = new DefaultListModel<>();

        setTitle(t("app_title"));
        setSize(1350, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(CLR_BG_MAIN);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Label.foreground", CLR_TEXT);
            UIManager.put("Button.foreground", Color.BLACK);
            UIManager.put("TitledBorder.titleColor", CLR_DARK);
            UIManager.put("TitledBorder.font", FONT_TITLE);
        } catch (Exception e) {}

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(CLR_BG_MAIN);
        
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CLR_PANEL_BG);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 2, 0, new Color(200, 200, 200)),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        lblBrand = new JLabel("AKILLI DEPREM ÇANTASI PLANLAMA ASİSTANI");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBrand.setForeground(CLR_BLUE);
        topBar.add(lblBrand, BorderLayout.WEST);

        btnLanguage = createButton("TR / EN", new Color(220, 220, 220));
        btnLanguage.setPreferredSize(new Dimension(100, 35));
        btnLanguage.addActionListener(e -> toggleLanguage());
        topBar.add(btnLanguage, BorderLayout.EAST);
        
        mainContainer.add(topBar, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tabbedPane.setBackground(CLR_BG_MAIN);
        
        tabbedPane.addTab(t("tab_ind"), createIndividualPanel());
        tabbedPane.addTab(t("tab_fam"), createFamilyPanel());
        tabbedPane.addTab(t("tab_hist"), createHistoryPanel());

        mainContainer.add(tabbedPane, BorderLayout.CENTER);
        add(mainContainer);
        
        checkIndividualMissing();
    }

    private JPanel createIndividualPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(CLR_BG_MAIN);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setBackground(CLR_PANEL_BG);
        topPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        
        lblName = new JLabel(t("lbl_name"));
        topPanel.add(lblName);
        indNameField = new JTextField(10);
        indNameField.setFont(FONT_NORMAL);
        topPanel.add(indNameField);
        
        lblProfile = new JLabel(t("lbl_profile"));
        topPanel.add(lblProfile);
        indProfileBox = new JComboBox<>();
        indProfileBox.setFont(FONT_NORMAL);
        updateComboBoxes(indProfileBox);
        topPanel.add(indProfileBox);
        
        topPanel.add(new JLabel("Risk:"));
        indRiskBox = new JComboBox<>();
        updateRiskBoxes(indRiskBox);
        topPanel.add(indRiskBox);
        
        btnApplyProfile = createButton(t("btn_apply"), CLR_BLUE);
        btnApplyProfile.addActionListener(e -> updateIndividualProfile());
        topPanel.add(btnApplyProfile);
        
        lblDoluluk = new JLabel(t("lbl_fill"));
        topPanel.add(lblDoluluk);
        indProgressBar = new JProgressBar(0, 100);
        indProgressBar.setStringPainted(true);
        indProgressBar.setPreferredSize(new Dimension(150, 30));
        indProgressBar.setForeground(CLR_GREEN);
        topPanel.add(indProgressBar);

        panel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setBackground(CLR_BG_MAIN);
        
        indEditorPanel = new JPanel();
        indEditorPanel.setBackground(CLR_PANEL_BG);
        indEditorPanel.setLayout(new BoxLayout(indEditorPanel, BoxLayout.Y_AXIS));
        indEditorPanel.setPreferredSize(new Dimension(320, 0));
        
        borderIndEditor = BorderFactory.createTitledBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true), 
                t("title_editor"), 
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, 
                FONT_TITLE, CLR_DARK);
        
        indEditorPanel.setBorder(BorderFactory.createCompoundBorder(
                borderIndEditor,
                new EmptyBorder(10, 10, 10, 10)
        ));

        lblProdName = new JLabel(t("lbl_prod"));
        indEditorPanel.add(lblProdName);
        indItemNameField = new JTextField();
        indEditorPanel.add(indItemNameField);
        indEditorPanel.add(Box.createVerticalStrut(5));
        
        lblProdWeight = new JLabel(t("lbl_kg"));
        indEditorPanel.add(lblProdWeight);
        indItemWeightField = new JTextField();
        indEditorPanel.add(indItemWeightField);
        indEditorPanel.add(Box.createVerticalStrut(5));
        
        lblProdCost = new JLabel(t("lbl_cost") + " (" + t("currency") + ")");
        indEditorPanel.add(lblProdCost);
        indItemCostField = new JTextField();
        indEditorPanel.add(indItemCostField);
        indEditorPanel.add(Box.createVerticalStrut(15));
        
        btnAdd = createButton(t("btn_add"), CLR_GREEN);
        btnAdd.addActionListener(e -> addIndividualItem());
        indEditorPanel.add(btnAdd);
        indEditorPanel.add(Box.createVerticalStrut(5));
        
        btnDel = createButton(t("btn_del"), CLR_RED);
        btnDel.addActionListener(e -> deleteIndividualItem());
        indEditorPanel.add(btnDel);
        
        indEditorPanel.add(Box.createVerticalStrut(20));
        btnBasic = createButton(t("btn_basic"), CLR_ORANGE);
        btnBasic.addActionListener(e -> {
            bagService.loadAutoItems(individualBag, indRiskBox.getSelectedIndex(), indProfileBox.getSelectedIndex());
            refreshTable(indTableModel, individualBag);
            updateIndividualStatus();
        });
        indEditorPanel.add(btnBasic);
        
        indEditorPanel.add(Box.createVerticalStrut(5));
        btnSave = createButton(t("btn_save"), CLR_DARK);
        btnSave.addActionListener(e -> saveIndividualBag());
        indEditorPanel.add(btnSave);

        indEditorPanel.add(Box.createVerticalStrut(20));
        
        indScorePanel = new JPanel(new BorderLayout());
        indScorePanel.setBackground(CLR_PANEL_BG);
        
        borderScore = BorderFactory.createTitledBorder(
                new LineBorder(Color.LIGHT_GRAY), 
                t("score_title"),
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, 
                FONT_TITLE, CLR_DARK);
        indScorePanel.setBorder(borderScore);
        
        survivalProgressBar = new JProgressBar(0, 100);
        survivalProgressBar.setStringPainted(true);
        survivalProgressBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        indScorePanel.add(survivalProgressBar);
        indEditorPanel.add(indScorePanel);
        
        indEditorPanel.add(Box.createVerticalStrut(10));
        
        indMissingArea = new JTextArea();
        indMissingArea.setEditable(false);
        indMissingArea.setBackground(new Color(250, 235, 235));
        indMissingArea.setBorder(new LineBorder(CLR_RED));
        indMissingArea.setForeground(CLR_RED);
        indEditorPanel.add(indMissingArea);

        centerPanel.add(indEditorPanel, BorderLayout.WEST);

        String[] cols = {t("col_prod"), t("col_kg"), t("col_cost") + " (" + t("currency") + ")", t("col_cat")};
        indTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        indTable = new JTable(indTableModel);
        indTable.setRowHeight(30);
        indTable.setFont(FONT_NORMAL);
        indTable.getTableHeader().setFont(FONT_BOLD);
        indTable.setSelectionBackground(new Color(220, 240, 255));
        indTable.setSelectionForeground(Color.BLACK);
        centerPanel.add(new JScrollPane(indTable), BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);

        indStatusLabel = new JLabel("Total: 0.0 kg | 0.0 " + t("currency"));
        indStatusLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        indStatusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        indStatusLabel.setForeground(CLR_DARK);
        panel.add(indStatusLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFamilyPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(CLR_BG_MAIN);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        headerPanel.setBackground(CLR_PANEL_BG);
        headerPanel.setBorder(new LineBorder(CLR_BLUE, 1, true));
        
        famHeaderLabel = new JLabel(t("fam_name"));
        headerPanel.add(famHeaderLabel);
        familyNameField = new JTextField(15);
        familyNameField.setFont(FONT_BOLD);
        headerPanel.add(familyNameField);
        
        btnSaveFamily = createButton(t("btn_fam_save"), CLR_BLUE);
        btnSaveFamily.addActionListener(e -> saveFamilyGroup());
        headerPanel.add(btnSaveFamily);

        famTotalWeightLabel = new JLabel(" | " + t("fam_total") + " 0.0 kg");
        famTotalWeightLabel.setFont(FONT_BOLD);
        headerPanel.add(famTotalWeightLabel);

        panel.add(headerPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(350);
        splitPane.setBackground(CLR_BG_MAIN);

        leftMemberPanel = new JPanel(new BorderLayout(5, 5));
        leftMemberPanel.setBackground(CLR_PANEL_BG);
        
        borderFamMembers = BorderFactory.createTitledBorder(
                new LineBorder(Color.LIGHT_GRAY), 
                t("sec_members"),
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, 
                FONT_TITLE, CLR_DARK);
        leftMemberPanel.setBorder(BorderFactory.createCompoundBorder(borderFamMembers, new EmptyBorder(5,5,5,5)));
        
        JPanel addMemPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        addMemPanel.setBackground(CLR_PANEL_BG);
        
        lblFamNameInput = new JLabel(t("lbl_mem_name"));
        addMemPanel.add(lblFamNameInput);
        famMemberNameField = new JTextField();
        addMemPanel.add(famMemberNameField);
        
        lblFamTypeInput = new JLabel(t("lbl_mem_type"));
        addMemPanel.add(lblFamTypeInput);
        famMemberTypeBox = new JComboBox<>();
        updateComboBoxes(famMemberTypeBox);
        addMemPanel.add(famMemberTypeBox);
        
        lblFamRisk = new JLabel(t("lbl_risk"));
        addMemPanel.add(lblFamRisk);
        famRiskBox = new JComboBox<>();
        updateRiskBoxes(famRiskBox);
        addMemPanel.add(famRiskBox);
        
        btnAddMem = createButton(t("btn_add_mem"), CLR_GREEN);
        btnAddMem.addActionListener(e -> addFamilyMember());
        addMemPanel.add(btnAddMem);
        
        leftMemberPanel.add(addMemPanel, BorderLayout.NORTH);
        
        familyMemberListModel = new DefaultListModel<>();
        familyMemberList = new JList<>(familyMemberListModel);
        familyMemberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        familyMemberList.addListSelectionListener(this::onFamilyMemberSelected);
        familyMemberList.setFont(FONT_NORMAL);
        leftMemberPanel.add(new JScrollPane(familyMemberList), BorderLayout.CENTER);
        
        btnRemMem = createButton(t("btn_rem_mem"), CLR_RED);
        btnRemMem.addActionListener(e -> removeFamilyMember());
        leftMemberPanel.add(btnRemMem, BorderLayout.SOUTH);

        splitPane.setLeftComponent(leftMemberPanel);

        famEditorPanel = new JPanel(new BorderLayout(10, 10));
        famEditorPanel.setBackground(CLR_PANEL_BG);
        
        borderFamBag = BorderFactory.createTitledBorder(
                new LineBorder(Color.LIGHT_GRAY), 
                t("sec_bag_edit"),
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, 
                FONT_TITLE, CLR_DARK);
        famEditorPanel.setBorder(BorderFactory.createCompoundBorder(borderFamBag, new EmptyBorder(5,5,5,5)));
        famEditorPanel.setVisible(false); 

        JPanel famEditTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        famEditTop.setBackground(CLR_PANEL_BG);
        famItemNameField = new JTextField(8);
        famItemWeightField = new JTextField(4);
        famItemCostField = new JTextField(4);
        
        btnFamAdd = createButton(t("btn_add"), CLR_GREEN);
        btnFamAdd.addActionListener(e -> addFamilyItem());
        btnFamDel = createButton(t("btn_del"), CLR_RED);
        btnFamDel.addActionListener(e -> deleteFamilyItem());
        
        btnFamBasic = createButton(t("btn_basic"), CLR_ORANGE);
        btnFamBasic.addActionListener(e -> {
            if(selectedFamilyMember != null) {
                int riskIdx = famRiskBox.getSelectedIndex();
                int catIdx = famMemberTypeBox.getSelectedIndex();
                
                bagService.loadAutoItems(selectedFamilyMember.getBag(), riskIdx, catIdx);
                updateFamilyUI();
            }
        });

        lblFamProd = new JLabel(t("lbl_prod"));
        famEditTop.add(lblFamProd);
        famEditTop.add(famItemNameField);
        lblFamKg = new JLabel(t("lbl_kg"));
        famEditTop.add(lblFamKg);
        famEditTop.add(famItemWeightField);
        lblFamCost = new JLabel(t("lbl_cost") + " (" + t("currency") + ")");
        famEditTop.add(lblFamCost);
        famEditTop.add(famItemCostField);
        
        famEditTop.add(btnFamAdd);
        famEditTop.add(btnFamDel);
        famEditTop.add(btnFamBasic);
        
        famEditorPanel.add(famEditTop, BorderLayout.NORTH);

        famBagTableModel = new DefaultTableModel(new String[]{t("col_prod"), t("col_kg"), t("col_cost") + " (" + t("currency") + ")", t("col_cat")}, 0);
        famTable = new JTable(famBagTableModel);
        famTable.setRowHeight(25);
        famTable.getTableHeader().setFont(FONT_BOLD);
        famEditorPanel.add(new JScrollPane(famTable), BorderLayout.CENTER);

        JPanel famStatusPanel = new JPanel(new BorderLayout());
        famStatusPanel.setBackground(CLR_PANEL_BG);
        famMemberStatusLabel = new JLabel("0.0 kg");
        famMemberStatusLabel.setFont(FONT_BOLD);
        famStatusPanel.add(famMemberStatusLabel, BorderLayout.WEST);
        
        famMemberProgressBar = new JProgressBar(0, 100);
        famMemberProgressBar.setStringPainted(true);
        famStatusPanel.add(famMemberProgressBar, BorderLayout.CENTER);
        
        famMissingArea = new JTextArea(4, 30);
        famMissingArea.setEditable(false);
        famMissingArea.setForeground(CLR_RED);
        famMissingArea.setBackground(new Color(250, 235, 235));
        famStatusPanel.add(famMissingArea, BorderLayout.SOUTH);

        famEditorPanel.add(famStatusPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(famEditorPanel);
        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    private JSplitPane createHistoryPanel() {
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(300);
        splitPane.setBackground(CLR_BG_MAIN);

        histListPanel = new JPanel(new BorderLayout());
        histListPanel.setBackground(CLR_PANEL_BG);
        
        borderHistList = BorderFactory.createTitledBorder(
                new LineBorder(Color.LIGHT_GRAY), 
                t("tab_hist"),
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, 
                FONT_TITLE, CLR_DARK);
        histListPanel.setBorder(borderHistList);
        
        historyList = new JList<>(historyListModel);
        historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyList.setFont(FONT_NORMAL);
        historyList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                SavedRecord r = historyList.getSelectedValue();
                if(r != null) showHistoryDetails(r);
            }
        });
        histListPanel.add(new JScrollPane(historyList), BorderLayout.CENTER);
        
        btnClearHistory = createButton(t("btn_clear"), CLR_RED);
        btnClearHistory.addActionListener(e -> {
            historyListModel.clear();
            historyDetailsArea.setText("");
        });
        histListPanel.add(btnClearHistory, BorderLayout.SOUTH);

        histDetailPanel = new JPanel(new BorderLayout());
        histDetailPanel.setBackground(CLR_PANEL_BG);
        
        borderHistDetail = BorderFactory.createTitledBorder(
                new LineBorder(Color.LIGHT_GRAY), 
                t("title_details"),
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, 
                FONT_TITLE, CLR_DARK);
        histDetailPanel.setBorder(borderHistDetail);
        
        historyDetailsArea = new JTextArea();
        historyDetailsArea.setEditable(false);
        historyDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        historyDetailsArea.setMargin(new Insets(10,10,10,10));
        histDetailPanel.add(new JScrollPane(historyDetailsArea), BorderLayout.CENTER);
        
        btnExportHistory = createButton(t("btn_export"), CLR_BLUE);
        btnExportHistory.addActionListener(e -> exportHistoryToTxt());
        histDetailPanel.add(btnExportHistory, BorderLayout.SOUTH);

        splitPane.setLeftComponent(histListPanel);
        splitPane.setRightComponent(histDetailPanel);

        return splitPane;
    }

    private void updateIndividualProfile() {
        int index = indProfileBox.getSelectedIndex();
        double cap = getCapacityByIndex(index);
        
        List<Item> currentItems = individualBag.getItems();
        EmergencyBag newBag = new EmergencyBag(cap);
        
        try {
            for(Item i : currentItems) newBag.addItem(i);
            individualBag = newBag;
            JOptionPane.showMessageDialog(this, t("msg_saved"));
        } catch (OverweightBagException e) {
            JOptionPane.showMessageDialog(this, t("msg_full") + "\n" + e.getMessage());
        }
        updateIndividualStatus();
    }

    private void addIndividualItem() {
        try {
            String name = indItemNameField.getText();
            double weight = Double.parseDouble(indItemWeightField.getText());
            double cost = 0;
            try { cost = Double.parseDouble(indItemCostField.getText()); } catch(Exception ignored){}
            
            Item newItem = new Item(name, weight);
            newItem.setCost(cost);
            newItem.setCategory("Manuel");
            
            individualBag.addItem(newItem);
            
            refreshTable(indTableModel, individualBag);
            updateIndividualStatus();
            
            indItemNameField.setText("");
            indItemWeightField.setText("");
            indItemCostField.setText("");
        } catch (OverweightBagException e) {
             JOptionPane.showMessageDialog(this, t("msg_full"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, t("msg_num"));
        }
    }

    private void deleteIndividualItem() {
        int row = indTable.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, t("msg_no_sel"));
            return;
        }
        
        String name = (String) indTableModel.getValueAt(row, 0);
        double w = (double) indTableModel.getValueAt(row, 1);
        
        individualBag.removeItem(name, w);
        refreshTable(indTableModel, individualBag);
        updateIndividualStatus();
    }

    private void updateIndividualStatus() {
        double current = individualBag.getCurrentWeight();
        double max = individualBag.getCapacity();
        double totalCost = individualBag.getTotalCost();
        
        indStatusLabel.setText(String.format("Total: %.2f / %.1f kg | %.2f %s", current, max, totalCost, t("currency")));
        
        int percent = 0;
        if(max > 0) percent = (int)((current/max)*100);
        
        indProgressBar.setValue(percent);
        indProgressBar.setForeground(getColorForPercent(percent));
        
        checkIndividualMissing();
    }

    private void checkIndividualMissing() {
        String report = bagService.getMissingItemsReport(individualBag);
        int score = bagService.calculateSurvivalScore(individualBag);
        
        indMissingArea.setText(report);
        if(report.contains("Eksik")) indMissingArea.setForeground(CLR_RED);
        else indMissingArea.setForeground(CLR_GREEN);
        
        updateSurvivalScoreUI(score, survivalProgressBar);
    }
    
    private void updateSurvivalScoreUI(int score, JProgressBar bar) {
        bar.setValue(score);
        String title = t("score_title") + ": " + score + "/100";
        if (score < 50) bar.setForeground(CLR_RED);
        else if (score < 80) bar.setForeground(CLR_ORANGE);
        else bar.setForeground(CLR_GREEN);
        
        if(bar.getParent() != null && ((JComponent) bar.getParent()).getBorder() instanceof TitledBorder)
            ((TitledBorder)((JComponent) bar.getParent()).getBorder()).setTitle(title);
        if(bar.getParent() != null) bar.getParent().repaint();
    }

    private void addFamilyMember() {
        String name = famMemberNameField.getText();
        if(name.isEmpty()) {
            JOptionPane.showMessageDialog(this, t("msg_name_req"));
            return;
        }
        int typeIdx = famMemberTypeBox.getSelectedIndex();
        int riskIdx = famRiskBox.getSelectedIndex();
        String typeName = (String)famMemberTypeBox.getSelectedItem();
        double cap = getCapacityByIndex(typeIdx);
        
        FamilyMember member = new FamilyMember(name, typeName, cap);
        currentFamilyMembers.add(member);
        familyMemberListModel.addElement(name + " (" + typeName + ")");
        famMemberNameField.setText("");
        updateFamilyTotalWeight();
        
        bagService.loadAutoItems(member.getBag(), riskIdx, typeIdx);
    }

    private void onFamilyMemberSelected(ListSelectionEvent e) {
        if(e.getValueIsAdjusting() || familyMemberList.getSelectedIndex() == -1) return;
        
        int index = familyMemberList.getSelectedIndex();
        selectedFamilyMember = currentFamilyMembers.get(index);
        
        famEditorPanel.setVisible(true);
        if (borderFamBag != null) borderFamBag.setTitle(selectedFamilyMember.getName());
        famEditorPanel.repaint();
        
        updateFamilyUI();
    }

    private void updateFamilyUI() {
        if(selectedFamilyMember == null) return;
        
        refreshTable(famBagTableModel, selectedFamilyMember.getBag());
        
        double current = selectedFamilyMember.getBag().getCurrentWeight();
        double max = selectedFamilyMember.getCapacity();
        double cost = selectedFamilyMember.getBag().getTotalCost();
        
        famMemberStatusLabel.setText(String.format("%.2f / %.1f kg | %.2f %s", current, max, cost, t("currency")));
        int percent = 0;
        if(max > 0) percent = (int)((current/max)*100);
        
        famMemberProgressBar.setValue(percent);
        famMemberProgressBar.setForeground(getColorForPercent(percent));
        
        String report = bagService.getMissingItemsReport(selectedFamilyMember.getBag());
        famMissingArea.setText(report);
        
        updateFamilyTotalWeight();
    }

    private void addFamilyItem() {
        if(selectedFamilyMember == null) {
            JOptionPane.showMessageDialog(this, t("msg_no_member"));
            return;
        }
        try {
            String name = famItemNameField.getText();
            double w = Double.parseDouble(famItemWeightField.getText());
            double c = 0;
            try { c = Double.parseDouble(famItemCostField.getText()); } catch(Exception ignored){}
            
            Item ni = new Item(name, w);
            ni.setCost(c);
            selectedFamilyMember.getBag().addItem(ni);
            
            updateFamilyUI();
            famItemNameField.setText("");
            famItemWeightField.setText("");
            famItemCostField.setText("");
        } catch(OverweightBagException e) {
            JOptionPane.showMessageDialog(this, t("msg_full"));
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, t("msg_num"));
        }
    }

    private void deleteFamilyItem() {
        if(selectedFamilyMember == null) {
            JOptionPane.showMessageDialog(this, t("msg_no_member"));
            return;
        }
        int row = famTable.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, t("msg_no_sel"));
            return;
        }
        
        String name = (String) famBagTableModel.getValueAt(row, 0);
        double w = (double) famBagTableModel.getValueAt(row, 1);
        
        selectedFamilyMember.getBag().removeItem(name, w);
        updateFamilyUI();
    }
    
    private void removeFamilyMember() {
        int index = familyMemberList.getSelectedIndex();
        if(index != -1) {
            currentFamilyMembers.remove(index);
            familyMemberListModel.remove(index);
            famEditorPanel.setVisible(false);
            selectedFamilyMember = null;
            updateFamilyTotalWeight();
        } else {
            JOptionPane.showMessageDialog(this, t("msg_no_member"));
        }
    }
    
    private void updateFamilyTotalWeight() {
        double total = 0;
        double totalCost = 0;
        for(FamilyMember m : currentFamilyMembers) {
            total += m.getBag().getCurrentWeight();
            totalCost += m.getBag().getTotalCost();
        }
        famTotalWeightLabel.setText(" | " + t("fam_total") + " " + String.format("%.2f", total) + " kg | " + String.format("%.2f", totalCost) + " " + t("currency"));
    }

    private void saveIndividualBag() {
        if(individualBag.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, t("msg_empty"));
            return;
        }
        String name = indNameField.getText().isEmpty() ? "No Name" : indNameField.getText();
        String type = (String)indProfileBox.getSelectedItem();
        
        SavedRecord record = new SavedRecord(name, "Bireysel/Individual");
        record.addMemberBag(name, type, individualBag);
        
        bagRepository.save(record);
        
        historyListModel.addElement(record);
        JOptionPane.showMessageDialog(this, t("msg_saved"));
    }
    
    private void saveFamilyGroup() {
        if(currentFamilyMembers.isEmpty()) {
            JOptionPane.showMessageDialog(this, t("msg_empty"));
            return;
        }
        String famName = familyNameField.getText().isEmpty() ? "Family" : familyNameField.getText();
        
        SavedRecord record = new SavedRecord(famName, "Aile/Family");
        for(FamilyMember m : currentFamilyMembers) {
            record.addMemberBag(m.getName(), m.getType(), m.getBag());
        }
        
        bagRepository.save(record);
        
        historyListModel.addElement(record);
        JOptionPane.showMessageDialog(this, t("msg_saved"));
    }

    private void showHistoryDetails(SavedRecord r) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== KAYIT DETAYI / RECORD DETAIL ===\n");
        sb.append("Ad/Name: ").append(r.getRecordName()).append("\n");
        sb.append("Tip/Type: ").append(r.getRecordType()).append("\n");
        sb.append("Tarih/Date: ").append(r.getDate()).append("\n\n");
        
        double grandTotalWeight = 0;
        double grandTotalCost = 0;
        
        for(SavedRecord.SubBag sub : r.getBags()) {
            sb.append(">> ").append(sub.ownerName).append(" (").append(sub.ownerType).append(")\n");
            double subCost = 0;
            for(Item i : sub.items) {
                sb.append("   - ").append(i.getName())
                  .append(" : ").append(String.format("%.2f", i.getWeight())).append(" kg")
                  .append(" | ").append(String.format("%.2f", i.getCost())).append(" " + t("currency") + "\n");
                subCost += i.getCost();
            }
            sb.append("   [Alt Toplam: ").append(String.format("%.2f", sub.totalWeight)).append(" kg | ").append(String.format("%.2f", subCost)).append(" " + t("currency") + "]\n\n");
            grandTotalWeight += sub.totalWeight;
            grandTotalCost += subCost;
        }
        sb.append("========================\n");
        sb.append("GENEL TOPLAM / GRAND TOTAL: ").append(String.format("%.2f", grandTotalWeight)).append(" kg\n");
        sb.append("TOPLAM MALİYET / TOTAL COST: ").append(String.format("%.2f", grandTotalCost)).append(" " + t("currency"));
        
        historyDetailsArea.setText(sb.toString());
    }
    
    private void exportHistoryToTxt() {
        SavedRecord r = historyList.getSelectedValue();
        if(r == null) {
            JOptionPane.showMessageDialog(this, t("msg_no_sel"));
            return;
        }
        bagRepository.save(r); 
        JOptionPane.showMessageDialog(this, t("msg_saved"));
    }
    
    private void refreshTable(DefaultTableModel model, EmergencyBag bag) {
        model.setRowCount(0);
        for(Item i : bag.getItems()) {
            model.addRow(new Object[]{i.getName(), i.getWeight(), i.getCost(), i.getCategory()});
        }
    }

    private double getCapacityByIndex(int index) {
        switch(index) {
            case 0: return 15.0; 
            case 1: return 10.0; 
            case 2: return 5.0;  
            case 3: return 12.0; 
            case 4: return 10.0; 
            default: return 15.0;
        }
    }
    
    private Color getColorForPercent(int p) {
        if(p > 90) return CLR_RED;
        if(p > 70) return CLR_ORANGE;
        return CLR_GREEN;
    }
    
    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.BLACK);
        btn.setFont(FONT_BOLD);
        btn.setFocusPainted(false);
        return btn;
    }
    
    private void initDictionary() {
        dict = new HashMap<>();
        dict.put("app_title", new String[]{"Akıllı Deprem Çantası Planlama Asistanı", "Smart Earthquake Bag Planning Assistant"});
        dict.put("tab_ind", new String[]{" Bireysel Mod ", " Individual Mode "});
        dict.put("tab_fam", new String[]{" Aile Modu ", " Family Mode "});
        dict.put("tab_hist", new String[]{" Geçmiş Kayıtlar ", " History Records "});
        
        dict.put("lbl_name", new String[]{"Ad Soyad:", "Full Name:"});
        dict.put("lbl_profile", new String[]{"Profil Tipi:", "Profile Type:"});
        dict.put("btn_apply", new String[]{"Profili Uygula", "Apply Profile"});
        dict.put("lbl_fill", new String[]{"Doluluk:", "Capacity:"});
        dict.put("lbl_risk", new String[]{"Risk Seviyesi:", "Risk Level:"});
        
        dict.put("lbl_prod", new String[]{"Ürün Adı:", "Item Name:"});
        dict.put("lbl_kg", new String[]{"Ağırlık (kg):", "Weight (kg):"});
        dict.put("lbl_cost", new String[]{"Maliyet:", "Cost:"});
        dict.put("btn_add", new String[]{"Ekle", "Add Item"});
        dict.put("btn_del", new String[]{"Seçileni Sil", "Delete Selected"});
        dict.put("btn_basic", new String[]{"Risk & Profil Eşyalarını Yükle", "Load Risk & Profile Items"});
        dict.put("btn_save", new String[]{"Çantayı Kaydet", "Save Bag"});
        
        dict.put("col_prod", new String[]{"Ürün", "Item"});
        dict.put("col_kg", new String[]{"Ağırlık", "Weight"});
        dict.put("col_cost", new String[]{"Maliyet", "Cost"});
        dict.put("col_cat", new String[]{"Kategori", "Category"});
        
        dict.put("score_title", new String[]{"Hayatta Kalma Puanı", "Survival Score"});
        dict.put("fam_name", new String[]{"AİLE GRUP ADI:", "FAMILY GROUP NAME:"});
        dict.put("btn_fam_save", new String[]{"Tüm Aileyi Kaydet", "Save Family Group"});
        dict.put("fam_total", new String[]{"Toplam Aile Yükü:", "Total Family Load:"});
        
        dict.put("sec_members", new String[]{"1. Aile Bireyleri", "1. Family Members"});
        dict.put("btn_add_mem", new String[]{"Birey Ekle", "Add Member"});
        dict.put("btn_rem_mem", new String[]{"Bireyi Çıkar", "Remove Member"});
        dict.put("sec_bag_edit", new String[]{"2. Seçili Bireyin Çantası", "2. Selected Member's Bag"});
        
        dict.put("btn_clear", new String[]{"Geçmişi Temizle", "Clear History"});
        dict.put("btn_export", new String[]{"TXT Olarak İndir", "Export as TXT"});
        
        dict.put("msg_saved", new String[]{"Başarıyla Kaydedildi!", "Saved Successfully!"});
        dict.put("msg_err", new String[]{"Hata:", "Error:"});
        dict.put("msg_full", new String[]{"Çanta Kapasitesi Doldu!", "Bag Capacity Full!"});
        dict.put("msg_num", new String[]{"Lütfen geçerli sayı giriniz.", "Please enter valid numbers."});
        dict.put("msg_empty", new String[]{"Çanta boş! Kaydedilecek bir şey yok.", "The bag is empty! Nothing to save."});
        dict.put("msg_no_member", new String[]{"Lütfen listeden bir aile bireyi seçin.", "Please select a family member from the list."});
        dict.put("msg_no_sel", new String[]{"Lütfen silinecek bir satır seçin.", "Please select a row to delete."});
        dict.put("msg_name_req", new String[]{"Lütfen bir isim girin.", "Please enter a name."});
        
        dict.put("p_adult", new String[]{"Yetişkin (15kg)", "Adult (15kg)"});
        dict.put("p_child", new String[]{"Çocuk (10kg)", "Child (10kg)"});
        dict.put("p_baby", new String[]{"Bebek (5kg)", "Baby (5kg)"});
        dict.put("p_elder", new String[]{"Yaşlı (12kg)", "Elderly (12kg)"});
        dict.put("p_chronic", new String[]{"Kronik Hasta (10kg)", "Chronic Patient (10kg)"});
        
        dict.put("r_high", new String[]{"YÜKSEK (High)", "HIGH"});
        dict.put("r_medium", new String[]{"ORTA (Medium)", "MEDIUM"});
        dict.put("r_low", new String[]{"DÜŞÜK (Low)", "LOW"});
        
        dict.put("lbl_mem_name", new String[]{"Birey Adı:", "Member Name:"});
        dict.put("lbl_mem_type", new String[]{"Birey Tipi:", "Member Type:"});
        
        dict.put("title_editor", new String[]{"Çanta Düzenleyici", "Bag Editor"});
        dict.put("title_details", new String[]{"Detaylar", "Details"});
        
        dict.put("currency", new String[]{"TL", "$"});
    }

    private String t(String key) {
        if (!dict.containsKey(key)) return key;
        return dict.get(key)[currentLang];
    }

    private void toggleLanguage() {
        currentLang = (currentLang == 1) ? 0 : 1;
        updateTexts();
    }

    private void updateTexts() {
        setTitle(t("app_title"));
        lblBrand.setText(currentLang == 0 ? "AKILLI DEPREM ÇANTASI PLANLAMA ASİSTANI" : "SMART EARTHQUAKE BAG PLANNING ASSISTANT");
        
        tabbedPane.setTitleAt(0, t("tab_ind"));
        tabbedPane.setTitleAt(1, t("tab_fam"));
        tabbedPane.setTitleAt(2, t("tab_hist"));
        
        lblName.setText(t("lbl_name"));
        lblProfile.setText(t("lbl_profile"));
        btnApplyProfile.setText(t("btn_apply"));
        lblDoluluk.setText(t("lbl_fill"));
        
        lblProdName.setText(t("lbl_prod"));
        lblProdWeight.setText(t("lbl_kg"));
        lblProdCost.setText(t("lbl_cost"));
        btnAdd.setText(t("btn_add"));
        btnDel.setText(t("btn_del"));
        btnBasic.setText(t("btn_basic"));
        btnSave.setText(t("btn_save"));
        
        indTableModel.setColumnIdentifiers(new String[]{t("col_prod"), t("col_kg"), t("col_cost") + " (" + t("currency") + ")", t("col_cat")});
        
        famHeaderLabel.setText(t("fam_name"));
        btnSaveFamily.setText(t("btn_fam_save"));
        updateFamilyTotalWeight(); 
        
        lblFamNameInput.setText(t("lbl_mem_name"));
        lblFamTypeInput.setText(t("lbl_mem_type"));
        lblFamRisk.setText(t("lbl_risk"));
        
        btnAddMem.setText(t("btn_add_mem"));
        btnRemMem.setText(t("btn_rem_mem"));
        
        if (borderIndEditor != null) borderIndEditor.setTitle(t("title_editor"));
        if (borderScore != null) borderScore.setTitle(t("score_title"));
        if (borderFamMembers != null) borderFamMembers.setTitle(t("sec_members"));
        if (borderFamBag != null) borderFamBag.setTitle(t("sec_bag_edit"));
        if (borderHistList != null) borderHistList.setTitle(t("tab_hist"));
        if (borderHistDetail != null) borderHistDetail.setTitle(t("title_details"));

        if (famBagTableModel != null) 
            famBagTableModel.setColumnIdentifiers(new String[]{t("col_prod"), t("col_kg"), t("col_cost") + " (" + t("currency") + ")", t("col_cat")});
        
        lblFamProd.setText(t("lbl_prod"));
        lblFamKg.setText(t("lbl_kg"));
        lblFamCost.setText(t("lbl_cost") + " (" + t("currency") + ")");
        
        btnFamAdd.setText(t("btn_add"));
        btnFamDel.setText(t("btn_del"));
        
        btnClearHistory.setText(t("btn_clear"));
        btnExportHistory.setText(t("btn_export"));
        
        updateComboBoxes(indProfileBox);
        updateComboBoxes(famMemberTypeBox);
        updateRiskBoxes(indRiskBox);
        updateRiskBoxes(famRiskBox);
        
        updateIndividualStatus();
        if(selectedFamilyMember != null) updateFamilyUI();
        
        revalidate();
        repaint();
    }
    
    private void updateComboBoxes(JComboBox<String> box) {
        if(box == null) return;
        int idx = box.getSelectedIndex();
        box.removeAllItems();
        box.addItem(t("p_adult"));
        box.addItem(t("p_child"));
        box.addItem(t("p_baby"));
        box.addItem(t("p_elder"));
        box.addItem(t("p_chronic"));
        if(idx >= 0 && idx < box.getItemCount()) box.setSelectedIndex(idx);
        else box.setSelectedIndex(0);
    }
    
    private void updateRiskBoxes(JComboBox<String> box) {
        if(box == null) return;
        int idx = box.getSelectedIndex();
        box.removeAllItems();
        box.addItem(t("r_high"));
        box.addItem(t("r_medium"));
        box.addItem(t("r_low"));
        if(idx >= 0 && idx < box.getItemCount()) box.setSelectedIndex(idx);
        else box.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
package Presentation;

import Domain.Model;
import Domain.Observer;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainView extends JFrame implements Observer {
    private Model model;
    private MainViewController controller;
    private JButton generateButton;
    private JButton newWatchListButton;
    private JButton newUserButton;
    private JButton addMovieToSeenButton;
    private JButton actualizeWatchListButton;
    private ButtonGroup SelectorModeButtonGroup;
    private JRadioButton RandomSelectorModeRadioButton;
    private JRadioButton NotSeenSelectorModeRadioButton;
    private JComboBox<String> watchListComboBox;
    private JComboBox<String> userComboBox;
    private JLabel lastSeenLabel;
    private JButton deleteUserButton;
    private JButton deleteWatchListButton;
    private JButton filtersButton;
    private JButton viewWatchListButton;
    public MainView(Model model){
        this.model = model;
        FlatIntelliJLaf.setup();
        setTitle("Main View");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40)); // Add left and right margins
        add(mainPanel);
        initializeUserPanel(mainPanel);
        initializeWatchListPanel(mainPanel);
        initializeSelectorModePanel(mainPanel);
        initializeButtonsPanel(mainPanel);
        setVisible(true);
    }

    private void setComponentFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setComponentFont(child, font);
            }
        }
    }

    private void initializeButtonsPanel(JPanel mainPanel) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        generateButton = new JButton("Generate");
        generateButton.setPreferredSize(new Dimension(250, 180)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonsPanel.add(generateButton, gbc );
        lastSeenLabel = new JLabel("Last Seen: " + model.getCurrentMovie());
        lastSeenLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonsPanel.add(lastSeenLabel, gbc);
        addMovieToSeenButton = new JButton("Add Movie to Seen");
        addMovieToSeenButton.setPreferredSize(new Dimension(250, 100));
        gbc.gridx = 1;
        gbc.gridy = 2;
        buttonsPanel.add(addMovieToSeenButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.weighty = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(buttonsPanel, gbc);
        setComponentFont(buttonsPanel, new Font("Arial", Font.PLAIN, 16)); // Set larger font
    }

    private void initializeSelectorModePanel(JPanel mainPanel) {
        JPanel selectorAndFilterPanel = new JPanel();
        selectorAndFilterPanel.setLayout(new BoxLayout(selectorAndFilterPanel, BoxLayout.X_AXIS));
        JPanel filterAndViewPanel = new JPanel();
        JPanel selectorModePanel = new JPanel();
        selectorModePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        selectorModePanel.setLayout(new BoxLayout(selectorModePanel, BoxLayout.Y_AXIS));
        selectorModePanel.add(new JLabel("Selector Mode:"));
        SelectorModeButtonGroup = new ButtonGroup();
        RandomSelectorModeRadioButton = new JRadioButton("Random");
        NotSeenSelectorModeRadioButton = new JRadioButton("Not Seen");
        SelectorModeButtonGroup.add(RandomSelectorModeRadioButton);
        SelectorModeButtonGroup.add(NotSeenSelectorModeRadioButton);
        selectorModePanel.add(RandomSelectorModeRadioButton);
        selectorModePanel.add(NotSeenSelectorModeRadioButton);
        filterAndViewPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        filterAndViewPanel.setLayout(new BoxLayout(filterAndViewPanel, BoxLayout.Y_AXIS));
        viewWatchListButton = new JButton("View Watchlist");
        filterAndViewPanel.add(viewWatchListButton);
        filtersButton = new JButton("Filters");
        filterAndViewPanel.add(filtersButton);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        selectorAndFilterPanel.add(filterAndViewPanel);
        selectorAndFilterPanel.add(Box.createHorizontalStrut(400));
        selectorAndFilterPanel.add(selectorModePanel);
        mainPanel.add(selectorAndFilterPanel, gbc);
        setComponentFont(selectorModePanel, new Font("Arial", Font.PLAIN, 16)); // Set larger font
        setComponentFont(filterAndViewPanel, new Font("Arial", Font.PLAIN, 16)); // Set larger font
    }

    private void initializeWatchListPanel(JPanel mainPanel) {
        JPanel watchListPanel = new JPanel();
        watchListPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        watchListPanel.setLayout(new BoxLayout(watchListPanel, BoxLayout.X_AXIS));
        watchListPanel.add(new JLabel("Watchlist: "));
        watchListComboBox = new JComboBox<>();
        ArrayList<String> noms = (ArrayList<String>) model.getAllWatchListsName();
        if (noms.size() == 0) {
            watchListComboBox.setEnabled(false);
        }
        for (String nom : noms) {
            watchListComboBox.addItem(nom);
        }
        watchListPanel.add(watchListComboBox);
        actualizeWatchListButton = new JButton("Actualize");
        watchListPanel.add(actualizeWatchListButton);
        newWatchListButton = new JButton("New");
        watchListPanel.add(newWatchListButton);
        watchListPanel.add(Box.createHorizontalGlue());
        deleteWatchListButton = new JButton("Delete");
        watchListPanel.add(deleteWatchListButton);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(watchListPanel, gbc);
        setComponentFont(watchListPanel, new Font("Arial", Font.PLAIN, 16)); // Set larger font
    }

    private void initializeUserPanel(JPanel mainPanel) {
        JPanel userPanel = new JPanel();
        userPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        userPanel.add(new JLabel("User: "));
        userComboBox = new JComboBox<>();
        ArrayList<String> noms = (ArrayList<String>) model.getAllUsersName();
        if (noms.size() == 0) {
            userComboBox.setEnabled(false);
        }
        for (String nom : noms) {
            userComboBox.addItem(nom);
        }
        userPanel.add(userComboBox);
        newUserButton = new JButton("New");
        userPanel.add(newUserButton);
        userPanel.add(Box.createHorizontalGlue());
        deleteUserButton = new JButton("Delete");
        userPanel.add(deleteUserButton);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(userPanel, gbc);
        setComponentFont(userPanel, new Font("Arial", Font.PLAIN, 16)); // Set larger font
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public MainViewController initializeController() {
        this.controller = new MainViewController(model, this);
        generateButton.addActionListener(controller);
        newWatchListButton.addActionListener(controller);
        newUserButton.addActionListener(controller);
        addMovieToSeenButton.addActionListener(controller);
        actualizeWatchListButton.addActionListener(controller);
        RandomSelectorModeRadioButton.addActionListener(controller);
        NotSeenSelectorModeRadioButton.addActionListener(controller);
        watchListComboBox.addActionListener(controller);
        userComboBox.addActionListener(controller);
        return controller;
    }

    @Override
    public void update() {
        boolean hasUser = model.isThereUser();
        boolean hasWatchList = model.isThereWatchList();
        boolean hasCurrentMovie = model.isThereCurrentMovie();
        actualizeWatchListButton.setEnabled(hasWatchList);
        addMovieToSeenButton.setEnabled(hasUser && hasCurrentMovie);
        generateButton.setEnabled(hasUser && hasWatchList);
        userComboBox.revalidate();
        userComboBox.repaint();
        watchListComboBox.revalidate();
        watchListComboBox.repaint();
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public JButton getNewWatchListButton() {
        return newWatchListButton;
    }

    public JButton getNewUserButton() {
        return newUserButton;
    }

    public JButton getAddMovieToSeenButton() {
        return addMovieToSeenButton;
    }

    public JButton getActualizeWatchListButton() {
        return actualizeWatchListButton;
    }

    public ButtonGroup getSelectorModeButtonGroup() {
        return SelectorModeButtonGroup;
    }

    public JRadioButton getRandomSelectorModeRadioButton() {
        return RandomSelectorModeRadioButton;
    }

    public JRadioButton getNotSeenSelectorModeRadioButton() {
        return NotSeenSelectorModeRadioButton;
    }

    public JComboBox<String> getWatchListComboBox() {
        return watchListComboBox;
    }

    public JComboBox<String> getUserComboBox() {
        return userComboBox;
    }

    public void addWatchList(String watchListName) {
        watchListComboBox.addItem(watchListName);
        watchListComboBox.setEnabled(true);
    }
    public void addUser(String userName) {
        userComboBox.addItem(userName);
        userComboBox.setEnabled(true);
    }
}
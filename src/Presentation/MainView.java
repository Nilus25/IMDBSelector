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
    public MainView(Model model){
        this.model = model;
        FlatIntelliJLaf.setup();
        setTitle("Main View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        initializeUserPanel(mainPanel);
        initializeWatchListPanel(mainPanel);
        initializeSelectorModePanel(mainPanel);
        initializeButtonsPanel(mainPanel);
        setVisible(true);
    }

    private void initializeButtonsPanel(JPanel mainPanel) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        generateButton = new JButton("Generate");
        buttonsPanel.add(generateButton, BorderLayout.CENTER);
        addMovieToSeenButton = new JButton("Add Movie to Seen");
        buttonsPanel.add(addMovieToSeenButton, BorderLayout.SOUTH);
        mainPanel.add(buttonsPanel);
    }

    private void initializeSelectorModePanel(JPanel mainPanel) {
        JPanel selectorModePanel = new JPanel();
        selectorModePanel.setLayout(new BoxLayout(selectorModePanel, BoxLayout.Y_AXIS));
        selectorModePanel.add(new JLabel("Selector Mode:"));
        SelectorModeButtonGroup = new ButtonGroup();
        RandomSelectorModeRadioButton = new JRadioButton("Random");
        NotSeenSelectorModeRadioButton = new JRadioButton("Not Seen");
        SelectorModeButtonGroup.add(RandomSelectorModeRadioButton);
        SelectorModeButtonGroup.add(NotSeenSelectorModeRadioButton);
        RandomSelectorModeRadioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        NotSeenSelectorModeRadioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectorModePanel.add(RandomSelectorModeRadioButton);
        selectorModePanel.add(NotSeenSelectorModeRadioButton);
        mainPanel.add(selectorModePanel);
    }

    private void initializeWatchListPanel(JPanel mainPanel) {
        JPanel watchListPanel = new JPanel();
        watchListPanel.setLayout(new BoxLayout(watchListPanel, BoxLayout.X_AXIS));
        watchListPanel.add(new JLabel("Watch List:"));
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
        watchListPanel.add(Box.createHorizontalGlue());
        newWatchListButton = new JButton("New Watch List");
        watchListPanel.add(newWatchListButton);
        mainPanel.add(watchListPanel);
    }

    private void initializeUserPanel(JPanel mainPanel) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        userPanel.add(new JLabel("User:"));
        userComboBox = new JComboBox<>();
        ArrayList<String> noms = (ArrayList<String>) model.getAllUsersName();
        if (noms.size() == 0) {
            userComboBox.setEnabled(false);
        }
        for (String nom : noms) {
            userComboBox.addItem(nom);
        }
        userPanel.add(userComboBox);
        userPanel.add(Box.createHorizontalGlue());
        newUserButton = new JButton("New User");
        userPanel.add(newUserButton);
        mainPanel.add(userPanel);
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

package Presentation;

import Domain.Model;
import Domain.Observer;
import Domain.ObserverComboBoxes;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainView extends JFrame implements Observer, ObserverComboBoxes {
    private Model model;
    private MainViewController controller;
    private JButton generateButton;
    private JButton newWatchListButton;
    private JButton newUserButton;
    private JButton addMovieToSeenButton;
    private JButton actualizeWatchListButton;
    private JRadioButton RandomSelectorModeRadioButton;
    private JRadioButton NotSeenSelectorModeRadioButton;
    private JComboBox<String> watchListComboBox;
    private JComboBox<String> userComboBox;
    private JButton deleteUserButton;
    private JButton deleteWatchListButton;
    private JButton filtersButton;
    private JButton viewWatchListButton;
    private JPanel mainPanel;
    private JLabel TitleLabel;
    private JLabel DirectorLabel;
    private JLabel YearLabel;
    private JLabel RatingLabel;
    private JLabel MissatgesLabel;
    private JLabel TypeLabel;

    static {
        FlatArcOrangeIJTheme.setup();
    }
    public MainView(Model model) {
        this.model = model;
        setTitle("Main View");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);
        updateComboBoxes();
        setVisible(true);
        repaint();
    }

    private void setComponentFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setComponentFont(child, font);
            }
        }
    }

    public void getAllWatchlists(){
        ArrayList<String> noms = (ArrayList<String>) model.getAllWatchListsName();
        watchListComboBox.setEnabled(!noms.isEmpty());
        for (String nom : noms) {
            watchListComboBox.addItem(nom);
        }
    }

     public void getAllUsernames(){
        ArrayList<String> noms = (ArrayList<String>) model.getAllUsersName();
         userComboBox.setEnabled(!noms.isEmpty());
        for (String nom : noms) {
            userComboBox.addItem(nom);
        }
    }
    public void setModel(Model model) {
        this.model = model;
    }

    public void initializeController() {
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
        deleteUserButton.addActionListener(controller);
        deleteWatchListButton.addActionListener(controller);
        filtersButton.addActionListener(controller);
        viewWatchListButton.addActionListener(controller);

    }

    @Override
    public void update() {
        boolean hasUser = model.isThereUser();
        boolean hasWatchList = model.isThereWatchList();
        boolean hasCurrentMovie = model.isThereCurrentMovie();
        deleteUserButton.setEnabled(hasUser);
        deleteWatchListButton.setEnabled(hasWatchList);
        actualizeWatchListButton.setEnabled(hasWatchList);
        addMovieToSeenButton.setEnabled(hasUser && hasCurrentMovie);
        generateButton.setEnabled(hasUser && hasWatchList);
        if (hasCurrentMovie){
            ArrayList<String> movieInformation = (ArrayList<String>) model.getCurrentMovieInformation();
            TitleLabel.setText("<html>Title: <b>" + movieInformation.get(0) + "</b></html>");
            DirectorLabel.setText("<html>Director: <b>" + movieInformation.get(1) + "</b></html>");
            YearLabel.setText("<html>Year: <b>" + movieInformation.get(2) + "</b></html>");
            RatingLabel.setText("<html>Rating: <b>" + movieInformation.get(3) + "</b></html>");
            TypeLabel.setText("<html>Type: <b>" + movieInformation.get(4) + "</b></html>");
        }
    }

    @Override
    public void updateComboBoxes() {
        ActionListener[] userComboBoxListeners = userComboBox.getActionListeners();
        ActionListener[] watchListComboBoxListeners = watchListComboBox.getActionListeners();
        for (ActionListener listener : userComboBoxListeners) {
            userComboBox.removeActionListener(listener);
        }
        for (ActionListener listener : watchListComboBoxListeners) {
            watchListComboBox.removeActionListener(listener);
        }
        userComboBox.removeAllItems();
        watchListComboBox.removeAllItems();
        getAllUsernames();
        getAllWatchlists();
        boolean hasUser = model.isThereUser();
        boolean hasWatchList = model.isThereWatchList();
        boolean hasCurrentMovie = model.isThereCurrentMovie();
        if (hasUser){
            userComboBox.setSelectedItem(model.getUser().getName());
            userComboBox.setEditable(false);
            userComboBox.getEditor().getEditorComponent().setEnabled(true);
        }
        else {
            userComboBox.setEditable(true);
            userComboBox.getEditor().getEditorComponent().setEnabled(false);
        }
        if (hasWatchList){
            watchListComboBox.setSelectedItem(model.getWatchlist().getName());
            watchListComboBox.setEditable(false);
            watchListComboBox.getEditor().getEditorComponent().setEnabled(true);
        }
        else {
            watchListComboBox.setEditable(true);
            watchListComboBox.getEditor().getEditorComponent().setEnabled(false);
        }
        for (ActionListener listener : userComboBoxListeners) {
            userComboBox.addActionListener(listener);
        }
        for (ActionListener listener : watchListComboBoxListeners) {
            watchListComboBox.addActionListener(listener);
        }
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
    public JButton getDeleteUserButton() {
        return deleteUserButton;
    }

    public JButton getDeleteWatchListButton() {
        return deleteWatchListButton;
    }

    public JButton getFiltersButton() {
        return filtersButton;
    }

    public JButton getViewWatchListButton() {
        return viewWatchListButton;
    }

    public void throwLastMovieGeneratedForUser(String name, String year) {
        int response = JOptionPane.showOptionDialog(
                this,
                "<html>The last movie generated for this user was <b>" + name + " (" + year + ")</b>.<br>Did you watch it?",                "Last Movie Generated",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Watched it!", "No"},
                "Watched it!"
        );
        if (response == JOptionPane.YES_OPTION) {
            model.addLastMovieGeneratedForUserToSeen();
        }
    }
}
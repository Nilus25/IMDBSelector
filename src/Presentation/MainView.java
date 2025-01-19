package Presentation;

import Domain.Exceptions.MovieNotFoundException;
import Domain.Exceptions.UserAlreadyExistsException;
import Domain.Exceptions.WatchListAlreadyExistsException;
import Domain.Model;
import Domain.Observer;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainView extends JFrame implements Observer {
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
        deleteUserButton.addActionListener(controller);
        deleteWatchListButton.addActionListener(controller);
        filtersButton.addActionListener(controller);
        viewWatchListButton.addActionListener(controller);

        return controller;
    }

    @Override
    public void update() {
        boolean hasUser = model.isThereUser();
        boolean hasWatchList = model.isThereWatchList();
        boolean hasCurrentMovie = model.isThereCurrentMovie();
        if (hasUser){
            userComboBox.setEditable(false);
            userComboBox.getEditor().getEditorComponent().setEnabled(true);
        }
        else {
            userComboBox.setEditable(false);
            userComboBox.getEditor().getEditorComponent().setEnabled(false);
        }
        if (hasWatchList){
            watchListComboBox.setEditable(false);
            watchListComboBox.getEditor().getEditorComponent().setEnabled(true);
        }
        else {
            watchListComboBox.setEditable(false);
            watchListComboBox.getEditor().getEditorComponent().setEnabled(false);
        }
        deleteUserButton.setEnabled(hasUser);
        deleteWatchListButton.setEnabled(hasWatchList);
        actualizeWatchListButton.setEnabled(hasWatchList);
        addMovieToSeenButton.setEnabled(hasUser && hasCurrentMovie);
        generateButton.setEnabled(hasUser && hasWatchList);

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
}
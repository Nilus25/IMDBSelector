package Presentation;

import Domain.Exceptions.MovieNotFoundException;
import Domain.Exceptions.UserAlreadyExistsException;
import Domain.Exceptions.WatchListAlreadyExistsException;
import Domain.Model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class MainViewController implements ActionListener {
    private Model model;
    private MainView view;
    private Map<Object, Runnable> actionMap;
    public MainViewController(Model model, MainView view) {
        this.view = view;
        this.model = model;
        initializeActionMap();
    }
    private void initializeActionMap() {
        actionMap = new HashMap<>();
        actionMap.put(view.getGenerateButton(), this::handleGenerateButton);
        actionMap.put(view.getNewWatchListButton(), this::handleNewWatchListButton);
        actionMap.put(view.getNewUserButton(), this::handleNewUserButton);
        actionMap.put(view.getAddMovieToSeenButton(), this::handleAddMovieToSeenButton);
        actionMap.put(view.getActualizeWatchListButton(), this::handleActualizeWatchListButton);
        actionMap.put(view.getRandomSelectorModeRadioButton(), this::handleRandomSelectorModeRadioButton);
        actionMap.put(view.getNotSeenSelectorModeRadioButton(), this::handleNotSeenSelectorModeRadioButton);
        actionMap.put(view.getWatchListComboBox(), this::handleWatchListComboBox);
        actionMap.put(view.getUserComboBox(), this::handleUserComboBox);
        actionMap.put(view.getDeleteUserButton(), this::handleDeleteUserButton);
        actionMap.put(view.getDeleteWatchListButton(), this::handleDeleteWatchListButton);
        actionMap.put(view.getFiltersButton(), this::handleFiltersButton);
        actionMap.put(view.getViewWatchListButton(), this::handleViewWatchListButton);
    }

    private void handleViewWatchListButton() {
    }

    private void handleFiltersButton() {
        JDialog filtersDialog = new JDialog(view, "Filters", true);
        FiltersView filtersView = model.getFiltersView();
        filtersDialog.setContentPane(filtersView.getMainPanel());
        filtersDialog.setSize(900, 600);
        filtersDialog.setLocationRelativeTo(view);
        filtersDialog.setVisible(true);
    }

    private void handleDeleteWatchListButton() {
        int response = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete the selected watchlist?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            model.deleteWatchList();
        }
    }

    private void handleDeleteUserButton() {
        int response = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete the selected user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            model.deleteUser();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Runnable action = actionMap.get(e.getSource());
        if (action != null) {
            action.run();
        }
    }
    private void handleUserComboBox() {
        String name = (String) view.getUserComboBox().getSelectedItem();
        model.loadUser(name);
    }

    private void handleWatchListComboBox() {
        String name = (String) view.getWatchListComboBox().getSelectedItem();
        model.loadWatchList(name);
    }
    private void handleGenerateButton() {
        try {
            model.selectRandomMovie();
        }
        catch (MovieNotFoundException e) {
            throwError(e.getMessage());
        }
    }

    private void handleNewWatchListButton(){
        String data = getDataWatchlist();
        if (data != null) {
            String watchListName = JOptionPane.showInputDialog(view, "Enter the name of the new watch list:", "New Watch List", JOptionPane.PLAIN_MESSAGE);
            if (watchListName != null && !watchListName.trim().isEmpty()) {
                try {
                    model.newWatchList(watchListName.trim(), data);
                } catch (WatchListAlreadyExistsException e) {
                    throwError(e.getMessage());
                }
            }
        }
    }

    private String getDataWatchlist() {
        String data = null;
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(view);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            Path path = selectedFile.toPath();
            try {
                data = Files.readString(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }

    private void handleNewUserButton() {
        String userName = JOptionPane.showInputDialog(view, "Enter the name of the new user:", "New User", JOptionPane.PLAIN_MESSAGE);
        if (userName != null && !userName.trim().isEmpty()) {
            try {
                model.newUser(userName.trim());
            } catch (UserAlreadyExistsException e) {
                throwError(e.getMessage());
            }
        }
    }

    private void throwError(String message) {
        JDialog errorDialog = new JDialog(view, "Error", true);
        errorDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        errorDialog.setSize(300, 150);
        errorDialog.setLocationRelativeTo(view);
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        errorDialog.add(messageLabel);
        errorDialog.setVisible(true);
    }

    private void handleAddMovieToSeenButton() {
        model.addMovieToSeen();
    }

    private void handleActualizeWatchListButton() {
        String movies = getDataWatchlist();
        if (movies != null) {
            model.actualizeWatchList(movies);
        }
    }

    private void handleRandomSelectorModeRadioButton() {
        model.setModeRandom();
    }

    private void handleNotSeenSelectorModeRadioButton() {
        model.setModeNotSeen();
    }
}


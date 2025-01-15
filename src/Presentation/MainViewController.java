package Presentation;

import Domain.Exceptions.UserAlreadyExistsException;
import Domain.Exceptions.WatchListAlreadyExistsException;
import Domain.Model;
import Domain.Observer;
import Domain.WatchList;
import com.opencsv.CSVReader;

import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewController implements ActionListener, Observer {
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
        model.selectRandomMovie();
    }

    private void handleNewWatchListButton() {
        List<String> movies = getMoviesFromCSV();
        if (movies != null) {
            String watchListName = JOptionPane.showInputDialog(view, "Enter the name of the new watch list:", "New Watch List", JOptionPane.PLAIN_MESSAGE);
            if (watchListName != null && !watchListName.trim().isEmpty()) {
                try {
                    model.newWatchList(watchListName.trim(), movies);
                } catch (WatchListAlreadyExistsException e) {
                    throwError(e.getMessage());
                }
            }
        }
    }

    private List<String> getMoviesFromCSV() {
        List<String> movies = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files", "csv"));
        int returnValue = fileChooser.showOpenDialog(view);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            try (CSVReader reader = new CSVReader(new FileReader(selectedFile))) {
                String[] line;
                while ((line = reader.readNext()) != null) {
                    movies.add(String.join(",", line));
                }
                return movies;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
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
        errorDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
        List<String> movies = getMoviesFromCSV();
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

    @Override
    public void update() {
        // Update the view based on changes in the model
    }
}

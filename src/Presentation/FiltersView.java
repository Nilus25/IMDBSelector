package Presentation;

import Domain.Model;

import javax.swing.*;
import java.awt.*;

public class FiltersView extends JPanel {
    private JPanel mainPanel;
    private JTextField keywordTextField;
    private JButton addKeywordButton;
    private JList keywordsList;
    private JButton deleteKeywordButton;
    private JSpinner minYearSpinner;
    private JSpinner maxYearSpinner;
    private JSpinner maxRatingSpinner;
    private JSpinner minRatingSpinner;
    private JButton resetKeywordsButton;
    private JButton resetFiltersButton;
    private JButton applyFiltersButton;
    private JButton cancelButton;
    private JLabel YearLabel;
    private JPanel MinYearLabel;
    private JLabel maxYearLabel;
    private JLabel maxRatingLabel;
    private JLabel minRatingLabel;
    private JLabel genresLabel;
    private JLabel typesLabel;
    private JPanel genresScrollBar;
    private JPanel typesScrollBar;
    private JLabel keywordsLabel;

    public FiltersView(Model model) {
    }

    public void initializeController() {
    }

    public Container getMainPanel() {
        return mainPanel;
    }
}

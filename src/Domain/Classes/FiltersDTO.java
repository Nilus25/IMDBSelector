package Domain.Classes;

public class FiltersDTO{
    private boolean[] checkedTypes;
    private boolean[] checkedGenres;
    private int minimumYearValue;
    private int maximumYearValue;
    private double minimumRatingValue;
    private double maximumRatingValue;

    public FiltersDTO(boolean[] checkedTypes, boolean[] checkedGenres, int minimumYearValue, int maximumYearValue, double minimumRatingValue, double maximumRatingValue) {
        this.checkedTypes = checkedTypes;
        this.checkedGenres = checkedGenres;
        this.minimumYearValue = minimumYearValue;
        this.maximumYearValue = maximumYearValue;
        this.minimumRatingValue = minimumRatingValue;
        this.maximumRatingValue = maximumRatingValue;
    }

    public boolean[] getCheckedTypes() {
        return checkedTypes;
    }

    public void setCheckedTypes(boolean[] checkedTypes) {
        this.checkedTypes = checkedTypes;
    }

    public boolean[] getCheckedGenres() {
        return checkedGenres;
    }

    public void setCheckedGenres(boolean[] checkedGenres) {
        this.checkedGenres = checkedGenres;
    }

    public int getMinimumYearValue() {
        return minimumYearValue;
    }

    public void setMinimumYearValue(int minimumYearValue) {
        this.minimumYearValue = minimumYearValue;
    }

    public int getMaximumYearValue() {
        return maximumYearValue;
    }

    public void setMaximumYearValue(int maximumYearValue) {
        this.maximumYearValue = maximumYearValue;
    }

    public double getMinimumRatingValue() {
        return minimumRatingValue;
    }

    public void setMinimumRatingValue(double minimumRatingValue) {
        this.minimumRatingValue = minimumRatingValue;
    }

    public double getMaximumRatingValue() {
        return maximumRatingValue;
    }

    public void setMaximumRatingValue(double maximumRatingValue) {
        this.maximumRatingValue = maximumRatingValue;
    }
    
}

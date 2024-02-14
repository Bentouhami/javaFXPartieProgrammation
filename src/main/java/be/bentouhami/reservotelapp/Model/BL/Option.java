package be.bentouhami.reservotelapp.Model.BL;

public class Option {
    private int id_option;
    private String description_option;
    private String option;

    public Option(int id_option, String description_option, String option) {
        this.id_option = id_option;
        this.description_option = description_option;
        this.option = option;
    }

    public int getId_option() {
        return id_option;
    }

    public String getDescription_option() {
        return description_option;
    }

    public void setDescription_option(String description_option) {
        this.description_option = description_option;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}



package be.bentouhami.reservotelapp.Model.BL;

public class Equipement {
    private int id_equipement;
    private String equipement;
    private String description_equipement;

    public Equipement(int id_equipement,
                      String equipement,
                      String description_equipement) {
        this.id_equipement = id_equipement;
        this.equipement = equipement;
        this.description_equipement = description_equipement;
    }
    public Equipement(String equipement,
                      String description_equipement) {
        this.equipement = equipement;
        this.description_equipement = description_equipement;
    }

    public int getId_equipement() {
        return id_equipement;
    }
    public String getDescription_equipement() {
        return description_equipement;
    }
    public void setDescription_equipement(String description_equipement) {
        this.description_equipement = description_equipement;
    }
    public String getEquipement() {
        return equipement;
    }

    public void setEquipement(String equipement) {
        this.equipement = equipement;
    }

}

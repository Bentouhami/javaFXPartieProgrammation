package be.bentouhami.reservotelapp.Model.BL;

public class Equipement {
    private long idEquipement;
    private String nomEquipement;
    public Equipement(long idEquipement, String nomEquipement){
        this.idEquipement = idEquipement;
        this.nomEquipement = nomEquipement;
    }

    public long getIdEquipement() {
        return idEquipement;
    }

    public String getNomEquipement() {
        return nomEquipement;
    }

    public void setNomEquipement(String nomEquipement) {
        this.nomEquipement = nomEquipement;
    }

}

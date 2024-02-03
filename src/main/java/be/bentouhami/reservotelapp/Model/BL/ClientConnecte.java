package be.bentouhami.reservotelapp.Model.BL;

public class ClientConnecte {


    private Client clientConnecte;

    public ClientConnecte() {
    }

    public ClientConnecte(Client client) {
       this.clientConnecte = client;
    }

    public void connecter(Client client) {
        this.clientConnecte = client;
    }

    public void deconnecter() {
        this.clientConnecte = null;
    }

    public Client getClientConnecte() {
        return clientConnecte;
    }

    public boolean estConnecte() {
        return clientConnecte != null;
    }

}

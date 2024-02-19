package ClientHandlers;

import DAO.WarehouseDAO;
import Entities.Warehouse;

import java.io.IOException;

public class WarehousesHandler {
    public WarehousesHandler() throws IOException {
        MainClientHandler.getOos().writeObject("Ok");
        MainClientHandler.getOos().flush();
    }

    public void GetAllWarehouses() throws IOException {
        MainClientHandler.getOos().writeObject(WarehouseDAO.GetAllWarehouses());
        MainClientHandler.getOos().flush();
    }

    public void AddWarehouse() throws IOException, ClassNotFoundException {
        WarehouseDAO.AddWarehosue((Warehouse)MainClientHandler.getOis().readObject());
    }
}

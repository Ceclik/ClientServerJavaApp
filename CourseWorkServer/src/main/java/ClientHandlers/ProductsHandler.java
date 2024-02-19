package ClientHandlers;

import DAO.ProductDAO;
import DAO.WarehouseDAO;
import Entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class ProductsHandler {

    public ProductsHandler() throws IOException {
        MainClientHandler.SendString("Ok");
    }

    public void GetAllProductsInfo() throws IOException {
        MainClientHandler.getOos().writeObject(ProductDAO.GetAllProducts());
        MainClientHandler.getOos().flush();
    }

    public void GetAllProductsOfWarehouse() throws IOException, ClassNotFoundException {
        Long warehouseId = (Long) MainClientHandler.getOis().readObject();
        MainClientHandler.getOos().writeObject(ProductDAO.getProductsByWarehouseId(warehouseId));
    }

    public void AddProduct() throws IOException, ClassNotFoundException {
        Product product = (Product) MainClientHandler.getOis().readObject();
        ProductDAO.AddProduct(product);
        WarehouseDAO.UpdateCapacity(product.getWarehouseId(), product.getAmountInWarehouse(), "add");
    }

    public void FulfillProductAmount() throws IOException, ClassNotFoundException {
        String name = (String) MainClientHandler.getOis().readObject();
        MainClientHandler.SendString("next");
        int amount = (int)MainClientHandler.getOis().readObject();
        Product fullProduct = ProductDAO.GetProductByName(name);
        Long id;
        if(fullProduct != null) {
            id = fullProduct.getProduct_id();
            ProductDAO.UpdateAmount(id, amount, "fulfill");
        }
        else System.out.println("Product hasn't been found!");

    }
}

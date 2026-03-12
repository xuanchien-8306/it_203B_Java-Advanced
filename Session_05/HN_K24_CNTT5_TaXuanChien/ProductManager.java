package Session_05.HN_K24_CNTT5_TaXuanChien;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductManager {

    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) throws InvalidProductException {

        for (Product p : products) {
            if (p.getId() == product.getId()) {
                throw new InvalidProductException("ID sản phẩm đã tồn tại");
            }
        }

        products.add(product);
    }

    public void displayProducts() {

        System.out.printf("%-5s %-20s %-10s %-10s %-15s\n",
                "ID", "Tên sản phẩm", "Giá", "Số lượng", "Danh mục");

        for (Product p : products) {
            System.out.printf("%-5d %-20s %-10.2f %-10d %-15s\n",
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory());
        }
    }

    public void updateQuantity(int id, int newQuantity) throws InvalidProductException {

        boolean found = false;

        for (Product p : products) {
            if (p.getId() == id) {
                p.setQuantity(newQuantity);
                found = true;
            }
        }

        if (!found) {
            throw new InvalidProductException("Không tìm thấy sản phẩm");
        }
    }

    public void deleteOutOfStock() {

        Iterator<Product> iterator = products.iterator();

        while (iterator.hasNext()) {
            Product p = iterator.next();

            if (p.getQuantity() == 0) {
                iterator.remove();
            }
        }
    }
}
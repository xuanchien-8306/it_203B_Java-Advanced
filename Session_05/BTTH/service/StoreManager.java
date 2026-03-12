// StoreManager.java
package Session_05.BTTH.service;

import Session_05.BTTH.exception.InvalidOrderIdException;
import Session_05.BTTH.model.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class    StoreManager {
    private List<MenuItem> menu = new ArrayList<>();

    public void addToMenu(MenuItem item) { menu.add(item); }

    public List<MenuItem> getMenu() { return new ArrayList<>(menu); }

    public MenuItem getItemById(String id) {
        return menu.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new InvalidOrderIdException("Không tìm thấy món: " + id));
    }

    public double calculateTotal(Map<MenuItem, Integer> cart) {
        return cart.entrySet().stream()
                .mapToDouble(e -> e.getKey().calculatePrice() * e.getValue())
                .sum();
    }

    public Iterable<Object> searchByName(String keyword) {

        return null;
    };
}
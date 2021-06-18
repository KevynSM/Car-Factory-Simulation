package pt.ual.pp.models;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    private int quantityMax;
    private int quantity;

    public Zona(int quantityMax) {
        this.quantityMax = quantityMax;
        this.quantity = 0;
    }

    public synchronized void enterZona() {
        while(quantity >= quantityMax) {
            try {
                wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        quantity++;

    }

    public synchronized void leaveZone() {
        quantity--;
        notify();
    }

    public int getQuantity() {
        return quantity;
    }
}

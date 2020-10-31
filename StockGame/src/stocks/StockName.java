package stocks;

import java.io.Serializable;

public enum StockName {
    HTC("宏達電"), HONHAI("鴻海"), BRAVESCREEN("倉和");

    private String name;

    StockName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;

    }
}

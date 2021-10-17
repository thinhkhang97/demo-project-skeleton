package testdata.purchasing;

public enum BaseComputerPrice {
    CHEAP("Cheap", 800.0),
    STANDARD("Standard", 1200.0);

    private final String type;

    private final Double value;

    BaseComputerPrice(String type, Double value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Double getValue() {
        return value;
    }
}

package testdata.purchasing;

public class ComputerOrder {
    private ComputerType computerType;
    private ComputerDataObject computerDataObject;
    private int quantity;

    public ComputerOrder(ComputerType computerType, ComputerDataObject computerDataObject, int quantity) {
        this.computerType = computerType;
        this.computerDataObject = computerDataObject;
        this.quantity = quantity;
    }

    public ComputerType getComputerType() {
        return computerType;
    }

    public ComputerDataObject getComputerDataObject() {
        return computerDataObject;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setComputerType(ComputerType computerType) {
        this.computerType = computerType;
    }

    public void setComputerDataObject(ComputerDataObject computerDataObject) {
        this.computerDataObject = computerDataObject;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

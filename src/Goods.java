public class Goods {
    private int id;
    private String name;
    private double price;
    private String description;
    private int sellerId;
    private boolean onSale;
    private String imagePath;

    public Goods(int id, String name, double price, String description, int sellerId, boolean onSale, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.sellerId = sellerId;
        this.onSale = onSale;
        this.imagePath = imagePath;
    }
    public int getId(){
        return id;
    }

}

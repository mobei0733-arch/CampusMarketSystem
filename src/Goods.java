public class Goods {
    private int id;
    private String name;
    private double price;
    private String category;
    private String description;
    private int sellerId;
    private String status;
    private String imagePath;

    public Goods(int id, String name, double price, String category, String description, int sellerId, String status, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.sellerId = sellerId;
        this.status = status;
        this.imagePath = imagePath;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription(){
        return description;
    }

    public int getSellerId(){
        return sellerId;
    }

    public String getStatus(){
        return status;
    }

    public String getImagePath(){
        return imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

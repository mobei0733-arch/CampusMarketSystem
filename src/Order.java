public class Order {
    private int id;          // 订单ID
    private int goodsId;    // 商品ID
    private int buyerId;    // 买家ID
    private int sellerId;   // 卖家ID
    private double price;// 成交价格

    public Order(int id, int goodsId, int buyerId,
                 int sellerId, double price) {
        this.id = id;
        this.goodsId = goodsId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public double getPrice() {
        return price;
    }
}

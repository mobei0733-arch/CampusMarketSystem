public class Order {
    private long id;          // 订单ID
    private int goodsId;    // 商品ID
    private int buyerId;    // 买家ID
    private int sellerId;   // 卖家ID
    private double price;// 成交价格
    private String status;// 订单状态

    public Order(long id, int goodsId, int buyerId,
                 int sellerId, double price,String status) {
        this.id = id;
        this.goodsId = goodsId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.status=status;
    }

    public long getId() {
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

    public String getStatus() {
        return status;
    }
}

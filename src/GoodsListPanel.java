import java.awt.*;
import java.util.Locale;
import javax.swing.*;

public class GoodsListPanel extends JPanel {
    // 搜索框
    private JTextField searchField;
    // 商品列表
    private JPanel goodsPanel;
    // 是否为管理员模式
    private boolean adminMode;

    public GoodsListPanel(boolean adminMode) {
        this.adminMode=adminMode;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.white);

        // 搜索框
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.white);
        searchField = new JTextField(20);
        JButton searchButton = new JButton("搜索");
        JButton clearButton = new JButton("清空搜索");

        searchPanel.add(new JLabel("商品名称"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);


        // 排序
        JPanel sortPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton defaultButton=new JButton("默认排序");
        JButton priceUpButton=new JButton("价格升序");
        JButton priceDownButton=new JButton("价格降序");

        sortPanel.add(defaultButton);
        sortPanel.add(priceUpButton);
        sortPanel.add(priceDownButton);

        JPanel topPanel=new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));
        topPanel.add(searchPanel);
        topPanel.add(sortPanel);

        add(topPanel,BorderLayout.NORTH);


        // 商品列表
        goodsPanel = new JPanel();
        goodsPanel.setLayout(new BoxLayout(goodsPanel, BoxLayout.Y_AXIS));
        goodsPanel.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(goodsPanel);
        add(scrollPane, BorderLayout.CENTER);

        //点击搜索
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            refreshGoods(keyword);
//            // 搜索完成后清空搜索框
//            searchField.setText("");
        });
        //回车
        searchField.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            refreshGoods(keyword);
//            // 搜索完成后清空搜索框
//            searchField.setText("");
        });
        clearButton.addActionListener(e -> {
            searchField.setText("");
            refreshGoods("");
        });
        //初次加载商品
        refreshGoods("");

        //默认排序
        defaultButton.addActionListener(e -> {
            refreshGoods(searchField.getText().trim());
        });

        //价格升序
        priceUpButton.addActionListener(e -> {
            GoodsSort.sortPrice(DataManager.showGoodsList,true);
            refreshShowGoods();
        });

        //价格降序
        priceDownButton.addActionListener(e -> {
            GoodsSort.sortPrice(DataManager.showGoodsList,false);
            refreshShowGoods();
        });

    }

    //加载刷新 搜索商品
    public void refreshGoods(String keyword) {
        goodsPanel.removeAll();
        boolean found = false;
        DataManager.showGoodsList.clear();

        // 商品列表，搜索商品
        for (Goods goods : DataManager.goodsList) {
            if(!adminMode && !"在售".equals(goods.getStatus())) {
                continue;
            }
            String name = goods.getName();
            if (name == null) {
                name = "";
            }
            if (!name.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))) {
                continue;
            }
            found = true;

            // 创建商品卡片并添加到列表
            DataManager.showGoodsList.add(goods);

            JPanel itemPanel = createGoodsItem(goods);
            goodsPanel.add(itemPanel);
            goodsPanel.add(Box.createVerticalStrut(5));

        }
        if (!found) {
            JLabel emptyLabel = new JLabel(keyword.isEmpty() ? "目前还没有商品" : "没有找到相关商品");
            emptyLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            goodsPanel.add(emptyLabel);
            goodsPanel.add(Box.createVerticalStrut(30));
        }
        goodsPanel.revalidate();
        goodsPanel.repaint();
    }
    //创建商品卡片
    private JPanel createGoodsItem(Goods goods) {
        // 创建商品卡片
        JPanel itemPanel = new JPanel(new BorderLayout(10,5));
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if(adminMode){
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,150));
        }else{
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,120));
        }
        if(adminMode){
            itemPanel.setPreferredSize(new Dimension(650, 150));
        }else{
            itemPanel.setPreferredSize(new Dimension(650, 120));
        }
        itemPanel.setBackground(Color.WHITE);

        //商品信息
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);


        JLabel nameLabel = new JLabel("商品名称：" + goods.getName());
        JLabel priceLabel = new JLabel("价格：¥" + goods.getPrice());
        JLabel categoryLabel = new JLabel("分类：" + goods.getCategory());
        JLabel descriptionLabel = new JLabel("描述：" + goods.getDescription());

        JLabel sellerLabel = new JLabel();
        if(!adminMode && goods.getSellerId()==DataManager.currentUser.getId()){
            sellerLabel = new JLabel("卖家ID：" + goods.getSellerId()+"（我的商品）");
        }
        else {
            sellerLabel = new JLabel("卖家ID：" + goods.getSellerId());
        }
        JLabel statusLabel = new JLabel("状态：" + goods.getStatus());

        //买家信息（仅管理员模式下显示）
        JLabel buyerLabel=null;
        if(adminMode){
            buyerLabel=new JLabel(getBuyerInfo(goods));
        }

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(descriptionLabel);
        infoPanel.add(sellerLabel);
        infoPanel.add(statusLabel);

        if(adminMode){
            infoPanel.add(buyerLabel);
        }

        // 商品图片
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(90, 90));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // 设置商品图片
        setGoodsImage(imageLabel, goods);

        itemPanel.add(imageLabel, BorderLayout.WEST);
        itemPanel.add(infoPanel, BorderLayout.CENTER);

        // 购买按钮
        JPanel buttonPanel=new JPanel();
        //判断是不是自己的商品
        if(!adminMode && goods.getSellerId()!=DataManager.currentUser.getId()){
            //不是自己的商品，可购买
            JButton buyButton=new JButton("购买商品");
            buyButton.addActionListener(e -> {
                buyGoods(goods);
            });
            buttonPanel.add(buyButton);
        }
        itemPanel.add(buttonPanel,BorderLayout.SOUTH);

        return itemPanel;
    }

    // 设置商品图片
    private void setGoodsImage(JLabel imageLabel, Goods goods) {
        String imagePath = goods.getImagePath();

        if (imagePath != null && !imagePath.trim().isEmpty()) {
            ImageIcon icon = new ImageIcon(imagePath);

            if (icon.getIconWidth() > 0) {
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(
                        80, 80, Image.SCALE_SMOOTH
                );
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                imageLabel.setText("暂无图片");
            }
        } else {
            imageLabel.setText("暂无图片");
        }
    }

    private void buyGoods(Goods goods) {
        int result=JOptionPane.showConfirmDialog(
                this,
                "确定购买该商品吗？",
                "购买确认",
                JOptionPane.YES_NO_OPTION
        );


        if(result!=JOptionPane.YES_OPTION){
            return;
        }

        long orderId=DataManager.nextOrderId++;
        //当前用户为买家
        int buyerId=DataManager.currentUser.getId();
        Order order=new Order(orderId,
                goods.getId(),
                buyerId,
                goods.getSellerId(),
                goods.getPrice(),
                "已购买");


        //加入订单列表
        DataManager.orderList.add(order);


        //修改商品状态
        goods.setStatus("已售出");
        JOptionPane.showMessageDialog(this, "购买成功");

        refreshGoods("");
    }
    private void refreshShowGoods(){
        goodsPanel.removeAll();

        for(Goods goods:DataManager.showGoodsList) {
            JPanel itemPanel = createGoodsItem(goods);

            goodsPanel.add(itemPanel);
            goodsPanel.add(Box.createVerticalStrut(5));
        }
        goodsPanel.revalidate();
        goodsPanel.repaint();
    }
    //获取买家信息(仅管理员模式下显示)
    private String getBuyerInfo(Goods goods){
        for(Order order:DataManager.orderList){
            if(order.getGoodsId()==goods.getId()){
                return "买家ID："+order.getBuyerId();
            }
        }
        return "暂无买家";
    }

}


import java.awt.*;
import java.util.Locale;
import javax.swing.*;

public class GoodsListPanel extends JPanel {
    // 搜索框
    private JTextField searchField;
    // 商品列表
    private JPanel goodsPanel;
    public GoodsListPanel() {
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
        add(searchPanel, BorderLayout.NORTH);

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

    }
    private void refreshGoods(String keyword) {
        goodsPanel.removeAll();
        boolean found = false;
        // 遍历商品列表，搜索商品
        for (Goods goods : DataManager.goodsList) {
            String name = goods.getName();
            if (name == null) {
                name = "";
            }
            if (!name.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))) {
                continue;
            }
            found = true;

            // 创建商品卡片并添加到列表
            JPanel itemPanel = createGoodsItem(goods);
            goodsPanel.add(itemPanel);
            goodsPanel.add(Box.createVerticalStrut(5));

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
    }
    private JPanel createGoodsItem(Goods goods) {
        // 创建商品卡片
        JPanel itemPanel = new JPanel(new BorderLayout(10,5));
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        itemPanel.setPreferredSize(new Dimension(650, 120));
        itemPanel.setBackground(Color.WHITE);

        //商品信息
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);


        JLabel nameLabel = new JLabel("商品名称：" + goods.getName());
        JLabel priceLabel = new JLabel("价格：¥" + goods.getPrice());
        JLabel categoryLabel = new JLabel("分类：" + goods.getCategory());
        JLabel descriptionLabel = new JLabel("描述：" + goods.getDescription());
        JLabel sellerLabel = new JLabel("卖家ID：" + goods.getSellerId());
        JLabel statusLabel = new JLabel("状态：" + goods.getStatus());

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(descriptionLabel);
        infoPanel.add(sellerLabel);
        infoPanel.add(statusLabel);

        // 商品图片
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(90, 90));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // 设置商品图片
        setGoodsImage(imageLabel, goods);

        itemPanel.add(imageLabel, BorderLayout.WEST);
        itemPanel.add(infoPanel, BorderLayout.CENTER);

        // 添加商品卡片到列表
        goodsPanel.add(itemPanel);
        goodsPanel.add(Box.createVerticalStrut(5));
        return goodsPanel;
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

    public void refreshGoodsList() {
        refreshGoods(searchField.getText().trim());
    }
}


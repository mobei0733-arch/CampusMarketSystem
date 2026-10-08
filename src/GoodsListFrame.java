import java.awt.*;
import javax.swing.*;

public class GoodsListFrame extends JPanel {
    private JTextField searchField;
    private JPanel goodsPanel;
    public GoodsListFrame() {
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



        // 读取商品
        for (Goods goods : DataManager.goodsList) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            itemPanel.setMaximumSize(new Dimension(650, 100));

            //商品信息
            JPanel infoPanel = new JPanel();

            infoPanel.setLayout(
                    new BoxLayout(infoPanel, BoxLayout.Y_AXIS)
            );

            JLabel nameLabel =
                    new JLabel("商品名称：" + goods.getName());

            JLabel priceLabel =
                    new JLabel("价格：¥" + goods.getPrice());

            JLabel categoryLabel =
                    new JLabel("分类：" + goods.getCategory());

            JLabel descriptionLabel =
                    new JLabel("描述：" + goods.getDescription());

            JLabel sellerLabel =
                    new JLabel("卖家ID：" + goods.getSellerId());

            JLabel statusLabel =
                    new JLabel(goods.getStatus());

            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);
            infoPanel.add(categoryLabel);
            infoPanel.add(descriptionLabel);
            infoPanel.add(sellerLabel);
            infoPanel.add(statusLabel);

            JLabel imageLabel = new JLabel();

            String imagePath = goods.getImagePath();

            if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image image = icon.getImage();

                Image scaledImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

                imageLabel.setIcon(new ImageIcon(scaledImage));

            }
            itemPanel.add(imageLabel, BorderLayout.WEST);
            itemPanel.add(infoPanel, BorderLayout.CENTER);

            goodsPanel.add(itemPanel);

            goodsPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(goodsPanel);
        add(scrollPane);

        setVisible(true);
    }
}

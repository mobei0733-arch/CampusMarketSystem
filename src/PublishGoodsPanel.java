import java.awt.*;
import javax.swing.*;

public class PublishGoodsPanel extends JPanel {
    private String imagePath = "";
    public PublishGoodsPanel() {
        setLayout(new BorderLayout());

        // 商品名称
        JLabel nameLabel = new JLabel("商品名称:");
        JTextField nameField = new JTextField(15);

        // 商品价格
        JLabel priceLabel = new JLabel("商品价格:");
        JTextField priceField = new JTextField(15);

        // 商品分类
        JLabel categoryLabel = new JLabel("商品分类:");
        String[] categories = {
                "教材",
                "电子产品",
                "生活用品",
                "服装",
                "体育用品"
        };
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);

        // 商品描述
        JLabel descriptionLabel = new JLabel("商品描述:");
        JTextField descriptionField = new JTextField(15);

        //商品图片
        JLabel imageLabel = new JLabel("商品图片:");
        JButton imageButton = new JButton("选择图片");

        JButton publishButton = new JButton("发布商品");


        //创建面板
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // 商品名称
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // 商品价格
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(priceLabel, gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        // 商品分类
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        panel.add(categoryComboBox, gbc);

        // 商品描述
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        panel.add(descriptionField, gbc);

        // 商品图片
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(imageLabel, gbc);
        gbc.gridx = 1;
        panel.add(imageButton, gbc);

        // 发布商品按钮
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(publishButton, gbc);

        add(panel, BorderLayout.CENTER);

        // 选择图片
        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                imagePath = fileChooser.getSelectedFile().getPath();
                JOptionPane.showMessageDialog(this, "图片选择成功");
            }
        });

        // 发布商品
        publishButton.addActionListener(e -> {
            String name = nameField.getText();
            String priceText = priceField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            String description = descriptionField.getText();

            //判断是否为空
            if (name.isEmpty() || priceText.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写完整商品信息");
                return;
            }

            //判断价格是否为数字
            double price;
            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "价格必须为数字");
                return;
            }

            //判断价格是否为正数
            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "价格必须为正数");
                return;
            }

            //商品ID
            int goodsId = DataManager.goodsList.size() + 1;

            //获取当前用户ID
            int sellerId = DataManager.currentUser.getId();

            //创建商品对象
            Goods goods = new Goods(goodsId, name, price, category, description, sellerId, "在售", imagePath);

            //保存商品
            DataManager.goodsList.add(goods);

           //提示发布成功
           JOptionPane.showMessageDialog(this, "商品发布成功");

//// 调试信息
//            System.out.println("商品发布成功：" + goods.getName());
//            System.out.println("当前商品总数：" + DataManager.goodsList.size());
//            System.out.println("图片路径：" + goods.getImagePath());

            // 清空输入框
            nameField.setText("");
            priceField.setText("");
            descriptionField.setText("");

            // 恢复商品分类默认选项
            categoryComboBox.setSelectedIndex(0);
            imagePath = "";
        });
    }
}

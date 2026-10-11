import javax.swing.*;
import java.awt.*;
public class MyGoodsPanel extends JPanel {
    private JPanel goodsPanel;
    private String newImagePath;
    public MyGoodsPanel() {
        setLayout(new BorderLayout(10,10));
        setBackground(Color.WHITE);
        JLabel titleLabel=new JLabel("我发布的商品",SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        add(titleLabel,BorderLayout.NORTH);

        goodsPanel=new JPanel();
        goodsPanel.setLayout(new BoxLayout(goodsPanel, BoxLayout.Y_AXIS));
        goodsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane=new JScrollPane(goodsPanel);
        add(scrollPane,BorderLayout.CENTER);

        refreshGoods();

    }

    public void refreshGoods() {
        goodsPanel.removeAll();
        if(DataManager.currentUser==null){
            return;
        }
        int userId=DataManager.currentUser.getId();

        boolean hasGoods=false;

        for(Goods goods:DataManager.goodsList) {
            if(goods.getSellerId()!=userId){
                continue;
            }
            hasGoods=true;

            JPanel itemPanel=new JPanel(new BorderLayout(10,5));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,130));

            //商品图片
            JLabel imageLabel = new JLabel();

            imageLabel.setPreferredSize(new Dimension(90,90));

            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //设置图片
            setGoodsImage(imageLabel,goods);
            itemPanel.add(imageLabel, BorderLayout.WEST);

            //商品信息
            JPanel infoPanel=new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.add(new JLabel("商品名称："+goods.getName()));
            infoPanel.add(new JLabel("价格："+goods.getPrice()));
            infoPanel.add(new JLabel("分类："+goods.getCategory()));
            infoPanel.add(new JLabel("商品描述："+goods.getDescription()));
            //判断商品状态
            if(goods.getStatus().equals("已售出")){
                infoPanel.add(new JLabel("状态：已售出"));

                Order order=findOrder(goods.getId());
                if(order!=null){
                    infoPanel.add(new JLabel("订单编号："+order.getId()));
                    infoPanel.add(new JLabel("购买用户ID："+order.getBuyerId()));
                }
            }else{
                infoPanel.add(new JLabel("状态："+goods.getStatus()));
            }

            itemPanel.add(infoPanel,BorderLayout.CENTER);

            //修改商品
            JPanel buttonPanel=new JPanel();
            if(!goods.getStatus().equals("已售出")){
                JButton changeButton=new JButton("修改商品");
                changeButton.addActionListener(e ->{changeGoods(goods);});
                buttonPanel.add(changeButton);
            }

            //下架
           if(goods.getStatus().equals("在售")){
                JButton downButton=new JButton("下架商品");

                downButton.addActionListener(e ->{
                    int result=JOptionPane.showConfirmDialog(this,"确定下架吗","提示",JOptionPane.YES_NO_CANCEL_OPTION);
                    if(result==JOptionPane.YES_OPTION){
                        goods.setStatus("已下架");
                        refreshGoods();
                    }});
                buttonPanel.add(downButton);
           }
           else if(goods.getStatus().equals("已下架")) {
               JButton onButton = new JButton("上架商品");

               onButton.addActionListener(e -> {
                   int result2 = JOptionPane.showConfirmDialog(this, "确定重新上架该商品吗？", "提示", JOptionPane.YES_NO_OPTION);
                   if (result2 == JOptionPane.YES_OPTION) {
                       goods.setStatus("在售");
                       refreshGoods();
                   }
               });
               buttonPanel.add(onButton);
           }

            itemPanel.add(buttonPanel,BorderLayout.SOUTH);
            goodsPanel.add(itemPanel);
            goodsPanel.add(Box.createVerticalStrut(10));
        }

        if(!hasGoods){
            JLabel emptyLabel=new JLabel("你还没有发布商品");
            emptyLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            goodsPanel.add(emptyLabel);

        }
        goodsPanel.revalidate();
        goodsPanel.repaint();
    }

    //修改商品
    public void changeGoods(Goods goods){
        //修改商品名称
        newImagePath = goods.getImagePath();
        JDialog dialog=new JDialog();
        dialog.setTitle("修改商品");
        dialog.setSize(400,400);
        dialog.setLocationRelativeTo(this);

        JPanel panel=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(8,8,8,8);

        //商品名称
        JLabel nameLabel = new JLabel("商品名称:");
        JTextField nameField = new JTextField(goods.getName(),15);

        //价格
        JLabel priceLabel = new JLabel("商品价格:");
        JTextField priceField = new JTextField(String.valueOf(goods.getPrice()), 15);

        //分类
        JLabel categoryLabel = new JLabel("商品分类:");

        String[] categories = {
                "教材",
                "电子产品",
                "生活用品",
                "服装",
                "体育用品"
        };

        JComboBox<String> categoryBox = new JComboBox<>(categories);
        categoryBox.setSelectedItem(goods.getCategory());

        //描述
        JLabel descriptionLabel = new JLabel("商品描述:");
        JTextField descriptionField = new JTextField(goods.getDescription(), 15);

        //商品图片
        JLabel imageLabel = new JLabel("商品图片:");
        JButton imageButton = new JButton("重新选择图片");

        imageButton.addActionListener(e->{
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(dialog);
            if(result == JFileChooser.APPROVE_OPTION){
                newImagePath = chooser.getSelectedFile().getPath();
                JOptionPane.showMessageDialog(dialog, "图片选择成功");
            }
        });
        JButton saveButton = new JButton("保存修改");


        //商品名称
        gbc.gridx=0;
        gbc.gridy=0;
        panel.add(nameLabel,gbc);
        gbc.gridx=1;
        panel.add(nameField,gbc);
        //商品价格
        gbc.gridx=0;
        gbc.gridy=1;
        panel.add(priceLabel,gbc);
        gbc.gridx=1;
        panel.add(priceField,gbc);
        //商品分类
        gbc.gridx=0;
        gbc.gridy=2;
        panel.add(categoryLabel,gbc);
        gbc.gridx=1;
        panel.add(categoryBox,gbc);
        //商品描述
        gbc.gridx=0;
        gbc.gridy=3;
        panel.add(descriptionLabel,gbc);
        gbc.gridx=1;
        panel.add(descriptionField,gbc);
        //商品图片
        gbc.gridx=0;
        gbc.gridy=4;
        panel.add(imageLabel,gbc);
        gbc.gridx=1;
        panel.add(imageButton,gbc);
        //保存修改
        gbc.gridx=1;
        gbc.gridy=5;
        panel.add(saveButton,gbc);

        dialog.add(panel);

        saveButton.addActionListener(e->{
                    String name = nameField.getText().trim();
                    String priceText = priceField.getText().trim();

                    if(name.isEmpty() || priceText.isEmpty()){
                        JOptionPane.showMessageDialog(dialog, "商品名称和价格不能为空");
                        return;
                    }
                    double price;

                    try{
                        price = Double.parseDouble(priceText);
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(dialog, "价格必须是数字");

                        return;
                    }

                    if(price<=0){
                        JOptionPane.showMessageDialog(dialog, "价格必须大于0");
                        return;
                    }

            //修改商品信息
            goods.setName(name);
            goods.setPrice(price);
            goods.setCategory((String)categoryBox.getSelectedItem());
            goods.setDescription(descriptionField.getText());
            goods.setImagePath(newImagePath);

            JOptionPane.showMessageDialog(dialog, "修改成功");

            dialog.dispose();

            refreshGoods();

        });
        dialog.setVisible(true);

    }

    //设置图片
    private void setGoodsImage(JLabel imageLabel, Goods goods){
        String imagePath = goods.getImagePath();

        if(imagePath!=null && !imagePath.trim().isEmpty()){
            ImageIcon icon = new ImageIcon(imagePath);

            if(icon.getIconWidth()>0){
                Image image = icon.getImage();

                Image scaledImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

                imageLabel.setIcon(new ImageIcon(scaledImage));
            }
        }else{
            imageLabel.setText("暂无图片");
        }
    }

    private Order findOrder(int goodsId){
        for(Order order:DataManager.orderList){
            if(order.getGoodsId()==goodsId){
                return order;
            }
        }
        return null;
    }

}


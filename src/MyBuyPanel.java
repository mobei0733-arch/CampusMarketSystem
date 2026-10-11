import javax.swing.*;
import java.awt.*;

public class MyBuyPanel extends JPanel {
    private JPanel goodsPanel;

    public MyBuyPanel() {
        setLayout(new BorderLayout(10,10));
        setBackground(Color.white);

        JLabel titleLabel=new JLabel("我购买的商品",SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑",Font.BOLD,22));

        add(titleLabel,BorderLayout.NORTH);

        goodsPanel=new JPanel();
        goodsPanel.setLayout(new BoxLayout(goodsPanel,BoxLayout.Y_AXIS));
        goodsPanel.setBackground(Color.white);

        JScrollPane scrollPane=new JScrollPane(goodsPanel);
        add(scrollPane,BorderLayout.CENTER);

        refreshBuyGoods();
    }

    public void refreshBuyGoods() {
        goodsPanel.removeAll();

        int userId=DataManager.currentUser.getId();

        boolean hasGoods=false;

        for(Order order:DataManager.orderList) {
            if(order.getBuyerId()!=userId) {
                continue;
            }

            JPanel itemPanel=new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));

            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));

            Goods goods=findGoods(order.getGoodsId());

            if(goods==null) {
                continue;
            }

            hasGoods=true;

            //商品图片
            JLabel imageLabel=new JLabel();
            imageLabel.setPreferredSize(new Dimension(90,90));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            //设置图片
            setGoodsImage(imageLabel,goods);
            itemPanel.add(imageLabel,BorderLayout.WEST);

            //商品信息
            JPanel infoPanel=new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));

            infoPanel.add(new JLabel("订单编号："+order.getId()));
            infoPanel.add(new JLabel("商品名称："+goods.getName()));
            infoPanel.add(new JLabel("购买价格："+order.getPrice()));
            infoPanel.add(new JLabel("卖家ID："+order.getSellerId()));
            infoPanel.add(new JLabel("状态："+order.getStatus()));

            itemPanel.add(infoPanel,BorderLayout.CENTER);
            goodsPanel.add(itemPanel);
            goodsPanel.add(Box.createVerticalStrut(10));

        }

        if(!hasGoods) {
            JLabel emptyLabel=new JLabel("暂无购买商品");
            emptyLabel.setFont(new Font("微软雅黑",Font.PLAIN,18));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            goodsPanel.add(emptyLabel);
        }

        goodsPanel.validate();
        goodsPanel.repaint();

    }

    private Goods findGoods(int goodsId) {
        for(Goods goods:DataManager.goodsList) {
            if(goods.getId()==goodsId) {
                return goods;
            }
        }
        return null;
    }
    private void setGoodsImage(JLabel imageLabel, Goods goods){
        String imagePath=goods.getImagePath();
        if(imagePath!=null && !imagePath.trim().isEmpty()){
            ImageIcon icon=new ImageIcon(imagePath);
            if(icon.getIconWidth()>0){
                Image image=icon.getImage();
                Image scaledImage=image.getScaledInstance(
                        80,
                        80,
                        Image.SCALE_SMOOTH
                );
                imageLabel.setIcon(new ImageIcon(scaledImage));
            }
        }else{
            imageLabel.setText("暂无图片");
        }

    }

}
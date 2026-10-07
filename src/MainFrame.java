import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        setTitle("校园市场交易管理系统");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        User user =DataManager.currentUser;

        JLabel welcomeLabel=new JLabel("欢迎，"+user.getUsername());

        JButton goodsButton=new JButton("浏览商品");
        JButton publishButton=new JButton("发布商品");
        JButton mygoodsButton=new JButton("我的商品");
        JButton orderButton=new JButton("我的订单");
        JButton userButton=new JButton("个人信息");
        JButton switchButton=new JButton("切换用户");

        JPanel panel=new JPanel(new GridLayout(4,2,10,10));

        panel.add(welcomeLabel);
        panel.add(new JPanel());
        panel.add(goodsButton);
        panel.add(publishButton);
        panel.add(mygoodsButton);
        panel.add(orderButton);
        panel.add(userButton);
        panel.add(switchButton);

        add(panel);
        setVisible(true);





    }
}

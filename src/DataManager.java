import java.util.ArrayList;
public class DataManager {
    public static HashMap users=new HashMap();
    public static UserBST userBST=new UserBST();

    public static User currentUser;

    public static ArrayList<Goods> goodsList=new ArrayList<>();
    public static ArrayList<Goods> showGoodsList=new ArrayList<>();
    public static ArrayList<Order> orderList=new ArrayList<>();

    public static int nextUserid=202600001;
    public static int nextGoodsId = 1001;
    public static long nextOrderId = 3710020202600001L;

    public static void initAdmin(){
        String username="admin";
        if(userBST.search(username)!=null){
            return;
        }
        User admin=new User(202600000,username,"admin","admin");
        users.put(admin.getId(), admin);
        userBST.insert(admin);
    }
}

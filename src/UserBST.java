public class UserBST {
    public static class Node{
        String username;

        Node left;
        Node right;

        Node(String username){
            this.username=username;
        }
    }

    //根节点
    private Node root;

    //插入
    public void insert(String username){
        Node newnode=new Node(username);
        if(root==null){
            root=newnode;
            return;
        }
        Node current=root;

        while(true) {
            int result = username.compareTo(current.username);
            if (result < 0) {
                //插入到左子树
                if (current.left == null) {
                    current.left = newnode;
                    return;
                }
                current = current.left;
            } else if (result > 0) {
                //插入到右子树
                if (current.right == null) {
                    current.right = newnode;
                    return;
                }
                current = current.right;
            }
            else{
                //用户名已存在
                throw new IllegalArgumentException("用户名已存在");
            }

        }
    }
    //查找，判断用户名是否存在
    public boolean search(String username){
        Node current=root;
        while(current!=null){
            int result=username.compareTo(current.username);
            if(result==0){
                return true;
            }
            if(result<0){
                current=current.left;
            }
            else if(result>0){
                current=current.right;
            }
        }
        return false;
    }
    //删除

}

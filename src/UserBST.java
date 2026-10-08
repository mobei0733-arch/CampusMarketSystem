public class UserBST {
    public static class Node{
        String username;
        User user;

        Node left;
        Node right;

        Node(String username,User user){
            this.username=username;
            this.user=user;
        }
    }

    //根节点
    private Node root;

    //插入
    public void insert(User user){
        String username=user.getUsername();
        Node newnode=new Node(username,user);
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
    public User search(String username){
        Node current=root;
        while(current!=null){
            int result=username.compareTo(current.username);
            if(result==0){
                return current.user;
            }
            if(result<0){
                current=current.left;
            }
            else {
                current=current.right;
            }
        }
        return null;
    }
    //删除
    public void delete(String username){
        Node current=root;
        Node parent=null;
        //找到删除节点和父节点
        while(current!=null){
            int result=username.compareTo(current.username);
            if(result==0){
                break;
            }
            parent=current;

            if(result<0){
                current=current.left;
            }
            else {
                current=current.right;
            }
        }
        if(current==null){
            return; //删除节点不存在
        }

        //删除节点有左右子树
        if(current.left!=null&&current.right!=null) {
            //找右子树的最小节点min
            Node minParent=current;
            Node min=current.right;

            while(min.left!=null){
                minParent=min;
                min=min.left;
            }
            //右子树最小替换删除节点
            current.username=min.username;
            current.user=min.user;
            //删除
            if(minParent==current){
                current.right=min.right;
            }//min是current的右孩子（没有左子树，右子树可能有）
            else{
                minParent.left=min.right;
            }//min是current的右孩子的左子树（可能有右子树）

            return;
        }

        //删除节点最多有一个子节点
        Node child;
        if(current.left!=null){
            child=current.left;
        }
        else {
            child=current.right;
        }

        if(parent==null){
            root=child;
        }
        else if(parent.left==current){
            parent.left=child;
        }
        else {
            parent.right=child;
        }
    }
}

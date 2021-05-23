package skiplist;

import java.util.Random;
import java.util.Stack;

/**
 * @author zhang fan
 * @date 2021/5/23 11:37
 */
public class SkipList<T> {
    SkipNode headNode; //头节点，入口
    int highLevel;  //层数：1<=highLevel<=32
    Random random;//添加元素时随机添加索引
    final int MAX_LEVEL = 32;

     SkipList() {
         headNode = new SkipNode(Integer.MIN_VALUE,null);
         highLevel = 0;
         random = new Random();
    }
    public SkipNode search(int key){
         SkipNode team = headNode;
         while (team!=null){
             if(team.key==key){
                 return team;
             }else if(team.right==null){
                 team =team.down;
             }else if(team.right.key>key){ //右侧大于key，下降
                 team =team.down;
             }else{    //右侧小于key，向右
                 team =team.right;
             }
         }
         return null;
    }
    public void delete(int key){
         SkipNode team = headNode;
         while (team!=null){
             if(team.right==null){
                 team =team.down;
             }else if(team.right.key==key){//右侧即为待删除节点
                 team.right = team.right.right;
                 team =team.down;
             }else if(team.right.key>key){
                 team =team.down;
             }else {
                 team = team.right;
             }
         }
    }
    public void add(SkipNode node){
         int key = node.key;
         SkipNode findNode = search(key);//查找是否存在相应的节点，存在则更新节点的值
         if(findNode!=null){
             findNode.value = node.value;
             return;
         }
         Stack<SkipNode> stack = new Stack<>();//用栈记录向下和向右的节点
         SkipNode team = headNode;
         while (team!=null){
             if(team.right==null){
                 stack.add(team);
                 team = team.down;
             }else if(team.right.key>key){
                 stack.add(team);
                 team = team.down;
             }else {
                 team = team.right;
             }
         }
         int level = 1;
         SkipNode downNode = null;//保持前驱节点(即down的指向,初始为null)
         while (!stack.isEmpty()){
             team = stack.pop();
             SkipNode nodeTeam = new SkipNode(node.key,node.value);
             nodeTeam.down = downNode;//处理竖向
             downNode = nodeTeam;//标记新的节点下次使用
             if(team.right==null){
                 team.right = nodeTeam;
             }else{//水平方向处理
                 nodeTeam.right = team.right;
                 team.right = nodeTeam;
             }
             if(level>MAX_LEVEL) break;
             double num = random.nextDouble();
             if(num>0.5) break;;
             level++;
             if(level>highLevel){//比最大高度还高，但<=32
                 highLevel =level;
                 SkipNode highHeadNode = new SkipNode(Integer.MIN_VALUE,null);
                 highHeadNode.down = headNode;
                 headNode = highHeadNode;
                 stack.add(headNode);
             }
         }

    }
    public void printList(){
        SkipNode teamNode=headNode;
        int index=1;
        SkipNode last=teamNode;
        while (last.down!=null){
            last=last.down;
        }
        while (teamNode!=null) {
            SkipNode enumNode=teamNode.right;
            SkipNode enumLast=last.right;
            System.out.printf("%-8s","head->");
            while (enumLast!=null&&enumNode!=null) {
                if(enumLast.key==enumNode.key)
                {
                    System.out.printf("%-5s",enumLast.key+"->");
                    enumLast=enumLast.right;
                    enumNode=enumNode.right;
                }
                else{
                    enumLast=enumLast.right;
                    System.out.printf("%-5s","");
                }

            }
            teamNode=teamNode.down;
            index++;
            System.out.println();
        }

    }

    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();
        for (int i = 1; i < 20; i++) {
            skipList.add(new SkipNode(i,"zhangfan"));
        }
        skipList.printList();
        skipList.delete(4);
        skipList.delete(8);
        skipList.printList();
    }
}

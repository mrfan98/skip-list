package skiplist;

import java.util.Random;
import java.util.Stack;

/**
 * @author zhang fan
 * @date 2021/5/23 11:37
 */
public class SkipList<T> {
    SkipNode headNode; //ͷ�ڵ㣬���
    int highLevel;  //������1<=highLevel<=32
    Random random;//���Ԫ��ʱ����������
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
             }else if(team.right.key>key){ //�Ҳ����key���½�
                 team =team.down;
             }else{    //�Ҳ�С��key������
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
             }else if(team.right.key==key){//�Ҳ༴Ϊ��ɾ���ڵ�
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
         SkipNode findNode = search(key);//�����Ƿ������Ӧ�Ľڵ㣬��������½ڵ��ֵ
         if(findNode!=null){
             findNode.value = node.value;
             return;
         }
         Stack<SkipNode> stack = new Stack<>();//��ջ��¼���º����ҵĽڵ�
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
         SkipNode downNode = null;//����ǰ���ڵ�(��down��ָ��,��ʼΪnull)
         while (!stack.isEmpty()){
             team = stack.pop();
             SkipNode nodeTeam = new SkipNode(node.key,node.value);
             nodeTeam.down = downNode;//��������
             downNode = nodeTeam;//����µĽڵ��´�ʹ��
             if(team.right==null){
                 team.right = nodeTeam;
             }else{//ˮƽ������
                 nodeTeam.right = team.right;
                 team.right = nodeTeam;
             }
             if(level>MAX_LEVEL) break;
             double num = random.nextDouble();
             if(num>0.5) break;;
             level++;
             if(level>highLevel){//�����߶Ȼ��ߣ���<=32
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

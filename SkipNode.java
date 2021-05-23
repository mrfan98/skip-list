package skiplist;

/**
 * @author zhang fan
 * @date 2021/5/23 11:35
 */
public class SkipNode<T> {
    int key;
    T value;
    SkipNode right,down;

    public SkipNode(int key, T value) {
        this.key = key;
        this.value = value;
    }
}

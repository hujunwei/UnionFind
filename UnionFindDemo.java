package UnionFind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnionFindDemo {
    // Key is Value, Value is Parent
    // 等价于int[] Parent. arr[i] 为parent, i 是自己
    private Map<String, String> parentMap = new HashMap<>();
    private Map<String, Integer> rank = new HashMap<>();

    public void init(String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            parentMap.put(strs[i], strs[i]);
            rank.put(strs[i], 1);
        }
    }

    public String find(String str) {
        // 如果str的parent是自己
        if (parentMap.get(str).equals(str)) {
            return str;
        }

        // 如果不是自己，则继续找自己当前parent
        String parent = find(parentMap.get(str));
        // 找的过程中，做路径压缩, 把当前层的parent更新为parent的parent
        parentMap.put(parentMap.get(str), parent);

        return parent;
    }

    public void merge(String str1, String str2) {
        String root1 = this.find(str1);
        String root2 = this.find(str2);

        // 不是同源才进行合并
        if (root1 != root2) {
            // 如果root1的群的深度比root2小，则root1合并到root2
            if (rank.get(root1) < rank.get(root2)) {
                parentMap.put(parentMap.get(root1), root2);
            }
            // 如果root1的群的深度比root2大，或者相等，则root2合并到root1
            else {
                parentMap.put(parentMap.get(root2), root1);
            }

            // 如果深度值相等,则root2合并到root1,更新root1深度值
            if (rank.get(root1) == rank.get(root2)) {
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }
    
    public void printParentMap() {
        System.out.println("Parent Map is:");
        for (Map.Entry<String, String> e : parentMap.entrySet()) {
            System.out.println("<" + e.getKey() + ":" + e.getValue() + ">");
        }
    }

    public void printRank() {
        System.out.println("Rank Map is:");
        for (Map.Entry<String, Integer> e : rank.entrySet()) {
            System.out.println("<" + e.getKey() + ":" + e.getValue() + ">");
        }
    }
}

class UnionFindTest {
    public static void main(String[] args) {
        // Input
        String[] strs = new String[]{ "great", "good", "fine", "drama", "acting", "skills", "talent" };
        List<String[]> pairs = new ArrayList<>();
        pairs.add(new String[]{ "great", "good" });
        pairs.add(new String[]{ "fine", "good" });
        pairs.add(new String[]{ "drama", "acting" });
        pairs.add(new String[]{ "skills", "talent" });
    
        // Init
        UnionFindDemo unionFind = new UnionFindDemo();
        unionFind.init(strs);
        
        // Merge
        for (String[] pair : pairs) {
            unionFind.merge(pair[0], pair[1]);
        }
        
        // Print
        unionFind.printParentMap();
        unionFind.printRank();
    }
}

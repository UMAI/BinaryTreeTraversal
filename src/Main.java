import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static int numberOfElements;
    static int[] preorder;
    static int[] inorder;
    static int[] postorder;
    static BinaryTree tree;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int numberOfSets = scanner.nextInt();
        for (int k = 0; k < numberOfSets; k++) {
            numberOfElements = scanner.nextInt();
            scanner.nextLine();
            String order = scanner.nextLine();
            if (order.equals("PREORDER")) {
                preorder = new int[numberOfElements];
                for (int i = 0; i < numberOfElements; i++) {
                    preorder[i] = scanner.nextInt();
                }
            } else {
                postorder = new int[numberOfElements];
                for (int i = 0; i < numberOfElements; i++) {
                    postorder[i] = scanner.nextInt();
                }
            }
            scanner.nextLine();
            scanner.nextLine();
            inorder = new int[numberOfElements];
            for (int i = 0; i < numberOfElements; i++) {
                inorder[i] = scanner.nextInt();
            }

            System.out.println("SET: " + (k+1));
            tree = new BinaryTree(new Vertex(-1, null, null));
            if (order.equals("PREORDER")) {
                makeTreeFromPREORDERandINORDER(tree.root, 0, numberOfElements-1, 0, numberOfElements-1);
                System.out.println("POSTORDER:");
                tree.displayPOSTORDER(tree.root);
            } else {
                makeTreeFromPOSTORDERandINORDER(tree.root, 0, numberOfElements-1, 0, numberOfElements-1);
                System.out.println("PREORDER:");
                tree.displayPREORDER(tree.root);
            }
            System.out.println();
            System.out.println("LEVELORDER:");
            tree.displayLEVELORDER(tree.root);
            if (k != numberOfSets-1)
                System.out.println();
        }
    }

    private static void makeTreeFromPREORDERandINORDER(Vertex vertex, int leftPre, int rightPre, int leftIn, int rightIn) {
        vertex.data = preorder[leftPre];
        if (leftPre == rightPre)
            return;
        int i = leftIn;
        while ((inorder[i] != preorder[leftPre]) && (i <= rightIn))
            i++;
        if (leftPre+1 <= i+leftPre-leftIn && leftIn <= i-1) {
            vertex.left = new Vertex(-1, null, null);
            makeTreeFromPREORDERandINORDER(vertex.left, leftPre+1, i+leftPre-leftIn, leftIn, i-1);
        }
        if (leftPre+i+1-leftIn <= rightPre && i+1 <= rightIn) {
            vertex.right = new Vertex(-1, null, null);
            makeTreeFromPREORDERandINORDER(vertex.right, leftPre+i+1-leftIn, rightPre, i+1, rightIn);
        }
    }

    private static void makeTreeFromPOSTORDERandINORDER(Vertex vertex, int leftPost, int rightPost, int leftIn, int rightIn) {
        vertex.data = postorder[rightPost];
        if (leftPost == rightPost)
            return;
        int i = leftIn;
        while ((inorder[i] != postorder[rightPost]) && (i <= rightIn))
            i++;
        if (leftPost <= leftPost+i-leftIn-1 && leftIn <= i-1) {
            vertex.left = new Vertex(-1, null, null);
            makeTreeFromPOSTORDERandINORDER(vertex.left, leftPost, leftPost+i-leftIn-1, leftIn, i-1);
        }
        if (leftPost+i-leftIn <= rightPost-1 && i+1 <= rightIn) {
            vertex.right = new Vertex(-1, null, null);
            makeTreeFromPOSTORDERandINORDER(vertex.right, leftPost+i-leftIn, rightPost-1, i+1, rightIn);
        }
    }
}

class BinaryTree {
    Vertex root;

    public BinaryTree(Vertex root) {
        this.root = root;
    }

    public void displayPREORDER(Vertex vertex) {
        if (vertex != null) {
            System.out.print(vertex.data + " ");
            displayPREORDER(vertex.left);
            displayPREORDER(vertex.right);
        }
    }

    public void displayPOSTORDER(Vertex vertex) {
        if (vertex != null) {
            displayPOSTORDER(vertex.left);
            displayPOSTORDER(vertex.right);
            System.out.print(vertex.data + " ");
        }
    }

    public void displayLEVELORDER(Vertex vertex) {
        if (vertex != null) {
            int height = height(root);
            for (int i = 1; i <= height; i++) {
                displayLevel(root, i);
            }
        }
    }

    private int height(Vertex vertex) {
        if (vertex == null)
            return 0;
        else
        {
            int lheight = height(vertex.left);
            int rheight = height(vertex.right);

            if (lheight > rheight)
                return(lheight+1);
            else return(rheight+1);
        }
    }

    private void displayLevel(Vertex vertex, int level) {
        if (vertex == null)
            return;
        if (level == 1)
            System.out.print(vertex.data + " ");
        else if (level > 1)
        {
            displayLevel(vertex.left, level-1);
            displayLevel(vertex.right, level-1);
        }
    }
}

class Vertex {
    int data;
    Vertex left, right;

    public Vertex(int data, Vertex left, Vertex right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}

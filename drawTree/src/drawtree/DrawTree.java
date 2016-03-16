package drawtree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class Node{
    
    String info = "";
    int value;
    Node left = null;
    Node right = null;
    int poziom = 1;
    
    Node(String in,int a)
    {
        info =in +a;
        value=a;
        poziom=1;
    }
    
    public void nodeLeft(Node n)
    {
        this.left = n;
        n.poziom=this.poziom+1;
    }
    
    public void nodeRight(Node n)
    {
        this.right = n;
        n.poziom=this.poziom+1;
    }
}

class Tree{
    
    Node root = null;
    long ilew = 0;

    Tree(Node a)
    {
        root = a;
        ilew++;
        a.poziom=1;
      
    }   
    
    public void add(Node r,Node a)
    {
        if(r.value>a.value)
            if(r.left!=null)
                add(r.left,a);
            else
                r.left=a;
        else
            if(r.right!=null)
                add(r.right,a);
            else
                r.right=a;
    }
}

public class DrawTree {
        
    public static int pow(int a, int b)
    {
        int tmp=1;
        for(int i=0;i<b;i++)
            tmp*=a;
        return tmp;
    }
    
    public static double min(double a, double b)
    {
        if(a<b)
            return a;
        else
            return b;
    }

    public static double max(double a, double b)
    {
        if(a>b)
            return a;
        else
            return b;
    }

    public static void drawTree(Tree tree,int nodes)
    {
       JPanel treePanel = new JPanel()
       {    
            public void drawOval(Graphics x,double horizontalPosition,double verticalPosition, double width, double height, Color color)
            {
                x.fillOval((int)horizontalPosition,(int) verticalPosition,(int) width,(int) height);
                x.setColor(color);
            }
        
            public void drwTree(Node r,Graphics x,double horizontalPosition,double verticalPosition, double width, double height, double ParentHorizontalPosition)
            {
                if(r.left!=null)
                {   
                    double nextHorizontalPosition;
                    if(ParentHorizontalPosition==horizontalPosition)
                        nextHorizontalPosition = horizontalPosition/2;
                    else
                        nextHorizontalPosition =horizontalPosition-(max(ParentHorizontalPosition,horizontalPosition) - min(horizontalPosition,ParentHorizontalPosition))/2;
                    double nextVerticalPosition = verticalPosition+50;
                    x.drawLine((int)(horizontalPosition+width/2), (int) (verticalPosition+width/2), (int) (nextHorizontalPosition+width/2),(int) (nextVerticalPosition+width/2));
                    drwTree(r.left,x,nextHorizontalPosition,nextVerticalPosition,width,height,horizontalPosition);
                }
                if(r.right!=null)
                {
                    double nextHorizontalPosition;
                    if(ParentHorizontalPosition==horizontalPosition)
                        nextHorizontalPosition = 3*horizontalPosition/2;
                    else
                        nextHorizontalPosition =horizontalPosition-(min(horizontalPosition,ParentHorizontalPosition) - max(ParentHorizontalPosition,horizontalPosition))/2;
                    double q = verticalPosition+50;
                    x.drawLine((int)(horizontalPosition+width/2), (int) (verticalPosition+width/2), (int) (nextHorizontalPosition+width/2),(int) (q+width/2));
                    drwTree(r.right,x,nextHorizontalPosition,q,width,height,horizontalPosition);
                }
                if(r==tree.root)
                    x.setColor(Color.RED);
                else
                    x.setColor(Color.BLACK);
                x.drawString(r.info, (int) (horizontalPosition-10),(int) verticalPosition);
                drawOval(x,horizontalPosition,verticalPosition,width,height,Color.BLACK); 
            }
        
            @Override
            public void paint(Graphics g)
            {
                super.paint(g);
                int r=30;
                int treeHeight=nodes;
                int factor=15+3*treeHeight;
                int horizontalPosition=pow(2,treeHeight)*factor/2;
                int verticalDistB2Nodes=50;
                drwTree(tree.root, g, horizontalPosition, verticalDistB2Nodes, r, r,horizontalPosition); 
            }
        };
       
        treePanel.setLayout(new BorderLayout());
        JFrame frame = new JFrame();
        double horizontalVisible=1.0;
        double verticalVisible=1.0;
        int horizontalMargin=100;
        int verticalMargin=100;
        int verticalDistB2Nodes=50;
        int factor=15+3*nodes;
        treePanel.setPreferredSize(new Dimension((int)(horizontalVisible*pow(2,nodes)*factor+horizontalMargin),(int) verticalVisible*(nodes*verticalDistB2Nodes)+verticalMargin));
        JScrollPane jScrollPane = new JScrollPane(treePanel);
        frame.add(jScrollPane, BorderLayout.CENTER);
        frame.setSize(800,600);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Node a = new Node("a",0);
        Node b = new Node("b",1);
        Node c = new Node("c",2);
        Node d = new Node("d",3);
        Node z = new Node("z",26);
        
        Tree tree = new Tree(c);
        tree.add(tree.root, a);
        tree.add(tree.root, z);
        tree.add(tree.root, b);
        tree.add(tree.root, d);
        
        drawTree(tree,5);
        
        
    }
    
}

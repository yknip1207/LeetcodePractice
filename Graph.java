import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class Graph {

	static class Node{
		public final String name;
		public final HashSet<Edge> inEdges;
		public final HashSet<Edge> outEdges;
		public ArrayList<Boolean> inData;
		public Boolean outData;
		public int type = -1;
		public Boolean isEndNode = false;
		public Node(String name) {
			this.name = name;
			inEdges = new HashSet<Edge>();
			outEdges = new HashSet<Edge>();
		}
		public Node addEdge(Node node){
			Edge e = new Edge(this, node);
			outEdges.add(e);
			node.inEdges.add(e);
			return this;
		}
		@Override
		public String toString() {
			return name;
		}
		

		public void updateInData(Node n){
			if (this.inData == null) 
				this.inData = new ArrayList<Boolean>();
		
			this.inData.add(new Boolean(n.outData));
//			System.out.println(this.name +"的inData多加了一個" + n.name+"的"+n.outData);
		}
		public void setData(Boolean in){
			if(this.inData == null)
				this.inData = new ArrayList<Boolean>();
			this.inData.add(new Boolean(in));
			this.outData = new Boolean(in);
		}


		public void operate(){
			switch(this.type){
				case 1://AND gate
					this.outData = new Boolean(!this.inData.contains(false));
//					System.out.println("經過了AND gate, 變成" + this.outData);
					break;
				case 2://OR gate
					this.outData = new Boolean(this.inData.contains(true));
//					System.out.println("經過了OR gate, 變成" + this.outData);
					break;
				case 3://NOT gate
					this.outData = new Boolean(!this.inData.get(0));
//					System.out.println("經過了NOT gate, 變成" + this.outData);
					break;
				default:
					this.outData = new Boolean(this.inData.get(0));
//					System.out.println("不是gate，還是" + this.outData);
					break;
			}
			
		}
		
		public void setType(int type){this.type = type;}
	}

	static class Edge{
		public final Node from;
		public final Node to;
		public Edge(Node from, Node to) {
			this.from = from;
			this.to = to;
		}
		@Override
		public boolean equals(Object obj) {
			Edge e = (Edge)obj;
			return e.from == from && e.to == to;
		}
	}

	public static void main(Boolean[] args) {
		

		Node i1 = new Node("i1");
		Node i2 = new Node("i2");

		Node g1 = new Node("g1");
		Node g2 = new Node("g2");

		i1.addEdge(g1);
		i2.addEdge(g1);
		
		i1.addEdge(g2);
		i2.addEdge(g2);
		
		i1.setData(args[0]);
		i2.setData(args[1]);
		
		g1.setType(2);
		g2.setType(1);
		
		Node[] allNodes = {i1,i2,g1,g2};

/**		
		Node i1 = new Node("i1");
		Node i2 = new Node("i2");
		Node i3 = new Node("i3");
		Node g1 = new Node("g1");
		Node g2 = new Node("g2");
		Node g3 = new Node("g3");
		i1.addEdge(g1);
		g2.addEdge(g1);
		g3.addEdge(g1);
		i2.addEdge(g2);
		g2.addEdge(g3);
		i3.addEdge(g3);
		i1.setData(args[0]);
		i2.setData(args[1]);
		i3.setData(args[2]);
		g1.setType(1);
		g2.setType(3);
		g3.setType(2);
		Node[] allNodes = {i1,i2,i3,g1,g2,g3};
		
**/
		//L <- Empty list that will contain the sorted elements
		ArrayList<Node> L = new ArrayList<Node>();

		//S <- Set of all nodes with no incoming edges
		HashSet<Node> S = new HashSet<Node>(); 
		for(Node n : allNodes){
			if(n.inEdges.size() == 0){
//				System.out.println(n.name);
				S.add(n);
			}
			if(n.outEdges.size() == 0){
				n.isEndNode = true;
			}
		}

		//while S is non-empty do
		while(!S.isEmpty()){
			//remove a node n from S
			Node n = S.iterator().next();
			S.remove(n);
//			System.out.println(n.name);
			//insert n into L
			L.add(n);

			//for each node m with an edge e from n to m do
			for(Iterator<Edge> it = n.outEdges.iterator();it.hasNext();){
				//remove edge e from the graph
				Edge e = it.next();
				Node m = e.to;
//				System.out.println(e.from.name + "->" + e.to.name + "被拿掉了");
				it.remove();//Remove edge from n
				m.inEdges.remove(e);//Remove edge from m
				m.updateInData(n);
				//if m has no other incoming edges then insert m into S
				if(m.inEdges.isEmpty()){
					m.operate();		//全部的inedge都拿掉後就可以計算了
					S.add(m);
				}
			}
		}
		//Check to see if all edges are removed
		boolean cycle = false;
		for(Node n : allNodes){
			if(!n.inEdges.isEmpty()){
				cycle = true;
				break;
			}
		}
		if(cycle){
//			System.out.println("Cycle present, topological sort not possible");
		}else{
//			System.out.println("Topological Sort: "+Arrays.toString(L.toArray()));
			
		}
		
		Collections.sort(L, new NameComparator());
		for(Node n : L){
			if(n.isEndNode)
				System.out.print( n.name + "=>" + (n.outData==false?"0":"1")+", ");
		}
	}
}
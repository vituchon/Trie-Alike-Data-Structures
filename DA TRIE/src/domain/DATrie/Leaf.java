package domain.DATrie;

public class Leaf implements Comparable{
	
	public int idx;
	public DescentTrieNode node;
	
	public Leaf(int idx,DescentTrieNode node) {
	
		this.idx = idx;
		this.node = node;
	}

	@Override
	public int compareTo(Object leaf) {

		if (leaf == null) return 1;
		else{
			DescentTrieNode n = ((Leaf)leaf).node; 
			if ( n.check == this.node.check ) return 0;
			else if ( n.check > this.node.check ) return -1 ;
			else return 1;
		}
		
	}
}

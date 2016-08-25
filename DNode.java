
public class DNode {
	
	int element;
	DNode prev,next;
	
	public DNode(int i,DNode next, DNode prev){
		element=i;
		this.prev=prev;
		this.next=next;
	}

}

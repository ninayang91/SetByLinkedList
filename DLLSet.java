
public class DLLSet {//An unordered collection of elements without duplicates
	private int size;
	DNode header,tail;
	
	public DLLSet(){
		size=0;
		header=new DNode(0, null,null);
		tail=new DNode(0,null,header);
		header.next=tail;
	}
	
	public DLLSet(int[] sortedArray){
		this();
		DNode p=header;
		for(int i=0;i<sortedArray.length;i++){
			DNode x=new DNode(sortedArray[i],p.next,p);
			p.next.prev=x;
			p.next=x;
			p=x;
			size++;
		}
		
	}
	
	public DLLSet copy(){//deep copy, separate memory
		DLLSet temp=new DLLSet();
		DNode q=temp.header;
		for(DNode p=header.next;p!=tail;p=p.next){
			DNode x=new DNode(p.element,q.next,q);
			q.next.prev=x;
			q.next=x;
			q=x;
			temp.size++;
		}
		
		return temp;
	}
	
	public static DLLSet recUnion(DLLSet[] sArray){//there are a group of sets in the sArray
		return recUnion(sArray,0,sArray.length-1);
	}
	
	private static DLLSet recUnion(DLLSet[] sArray,int first, int last){//divide and conquer//O(nlgn)
		if(first>last) return null;
		//DLLSet temp=new DLLSet();
		if(first==last){
			return sArray[first].copy();
		}else{
			int mid=(first+last)/2;
			DLLSet set1=recUnion(sArray,first,mid);
			DLLSet set2=recUnion(sArray,mid+1,last);
			return set1.union(set2);
		}
/*		for(int i=0;i<=last;i++){
			temp=temp.union(sArray[i]);
		}*/
/*		while(first<=last){
			int mid=(first+last)/2;
			DLLSet set1=recUnion(sArray,first,mid);
			DLLSet set2=recUnion(sArray,mid+1,last);
			temp=set1.union(set2);
		}*/
		//return temp;
	}
	
	public static DLLSet fastUnion(DLLSet [] sArray){// use loops to implement binary union//O(nlgn)

		int n=sArray.length;
		DLLSet[] temp=sArray;
		while(n>=2){
			
			//save the union value in each loop
		if(n%2==0){
			
			for(int i=0;i<n-1;i=i+2){
				temp[i/2]=temp[i].union(temp[i+1]);
			}
		}else{// n is odd
			for(int i=0;i<n-2;i=i+2){
				temp[i/2]=temp[i].union(temp[i+1]);
				//temp[n/2-i/2]=sArray[n-2-i].union(sArray[n-1-i]);
			}
			temp[n/2-1]=temp[n-1].copy();//We could copy one from the middle to avoid more comparsions
			
		} 
		n=(n+1)/2;
		
		}
		return temp[0];
	}

	public DLLSet intersection(DLLSet s){
		DLLSet temp=new DLLSet();
		DNode pTemp=temp.header;
		DNode q=s.header.next,p=header.next;//two pointers,q refer to s, p refer to this;
		while(p!=tail && q!=s.tail){
			if(q.element<p.element){
				q=q.next;
			}else if(q.element==p.element){
				DNode x=new DNode(q.element,pTemp.next,pTemp);
				pTemp.next.prev=x;
				pTemp.next=x;
				temp.size++;
				q=q.next;
				p=p.next;
			}else{//q.element>p.element
				p=p.next;
			}
		}
		
		return temp;
	}
	
	public DLLSet union(DLLSet s){//Time O(n1+n2) in the worst case, Space O(1) apart from input and output set
		//Linked lists are ordered, merge s and this to meet the requirement of O(n1+n2)
		DLLSet temp=this.copy();
		DNode q=s.header.next,p=temp.header.next;//two pointers,q refer to s, p refer to temp (base on this);
		while(p!=temp.tail && q!=s.tail){
			if(q.element<p.element){
				DNode x=new DNode(q.element,p,p.prev);
				p.prev.next=x;
				p.prev=x;
				temp.size++;
				q=q.next;
			}else if(q.element==p.element){
				q=q.next;
			}else{//q.element>p.element
				p=p.next;
			}
		}
		if(p==temp.tail){
			while(q!=s.tail){
				DNode x=new DNode(q.element,p,p.prev);
				p.prev.next=x;
				p.prev=x;
				temp.size++;
				q=q.next;
			}
		}

		return temp;
	}
	
	public void remove(int v){
	
		for(DNode p=header.next;p!=tail;p=p.next){
			if(p.element==v){
				p.prev.next=p.next;
				p.next.prev=p.prev;
				size--;
				return;
			}
		}
	}
	
	public void add(int v){//make the linked list orderd
		if(header.next==tail){
			DNode temp=new DNode(v,header.next,header);
			header.next.prev=temp;
			header.next=temp;
			size++;
			return;
		}
		
		for(DNode p=header.next;p!=tail;p=p.next){
			if(p.element==v){
				return;
			}else if(p.element>v){
				DNode temp=new DNode(v,p,p.prev);
				p.prev.next=temp;
				p.prev=temp;
				size++;
				return;		
			}
		}
		DNode temp=new DNode(v,tail,tail.prev);
		tail.prev.next=temp;
		tail.prev=temp;
		size++;
		
		
	}
	
	public boolean isIn(int v){
		for(DNode p=header.next;p!=tail;p=p.next){
			if(p.element==v){
				return true;
			}
		}
		return false;
	}
	
	public int getSize(){
		return size;
	}
	
	public String toString(){
		if(size==0) return "The set is empty";
		String s="";
		DNode p=header.next;
		for(;p.next!=tail;p=p.next){
			s =s+p.element+ "\n";
		}
		s +=p.element;
		return s;
		
	}

}

package tree.trie;

public class TrieTree {
	private TrieTree[] nodes;
	private TrieTree father;
	private boolean finish;
	private static TrieTree root = new TrieTree();	
	private TrieTree tmp;
	private boolean ok;
	
	/**
	 * Construct TrieTree to a alphabet with 26 characters
	 */
	public TrieTree()
	{
		nodes = new TrieTree[26];
		finish = false;
		father = null;
	}
	
	/**
	 * Construct TrieTree to a alphabet with 26 characters and set its father
	 * @param f a father to this node
	 */
	public TrieTree(TrieTree f)
	{
		nodes = new TrieTree[26];
		finish = false;
		father = f;
	}
	
	/**
	 * Delete a child
	 * A father killing his own child, amazing!
	 * @param me a child to be killed
	 */
	private void killMeFather(TrieTree me)
	{
		for(int goingToDie = 0; goingToDie < 26; goingToDie++)
		{
			if(this.nodes[goingToDie] == me)
			{
				//Killing the child *_*
				nodes[goingToDie] = null;
			}
		}
	}

	private TrieTree[] getNodes() {
		return nodes;
	}
	
	private TrieTree getNodes(int i) {
		if(nodes[i] == null)
		{
			nodes[i] = new TrieTree();
		}
		return nodes[i];
	}
	
	private TrieTree getNodes(char c) {
		if(nodes[c -97] == null)
		{
			nodes[c - 97] = new TrieTree();
		}
		return nodes[c - 97];
	}

	private void setNodes(TrieTree[] node) {
		this.nodes = node;
	}
	
	private void setNodes(TrieTree node, int i) {
		this.nodes[i] = node;
	}
	
	private void setNodes(TrieTree node, char c) {
		this.nodes[c - 97] = node;
	}

	public TrieTree getFather() {
		return father;
	}

	public void setFather(TrieTree father) {
		this.father = father;
	}
	
	private boolean isFinish() {
		return finish;
	}

	private void setFinish(boolean finish) {
		this.finish = finish;
	}

	private static TrieTree getRoot() {
		return root;
	}

	private static void setRoot(TrieTree root) {
		TrieTree.root = root;
	}
	
	/**
	 * Verify is a node is terminal and it has children
	 * @return true or false
	 */
	private boolean hasMore()
	{
		for(int i = 0; i < 26; i++)
		{
			if (this.nodes[i] != null)
			{
				return (true && finish);
			}
		}
		return false;	
	}
	
	private int getNumberOfChildren()
	{
		int count = 0;
		for(int i = 0; i < 26; i++)
		{
			if (this.nodes[i] != null)
			{
				count++;
			}
		}
		return count;
	}
	
	public int search(String key)
	{
		this.ok = false;
		return search(key, this.root, 0);
	}
	
	/**
	 * Search in a recursive way a key in the TrieTree
	 * @param key A string to search in the tree
	 * @param no the node to search
	 * @param pos the character in the key to find
	 * @return the index of the last character found in the tree
	 */
	private int search(String key, TrieTree no, int pos)
	{
		key = key.toLowerCase();
		
		if(pos < key.length()) //tinha -1
		{
			char c = key.charAt(pos);
			if(no.getNodes(c) != null)
			{
				this.tmp = no;
				return search(key, no.getNodes(c), ++pos);
			}
		}
		//Last character of key is a final node, key found!
		else if(pos < key.length() && no.isFinish())
		{
			this.ok = true;
			this.tmp = no;
			return ++pos;
		}
		
		this.ok = false;
		this.tmp = no;
		return pos;
	}
	
	/**
	 * Remove a key from the tree
	 * @param key that will be removed
	 * @return true or false if success or fail
	 */
	public boolean remove(String key)
	{
		search(key);
		
		tmp.remove(key, new Wrapper(this.tmp));
		
		return this.ok;
	}
	
	/**
	 * Remove recursively 
	 * @param key that will be removed
	 * @param no a node to process
	 */
	private void remove(String key, Wrapper no){
		
		//Break condition
		if(no.no == null)
		{
			return;
		}
		
		//If the node is final and is not a leaf
		if(no.no.hasMore())
		{
			no.no.setFinish(false);
			this.ok = true;
			System.out.println("Return hasMore");
			return;
		}
		//This node is a leaf
		else
		{
			TrieTree father = no.no.getFather();
			
			if(no.no.getNumberOfChildren() > 1)
			{
				System.out.println("no.no.getNumberOfChildren(): " + no.no.getNumberOfChildren() );
				return;
			}
			else //if(no.no.getNodes().length == 1)
			{
				System.out.println("seting a node null");
				father.killMeFather(no.no);
				remove(key, new Wrapper(father));
				
			}
		}
		System.out.println("Return finish!");
		return;
	}
	/**
	 * Insert a key in TrieTree
	 * @param key a key to insert
	 * @return the status of insertion, true or false
	 */
	public boolean insert(String key)
	{
		return insert(key, this.root);
	}
	
	/**
	 * Recursive method to insert a key
	 * @param key to insert
	 * @param no the node where it will start the search to a prefix of the key
	 * @return true or success or false
	 */
	private boolean insert(String key, TrieTree no)
	{
		//Set string to lower case
		key = key.toLowerCase();
		
		TrieTree pt = no;
		//Search the biggest sub string in the tree
		int pos = search(key, pt, 0);
		//If there is not the key in the tree, add rest of the key
		if(!ok)
		{
			//Add in the tree each character from key that there is not in the tree
			for(int i = pos; i < key.length(); i++)
			{
				char j = key.charAt(i);
				tmp.setNodes(new TrieTree(tmp), j);
				tmp = tmp.getNodes(j);
			}
			//Set the last node as final node
			tmp.setFinish(true);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Print the tree
	 * @return a string with all words in the tree
	 */
	public String print()
	{
		return print(this.root, new StringBuilder());
	}
	
	/**
	 * print tree recursively
	 * @param no a node to start printing
	 * @return 
	 */
	private String print(TrieTree no, StringBuilder buff)
	{
		//Break condition
		if(no == null)
		{
			return null;
		}
		
		TrieTree[] nos = no.getNodes();
		
		for(int i = 0; i < 26; i++)
		{
			if(nos[i] != null)
			{
				System.out.printf("%c", 97 + i);
				
				//Buff storage the prefix, then if a key is prefix of another 
				//it is possible reprint this prefix
				buff.append(String.format("%c", 97 + i));
				
				if(nos[i].isFinish())
				{	
					System.out.println("");
					
					//Verify if this final word is a prefix to another
					TrieTree[] tmp = nos[i].getNodes();
					boolean coisa = false;
					
					for(int j = 0; j < 26; j++)
					{
						if(tmp[j] != null)
						{
							coisa = true;
							break;
						}
					}
					if(coisa)
					{
						System.out.print(buff.toString());
					}
				}
				print(nos[i], buff);
			}
		}
		
		return null;
	}
	
	/**
	 * Prints the node
	 *
	public String toString()
	{
		StringBuilder buff = new StringBuilder();
		
		for(int i = 0; i < 26; i++)
		{
			if(nodes[i] != null)
			{
				buff.append(String.format("%c ", (i + 97)));
			}
		}
		
		return buff.toString();
	}*/
	
	/**
	 * A Wrapper to make possible to modify received pointers 
	 * @author fenix
	 *
	 */
	private class Wrapper{
		public TrieTree no;
		
		public Wrapper(TrieTree no)
		{
			this.no = no;
		}
		
		public void setNull()
		{
			no = null;
		}
	}
}

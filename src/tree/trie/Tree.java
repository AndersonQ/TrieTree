package tree.trie;

import java.util.Scanner;

public class Tree {

	static TrieTree tree = new TrieTree();
	
	public static void main (String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int operation = Integer.MIN_VALUE;
		boolean c = true;
		
		tree.insert("and");
		//tree.insert("Anderson");
		//tree.insert("Fernando");
		tree.insert("Liege");
		//tree.insert("Tiago");
		
		while(c)
		{
			System.out.printf("What do you want?\n");
			System.out.printf("0 - Add new key\n");
			System.out.printf("1 - Search a key\n");
			System.out.printf("2 - Remove\n");
			System.out.printf("3 - Print tree\n");
			System.out.printf("4 - Exit\n");
			System.out.printf("Option: ");
			operation = sc.nextInt();
					
			switch (operation)
			{
				case 0 :
					insert();
					break;
				case 1:
					search();
					break;
				case 2:
					remove();
					break;
				case 3:
					tree.print();
					System.out.println();
					break;
				default:
				{
					c = false;
					System.out.printf("Bye\n");
				}
			}
		}
	}
	
	/**
	 * Ask to user a key and insert this in the tree
	 */
	private static void insert()
	{
		boolean ok;
		boolean added;
		String key;
		do
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("Give me a string to insert in the tree: ");
			key = sc.next();
			added = tree.insert(key);
			if(added)
			{
				System.out.println(key + " added!");
			}
			else
			{
				System.out.println("Problem to add key " + key);
			}
			System.out.print("Do you want to insert a new string?[Y/N]: ");
			if(sc.next().equalsIgnoreCase("y"))
			{
				ok = true;
			}
			else
			{
				ok = false;
			}
		}while(ok);
	}
	
	private static void search()
	{
		Scanner sc = new Scanner(System.in);
		String key;
		int ret;
		
		System.out.println("Give me a key to search: ");
		key = sc.next();
		ret = tree.search(key);
		if (ret == key.length())
		{
			System.out.println("Key found!");
		}
		else
		{
			System.out.println("Key NOT found!");
			System.out.println("ret = " + ret);
			System.out.println("key = " + key + " length = " + key.length());
		}
	}
	
	private static void remove()
	{
		Scanner sc = new Scanner(System.in);
		String key;

		System.out.println("Give me what you want to remove: ");
		key = key = sc.next();
		
		System.out.println("Remotios was a "+ tree.remove(key));
	}
}

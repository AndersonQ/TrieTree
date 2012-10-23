/*
 * Copyright 2012 Anderson Queiroz <contato(at)andersonq(dot)eti(dot)br>
 * 
 * This file is part of OFFViewer.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OFFViewer. If not, see <http://www.gnu.org/licenses/>.
 */

package tree.trie;

import java.util.Scanner;
import java.util.Vector;

public class Tree {

	static TrieTree tree = new TrieTree();
	
	public static void main (String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int operation = Integer.MIN_VALUE;
		boolean c = true;
		

		System.out.print("Words in the tree:");
		tree.insert("Anderson");
		tree.insert("Fernando");
		tree.insert("Liege");
		tree.insert("Tiago");
		for(int i = 0; i < 10; i++)
		{
			String key = String.format("%s%c", "UFABC", (97 + i));
			tree.insert(key);
		}
		tree.print();
				
		while(c)
		{
			System.out.printf("What do you want?\n");
			System.out.printf("0 - Add new key\n");
			System.out.printf("1 - Search a key\n");
			System.out.printf("2 - Remove\n");
			System.out.printf("3 - Get words words with a prefix chosen\n");
			System.out.printf("4 - Print tree\n");
			System.out.printf("5 - Exit\n");
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
					getSuggestions();
				case 4:
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
			//System.out.println("ret = " + ret);
			//System.out.println("key = " + key + " length = " + key.length());
		}
	}
	
	private static void remove()
	{
		Scanner sc = new Scanner(System.in);
		int ret;
		String key;

		System.out.print("Give me what you want to remove: ");
		key = sc.next();
		
		ret = tree.remove(key);
		
		System.out.println("Remotios was a "+ (ret == key.length()));
	}
	
	private static void getSuggestions()
	{
		Scanner sc = new Scanner(System.in);
		String key;
		int n;
		
		System.out.print("Give me a key: ");
		key = sc.next();
		System.out.print("Now, give the number of words to find: ");
		n = sc.nextInt();
		
		tree.getSuggestions(key, n);
	}
}

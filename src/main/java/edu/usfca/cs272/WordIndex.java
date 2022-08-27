package edu.usfca.cs272;

import java.nio.file.Path;
import java.util.List;

/**
 * A special type of {@link ForwardIndex} that indexes the UNIQUE words that
 * were found in a text file (represented by {@link Path} objects).
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
// TODO Update declaration to implement ForwardIndex interface
public class WordIndex {
	// TODO Implement ONLY the necessary methods here

	/**
	 * Demonstrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		/*
		 * IF YOU ARE SEEING COMPILE ERRORS... it is likely you have not yet
		 * properly implemented the interface!
		 */

		Path hello = Path.of("hello.txt");
		Path world = Path.of("world.txt");
		ForwardIndex<Path> index = new WordIndex();

		index.add(hello, List.of("hello", "hola", "aloha", "ciao"));
		index.add(world, List.of("earth", "mars", "venus", "pluto"));

		System.out.println(index.view());
		System.out.println(index.view(hello));
		System.out.println(index.view(world));
	}
}

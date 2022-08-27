package edu.usfca.cs272;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests the {@link WordIndex} class.
 *
 * @see WordIndex
 * @see ForwardIndex
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
@TestMethodOrder(MethodName.class)
public class WordIndexTest {
	/** Sample text file. */
	private static final Path animals = Path.of("src", "test", "resources", "animals.text");

	/** Sample text file. */
	private static final Path sentences = Path.of("src", "test", "resources", "sentences.md");

	/** Sample empty file. */
	private static final Path empty = Path.of("empty.txt");

	/** Sample simple file. */
	private static final Path hello = Path.of("hello.txt");

	/** Name of class being tested. */
	private static final String homework = WordIndex.class.getSimpleName();

	/**
	 * Creates an empty {@link WordIndex} and returns it casted as a
	 * {@link ForwardIndex}. If this method does not compile, then the
	 * {@link WordIndex} class is not properly implementing the
	 * {@link ForwardIndex} interface.
	 *
	 * @return new empty text file index
	 */
	public static ForwardIndex<Path> createEmpty() {
		/*
		 * IF YOU ARE SEEING COMPILE ERRORS... it is likely you have not yet
		 * properly implemented the interface!
		 */

		return new WordIndex();
	}

	/**
	 * Tests of an index with a single initial path and word.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class A_SimpleAddTests {
		/** Placeholder for index object being tested. */
		private ForwardIndex<Path> index;

		/**
		 * Initializes an empty text file index.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();
			index.add(hello, "hello");
		}

		/**
		 * Tests the toString() implementation.
		 */
		@Order(1)
		@Test
		public void testStringHello() {
			Assertions.assertTrue(index.toString().contains("hello"),
					"Override toString() with a useful implementation!");
		}

		/**
		 * Test number of paths.
		 */
		@Test
		@Order(2)
		public void testSizePaths() {
			Assertions.assertEquals(1, index.size(), index.toString());
		}

		/**
		 * Tests number of words for path.
		 */
		@Test
		@Order(3)
		public void testSizeWords() {
			Assertions.assertEquals(1, index.size(hello), index.toString());
		}

		/**
		 * Tests path exists in index.
		 */
		@Test
		@Order(4)
		public void testHasPath() {
			Assertions.assertTrue(index.has(hello), index.toString());
		}

		/**
		 * Tests word does NOT exist for a path.
		 */
		@Test
		@Order(5)
		public void testHasWordFalse() {
			Assertions.assertFalse(index.has(hello, "world"), index.toString());
		}

		/**
		 * Tests word DOES exist for a path.
		 */
		@Test
		@Order(6)
		public void testHasWordTrue() {
			Assertions.assertTrue(index.has(hello, "hello"), index.toString());
		}

		/**
		 * Tests paths are fetched properly.
		 */
		@Test
		@Order(7)
		public void testViewPaths() {
			Assertions.assertTrue(index.view().contains(hello), index.toString());
		}

		/**
		 * Tests words are fetched properly.
		 */
		@Test
		@Order(8)
		public void testViewWords() {
			Assertions.assertTrue(index.view(hello).contains("hello"),
					index.toString());
		}

		/**
		 * Tests size of paths fetched.
		 */
		@Test
		@Order(9)
		public void testViewPathsSize() {
			Assertions.assertEquals(1, index.view().size(), index.toString());
		}

		/**
		 * Tests size of words fetched.
		 */
		@Test
		@Order(10)
		public void testViewWordsSize() {
			Assertions.assertEquals(1, index.view(hello).size(), index.toString());
		}

		/**
		 * Tests adding same location/word pair twice has no impact.
		 */
		@Test
		@Order(11)
		public void testDoubleAdd() {
			index.add(hello, "hello");
			Assertions.assertEquals(1, index.size(hello), index.toString());
		}

		/**
		 * Tests adding new word for a location.
		 */
		@Test
		@Order(12)
		public void testAddNewWord() {
			index.add(hello, "world");
			Assertions.assertEquals(2, index.size(hello), index.toString());
		}

		/**
		 * Tests adding new location.
		 */
		@Test
		@Order(13)
		public void testAddNewPath() {
			index.add(Path.of("world.txt"), "world");
			Assertions.assertEquals(2, index.size(), index.toString());
		}
	}

	/**
	 * Tests empty index.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class B_EmptyTests {
		/** Placeholder for index object being tested. */
		private ForwardIndex<Path> index;

		/**
		 * Initializes an empty text file index.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();
		}

		/**
		 * Tests the toString() implementation.
		 */
		@Order(1)
		@Test
		public void testStringEmpty() {
			Assertions.assertFalse(index.toString().contains("TextFileIndex@"),
					"Override toString() with a useful implementation!");
		}

		/**
		 * Tests that there are no paths.
		 */
		@Test
		@Order(2)
		public void testSizePaths() {
			Assertions.assertEquals(0, index.size(), index.toString());
		}

		/**
		 * Tests that there are no words for a path not in our index.
		 */
		@Test
		@Order(3)
		public void testSizeWords() {
			Assertions.assertEquals(0, index.size(empty), index.toString());
		}

		/**
		 * Tests that a path does not exist as expected.
		 */
		@Test
		@Order(4)
		public void testHashPath() {
			Assertions.assertFalse(index.has(empty), index.toString());
		}

		/**
		 * Tests that a word does not exist as expected.
		 */
		@Test
		@Order(5)
		public void testHasWord() {
			Assertions.assertFalse(index.has(empty, "empty"), index.toString());
		}

		/**
		 * Tests that no paths are fetched as expected.
		 */
		@Test
		@Order(6)
		public void testViewPaths() {
			Assertions.assertTrue(index.view().isEmpty(), index.toString());
		}

		/**
		 * Tests that no words are fetched as expected.
		 */
		@Test
		@Order(7)
		public void testViewWords() {
			Assertions.assertTrue(index.view(empty).isEmpty(), index.toString());
		}
	}

	/**
	 * Tests the addAll implementations.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class C_AddAllTests {
		/** The target index. */
		private ForwardIndex<Path> index;

		/** The other index. */
		private ForwardIndex<Path> other;

		/**
		 * Initializes the indexes.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();
			other = createEmpty();
		}

		/**
		 * Tests adding array of one word.
		 */
		@Test
		@Order(1)
		public void testAddAllFalse() {
			index.add(hello, new String[] { "hello" });
			Assertions.assertEquals(1, index.size(hello), index.toString());
		}

		/**
		 * Tests adding array with two words.
		 */
		@Test
		@Order(2)
		public void testAddAllTrue() {
			index.add(hello, new String[] { "hello", "world" });
			Assertions.assertEquals(2, index.size(hello), index.toString());
		}

		/**
		 * Tests adding array with two words and one duplicate.
		 */
		@Test
		@Order(3)
		public void testAddAllDuplicate() {
			index.add(hello, new String[] { "hello", "world", "hello" });
			Assertions.assertEquals(2, index.size(hello), index.toString());
		}

		/**
		 * Tests adding array with mixed adds.
		 */
		@Test
		@Order(4)
		public void testMixedAdds() {
			index.add(hello, "hello");
			index.add(hello, new String[] { "hello", "world" });
			Assertions.assertEquals(2, index.size(hello), index.toString());
		}

		/**
		 * Tests adding an empty index to an empty index.
		 */
		@Test
		@Order(5)
		public void testEmptyEmpty() {
			index.addAll(other);
			Assertions.assertEquals(0, index.size(), index.toString());
			Assertions.assertEquals(0, other.size(), other.toString());
		}

		/**
		 * Tests adding an empty index to a simple index.
		 */
		@Test
		@Order(6)
		public void testSimpleEmpty() {
			index.add(hello, "hello");
			index.addAll(other);
			Assertions.assertEquals(1, index.view(hello).size(), index.toString());
			Assertions.assertEquals(0, other.view(hello).size(), other.toString());
		}

		/**
		 * Tests adding a simple index to an empty index.
		 */
		@Test
		@Order(7)
		public void testEmptySimple() {
			other.add(hello, "hello");
			index.addAll(other);
			Assertions.assertEquals(1, index.view(hello).size(), index.toString());
			Assertions.assertEquals(1, other.view(hello).size(), other.toString());
		}

		/**
		 * Tests adding a simple index to a simple index.
		 */
		@Test
		@Order(8)
		public void testSimpleSimpleDifferent() {
			index.add(hello, "hello");
			other.add(hello, "world");
			index.addAll(other);
			Assertions.assertEquals(2, index.view(hello).size(), index.toString());
			Assertions.assertEquals(1, other.view(hello).size(), other.toString());
		}

		/**
		 * Tests adding a simple index to a simple index.
		 */
		@Test
		@Order(9)
		public void testSimpleSimpleSame() {
			index.add(hello, "hello");
			other.add(hello, "hello");
			index.addAll(other);
			Assertions.assertEquals(1, index.view(hello).size(), index.toString());
			Assertions.assertEquals(1, other.view(hello).size(), other.toString());
		}

		/**
		 * Tests adding a simple index to a simple index.
		 */
		@Test
		@Order(10)
		public void testComplex() {
			index.add(Path.of("hello.txt"), List.of("hello", "hola"));
			index.add(Path.of("letters.txt"), List.of("a", "b", "c", "c"));

			other.add(Path.of("letters.txt"), List.of("b", "e"));
			other.add(Path.of("planets.txt"), List.of("earth", "mars"));

			index.addAll(other);

			Assertions.assertEquals(3, index.size(), index.toString());
			Assertions.assertEquals(4, index.view(Path.of("letters.txt")).size(),
					index.toString());
			Assertions.assertEquals(2, other.view(Path.of("letters.txt")).size(),
					other.toString());
		}
	}

	/**
	 * Tests of an index with a single initial location and word.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class D_ModificationTests {
		/** Placeholder for index object being tested. */
		private ForwardIndex<Path> index;

		/**
		 * Initializes an empty text file index.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();
			index.add(hello, "hello");
		}

		/**
		 * Tests that attempts to modify paths in index fails.
		 */
		@Test
		@Order(1)
		public void testPathsClear() {
			Collection<Path> elements = index.view();
			Assertions.assertThrows(Exception.class, () -> elements.clear());
			Assertions.assertTrue(index.has(hello), index.toString());
		}

		/**
		 * Tests that attempts to modify paths in index fails.
		 */
		@Test
		@Order(2)
		public void testPathsAdd() {
			Collection<Path> elements = index.view();
			Assertions.assertThrows(Exception.class, () -> elements.add(empty));
			Assertions.assertFalse(index.has(empty), index.toString());
		}

		/**
		 * Tests that attempts to modify words in index fails.
		 */
		@Test
		@Order(3)
		public void testWordsClear() {
			Collection<String> elements = index.view(hello);
			Assertions.assertThrows(Exception.class, () -> elements.clear());
			Assertions.assertTrue(index.has(hello, "hello"), index.toString());
		}

		/**
		 * Tests that attempts to modify words in index fails.
		 */
		@Test
		@Order(4)
		public void testWordsAdd() {
			Collection<String> elements = index.view(hello);
			Assertions.assertThrows(Exception.class, () -> elements.add("world"));
			Assertions.assertFalse(index.has(hello, "world"), index.toString());
		}
	}

	/**
	 * Tests real text files.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class E_RealIndexTests {
		/** Placeholder for index object being tested. */
		private ForwardIndex<Path> index;

		/**
		 * Initializes an empty text file index.
		 */
		@BeforeEach
		public void initializeIndex() {
			index = createEmpty();

			index.add(animals, getWords(animals));
			index.add(sentences, getWords(sentences));
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(1)
		@Test
		public void testAnimalPaths() {
			Assertions.assertTrue(index.has(animals), index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(2)
		@Test
		public void testSentencesPaths() {
			Assertions.assertTrue(index.has(sentences), index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(3)
		@Test
		public void testAnimals() {
			Assertions.assertEquals(8, index.size(animals), index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(4)
		@Test
		public void testSentences() {
			Assertions.assertEquals(41, index.size(sentences), index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(5)
		@Test
		public void testPaths() {
			Set<Path> expected = Set.of(animals, sentences);
			Assertions.assertTrue(index.view().containsAll(expected),
					index.toString());
		}

		/**
		 * Testing whether index was created properly.
		 */
		@Order(6)
		@Test
		public void testWords() {
			Set<String> expected = Set.of("okapi", "mongoose", "loris", "axolotl",
					"narwhal", "platypus", "echidna", "tarsier");
			Assertions.assertTrue(index.view(animals).containsAll(expected),
					index.toString());
		}
	}

	/**
	 * Tests of an index with a single initial location and word.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class F_ApproachTests {
		/**
		 * Testing whether implemented default methods in the interface only.
		 */
		@Order(1)
		@Test
		public void testAddList() {
			String debug = "\nDo not override default methods in " + homework
					+ "! For this homework, only implement those in the interface.\n";

			Method[] methods = WordIndex.class.getMethods();

			long found = Arrays.stream(methods)
					.filter(m -> m.getDeclaringClass().equals(WordIndex.class))
					.filter(m -> m.getName().startsWith("add"))
					.filter(m -> m.toString().contains("List"))
					.count();

			Assertions.assertTrue(found == 0, debug);
		}

		/**
		 * Testing whether implemented default methods in the interface only.
		 */
		@Order(2)
		@Test
		public void testAddArray() {
			String debug = "\nDo not override default methods in " + homework
					+ "! For this homework, only implement those in the interface.\n";

			Method[] methods = WordIndex.class.getMethods();

			long found = Arrays.stream(methods)
					.filter(m -> m.getDeclaringClass().equals(WordIndex.class))
					.filter(m -> m.getName().startsWith("add"))
					.filter(m -> m.toString().contains("String[]"))
					.count();

			Assertions.assertTrue(found == 0, debug);
		}

		/**
		 * Testing whether implemented default methods in the interface only.
		 */
		@Order(3)
		@Test
		public void testAddForwardIndex() {
			String debug = "\nDo not override default methods in " + homework
					+ "! For this homework, only implement those in the interface.\n";

			Method[] methods = WordIndex.class.getMethods();

			long found = Arrays.stream(methods)
					.filter(m -> m.getDeclaringClass().equals(WordIndex.class))
					.filter(m -> m.getName().startsWith("add"))
					.filter(m -> m.toString().contains("ForwardIndex"))
					.count();

			Assertions.assertTrue(found == 0, debug);
		}
	}

	/**
	 * Helper method to quickly read in a small text file and return words.
	 *
	 * @param path the path
	 * @return the words
	 */
	private static String[] getWords(Path path) {
		try {
			return Files.readString(path, StandardCharsets.UTF_8)
					.toLowerCase()
					.split("\\W+");
		}
		catch (IOException e) {
			Assertions.fail(e);
			return null;
		}
	}
}

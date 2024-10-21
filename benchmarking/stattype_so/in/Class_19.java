import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
class Test {
	public static void main(String args[]) {
		int[] solutionArray = { 1, 2, 3, 4, 5, 6, 16, 15, 14, 13, 12, 11 };

		shuffleArray(solutionArray);
		for (int i = 0; i < solutionArray.length; i++) {
			System.out.print(solutionArray[i] + " ");
		}
		System.out.println();

	}

	// Implementing Fisher???Yates shuffle
	static void shuffleArray(int[] ar) {
		// If running on Java 6 or older, use `new Random()` on RHS here
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	protected static List<Integer> range(int n) {
		return toList(new Range(n), new ArrayList<Integer>());
	}

	protected static <T> List<T> toList(Iterable<T> iterable) {
		return toList(iterable, new ArrayList<T>());
	}

	protected static <T> List<T> toList(Iterable<T> iterable, List<T> destination) {
		addAll(destination, iterable.iterator());

		return destination;
	}

	protected static <T> void addAll(Collection<T> collection, Iterator<T> iterator) {
		while (iterator.hasNext()) {
			collection.add(iterator.next());
		}
	}

}

class Range implements Iterable<Integer> {
  private int start;
  private int stop;
  private int step;

  Range(int n) {
    this(0, n, 1);
  }

  Range(int start, int stop) {
    this(start, stop, 1);
  }

  Range(int start, int stop, int step) {
    this.start = start;
    this.stop = stop;
    this.step = step;
  }

  @Override
  public Iterator<Integer> iterator() {
    final int min = start;
    final int max = stop / step;

    return new MyIterator();
  }
}

class MyIterator implements Iterator<Integer> {
      private int current = min;

      @Override
      public boolean hasNext() {
        return current < max;
      }

      @Override
      public Integer next() {
        if (hasNext()) {
          return current++ * step;
        } else {
          throw new NoSuchElementException("Range reached the end");
        }
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("Can't remove values from a Range");
      }

}

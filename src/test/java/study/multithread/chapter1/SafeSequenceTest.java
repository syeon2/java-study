package study.multithread.chapter1;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SafeSequenceTest {

	private final SafeSequence instance = new SafeSequence();
	private final Set<Integer> testSet = new ConcurrentSkipListSet<>();

	@AfterEach
	void afterEach() {
		testSet.clear();
	}

	@Test
	@DisplayName("Thread-Safe하게 만드는 기본적인 방법 - synchronized")
	void threadSafeUseSynchronized() {
		int size = 100000;

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= size; i++) {
					testSet.add(instance.setValue());
				}
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= size; i++) {
					testSet.add(instance.setValue());
				}
			}
		});

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		assertThat(testSet.contains(size * 2)).isTrue();
	}

	@Test
	@DisplayName("Thread-Safe하게 만드는 기본적인 방법2 - Atomic API")
	void threadSafeUseAtomicAPI() {
		int size = 100000;

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= size; i++) {
					testSet.add(instance.setValue2());
				}
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= size; i++) {
					testSet.add(instance.setValue2());
				}
			}
		});

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		assertThat(testSet.contains(size * 2)).isTrue();
	}
}
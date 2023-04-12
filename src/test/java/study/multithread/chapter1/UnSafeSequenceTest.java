package study.multithread.chapter1;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnSafeSequenceTest {

	private final Sequence instance = new UnSafeSequence();

	@Test
	@DisplayName("멀티스레드로 동시에 호출하면 rade condition이 발생, 메모리의 일관성이 깨진다.")
	void threadNotSafe() {
		int size = 1000000;
		Set<Integer> testSet = new ConcurrentSkipListSet<>();

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

		assertThat(testSet.contains(2000000)).isFalse();
	}
}
package study.multithread.chapter1;

import java.util.concurrent.atomic.AtomicInteger;

public class SafeSequence implements Sequence {

	private int value = 1;

	// 방법 1. method synchronized 예약어를 붙인다
	@Override
	public synchronized int setValue() {
		return this.value++;
	}

	// 방법 2. Atomic API를 사용한다. (원자적 연산)
	private AtomicInteger value2 = new AtomicInteger(1);

	public int setValue2() {
		return this.value2.incrementAndGet();
	}
}

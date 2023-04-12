package study.multithread.chapter1;

public class UnSafeSequence implements Sequence {

	private int value;

	@Override
	public int setValue() {
		return value++;
	}
}

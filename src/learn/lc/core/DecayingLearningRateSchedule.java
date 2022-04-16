package learn.lc.core;

/**
 * Implementation of the ``decaying'' learning rate schedule from AIMA p. 725.
 */
public class DecayingLearningRateSchedule implements LearningRateSchedule {

	/**
	 * Returns 1000/(1000+t).
	 * @see AIMA p. 725
	 */
	@Override
	public double alpha(int t) {
		return 1000.0/(1000.0+t);
	}

}

package pipeprt.dataLayer;

public class TransitionFactory {

	public static PipeTransition createTransition(double positionXInput,
			double positionYInput, String idInput, String nameInput,
			double nameOffsetXInput, double nameOffsetYInput, double rateInput,
			boolean timedTransition, boolean infServer, int angleInput,
			int priority) {
		return new PipeTransition(positionXInput, positionYInput, idInput,
				nameInput, nameOffsetXInput, nameOffsetYInput, rateInput,
				timedTransition, infServer, angleInput, priority);
	}

	public static PipeTransition createTransition(double positionXInput,
			double positionYInput) {
		return new PipeTransition(positionXInput, positionYInput);
	}

}

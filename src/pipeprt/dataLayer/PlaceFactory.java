package pipeprt.dataLayer;

import java.util.LinkedList;


public class PlaceFactory {

	public static PipePlace createPlace(double positionXInput,
			double positionYInput, String idInput, String nameInput,
			double nameOffsetXInput, double nameOffsetYInput,
			LinkedList<PipeMarking> initialMarkingInput,
			double markingOffsetXInput, double markingOffsetYInput,
			int capacityInput, boolean tagged) {
		return new PipePlace(positionXInput, positionYInput, idInput, nameInput,
				nameOffsetXInput, nameOffsetYInput, initialMarkingInput,
				markingOffsetXInput, markingOffsetYInput, capacityInput, tagged);
	}

	public static PipePlace createPlace(double positionXInput,
			double positionYInput, String idInput, String nameInput,
			double nameOffsetXInput, double nameOffsetYInput,
			LinkedList<PipeMarking> initialMarkingInput,
			double markingOffsetXInput, double markingOffsetYInput,
			int capacityInput) {
		return new PipePlace(positionXInput, positionYInput, idInput, nameInput,
				nameOffsetXInput, nameOffsetYInput, initialMarkingInput,
				markingOffsetXInput, markingOffsetYInput, capacityInput);
	}

	public static PipePlace createPlace(double positionXInput, double positionYInput) {
		return new PipePlace(positionXInput, positionYInput);
	}

}

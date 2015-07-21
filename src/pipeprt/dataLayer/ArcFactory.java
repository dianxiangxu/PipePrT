package pipeprt.dataLayer;

import java.util.LinkedList;

public class ArcFactory {

	public static NormalArc createNormalArc(double startPositionXInput,
			double startPositionYInput, double endPositionXInput,
			double endPositionYInput, PlaceTransitionObject sourceInput,
			PlaceTransitionObject targetInput, LinkedList<PipeMarking> weightInput,
			String idInput, boolean taggedInput) {
		return new NormalArc(startPositionXInput, startPositionYInput,
				endPositionXInput, endPositionYInput, sourceInput, targetInput,
				weightInput, idInput, taggedInput);
	}

	public static NormalArc createNormalArc(NormalArc arc) {
		return new NormalArc(arc);
	}

	public static NormalArc createNormalArc(PlaceTransitionObject newSource) {
		return new NormalArc(newSource);
	}

	public static InhibitorArc createInhibitorArc(double startPositionXInput,
			double startPositionYInput, double endPositionXInput,
			double endPositionYInput, PlaceTransitionObject sourceInput,
			PlaceTransitionObject targetInput, LinkedList<PipeMarking> weightInput,
			String idInput) {
		return new InhibitorArc(startPositionXInput, startPositionYInput,
				endPositionXInput, endPositionYInput, sourceInput, targetInput,
				weightInput, idInput);
	}

	public static InhibitorArc createInhibitorArc(InhibitorArc arc) {
		return new InhibitorArc(arc);
	}

	public static InhibitorArc createInhibitorArc(
			PlaceTransitionObject newSource) {
		return new InhibitorArc(newSource);
	}

	public static BidirectionalArc createBidirectionalArc(double startPositionXInput,
			double startPositionYInput, double endPositionXInput,
			double endPositionYInput, PlaceTransitionObject sourceInput,
			PlaceTransitionObject targetInput, LinkedList<PipeMarking> weightInput,
			String idInput) {
		return new BidirectionalArc(startPositionXInput, startPositionYInput,
				endPositionXInput, endPositionYInput, sourceInput, targetInput,
				weightInput, idInput);
	}

	public static BidirectionalArc createBidirectionalArc(BidirectionalArc arc) {
		return new BidirectionalArc(arc);
	}

	public static BidirectionalArc createBidirectionalArc(
			PlaceTransitionObject newSource) {
		return new BidirectionalArc(newSource);
	}

}

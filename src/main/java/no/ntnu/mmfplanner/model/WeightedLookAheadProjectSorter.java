package no.ntnu.mmfplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class uses the IFM heuristic, also known as the weighted look-ahead approach, to find the optimal NPV
 * of a project.
 */
public class WeightedLookAheadProjectSorter extends ProjectSorter {
	/**
	 * @param project
	 */
	public WeightedLookAheadProjectSorter(Project project) {
		super(project);
	}

	@Override
	protected void sort() {
		setProgressMax(1);

		// Initialize variables
		List<Mmf> mmfs = project.getMmfs();
		int[] periods = new int[project.size()];
		List<Mmf> finalStrand = new ArrayList<Mmf>();
		int finalPeriod = 0;
		int finalWNpv = 0;

		List<Mmf> unusedMmfs = new ArrayList<Mmf>(mmfs);

		// repeatedly find the most profitable MMF using the weighted look-ahead (IFM Heuristic)
		// approach, and add this to the final strand
		while (unusedMmfs.size() > 0) {
			List<List<Mmf>> strands = generateStrands(unusedMmfs, finalStrand);

			int maxWNpv = Integer.MIN_VALUE;
			Mmf maxMmf = null;

			// Calculate wnpvs for all strands, and add the most profitable to the final strand
			for (List<Mmf> strand : strands) {
				int period = finalPeriod;
				int wnpv = 0;

				for (Mmf mmf : strand) {
					wnpv += mmf.getWSaNpv(project.getInterestRate(), period, project.getWeightFactor(), strand.size());
					period += mmf.getPeriodCount();
				}

				if (wnpv >= maxWNpv) {
					maxWNpv = wnpv;
					maxMmf = strand.get(0);
				}
			}

			// we found the most profitable MMF, add it and remove it from unusedMmfs
			unusedMmfs.remove(maxMmf);

			finalStrand.add(maxMmf);
			finalWNpv += maxMmf.getSaNpv(project.getInterestRate(), finalPeriod);
			periods[project.getMmfs().indexOf(maxMmf)] = finalPeriod + 1;
			finalPeriod += maxMmf.getPeriodCount();
		}

		addResult(finalWNpv, periods);
		setProgress(1);
	}

}

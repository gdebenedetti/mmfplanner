package no.ntnu.mmfplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class uses the Simple Look-Ahead algorithm, also known as the simple look-ahead approach, to find the
 * optimal NPV of a project.
 */
public class SimpleLookAheadProjectSorter extends ProjectSorter {
	/**
	 * @param project
	 */
	public SimpleLookAheadProjectSorter(Project project) {
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
		int finalNpv = 0;

		List<Mmf> unusedMmfs = new ArrayList<Mmf>(mmfs);

		// repeatedly find the most profitable MMF using the simple look-ahead
		// approach, and add this to the final strand
		while (unusedMmfs.size() > 0) {
			List<List<Mmf>> strands = generateStrands(unusedMmfs, finalStrand);

			int maxNpv = Integer.MIN_VALUE;
			Mmf maxMmf = null;

			// Calculate npvs for all strands, and add the most profitable to the final strand
			for (List<Mmf> strand : strands) {
				int period = finalPeriod;
				int npv = 0;

				for (Mmf mmf : strand) {
					npv += mmf.getSaNpv(project.getInterestRate(), period);
					period += mmf.getPeriodCount();
				}

				if (npv >= maxNpv) {
					maxNpv = npv;
					maxMmf = strand.get(0);
				}
			}

			// we found the most profitable MMF, add it and remove it from unusedMmfs
			unusedMmfs.remove(maxMmf);

			finalStrand.add(maxMmf);
			finalNpv += maxMmf.getSaNpv(project.getInterestRate(), finalPeriod);
			periods[project.getMmfs().indexOf(maxMmf)] = finalPeriod + 1;
			finalPeriod += maxMmf.getPeriodCount();
		}

		addResult(finalNpv, periods);
		setProgress(1);
	}

}

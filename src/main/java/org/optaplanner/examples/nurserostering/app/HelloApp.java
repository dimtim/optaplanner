package org.optaplanner.examples.nurserostering.app;

import java.io.File;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;
import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.examples.nurserostering.persistence.NurseRosteringExporter;
import org.optaplanner.examples.nurserostering.persistence.NurseRosteringImporter;

public class HelloApp {
	public static final String SOLVER_CONFIG_XML = "org/optaplanner/examples/nurserostering/solver/nurseRosteringSolverConfig.xml";
	public static ScoreDirector scoreDirector;
	public static int printCount = 0;

	public static void main(String[] args) {
		Solver solver = SolverFactory.createFromXmlResource(SOLVER_CONFIG_XML)
				.buildSolver();
		ScoreDirectorFactory scoreDirectorFactory = solver
				.getScoreDirectorFactory();
		scoreDirector = scoreDirectorFactory.buildScoreDirector();

		NurseRosteringImporter importer = new NurseRosteringImporter();
		NurseRoster helloSolution = importer.readSolution(new File("/Users/admin/git/optaplannerschedules/data/nurserostering/import/toy2.xml"));

		scoreDirector.setWorkingSolution(helloSolution);
		System.out.println("\n\nTRYING COUNT AT:");
		solver.solve(helloSolution);
		NurseRoster bestHelloSolution = (NurseRoster) solver
				.getBestSolution();
		System.out.println(bestHelloSolution);
		NurseRosteringExporter exporter = new NurseRosteringExporter();
		exporter.writeSolution(bestHelloSolution, new File("/Users/admin/git/optaplannerschedules/data/nurserostering/import/toy2_solution.xml"));
	}

	public static String getSpacerFeed() {
		printCount++;
		if (printCount == 25) {
			printCount = 0;
			return "\n";
		} else {
			return " ";
		}
	}
}

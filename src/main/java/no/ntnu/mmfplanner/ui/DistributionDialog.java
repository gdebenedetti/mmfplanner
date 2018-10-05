package no.ntnu.mmfplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import no.ntnu.mmfplanner.ui.model.RevenueTableModel;
import no.ntnu.mmfplanner.ui.renderer.RevenueTableCellRenderer;

import org.uncommons.maths.demo.DistributionPanel;
import org.uncommons.maths.demo.GraphPanel;
import org.uncommons.maths.demo.ProbabilityDistribution;
import org.uncommons.maths.demo.RNGPanel;
import org.uncommons.swing.SwingBackgroundTask;

public class DistributionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final DistributionPanel distributionPanel = new DistributionPanel();
	private final RNGPanel rngPanel = new RNGPanel();
	private final GraphPanel graphPanel = new GraphPanel();
	private final JTable revenueTable = new javax.swing.JTable();
	private ProbabilityDistribution distribution;

	public DistributionDialog(MainFrame parent, boolean modal) {
		super(parent, modal);
		setTitle("Chose distribution");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		add(initComponents(), BorderLayout.WEST);
		add(graphPanel, BorderLayout.CENTER);

		revenueTable.setBackground(Color.WHITE);
		revenueTable.setModel(new RevenueTableModel(parent.getProject()));
		revenueTable.setDefaultRenderer(Object.class, new RevenueTableCellRenderer());

		add(revenueTable, BorderLayout.SOUTH);
		setSize(700, 500);
		setMinimumSize(new Dimension(500, 250));
		validate();
	}

	private JComponent initComponents() {
		Box controls = new Box(BoxLayout.Y_AXIS);
		controls.add(distributionPanel);
		controls.add(rngPanel);

		JButton executeButton = new JButton("Go");
		executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				DistributionDialog.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				new SwingBackgroundTask<GraphData>() {

					protected GraphData performTask() {
						distribution = distributionPanel.createProbabilityDistribution();

						Map<Double, Double> observedValues = distribution.generateValues(rngPanel.getIterations(),
								rngPanel.getRNG());
						Map<Double, Double> expectedValues = distribution.getExpectedValues();
						return new GraphData(observedValues, expectedValues, distribution.getExpectedMean(),
								distribution.getExpectedStandardDeviation());
					}

					protected void postProcessing(GraphData data) {
						graphPanel.generateGraph(distribution.getDescription(), data.getObservedValues(),
								data.getExpectedValues(), data.getExpectedMean(), data.getExpectedStandardDeviation(),
								distribution.isDiscrete());
						DistributionDialog.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}.execute();
			}
		});
		controls.add(executeButton);

		JButton setDistributionButton = new JButton("Set Distribution");
		setDistributionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (distribution != null) {
					RevenueTableModel model = (RevenueTableModel) revenueTable.getModel();
					int filaMMF = revenueTable.getSelectedRow();
					if (filaMMF != -1) {

						// comparo cantidad de períodos con la cantidad de valores generados por la
						// distribución
						Map<Double, Double> expectedValues = distribution.getExpectedValues();
						int expectedValuesCount = expectedValues.size();
						if (model.getColumnCount() <= expectedValuesCount) {
							// si hay menos o igual cantidad de periodos que de valores generados

							double step = (double) expectedValuesCount / model.getColumnCount();
							double valueAt = 0.0;

							// itero los valores del keyset que por ser LinkedHashMap vienen ordenados
							final Set<Map.Entry<Double, Double>> entries = expectedValues.entrySet();
							List<Double> values = new ArrayList<>();
							for (Map.Entry<Double, Double> entry : entries) {
								Double value = entry.getValue();
								values.add(value);
							}

							for (int i = 0; i < model.getColumnCount(); i++) {
								Double value = values.get((int) valueAt);
								value = value * 100; // TODO factor de multiplicacion
								model.setValueAt(value.intValue(), filaMMF, i);
								valueAt = valueAt + step;
							}

						} else {
							// si hay más períodos que valores generados
							JOptionPane.showMessageDialog(null, "generar rango de valores más amplio");
						}
					}
				}
			}
		});
		controls.add(setDistributionButton);

		return controls;
	}

	private static class GraphData {
		private final Map<Double, Double> observedValues;
		private final Map<Double, Double> expectedValues;
		private final double expectedMean;
		private final double expectedStandardDeviation;

		public GraphData(Map<Double, Double> observedValues, Map<Double, Double> expectedValues, double expectedMean,
				double expectedStandardDeviation) {
			this.observedValues = observedValues;
			this.expectedValues = expectedValues;
			this.expectedMean = expectedMean;
			this.expectedStandardDeviation = expectedStandardDeviation;
		}

		public Map<Double, Double> getObservedValues() {
			return observedValues;
		}

		public Map<Double, Double> getExpectedValues() {
			return expectedValues;
		}

		public double getExpectedMean() {
			return expectedMean;
		}

		public double getExpectedStandardDeviation() {
			return expectedStandardDeviation;
		}
	}

}

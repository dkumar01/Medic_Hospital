/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medic_hospital;

/**
 *
 * @author Deepak Kumar
 */
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GlucoseTrend {

    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    JFrame f = new JFrame();

    static int patientId;

    public GlucoseTrend(int pId) {

        patientId = pId;

    }

    public static void main(String[] args) {

    }

    public void populateChart(int id) {

        int[] glucoseList;
        double[] dateList;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/medic", "root", "");

            //To get the number of rows in the result
            String sql = "Select Count(*) from patient_statistics where patient_id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                glucoseList = new int[rs.getInt("Count(*)")];
                dateList = new double[rs.getInt("Count(*)")];
            } else {
                glucoseList = new int[1];
                dateList = new double[1];
            }

            //To get the statistic
            sql = "Select glucose_level, date_of_submission from patient_statistics where patient_id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            int li_item = 0;
            String date = "";
            while (rs.next()) {
                date = "";

                glucoseList[li_item] = rs.getInt("glucose_level");

                date = rs.getString("date_of_submission");
                date = date.replaceAll("/", "");
                date = date.replaceAll(":", "");
                date = date.replaceAll(" ", "");

                dateList[li_item] = Double.parseDouble(date);

                li_item++;
            }

            JFreeChart chart;
            chart = ChartFactory.createLineChart(
                    "Glucose Levels Over Time",
                    "Time", "Glucose Level",
                    createDataset(glucoseList, dateList),
                    PlotOrientation.VERTICAL,
                    true, true, false);

            //Setting the window contents of the JFrame
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
            f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            f.setLayout(new BorderLayout(0, 5));
            f.add(chartPanel, BorderLayout.CENTER);
            f.pack();
            f.setVisible(true);

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GlucoseTrend.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //populate Chart
    private DefaultCategoryDataset createDataset(int[] glucoseList, double[] dateList) {
        int i;
        int size = glucoseList.length;
        DefaultCategoryDataset data = new DefaultCategoryDataset();

        for (i = 0; i < size; i++) {
            data.addValue(glucoseList[i], "Glucose Level", "" + dateList[i]);

        }
        return data;
    }
}

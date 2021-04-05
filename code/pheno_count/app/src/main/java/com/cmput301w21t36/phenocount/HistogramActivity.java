package com.cmput301w21t36.phenocount;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class HistogramActivity extends AppCompatActivity {
    BarChart barchart;
    LineChart lineChart;
    Experiment experiment;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);

        //get the experiment from intent
        experiment = (Experiment) getIntent().getSerializableExtra("experiment");

        barchart = (BarChart) findViewById(R.id.bargraph);
        lineChart = (LineChart) findViewById(R.id.lineChart);

        ArrayList<Trial> trialList = experiment.getTrials();
        ArrayList<BarEntry> dataSet1 = new ArrayList<>();
        ArrayList<BarEntry> dataSet2 = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> datesList = new ArrayList<>();

        type = experiment.getExpType();

        for (int i = 0; i < trialList.size(); i++){
            datesList.add(trialList.get(i).getDate());
        }

        String[] dates = new String[datesList.size()];
        dates = datesList.toArray(dates);

        XAxis xAxisline = lineChart.getXAxis();
        xAxisline.setValueFormatter(new XAxisFormatter(dates));
        xAxisline.setLabelRotationAngle(-45);
        xAxisline.setTextSize(10);
        xAxisline.setPosition(XAxis.XAxisPosition.BOTTOM);

        Description description = new Description();
        description.setTextColor(Color.BLACK);
        description.setTextSize(10);

        barchart.animateXY(3000, 3000);
        lineChart.animateXY(3000, 3000);

        int i = 0;
        int j = 0;
        switch (type) {
            case "Binomial":
                BarDataSet barDataSet = new BarDataSet(dataSet1, "Success");
                barDataSet.setColor(Color.GREEN);
                BarDataSet failset = new BarDataSet(dataSet2, "Fails");
                failset.setColor(Color.RED);

                BarData binomialData = new BarData();
                binomialData.addDataSet(barDataSet);
                binomialData.addDataSet(failset);
                barchart.setData(binomialData);

                description.setText("Coin Flip Histogram");
                barchart.setDescription(description);

                XAxis xAxisBar;
                xAxisBar = barchart.getXAxis();
                xAxisBar.setEnabled(false);

                barchart.setVisibility(View.VISIBLE);
                int successCount = 0;
                int failCount = 0;
                for (i = 0; i < trialList.size(); i++) {
                    if (((Binomial) trialList.get(i)).getResult()) {
                        successCount++;
                    } else {
                        failCount++;
                    }
                }

                dataSet1.add(new BarEntry(0, successCount));
                dataSet2.add(new BarEntry(1, failCount));

                break;
            case "Count":
                lineChart.setVisibility(View.VISIBLE);
                int count = 0;
                String currentDate = null;
                if (trialList.size() != 0) {
                    currentDate = trialList.get(0).getDate();

                    for (i = 0; i < trialList.size(); i++) {
                        for (j = 0; j < trialList.size(); j++) {
                            if (trialList.get(0).getDate().equals(currentDate)) {
                                count++;
                            }
                        }

                        yValues.add(new Entry(i, count));
                        count = 0;
                        if (currentDate.equals(trialList.get(i).getDate())) {
                            i = trialList.size() + 1;
                        } else {
                            currentDate = trialList.get(i).getDate();
                        }
                    }

                    LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");
                    set1.setLineWidth(10);

                    LineData countData = new LineData();
                    countData.addDataSet(set1);

                    lineChart.setData(countData);
                }

                //                trial = String.valueOf(((Count) trialList.get(position)).getCount());
                break;
            case "NonNegativeCount":
                //                trial = String.valueOf(((NonNegativeCount) trialList.get(position)).getValue());
                break;
            case "Measurement":
                //                trial = String.valueOf(((Measurement) trialList.get(position)).getMeasurement());
                break;
        }
    }

    public class XAxisFormatter implements IAxisValueFormatter {
        public String[] mValues;
        public XAxisFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value];
        }
    }
}
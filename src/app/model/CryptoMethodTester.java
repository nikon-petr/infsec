package app.model;

import javafx.scene.chart.XYChart;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

public class CryptoMethodTester {

    private ArrayList<XYChart.Series> eserieses;
    private ArrayList<XYChart.Series> dserieses;

    public void test(File inputFile) {

        eserieses = new ArrayList<>();
        dserieses = new ArrayList<>();

        for (CryptoModes mode : EnumSet.allOf(CryptoModes.class)) {
            XYChart.Series eseries = new XYChart.Series();
            eseries.setName(mode.toString());
            XYChart.Series dseries = new XYChart.Series();
            dseries.setName(mode.toString());
            for (CryptoMethods method : EnumSet.allOf(CryptoMethods.class)) {
                CryptoMethod cm = new CryptoMethod(method, mode);
                File encodedFile = cm.encode(inputFile);
                cm.decode(encodedFile);
                eseries.getData().add(new XYChart.Data<>(method.toString(), cm.getEncodeTime()));
                dseries.getData().add(new XYChart.Data<>(method.toString(), cm.getDecodeTime()));
            }
            eserieses.add(eseries);
            dserieses.add(dseries);
        }
    }

    public ArrayList<XYChart.Series> getEserieses() {
        return eserieses;
    }

    public ArrayList<XYChart.Series> getDserieses() {
        return dserieses;
    }
}

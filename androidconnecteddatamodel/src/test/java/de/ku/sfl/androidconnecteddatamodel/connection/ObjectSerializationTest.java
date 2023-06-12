package de.ku.sfl.androidconnecteddatamodel.connection;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import de.ku.sfl.connection.objects.BarcodeType;
import de.ku.sfl.connection.objects.DiscoveredReport;
import de.ku.sfl.connection.objects.ReportVariant;
import de.ku.sfl.connection.objects.ScannerType;

import static de.ku.sfl.androidconnection.connection.Assert.*;

public class ObjectSerializationTest {
    @Test
    public void testDiscoveredReportSerialization() throws JSONException {
        DiscoveredReport report = createDiscoveredReport();
        String serializedReport = report.serialize().toString();
        DiscoveredReport deserializedReport = new DiscoveredReport(new JSONObject(serializedReport));

        assertReportEquals(deserializedReport, report);
    }

    private static DiscoveredReport createDiscoveredReport(){
        int id = 42;
        String internalName = "testInternalName";
        String key = "testKey";
        BarcodeType barcodeType = BarcodeType.DATA_MATRIX;
        ScannerType scannerType = ScannerType.MEDICAL;
        boolean visible = true;
        Calendar timestamp = GregorianCalendar.getInstance();
        timestamp.set(2023, 05, 03, 02, 01);
        boolean discovered = true;
        boolean read = true;
        String category = "the:reports:category";
        String location = "first_floor";
        int numberOfInstances = 3;

        Vector<ReportVariant> reportVariants = new Vector<>();
        reportVariants.add(createReportVariant());
        reportVariants.add(createReportVariant());

        DiscoveredReport report = new DiscoveredReport(
                id,
                internalName,
                key,
                barcodeType,
                scannerType,
                visible,
                reportVariants,
                timestamp,
                discovered,
                read,
                category,
                location,
                numberOfInstances
        );
        return report;
    }

    private static ReportVariant createReportVariant() {
        return new ReportVariant(
                "testExternalVariantName",
                "testDescription",
                1234,
                new Vector<Integer>(){{add(45); add(56);}}
        );
    }


}
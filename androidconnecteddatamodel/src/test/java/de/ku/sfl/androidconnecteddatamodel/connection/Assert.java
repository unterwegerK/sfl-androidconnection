package de.ku.sfl.androidconnecteddatamodel.connection;

import de.ku.sfl.connection.objects.DiscoveredReport;
import de.ku.sfl.connection.objects.ReportVariant;

import static org.junit.Assert.assertEquals;

public class Assert {
    public static void assertReportEquals(DiscoveredReport report0, DiscoveredReport report1){
        assertEquals(report0.getId(), report1.getId());
        assertEquals(report0.getInternalName(), report1.getInternalName());
        assertEquals(report0.getKey(), report1.getKey());
        assertEquals(report0.getBarcodeType(), report1.getBarcodeType());
        assertEquals(report0.getScannerType(), report1.getScannerType());
        assertEquals(report0.isVisible(), report1.isVisible());
        assertEquals(report0.getDiscoveryTimestamp().getTimeInMillis(), report1.getDiscoveryTimestamp().getTimeInMillis());
        assertEquals(report0.getCategory(), report1.getCategory());
        assertEquals(report0.getLocation(), report1.getLocation());
        assertEquals(report0.getNumberOfInstances(), report1.getNumberOfInstances());

        assertEquals(report0.getVariants().size(), report1.getVariants().size());
        for(int i = 0; i < report0.getVariants().size(); i++){
            assertReportVariantsEquals(report0.getVariants().get(i), report1.getVariants().get(i));
        }
    }

    public static void assertReportVariantsEquals(ReportVariant reportVariant0, ReportVariant reportVariant1) {
        assertEquals(reportVariant0.getExternalName(), reportVariant1.getExternalName());
        assertEquals(reportVariant0.getDescription(), reportVariant1.getDescription());
        assertEquals(reportVariant0.getPriority(), reportVariant1.getPriority());

        assertEquals(reportVariant0.getDependencies().size(), reportVariant1.getDependencies().size());
        for(int i = 0; i < reportVariant0.getDependencies().size(); i++){
            assertEquals(reportVariant0.getDependencies().get(i), reportVariant1.getDependencies().get(i));
        }
    }
}

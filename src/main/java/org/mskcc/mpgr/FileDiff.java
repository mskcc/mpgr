package org.mskcc.mpgr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Compare pipeline-kickoff JIRA-RSL MPGR output to new MPGR output.
 */
public class FileDiff {

    public static String requestFileDiff(String requestFileOrig, String requestFileDelphi) {
        String [] orig = requestFileOrig.split("\n");
        Set<String> origLinesSet = new HashSet<>(Arrays.asList(orig));
        StringBuilder sb = new StringBuilder();

        String [] newRequestFile = requestFileDelphi.split("\n");
        for (String newRFline : newRequestFile) {
            // only compare these fields, "Date" and other differences ignore
            if ("TumorType".startsWith(newRFline) || "NumberOfSamples".startsWith(newRFline) ||
                    "Species".startsWith(newRFline) || "RunID".startsWith(newRFline) ||
                    "Assay".startsWith(newRFline) || "ProjectName".startsWith(newRFline)) {
                if (!origLinesSet.contains(newRFline)) {
                    sb.append(newRFline + "\n");
                }
            }
        }
        return sb.toString();
    }

    public static String mappingFileDiff(String mappingFileOrig, String mappingFileNew) {
        String [] origLines = mappingFileOrig.split("\n");
        String [] newLines = mappingFileNew.split("\n");
        Set<String> origSet = new HashSet<>();
        for (String line : origLines)
            origSet.add(line.substring(0,50));
        Set<String> newSet = new HashSet<>();
        for (String line : newLines) {
            newSet.add(line.substring(0,50));
            if (!origSet.contains(line.substring(0,50))) {
                System.err.println(line);
            }
        }
        for (String line : origLines) {
            if (!newSet.contains(line.substring(0,50))) {
                System.err.println(line);
            }
        }
        System.err.flush(); System.out.flush();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TODO implement line by line diff
        System.out.println("-------Original--------");
        System.out.println(mappingFileOrig);
        System.out.println("---------------");
        System.out.println(mappingFileNew);
        return null;
    }
}
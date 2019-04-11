package org.mskcc.mpgr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


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
        // TODO implement line by line diff
        System.out.println(mappingFileOrig);
        System.out.println("---------------");
        System.out.println(mappingFileNew);
        return null;
    }
}
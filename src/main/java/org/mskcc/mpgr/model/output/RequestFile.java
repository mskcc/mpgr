package org.mskcc.mpgr.model.output;

import org.mskcc.mpgr.model.Project;
import org.mskcc.mpgr.model.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class RequestFile {

    public static String toString(Project project, Request request, List<SampleMPGR> samples) {
        // TODO review this function
        //validateProjectInfo(request);

        StringBuilder result = new StringBuilder();
        String pipelineName = "Roslin";

//        if (request.getRequestType() == RequestType.EXOME) {
            result.append(String.format("Pipelines: %s\n", pipelineName));
            result.append(String.format("Run_Pipeline: %s\n", pipelineName));
//        } else if (request.getRequestType() == RequestType.IMPACT) {
//            result.append("Pipelines: dmp\n");
//            result.append("Run_Pipeline: dmp\n");
//        } else if (request.getRequestType() == RequestType.RNASEQ) {
//            result.append("Run_Pipeline: rnaseq\n");
//        } else if (request.getRecipe() == Recipe.CH_IP_SEQ) {
//            result.append("Run_Pipeline: chipseq\n");
//        } else {
//            result.append("Run_Pipeline: other\n");
//        }

        result.append("PI_Name: " + request.piLastName + "," + request.piFirstName).append("\n");
        result.append("PI_E-mail: " + request.piEmail).append("\n");
        result.append("PI: " + request.labHeadEmail).append("\n");

        result.append("Investigator_Name: " + request.investigator).append("\n");
        result.append("Investigator_E-mail: " + request.investigatorEmail).append("\n");
        if (request.investigatorEmail.contains("@")) {
            String[] temp = request.investigatorEmail.split("@");
            result.append("Investigator: ").append(temp[0]).append("\n");
        }

        result.append("DeliverTo: NA").append("\n");

        result.append("ProjectID: Proj_" + request.requestId).append("\n");
        result.append("ProjectTitle: " + project.cmoFinalProjectTitle).append("\n");
        result.append("ProjectName: " + request.cmoProjectId).append("\n");
        result.append("ProjectDesc: " + project.cmoProjectBrief).append("\n");
        result.append("Project_Manager: " + request.projectManager).append("\n");
        result.append("Project_Manager_Email: " + request.projectManagerEmail).append("\n");

        result.append("Data_Analyst: " + request.dataAnalyst).append("\n");
        result.append("Data_Analyst_E-mail: " + request.dataAnalystEmail).append("\n");

        result.append("NumberOfSamples: " + getUniqueSamples(samples).size()).append("\n");

        result.append("TumorType: " + getTumorType(samples, project)).append("\n");
        result.append("Assay: " + getAssay(samples)).append("\n");
        String speciesWCommas = String.join(",", getAllSpecies(samples));
        result.append("Species: " + speciesWCommas).append("\n");

        result.append("RunNumber: 1").append("\n"); // TODO if rerun print reason
        Set<String> runIds = getRunID(samples);
        List<String> runsList = runIds.stream().collect(Collectors.toList());
        Collections.sort(runsList);
        result.append("RunID: " + String.join(", ", runsList)).append("\n");

        result.append("Institution: cmo\n");
        // TODO update for RNASEQ
        result.append("AmplificationTypes: NA\n");
        result.append("LibraryTypes: NA\n");
        result.append("Strand: NA\n");

        result.append("ProjectFolder: /ifs/work/pi/pipelineKickoff/manifests/Proj_" + request.requestId).append("\n");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        result.append("DateOfLastUpdate: ").append(dateFormat.format(new Date())).append("\n");

        String fileContents = result.toString();
        return fileContents;
    }

    public static Set<String> getUniqueSamples(List<SampleMPGR> samples) {
        Set<String> samplesByID = new HashSet<>();
        for (SampleMPGR s: samples) {
            samplesByID.add(s.sampleQC.cmoSampleId);
        }
        return samplesByID;
    }

    public static Set<String> getAllSpecies(List<SampleMPGR> samples) {
        Set<String> allSpecies = new HashSet<>();
        for (SampleMPGR s: samples) {
            allSpecies.add(s.sampleCMOInfo.species);
        }
        return allSpecies;
    }

    public static String getTumorType(List<SampleMPGR> samples, Project project) {
        Set<String> tumorTypes = new HashSet<>();
        for (SampleMPGR s: samples) {
            if (s.sampleCMOInfo.tumorType != null && !s.sampleCMOInfo.tumorType.trim().equals(""))
                tumorTypes.add(s.sampleCMOInfo.tumorType);
        }

        System.out.println("Tumor Types: " + String.join(",", tumorTypes));

        if (tumorTypes.size() == 1) {
            String [] values = tumorTypes.toArray(new String[1]);
            return values[0];
        } else if (tumorTypes.size() > 1)
            return "mixed";
        else
            return project.tumorType;
    }

    // TODO review RequestDataPropagator.java assignProjectSpecificInfo code, project 06208 is failing among others
    public static String getAssay(List<SampleMPGR> samples) {
        String species = String.join(",", getAllSpecies(samples));

        if (species.contains("Human"))
            return "AgilentExon_51MB_b37_v3";
        else if ("Mouse".equals(species))
            return "AgilentExon_51MB_b37_mm10_v3";
        else {
            System.err.println("Failed to determine Assay/Species: " + species);
            return "TODO";
        }
    }

    public static Set<String> getRunID(List<SampleMPGR> samples) {
        Set<String> runIds = new HashSet<>();
        for (SampleMPGR s: samples) {
            runIds.add(s.sampleQC.sequencerRunFolder);
        }
        return runIds;
    }
}
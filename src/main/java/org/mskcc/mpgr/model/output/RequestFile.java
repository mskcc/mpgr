package org.mskcc.mpgr.model.output;

import org.mskcc.mpgr.model.Project;
import org.mskcc.mpgr.model.Request;
import org.mskcc.mpgr.model.SampleQC;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class RequestFile {

    public static String toString(Project project, Request request, List<SampleMPGR> samples) {
        // TODO review this function
        //validateProjectInfo(request);

        StringBuilder result = new StringBuilder();
        String pipelineName = "";

        if (request.isExome()) {
            result.append("Pipelines: Roslin\n");
            result.append("Run_Pipeline: Roslin\n");
        } else if (request.isImpact()) {
            result.append("Pipelines: dmp\n");
            result.append("Run_Pipeline: dmp\n");
        } else {
            result.append("Pipelines: Roslin\n");
            result.append("Run_Pipeline: Roslin\n");
        }

        if (request.getPiEmail() == null || "".equals(request.getPiEmail())) {
            result.append("PI_Name: " + request.getLaboratoryHead()).append("\n");
            result.append("PI_E-mail: " + request.getLabHeadEmail()).append("\n");
            result.append("PI: " + request.getLabHeadEmail().split("@")[0]).append("\n");
        } else {
            result.append("PI_Name: " + request.getPiLastName() + "," + request.getPiFirstName()).append("\n");
            result.append("PI_E-mail: " + request.getPiEmail()).append("\n");
            result.append("PI: " + request.getLabHeadEmail()).append("\n");
        }

        result.append("Investigator_Name: " + request.getInvestigator()).append("\n");
        result.append("Investigator_E-mail: " + request.getInvestigatorEmail()).append("\n");
        if (request.getInvestigatorEmail().contains("@")) {
            String[] temp = request.getInvestigatorEmail().split("@");
            result.append("Investigator: ").append(temp[0]).append("\n");
        }

        result.append("DeliverTo: NA").append("\n");

        result.append("ProjectID: Proj_" + request.getRequestId()).append("\n");
        if (request.getCmoProjectId() == null || "".equals(request.getCmoProjectId()))
            result.append("ProjectName: " + project.getCmoProjectId()).append("\n");
        else
            result.append("ProjectName: " + request.getCmoProjectId()).append("\n");
        result.append("ProjectTitle: " + project.getCmoFinalProjectTitle()).append("\n");
        result.append("ProjectDesc: " + project.getCmoProjectBrief()).append("\n");
        result.append("Project_Manager: " + request.getProjectManager()).append("\n");
        result.append("Project_Manager_Email: " + request.getProjectManagerEmail()).append("\n");

        if ("".equals(request.getDataAnalyst())) {
            result.append("Data_Analyst: NA\n");
            result.append("Data_Analyst_E-mail: NA\n");
        } else {
            result.append("Data_Analyst: " + request.getDataAnalyst()).append("\n");
            result.append("Data_Analyst_E-mail: " + request.getDataAnalystEmail()).append("\n");
        }

        result.append("NumberOfSamples: " + getUniqueSamples(samples).size()).append("\n");

        result.append("TumorType: " + getTumorType(samples, project)).append("\n");
        result.append("Assay: " + getAssay(request, samples)).append("\n");
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

        result.append("ProjectFolder: /ifs/work/pi/pipelineKickoff/manifests/Proj_" + request.getRequestId()).append("\n");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        result.append("DateOfLastUpdate: ").append(dateFormat.format(new Date())).append("\n");

        String fileContents = result.toString();
        return fileContents;
    }

    public static Set<String> getUniqueSamples(List<SampleMPGR> samples) {
        Set<String> samplesByID = new HashSet<>();
        for (SampleMPGR s: samples) {
            samplesByID.add(s.sampleQC.getCmoSampleId());
        }
        return samplesByID;
    }

    public static Set<String> getAllSpecies(List<SampleMPGR> samples) {
        Set<String> allSpecies = new HashSet<>();
        for (SampleMPGR s: samples) {
            allSpecies.add(s.sample.getSpecies());
        }

        return allSpecies;
    }

    public static String getTumorType(List<SampleMPGR> samples, Project project) {
        Set<String> tumorTypes = new HashSet<>();
        for (SampleMPGR s: samples) {
            if (s.sample.getTumorType() != null && !s.sample.getTumorType().trim().equals(""))
                tumorTypes.add(s.sample.getTumorType());
        }

        System.out.println("Tumor Types Found: " + String.join(",", tumorTypes));

        if (tumorTypes.size() == 1) {
            String [] values = tumorTypes.toArray(new String[1]);
            System.out.println("One tumor type found: " + Arrays.toString(values));
            return values[0];
        } else if (tumorTypes.size() > 1)
            return "mixed";
        else
            return project.getTumorType();
    }

    public static String getAssay(Request request, List<SampleMPGR> samples) {
        if (request.isImpact())
            return request.getRequestName();

        if (request.isExome())
            return "EXOME";

        String species = String.join(",", getAllSpecies(samples));

        SampleQC sqc = samples.get(0).sampleQC;

        // TODO review RequestDataPropagator.java assignProjectSpecificInfo code, this probably is not 100%
        if (species.contains("Human")) {
            if (sqc.getBaitSet().contains("Agilent"))
                return "AgilentExon_51MB_b37_v3";
            else
                return sqc.getBaitSet().replace("_BAITS", "_b37");
        } else if ("Mouse".equals(species))
            return "AgilentExon_51MB_b37_mm10_v3";
        else {
            System.err.println("Failed to determine Assay/Species: " + species);
            return "TODO";
        }
    }

    public static Set<String> getRunID(List<SampleMPGR> samples) {
        Set<String> runIds = new HashSet<>();
        for (SampleMPGR s: samples) {
            runIds.add(s.sampleQC.getSequencerRunFolder());
        }
        return runIds;
    }
}
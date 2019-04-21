package org.mskcc.mpgr.model;

/**
 * Sample level information from multiple LIMS tables.
 */
public class SampleMPGR {
    public Sample sample;
    public SampleCMOInfoRecord sampleCMOInfo;
    public SampleQC sampleQC;

    public String runFolder; // currently only non-LIMS information required for the mapping file

    public SampleMPGR(Sample sample, SampleCMOInfoRecord sampleCMOInfo, SampleQC sampleQC, String runFolder) {
        this.sample = sample;
        this.sampleCMOInfo = sampleCMOInfo;
        this.sampleQC = sampleQC;
        this.runFolder = runFolder;
    }
}
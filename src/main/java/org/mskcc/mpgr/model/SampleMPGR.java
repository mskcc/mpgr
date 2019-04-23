package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Sample level information from multiple LIMS tables.
 */
@Getter @Setter
public class SampleMPGR {
    public Sample sample;
    public SampleCMOInfoRecord sampleCMOInfo;
    public SampleQC sampleQC;

    public ArchivedFastq archivedFastq; // currently only non-LIMS information required for the mapping file

    public SampleMPGR(Sample sample, SampleCMOInfoRecord sampleCMOInfo, SampleQC sampleQC, ArchivedFastq archivedFastq) {
        this.sample = sample;
        this.sampleCMOInfo = sampleCMOInfo;
        this.sampleQC = sampleQC;
        this.archivedFastq = archivedFastq;
    }
}
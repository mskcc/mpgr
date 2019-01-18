package org.mskcc.mpgr.model.output;

import org.mskcc.mpgr.model.Sample;
import org.mskcc.mpgr.model.SampleCMOInfoRecord;
import org.mskcc.mpgr.model.SampleQC;

public class SampleMPGR {
    public Sample sample;
    public SampleCMOInfoRecord sampleCMOInfo;
    public SampleQC sampleQC;

    public String path;

    public SampleMPGR(Sample sample, SampleCMOInfoRecord sampleCMOInfo, SampleQC sampleQC) {
        this.sample = sample;
        this.sampleCMOInfo = sampleCMOInfo;
        this.sampleQC = sampleQC;
        // guess the correct path so no need yet to mount /ifs/archive to run the code TODO
        this.path = "/ifs/archive/GCL/hiseq/" + sampleQC.sequencerRunFolder + "/Project_" + sampleCMOInfo.requestId + "/" + sampleCMOInfo.cmoSampleId;
    }
}
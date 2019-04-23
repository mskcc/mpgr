package org.mskcc.mpgr.model.output;

import org.mskcc.mpgr.model.ArchivedFastq;
import org.mskcc.mpgr.model.SampleMPGR;

import java.util.List;

/**
 * The mapping file shows a list of all samples and the path of their fastq file.
 <BR>
 For example:
 <BR>
 _1	s_C_6183D3_R006_d	JAX_0221_AHWMGJBBXX	/ifs/archive/GCL/hiseq/FASTQ/JAX_0221_AHWMGJBBXX/Project_07871_L/Sample_KDM6A-45_IGO_07871_L_33	PE
 _1	s_C_6183D3_R004_d	JAX_0221_AHWMGJBBXX	/ifs/archive/GCL/hiseq/FASTQ/JAX_0221_AHWMGJBBXX/Project_07871_L/Sample_KDM6A-47_IGO_07871_L_35	PE
 _1	s_C_6183D3_R005_d	JAX_0221_AHWMGJBBXX	/ifs/archive/GCL/hiseq/FASTQ/JAX_0221_AHWMGJBBXX/Project_07871_L/Sample_KDM6A-46_IGO_07871_L_34	PE
 <BR>
 */
public class MappingFile {
    private List<SampleMPGR> samples;
    private List<ArchivedFastq> pooledNormals;

    public MappingFile(List<SampleMPGR> samples, List<ArchivedFastq> pooledNormals) {
        this.samples = samples;
        this.pooledNormals = pooledNormals;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SampleMPGR sample : samples) {
            String s = String.format("_1\ts_%s\t%s\t%s\tPE\n",
                    sample.sampleCMOInfo.getCorrectedCMOID().replace('-', '_'),
                    sample.archivedFastq.getRunBaseDirectory(),
                    sample.archivedFastq.getFastqDirectory());
            sb.append(s);
        }
        for (ArchivedFastq pooledNormal : pooledNormals) {
            String sample = pooledNormal.getSample();
            if (pooledNormal.getSample().contains("_IGO_")) {
                sample = sample.substring(0, sample.indexOf("_IGO_"));
            }
            String s = String.format("_1\ts_%s\t%s\t%s\tPE\n",
                    sample,
                    pooledNormal.getRunBaseDirectory(),
                    pooledNormal.getFastqDirectory());
            sb.append(s);
        }
        return sb.toString();
    }
}
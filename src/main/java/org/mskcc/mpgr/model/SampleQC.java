package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Queried by "Request = '" + kickoffRequest.getId() + "' AND OtherSampleId = '" + sample.getCmoSampleId() in Pipeline Kickoff
 */
@Entity
@Table(name = "SeqAnalysisSampleQC")
@Getter @Setter @ToString @NoArgsConstructor
public class SampleQC {
    @Id
    private long recordId;

    @Column(name="othersampleid")
    private String cmoSampleId;

    private String sequencerRunFolder;
    private String request;

    private String altId;
    private String sampleAliases;

    @Column(name="seqqcstatus")
    private String status;

    private String baitSet; // for example: SureSelect-All-Exon-V4-hg19

    private long dateCreated;

    boolean passed() {
        if ("Passed".equals(status))
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleQC sampleQC = (SampleQC) o;

        if (cmoSampleId != null ? !cmoSampleId.equals(sampleQC.cmoSampleId) : sampleQC.cmoSampleId != null)
            return false;
        return sequencerRunFolder != null ? sequencerRunFolder.equals(sampleQC.sequencerRunFolder) : sampleQC.sequencerRunFolder == null;
    }

    @Override
    public int hashCode() {
        int result = cmoSampleId != null ? cmoSampleId.hashCode() : 0;
        result = 31 * result + (sequencerRunFolder != null ? sequencerRunFolder.hashCode() : 0);
        return result;
    }
}
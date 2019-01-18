package org.mskcc.mpgr.model;

import javax.persistence.*;

@Entity
@Table(name = "SeqAnalysisSampleQC")
/**
 * Queried by "Request = '" + kickoffRequest.getId() + "' AND OtherSampleId = '" + sample.getCmoSampleId() in Pipeline Kickoff
 */
public class SampleQC {
    @Id
    long recordId;

    @Column(name="othersampleid")
    public String cmoSampleId;

    public String sequencerRunFolder;
    public String request;

    String altId;
    String sampleAliases;

    @Column(name="seqqcstatus")
    String status;

    long dateCreated;

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

    @Override
    public String toString() {
        return "SampleQC{" +
                "recordId=" + recordId +
                ", cmoSampleId='" + cmoSampleId + '\'' +
                ", sequencerRunFolder='" + sequencerRunFolder + '\'' +
                ", request='" + request + '\'' +
                ", altId='" + altId + '\'' +
                ", sampleAliases='" + sampleAliases + '\'' +
                ", status='" + status + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
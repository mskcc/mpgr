package org.mskcc.mpgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "postSeqAnalysisQc")
/**
 * The sample QC status table as maintained by the PM team.
 */
public class SampleQCPM {
    @Id
    long recordId;

    @Column(name="othersampleid")
    String cmoSampleId;
    String sequencerRunFolder;
    String updatedRunFolder;
    @Column(name="postseqqcstatus")
    String status; // passed, failed, redacted

    String altId;

    long dateCreated;

    public SampleQCPM() {}

    public SampleQCPM(String cmoSampleId, String updatedRunFolder) {
        this.cmoSampleId = cmoSampleId;
        this.updatedRunFolder = updatedRunFolder;
    }

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

        SampleQCPM that = (SampleQCPM) o;

        if (cmoSampleId != null ? !cmoSampleId.equals(that.cmoSampleId) : that.cmoSampleId != null) return false;
        return updatedRunFolder != null ? updatedRunFolder.equals(that.updatedRunFolder) : that.updatedRunFolder == null;
    }

    @Override
    public int hashCode() {
        int result = cmoSampleId != null ? cmoSampleId.hashCode() : 0;
        result = 31 * result + (updatedRunFolder != null ? updatedRunFolder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SampleQCPM{" +
                "recordId=" + recordId +
                ", cmoSampleId='" + cmoSampleId + '\'' +
                ", sequencerRunFolder='" + sequencerRunFolder + '\'' +
                ", updatedRunFolder='" + updatedRunFolder + '\'' +
                ", status='" + status + '\'' +
                ", altId='" + altId + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The sample QC status table as maintained by the PM team.
 */
@Entity
@Table(name = "postSeqAnalysisQc")
@Getter @Setter @ToString @NoArgsConstructor
public class SampleQCPM {
    @Id
    private long recordId;

    @Column(name="othersampleid")
    private String cmoSampleId;
    private String sequencerRunFolder;
    private String updatedRunFolder;
    @Column(name="postseqqcstatus")
    private String status; // passed, failed, redacted

    private String altId;

    private long dateCreated;

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
}
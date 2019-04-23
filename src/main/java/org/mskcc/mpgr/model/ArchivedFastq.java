package org.mskcc.mpgr.model;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

/**
 * Archived fastq.gz location information, information currently not in the LIMS.
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class ArchivedFastq {
    private static Logger log = LoggerFactory.getLogger(ArchivedFastq.class);

    private String run;

    private String runBaseDirectory;
    private String project;
    private String sample;

    private String fastq;

    private Date fastqLastModified;

    private Date lastUpdated;

    /**
     * Returns the directory minus the file name.
     */
    public String getFastqDirectory() {
        File f = new File(fastq);
        return f.getParent();
    }

    // 2016 and after fastq naming convention
    public static String fastqName(String cmoSampleId, String sampleId) {
        return cmoSampleId + "_IGO_" + sampleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArchivedFastq that = (ArchivedFastq) o;

        if (run != null ? !run.equals(that.run) : that.run != null) return false;
        if (runBaseDirectory != null ? !runBaseDirectory.equals(that.runBaseDirectory) : that.runBaseDirectory != null)
            return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        return sample != null ? sample.equals(that.sample) : that.sample == null;
    }

    @Override
    public int hashCode() {
        int result = run != null ? run.hashCode() : 0;
        result = 31 * result + (runBaseDirectory != null ? runBaseDirectory.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (sample != null ? sample.hashCode() : 0);
        return result;
    }
}
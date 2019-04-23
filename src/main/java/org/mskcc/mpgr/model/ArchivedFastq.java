package org.mskcc.mpgr.model;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
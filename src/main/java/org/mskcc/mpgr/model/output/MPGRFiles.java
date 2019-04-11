package org.mskcc.mpgr.model.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * All files generatd by the pipeline kickoff code.
 */
@AllArgsConstructor
@Getter @Setter
public class MPGRFiles {
    private MappingFile mappingFile;
    private PairingFile pairingFile;
    private GroupingFile groupingFile;
    private RequestFile requestFile;

    private String jiraIssueKey;
}
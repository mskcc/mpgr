package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * A CMO Study, "Project" in the LIMS
 */
@Entity
@Getter @Setter
public class Project {
    @Id
    private String projectid;

    private String cmoProjectId;
    private String cmoFinalProjectTitle;
    private String cmoProjectBrief;

    private String tumorType;

    private long datecreated;

    public Project() {
    }
}
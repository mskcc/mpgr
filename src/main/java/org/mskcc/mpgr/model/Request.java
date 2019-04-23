package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString @Getter @Setter
public class Request {

    @Id
    private String requestId;
    private String requestName;

    private String projectid;

    private String cmoProjectId;

    private String laboratoryHead;
    private String labHeadEmail;

    private String investigator;
    private String investigatorEmail;

    private String dataAnalyst;
    private String dataAnalystEmail;

    private String projectManager;
    @Transient
    private String projectManagerEmail; // from lookup in limsUser table

    private boolean bicAutoRunnable;
    private Boolean manualDemux; // sometimes null as of Jan. 2019
    private String readMe;

    private String piFirstName;
    private String piLastName;
    private String piEmail;

    private long datecreated;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "request")
    private List<Sample> samples = new ArrayList<>();

    public Request() {
    }

    public boolean isImpact() {
        if (requestName.matches("(.*)PACT(.*)"))
            return true;
        return false;
    }

    public boolean isExome() {
        if (requestName.matches("(.*)WES(.*)"))
            return true;
        return false;
    }
}
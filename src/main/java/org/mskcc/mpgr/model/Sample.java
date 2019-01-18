package org.mskcc.mpgr.model;

import javax.persistence.*;

@Entity
public class Sample {
    @Id
    long recordId;

    String requestId;

    String otherSampleId; // aka cmoSampleId
    String sampleId; // aka igo Sample Id
    String altId;

    String exemplarSampleStatus; // 175 possible values, "Failed - Completed" samples are skipped

    String patientId;
    String createdBy;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="requestid", insertable=false, updatable = false)
    Request request;


}

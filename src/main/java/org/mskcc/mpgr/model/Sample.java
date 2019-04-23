package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Sample {
    @Id
    private long recordId;

    private String requestId;

    @Column(name="othersampleid")
    private String cmoSampleId;
    private String sampleId; // aka igo Sample Id
    private String altId;

    private String exemplarSampleStatus; // 175 possible values, "Failed - Completed" samples are skipped

    private String patientId;
    private String createdBy;

    private String tumorType;
    private String species;

    private long dateCreated;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="requestid", insertable=false, updatable = false)
    private Request request;

    @Override
    public String toString() {
        return "Sample{" +
                "recordId=" + recordId +
                ", requestId='" + requestId + '\'' +
                ", cmoSampleId='" + cmoSampleId + '\'' +
                ", sampleId='" + sampleId + '\'' +
                ", altId='" + altId + '\'' +
                ", exemplarSampleStatus='" + exemplarSampleStatus + '\'' +
                ", patientId='" + patientId + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", tumorType='" + tumorType + '\'' +
                ", species='" + species + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
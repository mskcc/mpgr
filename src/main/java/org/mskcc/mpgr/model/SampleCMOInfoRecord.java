package org.mskcc.mpgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="samplecmoinforecords")
public class SampleCMOInfoRecord {

    long recordId;

    @Id
    String sampleId;
    String userSampleId;
    public String correctedCMOID;  // Very important for the Mapping file
    @Column(name="othersampleid")
    public String cmoSampleId;
    String altId;

    String cmoPatientId;

    public String requestId;  // blank for 1/3 of entries

    public String tumorType;
    public String species;
    String preservation;
    String tumorOrNormal;
}

package org.mskcc.mpgr.repository;

import org.mskcc.mpgr.model.SampleQC;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SampleQCRepository extends CrudRepository<SampleQC, Long> {
    List<SampleQC> findByRequestAndStatus(String request, String status);

    //Queried by "Request = '" + kickoffRequest.getId() + "' AND OtherSampleId = '" + sample.getCmoSampleId() in Pipeline Kickoff
    List<SampleQC> findByRequestAndCmoSampleId(String request, String cmoSampleId);

    List<SampleQC> findBycmoSampleIdAndSequencerRunFolder(String cmoSampleId, String sequencerRunFolder);
}
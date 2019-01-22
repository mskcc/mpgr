package org.mskcc.mpgr.repository;

import org.mskcc.mpgr.model.SampleCMOInfoRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SampleCMOInfoRepository extends CrudRepository<SampleCMOInfoRecord, String> {
    Optional<SampleCMOInfoRecord> findByCmoSampleIdAndRequestId(String cmoSampleId, String requestId);

    List<SampleCMOInfoRecord> findByCmoSampleId(String cmoSampleId);

    Optional<SampleCMOInfoRecord> findBySampleIdAndCmoSampleId(String sampleId, String cmoSampleId);
}
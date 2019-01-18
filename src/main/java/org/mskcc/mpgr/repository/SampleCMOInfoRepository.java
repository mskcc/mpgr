package org.mskcc.mpgr.repository;

import org.mskcc.mpgr.model.SampleCMOInfoRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SampleCMOInfoRepository extends CrudRepository<SampleCMOInfoRecord, String> {
    Optional<SampleCMOInfoRecord> findByCmoSampleIdAndRequestId(String cmoSampleId, String requestId);
}
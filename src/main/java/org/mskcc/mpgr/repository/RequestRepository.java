package org.mskcc.mpgr.repository;

import org.mskcc.mpgr.model.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, String> {

}

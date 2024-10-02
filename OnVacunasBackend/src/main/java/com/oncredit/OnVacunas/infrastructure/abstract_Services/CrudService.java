package com.oncredit.OnVacunas.infrastructure.abstract_Services;

import org.springframework.data.domain.Page;

import com.oncredit.OnVacunas.utils.enums.SortType;

public interface CrudService<RQ, RS, ID> {
    
    RS create(RQ request);

    RS get(ID id);

    RS update(RQ request, ID id);

    void delete(ID id);

    Page<RS> getAll(int page, int size, SortType sortType);
}

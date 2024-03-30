package com.assambra.plugin.service;

import com.tvd12.ezydata.database.repository.EzyMaxIdRepository;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import lombok.Setter;
import com.tvd12.ezyfox.database.service.EzyMaxIdService;

@Setter
@AllArgsConstructor
@EzySingleton("maxIdService")
public class MaxIdService implements EzyMaxIdService {

    private final EzyMaxIdRepository maxIdRepository;

    @Override
    public void loadAll() {

    }

    @Override
    public Long incrementAndGet(String key) {
        return maxIdRepository.incrementAndGet(key);
    }

    @Override
    public Long incrementAndGet(String key, int delta) {
        return maxIdRepository.incrementAndGet(key, delta);
    }
}

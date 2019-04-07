package com.homework.lrucache.repository;

import com.homework.lrucache.repository.entity.CalcEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends CrudRepository<CalcEntity, String> {
}

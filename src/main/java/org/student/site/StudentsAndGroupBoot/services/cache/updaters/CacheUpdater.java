package org.student.site.StudentsAndGroupBoot.services.cache.updaters;

import java.util.List;

public interface CacheUpdater<T> {

    List<T> update();

}

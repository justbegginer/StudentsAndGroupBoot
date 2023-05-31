package org.student.site.StudentsAndGroupBoot.services.cache.updaters;

import java.util.List;

public abstract class CacheUpdater<T> {
    private boolean isNeedToBeUpdate;
    public CacheUpdater(){
        isNeedToBeUpdate = false;
    }
    public abstract List<T> update();

    public boolean isNeedToBeUpdate() {
        return isNeedToBeUpdate;
    }

    public void setNeedToBeUpdate(boolean needToBeUpdate) {
        isNeedToBeUpdate = needToBeUpdate;
    }
}

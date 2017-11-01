package com.johnhunsley.returns.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     representation of multiple different statistics for
 * </p>
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 27/10/2017
 *         Time : 15:06
 */
public class ReturnStats {
    private Map<String, Integer> catchStats;

    public Map<String, Integer> getCatchStats() {
        return catchStats;
    }

    public void setCatchStats(Map<String, Integer> catchStats) {
        this.catchStats = catchStats;
    }

    public void addStat(final String type, Integer count) {
        if(this.catchStats == null) this.catchStats = new HashMap<>();

        this.catchStats.put(type, count);
    }
}

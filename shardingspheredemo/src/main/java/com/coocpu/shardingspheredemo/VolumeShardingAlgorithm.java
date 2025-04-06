package com.coocpu.shardingspheredemo;

import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @auth Felix
 * @since 2025/3/19 21:51
 */
public class VolumeShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> preciseValue) {
        // 精确分片（INSERT/UPDATE/DELETE）
        long id = preciseValue.getValue();
        String suffix = String.valueOf(id / 1000);  // 每 1000 条分一表
        return "t_leave_record_" + suffix;
    }
//CREATE TABLE IF NOT EXISTS t_leave_record_10 LIKE t_leave_record_0;
    @Override
    public Collection<String> doSharding(Collection<String> tableNames, RangeShardingValue<Long> rangeValue) {
        // 范围分片（SELECT）
        Range<Long> range = rangeValue.getValueRange();
        long lower = range.hasLowerBound() ? range.lowerEndpoint() : 0;
        long upper = range.hasUpperBound() ? range.upperEndpoint() : Long.MAX_VALUE;

        Set<String> result = new LinkedHashSet<>();
        for (long i = lower; i <= upper; i++) {
            String suffix = String.valueOf(i / 1000);
            result.add("t_leave_record_" + suffix);
        }
        return result;
    }
}

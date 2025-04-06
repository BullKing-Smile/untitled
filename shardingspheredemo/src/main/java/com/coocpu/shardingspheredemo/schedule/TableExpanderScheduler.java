package com.coocpu.shardingspheredemo.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @auth Felix
 * @since 2025/3/19 21:53
 */
@Component
public class TableExpanderScheduler {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedDelay = 3600000)  // 每小时检查一次
    public void expandTableIfNeeded() {
        // 查询当前最大分片键值
        Long maxId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM t_leave_record", Long.class);
        if (maxId == null) return;

        // 计算所需表数量
        int requiredTables = (int) (maxId / 1000) + 1;

        // 动态创建缺失的表
        for (int i = 0; i <= requiredTables; i++) {
            String tableName = "t_leave_record" + i;
            if (!tableExists(tableName)) {
                jdbcTemplate.execute("CREATE TABLE " + tableName + " LIKE t_leave_record_0");
            }
        }
    }

    private boolean tableExists(String tableName) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?",
                Integer.class,
                tableName
        ) > 0;
    }
}
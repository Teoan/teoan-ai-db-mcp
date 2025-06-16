package com.teoan.teoanaimcpdemo.tool;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据库服务
 *
 * @author Teoan
 * @since 2025/6/15 12:11
 */
@Component
@RequiredArgsConstructor
public class DBTool {

    private final JdbcTemplate jdbcTemplate;

    private final String tablesSql = "SELECT TABLE_NAME, TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?";

    private final String dbSql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA " +
            "WHERE SCHEMA_NAME NOT IN ('information_schema', 'mysql', 'performance_schema', 'sys')";

    private final String schemaSql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";

    @Tool(description = "获取所有非内置的数据库名")
    public List<String> getNonBuiltInDatabases() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(dbSql);
        return maps.stream()
                .map(map -> MapUtil.getStr(map, "SCHEMA_NAME"))
                .collect(Collectors.toList());
    }


    @Tool(description = "根据库名获取所有可用的表名")
    public List<String> getTables(@ToolParam(description = "库名") String dbName) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(tablesSql, dbName);
        return maps.stream().map(map -> {
            String tableName = MapUtil.getStr(map, "TABLE_NAME");
            String tableComment = MapUtil.getStr(map, "TABLE_COMMENT");
            return tableName + " (" + tableComment + ")";
        }).collect(Collectors.toList());
    }


    @Tool(description = "根据表名和库名获取Schema")
    public String getTableSchema(@ToolParam(description = "表名") List<String> tables, @ToolParam(description = "库名") String dbName) {
        return tables.stream().filter(StrUtil::isNotBlank).map(tableName -> {
            List<Map<String, Object>> columns = jdbcTemplate.queryForList(schemaSql, dbName, tableName);
            String tablePrompt = columns.stream().map(map -> {
                String name = MapUtil.getStr(map, "COLUMN_NAME");
                String type = MapUtil.getStr(map, "DATA_TYPE");
                String comment = MapUtil.getStr(map, "COLUMN_COMMENT");
                return StrUtil.format("{} ({}) - {}", name, type, comment);
            }).collect(Collectors.joining(", \n"));
            return StrUtil.format("Table: {} ({})\n", tableName, tablePrompt);
        }).collect(Collectors.joining("\n"));
    }

    @Tool(description = "执行SQL语句并返回结果")
    public String runSql(@ToolParam(description = "sql") String sql) {
        if(StrUtil.isBlank(sql)) {
            return null;
        }
        if (sql.trim().toUpperCase().startsWith("SELECT")) {
            // 如果是查询语句，限制返回结果的最大条数为 500 条
            sql = sql.contains("LIMIT")? sql:  sql.replace(";","") + " LIMIT 500;";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
            return JSONUtil.toJsonStr(result);
        } else {
            // 如果是更新、插入、删除等语句，返回受影响的行数
            int affectedRows = jdbcTemplate.update(sql);
            return JSONUtil.toJsonStr(affectedRows);
        }
    }


}

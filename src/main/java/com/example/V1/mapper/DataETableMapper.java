package com.example.V1.mapper;

import com.example.V1.entity.DataETable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 异常数据表 Mapper 接口
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
public interface DataETableMapper extends BaseMapper<DataETable> {
    //查询总条数
    @Select("SELECT COUNT(*) FROM data_e_table")
    int getTotalCount();

    //删除最旧的20条（按创建时间升序排列）
    @Delete("""
    DELETE FROM data_e_table
    WHERE id IN (
        SELECT id FROM data_e_table
        ORDER BY create_time ASC
        LIMIT 20
    )
""")
    int deleteOldest20();
}

package com.example.V1.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.V1.Dto.MaintainWithDataDTO;
import com.example.V1.entity.MaintainTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 * 维护记录表 Mapper 接口
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
public interface MaintainTableMapper extends BaseMapper<MaintainTable> {
    IPage<MaintainWithDataDTO> getMaintainWithJoin(Page<?> page,
                                                   @Param("id") Long id,
                                                   @Param("userId") Long userId,
                                                   @Param("systemName") String systemName,
                                                   @Param("mtTime") LocalDateTime mtTime);

}

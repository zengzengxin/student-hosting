package com.luwei.model.hosting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2018-12-17
 */
public interface HostingMapper extends BaseMapper<Hosting> {

    int hostingTimer();
}

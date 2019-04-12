package com.luwei.model.hosting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luwei.model.hosting.pojo.web.HostingWebVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2018-12-17
 */
public interface HostingMapper extends BaseMapper<Hosting> {

    int hostingTimer();

    List<Hosting> getHostings(int start);
}

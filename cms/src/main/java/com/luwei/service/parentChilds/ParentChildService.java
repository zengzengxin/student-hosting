package com.luwei.service.parentChilds;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.model.parentChild.ParentChild;
import com.luwei.model.parentChild.ParentChildMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zzx
 * @since 2018-12-12
 */
@Service
@Slf4j
public class ParentChildService extends ServiceImpl<ParentChildMapper, ParentChild> {

    @Transactional
    public int  UnbindChilds(int id){
        return baseMapper.UnbindChilds(id);
    }
}

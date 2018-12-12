package com.luwei.service.parent;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.cms.ChildVO;
import com.luwei.model.parent.Parent;
import com.luwei.model.parent.ParentMapper;
import com.luwei.model.parent.pojo.cms.ParentCmsQueryDTO;
import com.luwei.model.parent.pojo.cms.ParentCmsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huanglp
 * Date: 2018-12-12
 */
@Slf4j
@Service
public class ParentService extends ServiceImpl<ParentMapper, Parent> {

    @Resource
    private ChildMapper childMapper;

    public IPage<ParentCmsVO> getParentPage(String condition, Page page) {
        ParentCmsQueryDTO parentWebQueryDTO = new ParentCmsQueryDTO();
        parentWebQueryDTO.setParentName(condition);

        IPage<ParentCmsVO> iPage = baseMapper.findParentPage( page,parentWebQueryDTO);
        iPage.setRecords(iPage.getRecords().stream().map(this::getchilds).collect(Collectors.toList()));
        return iPage;

    }


    private ParentCmsVO getchilds(ParentCmsVO parentWebVO) {
        List<ChildVO> list = childMapper.findChildsByParentsId(parentWebVO.getParentId());
        int childNumber = list.size();
        parentWebVO.setChildNumber(childNumber);
        return parentWebVO.setListChild(list);
    }

}

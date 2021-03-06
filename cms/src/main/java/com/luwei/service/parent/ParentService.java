package com.luwei.service.parent;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.cms.ChildCmsVO;
import com.luwei.model.parent.Parent;
import com.luwei.model.parent.ParentMapper;
import com.luwei.model.parent.pojo.cms.ParentCmsVO;
import com.luwei.model.parent.pojo.cms.ParentQueryDTO;
import com.luwei.service.child.ChildService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
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

    private ChildService childService;

    public IPage<ParentCmsVO> getParentPage(ParentQueryDTO parentQueryDTO, Page page) {

        IPage<ParentCmsVO> iPage = baseMapper.findParentPage(page, parentQueryDTO);
        iPage.setRecords(iPage.getRecords().stream().map(this::getchilds).collect(Collectors.toList()));
        return iPage;

    }

    private ParentCmsVO getchilds(ParentCmsVO parentCmsVO) {
        List<ChildCmsVO> list = childMapper.findChildsByParentsId(parentCmsVO.getParentId());
        int childNumber = list.size();
        parentCmsVO.setChildNumber(childNumber);
        return parentCmsVO.setListChild(list);
    }

    public void deleteParent(Integer id) {
        boolean flag = removeById(id);
        //删除该家长对应的孩子
        List<ChildCmsVO> childs = childMapper.findChildsByParentsId(id);


        Assert.isTrue(flag, MessageCodes.PARENT_DELETE_ERROR);
        log.info("----删除一条父母记录----" );
    }

}

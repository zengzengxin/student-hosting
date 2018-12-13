package com.luwei.service.parent;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.child.ChildMapper;
import com.luwei.model.child.pojo.cms.ChildVO;
import com.luwei.model.parent.Parent;
import com.luwei.model.parent.ParentMapper;
import com.luwei.model.parent.pojo.cms.ParentCmsQueryDTO;
import com.luwei.model.parent.pojo.cms.ParentCmsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
        //TODO 条件不明确
        ParentCmsQueryDTO parentWebQueryDTO = new ParentCmsQueryDTO();
        parentWebQueryDTO.setParentName(condition);

        IPage<ParentCmsVO> iPage = baseMapper.findParentPage( page,parentWebQueryDTO);
        iPage.setRecords(iPage.getRecords().stream().map(this::getchilds).collect(Collectors.toList()));
        return iPage;

    }


    private ParentCmsVO getchilds(ParentCmsVO parentCmsVO) {
        List<ChildVO> list = childMapper.findChildsByParentsId(parentCmsVO.getParentId());
        int childNumber = list.size();
        parentCmsVO.setChildNumber(childNumber);
        return parentCmsVO.setListChild(list);
    }


    public void deleteParent(Integer id){
        boolean flag = removeById(id);
        Assert.isTrue(flag, MessageCodes.PARENT_DELETE_ERROR);
        log.info("----删除一条父母记录----");
    }


     //TODO 还可能要添加通过父母id查询订单的接口
}

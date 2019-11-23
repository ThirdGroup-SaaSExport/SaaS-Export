package cn.itcast.service.cargo.impl;


import cn.itcast.dao.cargo.PackingDao;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;

import cn.itcast.service.cargo.PackingService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PackingServiceImpl implements PackingService {
    @Autowired
    private PackingDao packingDao;
    @Override
    public Packing findById(String id) {
        return packingDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Packing packing) {
            // 1 设置新装箱单的id
            packing.setPackingListId(UUID.randomUUID().toString());
        // 2 设置新装箱单的状态
        packing.setState(0L);
        // 3 设置新装箱单的时间
        packing.setCreateTime(new Date());
//        System.out.println("save");
        packingDao.insertSelective(packing);
    }

    @Override
    public void update(Packing packing) {
        packingDao.updateByPrimaryKey(packing);
    }

    @Override
    public void delete(String id) {
        packingDao.deleteByPrimaryKey(id);
        System.out.println("delete");
    }


    @Override
    public PageInfo findAll(PackingExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Packing> packingList = packingDao.selectByExample(example);
        return new PageInfo(packingList);
    }

}

package cn.itcast.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.itcast.dao.cargo.ProductMessageDao;
import cn.itcast.dao.cargo.ProductMsgDao;
import cn.itcast.domain.cargo.ProductMessage;
import cn.itcast.domain.cargo.ProductMessageExample;
import cn.itcast.domain.cargo.ProductMsg;
import cn.itcast.domain.cargo.ProductMsgExample;
import cn.itcast.service.cargo.ProductMessageService;
import cn.itcast.service.cargo.ProductMsgService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductMsgServiceImpl implements ProductMsgService {

    @Autowired
    private ProductMsgDao productMsgDao;

    //查询所有的货号和货物详细信息，分页显示
    @Override
    public PageInfo findAllProduct(ProductMsgExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<ProductMsg> list = productMsgDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    //新增货号和详细信息
    @Override
    public void saveProductMsg(ProductMsg productMsg) {
        productMsgDao.insert(productMsg);
    }

    //根据id去查询货号和货物详细信息
    @Override
    public ProductMsg findByProductId(String productNo) {
        return productMsgDao.selectByPrimaryKey(productNo);
    }

    //修改
    @Override
    public void updateProductMsg(ProductMsg productMsg) {
        productMsgDao.updateByPrimaryKeySelective(productMsg);
    }
    //删除
    @Override
    public void delete(String productNo) {
        productMsgDao.deleteByPrimaryKey(productNo);
    }

    //查询所有货号和货物详情
    @Override
    public List<ProductMsg> findAll() {
        ProductMsgExample example = new ProductMsgExample();
        return productMsgDao.selectByExample(example);
    }
}

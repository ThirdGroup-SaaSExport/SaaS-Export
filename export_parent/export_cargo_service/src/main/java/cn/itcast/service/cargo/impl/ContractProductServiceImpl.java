package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ContractProductDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.*;
import cn.itcast.domain.vo.ContractProductVo;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.service.cargo.ContractService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<ContractProduct> list = contractProductDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Autowired
    private ContractDao contractDao;

    @Override
    public void save(ContractProduct contractProduct) {
        //1.先计算货物金额
        double amount=0.0d;
        if (contractProduct.getCnumber()!=null&&contractProduct.getPrice()!=null){
            amount=contractProduct.getCnumber()*contractProduct.getPrice();
        }

        //1.2将金额保存到货物
        contractProduct.setId(UUID.randomUUID().toString());
        contractProduct.setAmount(amount);

        //保存货物
        contractProductDao.insertSelective(contractProduct);

        //2.计算合同金额,合同金额+货物金额
        //2.1获取合同实体
        Contract contract=contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //2.2更新合同金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        //更新合同数量
        contract.setProNum(contract.getProNum()+contractProduct.getCnumber());
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(ContractProduct contractProduct) {
        //1.先计算货物金额
        double amount=0.0d;
        if (contractProduct.getCnumber()!=null&&contractProduct.getPrice()!=null){
            amount=contractProduct.getCnumber()*contractProduct.getPrice();
        }

        //1.2将金额保存到货物
        contractProduct.setAmount(amount);

        //2.计算合同金额,合同金额-以前的货物金额+货物金额
        //2.1获取合同实体
        Contract contract=contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //获取以前的货物
        ContractProduct oldCp = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        //2.2更新合同金额
        contract.setTotalAmount(contract.getTotalAmount() - oldCp.getAmount() + amount);
        //更新合同数量
        contract.setProNum(contract.getProNum() - oldCp.getCnumber()+contractProduct.getCnumber());
        //保存货物
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public void delete(String id) {
        //获取货物下的附件
        ExtCproductExample example = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(id);
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(example);
        //循环附件,得到附件总金额和数量,并删除
        double amount=0.0d;
        int extNum=0;
        for (ExtCproduct extCproduct : extCproducts) {
            amount+=extCproduct.getAmount();
            extNum+=extCproduct.getCnumber();
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }
        //获取货物实体
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        //获取合同实体
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //计算合同金额和数量
        contract.setTotalAmount(contract.getTotalAmount()-contractProduct.getAmount()-amount);
        contract.setProNum(contract.getProNum()-contractProduct.getCnumber()-extNum);
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        contractProductDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<ContractProductVo> findByShipTime(String inputDate) {
        return contractDao.findByShipTime(inputDate);
    }
}

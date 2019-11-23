package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractProduct;
import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.ExtCProductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ExtCProductServiceImpl implements ExtCProductService {
    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public void save(ExtCproduct extCproduct) {
           //获取金额,数量
        double amount = 0.0d;
        if (extCproduct.getCnumber()!=null&&extCproduct.getPrice()!=null){
            amount=extCproduct.getPrice()*extCproduct.getCnumber();
        }
        //将金额保存到附件中
        extCproduct.setId(UUID.randomUUID().toString());
        extCproduct.setAmount(amount);
        extCproductDao.insertSelective(extCproduct);
        //获取合同主体
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //计算合同金额和数量
        contract.setTotalAmount(contract.getTotalAmount()+amount);
        contract.setExtNum(contract.getExtNum()+extCproduct.getCnumber());
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(ExtCproduct extCproduct) {
        //获取金额,数量
        double amount = 0.0d;
        if (extCproduct.getCnumber()!=null&&extCproduct.getPrice()!=null){
            amount=extCproduct.getPrice()*extCproduct.getCnumber();
        }
        //将金额保存到附件中
        extCproduct.setAmount(amount);
        //获取合同主体
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //获取以前的附件
        ExtCproduct oldExtc = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        //计算合同金额和数量,要减去以前的
        contract.setTotalAmount(contract.getTotalAmount()-oldExtc.getAmount()+amount);
        contract.setExtNum(contract.getExtNum()-oldExtc.getCnumber()+extCproduct.getCnumber());
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        //更新附件
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
    }

    @Override
    public void delete(String id) {
        //删除要更新合同
        //1获取附件
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        //2获取附件的金额和数量
        //获取合同主体
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //计算合同金额和数量
        contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());
        contract.setExtNum(contract.getProNum()-extCproduct.getCnumber());
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        //删除附件
        extCproductDao.deleteByPrimaryKey(id);
    }
}

package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.*;
import cn.itcast.domain.cargo.*;
import cn.itcast.domain.vo.ExportProductResult;
import cn.itcast.domain.vo.ExportResult;
import cn.itcast.service.cargo.ExportService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExportProductDao exportProductDao;

    @Autowired
    private ExtCproductDao extCproductDao; //附件表dao

    @Autowired
    private ExtEproductDao extEproductDao; //报运附件表dao

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Export export) {
        //---------------------报运主表
        //设置报运单id
        export.setId(UUID.randomUUID().toString());
        //获取ids
        String ids = export.getContractIds();
        String[] contractIds = ids.split(",");
        //获取货物数和附件数
        int proNum=0;
        int extNum=0;
        //通过ids找到合同list
        for (String contractId : contractIds) {
            Contract contract = contractDao.selectByPrimaryKey(contractId);
            //修改合同状态
            contract.setState(2);
            //更新合同
            contractDao.updateByPrimaryKeySelective(contract);

            //-------------------------------报运商品表
            //通过合同id找到货物list
            ContractProductExample contractProductExample = new ContractProductExample();
            ContractProductExample.Criteria criteria = contractProductExample.createCriteria();
            criteria.andContractIdEqualTo(contract.getId());
            List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);

            //建立货物表和报运单商品表的关联
            //设置map集合,key是货物id,value是商品id
            Map<String,String> map = new HashMap<>();

            //循环货物list
            for (ContractProduct contractProduct : contractProductList) {
                //创建报运商品单
                ExportProduct exportProduct = new ExportProduct();
                //利用beanUtils将货物复制给货物报运商品
                BeanUtils.copyProperties(contractProduct,exportProduct);
                //设置报运主表id
                exportProduct.setExportId(export.getId());
                //设置报运商品id
                exportProduct.setId(UUID.randomUUID().toString());
                //保存到数据库
                exportProductDao.insertSelective(exportProduct);
                //设置map
                map.put(contractProduct.getId(),exportProduct.getId());
                //设置货物数量
                proNum = proNum + contractProduct.getCnumber();
            }
            //----------------报运附件表
            //通过合同id找到附件list
            ExtCproductExample extCproductExample = new ExtCproductExample();
            ExtCproductExample.Criteria criteria1 = extCproductExample.createCriteria();
            criteria1.andContractIdEqualTo(contractId);
            List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
            //循环附件list
            for (ExtCproduct extCproduct : extCproducts) {
                //创建报运附件表
                ExtEproduct extEproduct = new ExtEproduct();
                //通过BeanUtils将附件表复制到报运附件表
                BeanUtils.copyProperties(extCproduct,extEproduct);
                //设置报运附件表的id
                extEproduct.setId(UUID.randomUUID().toString());
                //设置报运附件表的报运表id
                extEproduct.setExportId(export.getId());
                //设置报运附件表的报运商品表
                //通过附件表查询到对应的货物表的id
                String contractProductId = extCproduct.getContractProductId();
                //通过货物表和报运商品表的map关联查询到报运商品表的id,进行设置
                String exportProductId = map.get(contractProductId);
                extEproduct.setExportProductId(exportProductId);
                //设置附件数量
                extNum = extNum + extCproduct.getCnumber();
                //保存报运附件表
                extEproductDao.insertSelective(extEproduct);

            }
            //设置报运主表货物数量和附件数量
            export.setProNum(proNum);
            export.setExtNum(extNum);
            //保存报运主表到数据库
            exportDao.insertSelective(export);
        }

    }

    @Override
    public void update(Export export) {
        //保存更新后的报运单主表
        exportDao.updateByPrimaryKeySelective(export);
        //通过报运单查询货物list
        List<ExportProduct> exportProducts = export.getExportProducts();
        if (exportProducts!=null){
            //循环货物list,保存更新后的货物
            for (ExportProduct exportProduct : exportProducts) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    @Override
    public void delete(String id) {
        System.out.println(5555);
        System.out.println("-------------------2");
        Export export = exportDao.selectByPrimaryKey(id);
        //删除货物
        System.out.println("-------------------3");
        List<ExportProduct> exportProducts = export.getExportProducts();
        if (exportProducts!=null){
            for (ExportProduct exportProduct : exportProducts) {
                //删除附件,通过货物id找到对应的附件list
                ExtEproductExample extEproductExample = new ExtEproductExample();
                ExtEproductExample.Criteria criteria = extEproductExample.createCriteria();
                criteria.andExportProductIdEqualTo(exportProduct.getId());
                List<ExtEproduct> extEproducts = extEproductDao.selectByExample(extEproductExample);
                //循环删除附件
                if (extEproducts!=null){
                    for (ExtEproduct extEproduct : extEproducts) {
                        extEproductDao.deleteByPrimaryKey(extEproduct.getId());
                    }

                    exportProductDao.deleteByPrimaryKey(exportProduct.getId());
                }

            }
        }
        //删除报运主表
        exportDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Export> list = exportDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public void updateE(ExportResult exportResult) {
        //获取报运单实体类
        Export export = exportDao.selectByPrimaryKey(exportResult.getExportId());
        //设置税金
        export.setRemark(exportResult.getRemark());
        //设置状态
        export.setState(exportResult.getState());
        //更新到数据库
        exportDao.updateByPrimaryKeySelective(export);

        //获取货物
        Set<ExportProductResult> products = exportResult.getProducts();
        for (ExportProductResult product : products) {
            //创建一个货物
            ExportProduct exportProduct = new ExportProduct();
            //设置id,税金
            exportProduct.setId(product.getExportProductId());
            exportProduct.setTax(product.getTax());
            //更新到数据库
            exportProductDao.updateByPrimaryKeySelective(exportProduct);
        }
    }

    @Override
    public List<Export> findAllByExample(ExportExample exportExample) {
        return exportDao.selectByExample(exportExample);
    }
}

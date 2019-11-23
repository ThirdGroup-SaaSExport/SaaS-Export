package cn.itcast.service.company.impl;

import cn.itcast.common.entity.PageResult;
import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    public void save(Company company) {
        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);
    }

    public Company findById(String id) {

        return companyDao.findById(id);
    }

    public void update(Company company) {
        companyDao.update(company);
    }

    public void delete(String id) {
        companyDao.delete(id);
    }

    //查找所有分页信息
    public PageResult findAll(Integer page, Integer size) {
        //总记录数
        long total=companyDao.findTotal();
        //当前页的记录
        List rows=companyDao.findRows((page-1)*size,size);
        return new PageResult(total,rows,page,size);
    }
    //利用将分页信息封装到PageHelper
    public PageInfo findByHelper(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Company> list = companyDao.findAll();
        return new PageInfo(list);
    }
}

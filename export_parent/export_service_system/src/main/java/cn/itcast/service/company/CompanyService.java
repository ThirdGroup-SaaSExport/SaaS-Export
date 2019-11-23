package cn.itcast.service.company;

import cn.itcast.common.entity.PageResult;
import cn.itcast.domain.company.Company;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();

    void save(Company company);

    //通过id查询公司信息
    Company findById(String id);

    void update(Company company);

    void delete(String id);

    PageResult findAll(Integer page, Integer size);

    PageInfo findByHelper(Integer page, Integer size);
}

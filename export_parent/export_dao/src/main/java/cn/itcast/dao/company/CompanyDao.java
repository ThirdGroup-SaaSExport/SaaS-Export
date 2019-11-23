package cn.itcast.dao.company;

import cn.itcast.domain.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyDao {
    List<Company> findAll();

    void save(Company company);

    //通过id查询公司信息
    Company findById(String id);

    //修改公司信息
    void update(Company company);

    void delete(String id);

    long findTotal();

    List findRows(@Param("index") int index, @Param("size") Integer size);
}

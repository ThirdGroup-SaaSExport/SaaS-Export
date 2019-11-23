package cn.itcast.service.system;

import cn.itcast.domain.system.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {
    PageInfo findAll(String companyId, int page, int size);

    List<Dept> findAll(String companyId);

    void save(Dept dept);
    //
    Dept findById(String id);

    //更新部门信息
    void update(Dept dept);

    void delete(String id);
}

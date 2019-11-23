package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import com.github.pagehelper.PageInfo;

public interface ExtCProductService {
    //分页查询
    PageInfo findAll(ExtCproductExample example, int page, int size);

    //保存附件信息
    void save(ExtCproduct extCproduct);

    //通过id进行查询
    ExtCproduct findById(String id);

    //更新附件信息
    void update(ExtCproduct extCproduct);

    //通过id进行删除
    void delete(String id);
}

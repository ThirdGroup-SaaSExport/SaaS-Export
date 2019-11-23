package cn.itcast.service.cargo;


import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import com.github.pagehelper.PageInfo;


public interface PackingService {

    Packing findById(String id);

    void save(Packing packing);

    void update(Packing packing);

    void delete(String id);

	PageInfo findAll(PackingExample example, int page, int size);

}

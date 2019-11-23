package cn.itcast.service.cargo;

import com.github.pagehelper.PageInfo;
import cn.itcast.domain.cargo.ProductMessage;
import cn.itcast.domain.cargo.ProductMessageExample;

import java.util.List;

public interface ProductMessageService {
    //查询所有货物的货号和详细信息分页显示
    PageInfo findAllProduct(ProductMessageExample example, int page, int size);

    //新增货号和详细信息
    void saveProductMessage(ProductMessage productMessage);


    //根据货号查询货物的详细信息
    ProductMessage findByProductId(String productNo);

    //修改货物的详细信息
    void updateProductMessage(ProductMessage productMessage);

    //删除
    void delete(String productNo);

    //查询所有附件的货号和货物详情
    List<ProductMessage> findAll();
}

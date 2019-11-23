package cn.itcast.service.stat;

import java.util.List;
import java.util.Map;

public interface StatService {

    //厂家销售数据
    List<Map> getFactoryData(String companyId);

    //商品销售数据
    List<Map> getSellData(String companyId);

    //在线人数
    List<Map> getOnlineData(String companyId);
}

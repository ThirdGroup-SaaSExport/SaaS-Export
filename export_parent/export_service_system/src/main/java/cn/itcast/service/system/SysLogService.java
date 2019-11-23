package cn.itcast.service.system;

import cn.itcast.domain.system.SysLog;
import com.github.pagehelper.PageInfo;

public interface SysLogService {
    PageInfo findAll(String companyId, Integer page, Integer size);

    int save(SysLog log);
}

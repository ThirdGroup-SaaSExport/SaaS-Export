package cn.itcast.web.controller.stat;

import cn.itcast.service.stat.StatService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

    @Reference
    private StatService statService;

    @RequestMapping("/toCharts")
    public String toCharts(String chartsType){

        return "stat/stat-"+chartsType;
    }

    @RequestMapping("/getFactoryData")
    public @ResponseBody
    List<Map> getFactoryData(){
        return statService.getFactoryData(companyId);
    }

    @RequestMapping("/getSellData")
    public @ResponseBody
    List<Map> getSellData(){
        return statService.getSellData(companyId);
    }

    @RequestMapping("/getOnlineData")
    public @ResponseBody
    List<Map> getOnlineData(){
        return statService.getOnlineData(companyId);
    }
}

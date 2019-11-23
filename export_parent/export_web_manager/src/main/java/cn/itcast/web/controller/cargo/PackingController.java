package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportExample;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import cn.itcast.service.cargo.ExportService;

import cn.itcast.service.cargo.PackingService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/packing")
public class PackingController extends BaseController {
    /*@Reference
    private PackingService packingService;*/

    /*@Reference
    private PackageService1 packageService1;

    @Reference
    private ContractService contractService;*/
    @Reference
    private PackingService packingService;
    @Reference
    private ExportService exportService;
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
//        System.out.println("========");
//        packingService.delete("1111");
//        packingService.save(new Packing());
        PackingExample packingExample = new PackingExample();
        PackingExample.Criteria packingExampleCriteria = packingExample.createCriteria();
        packingExampleCriteria.andCompanyIdEqualTo(companyId);


        PageInfo pageInfo = packingService.findAll(packingExample, page, size);
//        Contract contract = contractService.findById("123123123");

//        String s = packageService1.list();
//        System.out.println("========pageinfo"+s);
        request.setAttribute("page", pageInfo);
        return "packing/packing-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria exportExampleCriteria = exportExample.createCriteria();
        exportExampleCriteria.andCompanyIdEqualTo(companyId);
        exportExampleCriteria.andStateEqualTo(2L);

        List<Export> exportList = exportService.findAllByExample(exportExample);
        request.setAttribute("exportList", exportList);
        return "packing/packing-add";
    }

    @RequestMapping("/edit")
    public String edit(Packing packing) {
        packing.setCompanyId(companyId);
        packing.setCompanyName(companyName);
        // 3 设置创建新装箱单的人
        packing.setCreateBy(loginUser.getUserName());
        // 4 设置创建新装箱单的部门
        packing.setCreateDept(loginUser.getDeptName());
//        System.out.println("id : "+ packing.getPackingListId());

        System.out.println(packing.getPackingListId());
        if (StringUtils.isEmpty(packing.getPackingListId())) {
            // 新增装箱单
            packingService.save(packing);
        } else {
            packingService.update(packing);
        }
        return "redirect:/cargo/packing/list.do";
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {   // id 为装箱单id : packingListId
        // 根据id查询装箱单
        Packing packing = packingService.findById(id);
        request.setAttribute("packing", packing);

        // 根据装箱单的 exportIds 查询 装箱单的出口报运单
        String exportIdStr = packing.getExportIds();
        request.setAttribute("exportIdStr", exportIdStr);

        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria exportExampleCriteria = exportExample.createCriteria();
        exportExampleCriteria.andCompanyIdEqualTo(companyId);
        exportExampleCriteria.andStateEqualTo(2L);

        List<Export> exportList = exportService.findAllByExample(exportExample);
        request.setAttribute("exportList", exportList);

        return "packing/packing-update";
    }

    @RequestMapping("/cancel")
    public String cancel(String id) {
        Packing packing = packingService.findById(id);
        packing.setState(0L);
        packingService.update(packing);
        return "redirect:/cargo/packing/list.do";
    }

    @RequestMapping("/submit")
    public String submit(String id) {
        Packing packing = packingService.findById(id);
        packing.setState(1L);
        packingService.update(packing);
        return "redirect:/cargo/packing/list.do";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        packingService.delete(id);
        return "redirect:/cargo/packing/list.do";
    }
}

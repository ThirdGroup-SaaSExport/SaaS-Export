package cn.itcast.test.service;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.service.cargo.ContractService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext-*.xml")
public class ContractServiceTest {
    @Reference
    private ContractService contractService;

    @Test
    public void test(){
        Contract contract = new Contract();
        contract.setCustomName("王美丽");
    }
}

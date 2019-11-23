package cn.itcast.web.job;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Myjob {

    public void excute(){
        System.out.println("================"+
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}

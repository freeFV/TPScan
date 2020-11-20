package com.leishianquan.util;

import com.leishianquan.exp.*;
import com.leishianquan.thinkphpexp.*;

import com.leishianquan.model.BasePayload;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Tools {

    public static boolean checkTheURL(String weburl){
        if(!weburl.startsWith("http")){
            return false;
        }
        return true;
    }

    public static String addTheURL(String weburl){
        if(!weburl.startsWith("http")){
            weburl="http://"+weburl;
        }
        return weburl;
    }

    public static String checkTheDomain(String weburl){
        if ("".equals(weburl.trim())){
            return "";
        }
        if (!weburl.startsWith("https")){
            weburl="https://"+weburl;
        }
        if (!weburl.endsWith("/")){
            weburl = weburl+"/";
        }
        return weburl;
    }

    public static HashSet<String> read(String path, String encode){

        HashSet<String> list=new HashSet<String>();
        FileInputStream fs;
        try {
            fs = new FileInputStream(new File(path));
            InputStreamReader isr=null;

            if(encode.equals("")){
                isr=new InputStreamReader(fs);
            }
            else{
                isr=new InputStreamReader(fs,encode);
            }
            BufferedReader br=new BufferedReader(isr);
            String tem=null;
            while((tem=br.readLine())!=null){
                tem=addTheURL(tem);
                if(!list.contains(tem)){
                    list.add(tem);
                }
            }
            br.close();
            isr.close();

        } catch (Exception e) {
        }

        return list;
    }

    public static BasePayload getPayload(String select){
        BasePayload bp=null;


        if(select.contentEquals("thinkphp 时间盲注")){
            bp=new thinkphp_checkcode_time_sqli();
        }
        if (select.contentEquals("thinkphp 5.0.23 debug模式")){
            bp=new thinkphp_construct_debug_rce();
        }
        if(select.contentEquals("thinkphp 5.0.23 命令执行")){
            bp=new thinkphp_construct_code_exec();
        }
        if(select.contentEquals("Thinkphp 3.x order by 注入漏洞")){
            bp=new thinkphp_debug_index_ids_sqli();
        }
        if(select.contentEquals("thinkphp 5.* driver_display命令执行")){
            bp=new thinkphp_driver_display_rce();
        }
        if(select.contentEquals("thinkphp 5.0.10 construct命令执行")){
            bp=new thinkphp_index_construct_rce();
        }
        if(select.contentEquals("thinkphp 5.* showid命令执行")){
            bp=new thinkphp_index_showid_rce();
        }
        if(select.contentEquals("thinkphp 5.* invokefunction命令执行")){
            bp=new thinkphp_invoke_func_code_exec();
        }
        if(select.contentEquals("thinkphp 2.1 lite命令执行")){
            bp=new thinkphp_lite_code_exec();
        }
        if(select.contentEquals("thinkphp 5.* filter命令执行")){
            bp=new thinkphp_method_filter_code_exec();
        }
        if(select.contentEquals("thinkphp multi注入")){
            bp=new thinkphp_multi_sql_leak();
        }
        if(select.contentEquals("thinkphp orderid注入")){
            bp=new thinkphp_pay_orderid_sqli();
        }
        if(select.contentEquals("thinkphp 5.1.* input命令执行")){
            bp=new thinkphp_request_input_rce();
        }
        if(select.contentEquals("thinkphp X_Forwarded_For注入")){
            bp=new thinkphp_view_recent_xff_sqli();
        }
        if(select.contentEquals("thinkphp 5.* templalte命令执行")){
            bp=new templalte_driver_rce();
        }
        if(select.contentEquals("thinkphp 5.* container命令执行")){
            bp=new container_invoke();
        }
        if(select.contentEquals("Thinkphp 3.2.3 update注入漏洞")){
            bp=new thinkphp_update_sql();
        }
        if(select.contentEquals("Thinkphp 3.2.3 缓存命令执行")){
            bp=new thinkphp_cache();
        }
        if(select.contentEquals("Thinkphp 日志泄露漏洞")){
            bp=new thinkphp_log();
        }






        return bp;
    }

}

package com.itheima.json;

import com.alibaba.fastjson.JSONObject;
import com.itheima.domain.City;
import com.itheima.domain.Province;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "CityServlet")
public class CityServlet extends HttpServlet {

    private Map<Province, List<City>> map;

    @Override
    public void init() throws ServletException {
        map =new HashMap<Province,List<City>>();
        //创建省份
        Province p1 = new Province();
        p1.setId(1);
        p1.setName("黑龙江");

        City c11 = new City();
        c11.setId(1);
        c11.setName("哈尔滨");

        City c12 = new City();
        c12.setId(2);
        c12.setName("大庆");

        List<City> l1 = new ArrayList<City>();
        l1.add(c11);
        l1.add(c12);
        map.put(p1,l1);

     Province p2 = new Province();
        p2.setId(2);
        p2.setName("吉林");

        City c21 = new City();
        c21.setId(1);
        c21.setName("长春");

        City c22 = new City();
        c22.setId(2);
        c22.setName("吉林");

        List<City> l2 = new ArrayList<City>();
        l2.add(c21);
        l2.add(c22);
        map.put(p2,l2);

        Province p3 = new Province();
        p3.setId(3);
        p3.setName("辽宁");

        City c31 = new City();
        c31.setId(1);
        c31.setName("沈阳");

        City c32 = new City();
        c32.setId(2);
        c32.setName("大连");

        List<City> l3 = new ArrayList<City>();
        l3.add(c31);
        l3.add(c32);
        map.put(p3,l3);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          doGet(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //处理响应中文乱码
        response.setCharacterEncoding("utf-8"); //响应乱码
        request.setCharacterEncoding("utf-8");  //请求中文乱码,只对post请求有效

        String method = request.getParameter("method");

        //判断method值是pro还是city来确定要做获取所有省份信息还是获取省份对应的城市信息
        if("pro".equals(method)){
            //获取省份信息
            Set<Province> keys = map.keySet();
            //将所有的省份对象的set集合转换成json,响应到浏览器
            String json = JSONObject.toJSONString(keys);
            response.getWriter().write(json);
        }

        //要获取城市信息
        if("city".equals(method)){
           //首先省份名称
           String pname=request.getParameter("pname");

           //遍历map,根据省份名称来获取对应的List<City>
           for(Province p:map.keySet()){
               if(pname.equals(p.getName())){
                   
                   List<City> citys = map.get(p);
                   //将city转换成json响应到浏览器
                   String json = JSONObject.toJSONString(citys);

                   response.getWriter().write(json);
                   break;
               }
           }
        }
    }
}

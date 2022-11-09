package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.SysWorkingDaysDao;
import com.jsdc.itss.mapper.SysWorkingDaysCalendarMappper;
import com.jsdc.itss.mapper.SysWorkingDaysDistinguishMapper;
import com.jsdc.itss.mapper.SysWorkingDaysMapper;
import com.jsdc.itss.model.SysWorkingDays;
import com.jsdc.itss.model.SysWorkingDaysCalendar;
import com.jsdc.itss.model.SysWorkingDaysDistinguish;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Service
@Transactional
public class SysWorkingDaysService extends BaseService<SysWorkingDaysDao, SysWorkingDays> {

    @Autowired
    private SysWorkingDaysMapper sysWorkingDaysMapper;
    @Autowired
    private SysWorkingDaysDao sysWorkingDaysDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysWorkingDaysDistinguishMapper daysDistinguishMapper;
    @Autowired
    private SysWorkingDaysCalendarMappper sysWorkingDaysCalendarMappper;

    public PageInfo<SysWorkingDays> toList(Integer pageIndex, Integer pageSize, SysWorkingDays beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysWorkingDays> sysWorkingDaysVos = sysWorkingDaysMapper.toList(beanParam);
//        for (SysWorkingDays temp : sysWorkingDaysVos){
//            Integer id =  temp.getId();
//            QueryWrapper<SysWorkingDaysDistinguish> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("is_del","0");
//            queryWrapper.eq("work_day_id",id);
//            List<SysWorkingDaysDistinguish> list = daysDistinguishMapper.selectList(queryWrapper);
//            if (!CollectionUtils.isEmpty(list)){
//                temp.setStart_time_AM(list.get(0).getStart_time_AM());
//                temp.setEnd_time_AM(list.get(0).getEnd_time_AM());
//                temp.setStart_time_PM(list.get(0).getStart_time_PM());
//                temp.setEnd_time_PM(list.get(0).getEnd_time_PM());
//            }
//        }
        PageInfo<SysWorkingDays> page = new PageInfo<>(sysWorkingDaysVos);

        return page;
    }


    public ResultInfo getWorkTime(SysWorkingDays beanParam){
        QueryWrapper<SysWorkingDaysDistinguish> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("work_day_id",beanParam.getId());
        SysWorkingDaysDistinguish sysWorkingDaysDistinguish = daysDistinguishMapper.selectOne(queryWrapper);
        return ResultInfo.success(sysWorkingDaysDistinguish);
    }


    public ResultInfo getById(Integer id) {
        QueryWrapper<SysWorkingDays> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        SysWorkingDays sysWorkingDays = selectOne(queryWrapper);

        QueryWrapper<SysWorkingDaysDistinguish> distinguishQueryWrapper = new QueryWrapper<>();
        distinguishQueryWrapper.eq("is_del", 0);
        distinguishQueryWrapper.eq("work_day_id", id);
        List<SysWorkingDaysDistinguish> list = daysDistinguishMapper.selectList(distinguishQueryWrapper);

        sysWorkingDays.setDistinguishes(list);
        return ResultInfo.success(sysWorkingDays);
    }

    /**
     * 添加
     */
    public ResultInfo addSysWorkingDays(SysWorkingDays bean) {
        List<SysWorkingDays> list = this.getAllList();
        if ("0".equals(bean.getAcquiesce())){
            if (null == bean.getId() ){
                if (!CollectionUtils.isEmpty(list)){
                    for (int i = 0 ; i < list.size() ; i++){
                        SysWorkingDays temp = list.get(i);
                        temp.setAcquiesce("1");
                        sysWorkingDaysMapper.updateById(temp);
                    }
                }
            }else {
                if (!CollectionUtils.isEmpty(list)){
                    for (int i = 0 ; i < list.size() ; i++){
                        SysWorkingDays temp = list.get(i);
                        if (!temp.getId().equals(bean.getId())){
                            temp.setAcquiesce("1");
                            sysWorkingDaysMapper.updateById(temp);
                        }
                    }
                }
            }
        }



        if (bean.getId()==null || bean.getId().equals("")){
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());




            SysWorkingDaysDistinguish sysWorkingDaysDistinguish = new SysWorkingDaysDistinguish();

            sysWorkingDaysDistinguish.setCreate_time(new Date());
            // 创建者
            sysWorkingDaysDistinguish.setCreate_user(sysUserService.getUser().getId());
            sysWorkingDaysDistinguish.setStart_time_AM(bean.getStart_time_AM());
            sysWorkingDaysDistinguish.setEnd_time_AM(bean.getEnd_time_AM());
            sysWorkingDaysDistinguish.setStart_time_PM(bean.getStart_time_PM());
            sysWorkingDaysDistinguish.setEnd_time_PM(bean.getEnd_time_PM());
            sysWorkingDaysDistinguish.setIs_del("0");
            //保存工作日表
            insert(bean);
            sysWorkingDaysDistinguish.setWork_day_id(bean.getId());

            daysDistinguishMapper.insert(sysWorkingDaysDistinguish);



            return ResultInfo.success();
        }else {

            return editSysWorkingDays(bean);
        }

    }

    /**
     * 编辑
     */
    public ResultInfo editSysWorkingDays(SysWorkingDays bean) {
        boolean flag = true;

        SysWorkingDays sysWorkingDaysTemp =  sysWorkingDaysMapper.selectById(bean.getId());
        if ("0".equals(sysWorkingDaysTemp.getAcquiesce())){
            if ("1".equals(bean.getAcquiesce())){
                flag = false;
                bean.setAcquiesce("0");
            }
        }

        // 修改者
         bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());

        //根据work_day_id删除掉关联表数据 重新添加
        UpdateWrapper<SysWorkingDaysDistinguish> distinguishUpdateWrapper = new UpdateWrapper();
        distinguishUpdateWrapper.eq("work_day_id", bean.getId());
        distinguishUpdateWrapper.set("is_del", "1");
        daysDistinguishMapper.update(null, distinguishUpdateWrapper);


        SysWorkingDaysDistinguish sysWorkingDaysDistinguish = new SysWorkingDaysDistinguish();

        sysWorkingDaysDistinguish.setCreate_time(new Date());
        // 创建者
        sysWorkingDaysDistinguish.setCreate_user(sysUserService.getUser().getId());
        sysWorkingDaysDistinguish.setStart_time_AM(bean.getStart_time_AM());
        sysWorkingDaysDistinguish.setEnd_time_AM(bean.getEnd_time_AM());
        sysWorkingDaysDistinguish.setStart_time_PM(bean.getStart_time_PM());
        sysWorkingDaysDistinguish.setEnd_time_PM(bean.getEnd_time_PM());
        sysWorkingDaysDistinguish.setIs_del("0");
        updateById(bean);
        sysWorkingDaysDistinguish.setWork_day_id(bean.getId());
        daysDistinguishMapper.insert(sysWorkingDaysDistinguish);

        if (flag){
            return ResultInfo.success();
        }else {
            return ResultInfo.customize_response(0,"数据为默认数据，默认状态未做修改",null,null);
        }

    }

    public ResultInfo deleteSysWorkingDays(Integer id) {


        SysWorkingDays bean = selectById(id);
        if ("0".equals(bean.getAcquiesce())){
            return ResultInfo.error("默认数据,无法删除！！");
        }

        bean.setUpdate_time(new Date());
        //设置删除状态
        bean.setIs_del("1");
        updateById(bean);

        //根据user_id删除掉用户角色关联表数据 重新添加
        UpdateWrapper<SysWorkingDaysDistinguish> distinguishUpdateWrapper = new UpdateWrapper();
        distinguishUpdateWrapper.eq("work_day_id", bean.getId());
        distinguishUpdateWrapper.set("is_del", "1");
        daysDistinguishMapper.update(null, distinguishUpdateWrapper);

        return ResultInfo.success();
    }

    public List<SysWorkingDays> dropDownData(){
        LambdaQueryWrapper<SysWorkingDays> queryWrapper = new LambdaQueryWrapper<>() ;
        List<SysWorkingDays> sysWorkingDaysVos = sysWorkingDaysMapper.selectList(queryWrapper.eq(SysWorkingDays::getIs_del, GlobalData.ISDEL_NO));
        return sysWorkingDaysVos ;
    }


    /**
     * 描述： 查询日历
     * @param sysWorkingDaysCalendar
     * @return
     */
    public List<SysWorkingDaysCalendar> selectCalendar(SysWorkingDaysCalendar sysWorkingDaysCalendar){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String value = simpleDateFormat.format(sysWorkingDaysCalendar.getValue());
//        String value =sysWorkingDaysCalendar.getValue();
        String[] tempValue1 = value.split("-");
        sysWorkingDaysCalendar.setYear(Integer.parseInt(tempValue1[0]));
        sysWorkingDaysCalendar.setMonth(Integer.parseInt(tempValue1[1]));
        //查询数据库是否有当前月份的数据 如果有直接返回
        QueryWrapper<SysWorkingDaysCalendar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year",sysWorkingDaysCalendar.getYear());
        queryWrapper.eq("month",sysWorkingDaysCalendar.getMonth());
        queryWrapper.eq("is_del","0");
        List<SysWorkingDaysCalendar> list =sysWorkingDaysCalendarMappper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(list)){
            return list;
        }
        //如果没有 则拼接时间 获取此月份的所有天数
        Integer year = sysWorkingDaysCalendar.getYear();
        Integer month = sysWorkingDaysCalendar.getMonth();
        String dateStart = year+"-"+month+"-1";
        int endMonth = month+1;
        String dateEnd = year+"-"+endMonth+"-1";

        Date realDateStart = null;
        Date realDateEnd = null;
        try {
            realDateStart = simpleDateFormat.parse(dateStart);
            realDateEnd = simpleDateFormat.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //当前月所有天的集合
        List<Date>  dateList  = findDates(realDateStart,realDateEnd);

        //返回的数据
        List<SysWorkingDaysCalendar> result = new ArrayList<>();

        //判断当前天是否是周六周日 如果是设置为休息
        for (int i= 0 ; i < dateList.size()-1 ; i ++ ){
            java.util.Calendar cal = java.util.Calendar.getInstance();
            Date d=dateList.get(i);
            cal.setTime(d);
            int w=cal.get(java.util.Calendar.DAY_OF_WEEK)-1;
            if(w==0) w=7;
            String weekday = null;
            if (w==1){
                weekday="周一";
            }else if (w==2){
                weekday="周二";
            }else if (w==3){
                weekday="周三";
            }else if (w==4){
                weekday="周四";
            }else if (w==5){
                weekday="周五";
            }else if (w==6){
                weekday="周六";
            }else if (w==7){
                weekday="周日";
            }
            Date date = dateList.get(i);
            String dateString = simpleDateFormat.format(date);
            String[] strings = dateString.split("-");
            for (int j= 0 ; j < strings.length ; j++){
                SysWorkingDaysCalendar calendar = new SysWorkingDaysCalendar();
                String tempYear = strings[0];
                String tempMonth = strings[1];
                String tempDay = strings[2];
                //添加年
                calendar.setYear(Integer.parseInt(tempYear));
                //添加月
                calendar.setMonth(Integer.parseInt(tempMonth));
                //添加日
                calendar.setDay(Integer.parseInt(tempDay));
                //添加周几
                calendar.setWeek(weekday);
                //如果当天是周六周日 设置为休息 否则设置为工作
                if (weekday.equals("周六")|| weekday.equals("周日")){
                    calendar.setGrade(1);
                }else {
                    calendar.setGrade(2);
                }
                calendar.setIs_del("0");
                calendar.setCreate_time(new Date());
                calendar.setCreate_user(sysUserService.getUser().getId());
                Integer tempMonth1 =Integer.parseInt(tempMonth);
                Integer tempDay1 =Integer.parseInt(tempDay);
                String tempMonth2 = tempMonth1+"";
                String tempDay2 = tempDay1+"";
                if (tempMonth1< 10){
                     tempMonth2 = "0"+tempMonth1;
                }
                if (tempDay1 < 10){
                    tempDay2 = "0"+tempDay1;
                }

                String tempDate = Integer.parseInt(tempYear)+"-"+tempMonth2+"-"+tempDay2;
                try {
                    calendar.setDate(simpleDateFormat.parse(tempDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String month_ = calendar.getMonth()<10?"0"+calendar.getMonth():calendar.getMonth()+"";
                String day_ = calendar.getDay()<10?"0"+calendar.getDay():calendar.getDay()+"";
                calendar.setDateStr(calendar.getYear()+"-"+month_+"-"+day_);
                //向数据库添加数据
                sysWorkingDaysCalendarMappper.insert(calendar);
                result.add(calendar);
                break;
            }
        }
        return result;
    }


    //获得指定时间段的所有天数
    public  List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        java.util.Calendar calBegin = java.util.Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        java.util.Calendar calEnd = java.util.Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(java.util.Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 修改日历
     */
    public ResultInfo setCalendar(SysWorkingDaysCalendar sysWorkingDaysCalendar){

        UpdateWrapper<SysWorkingDaysCalendar> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("date",sysWorkingDaysCalendar.getDate());
        updateWrapper.set("grade",sysWorkingDaysCalendar.getGrade());
        updateWrapper.set("update_time",new Date());
        updateWrapper.set("update_user",sysUserService.getUser().getId());

        sysWorkingDaysCalendarMappper.update(null,updateWrapper);
        return ResultInfo.success();
    }


    public List<SysWorkingDays> getAllList() {
        SysWorkingDays beanParam = new SysWorkingDays();
        beanParam.setAcquiesce("0");
        List<SysWorkingDays> sysWorkingDaysVos = sysWorkingDaysMapper.toList(beanParam);
        return sysWorkingDaysVos;
    }
}

package com.btcc.web.task.controller;

import com.btcc.util.TaskUtils;
import com.btcc.web.task.entity.ScheduleJob;
import com.btcc.web.task.service.TaskService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by peiyou on 2016/11/3.
 */
@Controller
@RequestMapping("/TaskController")
public class TaskController {

    @Autowired
    TaskService taskService;

    /**
     * 查询调度任务
     * @param modelAndView
     * @param job
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(ModelAndView modelAndView, ScheduleJob job){
        PageInfo<ScheduleJob> page = taskService.selectScheduleAll(job);
        modelAndView.addObject("pageinfo",page);
        modelAndView.addObject("from",job);
        modelAndView.setViewName("/task/index");
        return modelAndView;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(ModelAndView modelAndView, ScheduleJob job){
        if(job.getJobId() == null){
            modelAndView.addObject("job",job);
        }else {
            ScheduleJob jobDb = taskService.selectScheduleByJobId(job.getJobId());
            modelAndView.addObject("job", jobDb);
        }
        modelAndView.setViewName("/task/edit");
        return modelAndView;
    }
    /**
     * 立即运行一个任务
     * @param job
     * @return
     */
    @RequestMapping("/nowRun")
    @ResponseBody
    public String nowRun(ScheduleJob job){
        boolean success = taskService.nowRunScheduleByJob(job);
        JSONObject obj = new JSONObject();
        obj.put("success",success);
        return obj.toString();
    }

    /**
     * 删除
     * @param modelAndView
     * @param job
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(ModelAndView modelAndView, ScheduleJob job){
        JSONObject obj = new JSONObject();
        taskService.deleteScheduleByJobId(job.getJobId());
        obj.put("success",true);
        return obj.toString();
    }

    @RequestMapping("/insert")
    @ResponseBody
    public String insert(ModelAndView modelAndView,ScheduleJob job){
        JSONObject obj = new JSONObject();
        taskService.insertSchedule(job);
        obj.put("success",true);
        return obj.toString();
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(ModelAndView modelAndView,ScheduleJob job){
        JSONObject obj = new JSONObject();
        taskService.updateSchedule(job);
        obj.put("success",true);
        return obj.toString();
    }


    @RequestMapping("/checkCronExpression")
    @ResponseBody
    public String checkCronExpression(String cron){
        boolean flag = TaskUtils.checkCronExpression(cron);
        JSONObject obj = new JSONObject();
        obj.put("success",flag);
        return obj.toString();
    }
}

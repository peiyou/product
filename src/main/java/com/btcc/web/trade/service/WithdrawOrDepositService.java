package com.btcc.web.trade.service;

import com.btcc.web.trade.mapper.PlusWithdrawOrDepositMapper;
import com.btcc.web.trade.mapper.UserEmailMapper;
import com.btcc.web.trade.mapper.WithdrawOrDepositMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/9/29.
 */
@Service
public class WithdrawOrDepositService {

    @Autowired
    PlusWithdrawOrDepositMapper plusWithdrawOrDepositMapper;

    @Autowired
    WithdrawOrDepositMapper withdrawOrDepositMapper;

    @Autowired
    UserEmailMapper userEmailMapper;

    public List<Map<String,Object>> withdrawBTC(Map<String,Object> param){
        List<Map<String,Object>> list = withdrawOrDepositMapper.withdrawBTC(param);
        List<Integer> userIds = new ArrayList<>();
        getEmail(list, userIds);
        return list;
    }


    public List<Map<String,Object>> withdrawLTC(Map<String,Object> param){
        List<Map<String,Object>> list =  withdrawOrDepositMapper.withdrawLTC(param);
        List<Integer> userIds = new ArrayList<>();
        getEmail(list, userIds);
        return list;
    }

    public List<Map<String,Object>> depositBTC(Map<String,Object> param){
        List<Map<String,Object>> list = withdrawOrDepositMapper.depositBTC(param);
        List<Integer> userIds = new ArrayList<>();
        getEmail(list, userIds);
        return list;
    }

    public List<Map<String,Object>> depositLTC(Map<String,Object> param){
        List<Map<String,Object>> list =  withdrawOrDepositMapper.depositLTC(param);
        List<Integer> userIds = new ArrayList<>();
        getEmail(list, userIds);
        return list;
    }

    /**
     * @param param
     * @return
     */
    public List<Map<String,Object>> depositETH(Map<String,Object> param){
        List<Map<String,Object>> list = plusWithdrawOrDepositMapper.depositETH(param);
        List<Integer> userIds = new ArrayList<>();
        getEmail(list, userIds);
        getUserInfo(list, userIds);
        return list;
    }



    public List<Map<String,Object>> withdrawETH(Map<String,Object> param){
        List<Map<String,Object>> list =  plusWithdrawOrDepositMapper.withdrawETH(param);
        List<Integer> userIds = new ArrayList<>();
        getEmail(list, userIds);
        getUserInfo(list, userIds);
        return list;
    }

    public List<Map<String,Object>> withdrawSuccessETH(Map<String,Object> param){
        List<Map<String,Object>> list =  plusWithdrawOrDepositMapper.withdrawSuccessETH(param);
        List<Integer> userIds = new ArrayList<>();
        getEmail(list, userIds);
        getUserInfo(list, userIds);
        return list;
    }

    public List<Map<String,Object>> withdrawBCC(Map<String,Object> param){
        List<Map<String,Object>> list =  plusWithdrawOrDepositMapper.withdrawBCC(param);
        List<Integer> userIds = new ArrayList<>();
        getEmail(list, userIds);
        getUserInfo(list, userIds);
        return list;
    }

    public List<Map<String,Object>> withdrawSuccessBCC(Map<String,Object> param){
        List<Map<String,Object>> list =  plusWithdrawOrDepositMapper.withdrawSuccessBCC(param);
        List<Integer> userIds = new ArrayList<>();
        getEmail(list, userIds);
        getUserInfo(list, userIds);
        return list;
    }


    public List<Map<String,Object>> userInfo(List<Integer> userIds){
        return withdrawOrDepositMapper.userInfo(userIds);
    }

    public List<Map<String,Object>> queryUserEmail(List<Integer> userIds){
        return userEmailMapper.queryUserEmail(userIds);
    }


    private void getEmail(List<Map<String, Object>> list, List<Integer> userIds) {
        if(list != null && list.size() > 0){
            list.forEach(map -> {
                userIds.add((Integer) map.get("user_id"));
            });

        }
        if(userIds.size() > 0) {
            List<Map<String, Object>> emailList = this.queryUserEmail(userIds);
            if (emailList != null && emailList.size() > 0) {
                list.forEach(map -> {
                    Integer userId = (Integer) map.get("user_id");
                    emailList.forEach(email -> {
                        Integer emailUser = (Integer) email.get("user_id");
                        if (userId.equals(emailUser)) {
                            map.put("user_email", email.get("user_email"));
                        }
                    });
                });
            }
        }
    }


    private void getUserInfo(List<Map<String, Object>> list, List<Integer> userIds) {
        if(userIds.size() > 0){
            List<Map<String,Object>> userInfoList = userInfo(userIds);
            list.forEach(map -> {
                Integer userId = (Integer) map.get("user_id");
                userInfoList.forEach(userInfo -> {
                    Integer userInfoId = (Integer) userInfo.get("user_id");
                    if(userId.equals(userInfoId)){
                        map.put("name",userInfo.get("name"));
                        map.put("phone",userInfo.get("phone"));
                        map.put("phone_country",userInfo.get("phone_country"));
                        map.put("id_type",userInfo.get("id_type"));
                        map.put("value",userInfo.get("value"));
                        map.put("account_type",userInfo.get("account_type"));
                    }
                });
            });
        }
    }
}

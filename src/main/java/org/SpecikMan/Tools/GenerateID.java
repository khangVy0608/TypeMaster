package org.SpecikMan.Tools;

import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.RoleDao;
import org.SpecikMan.Entity.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateID {
    private static final AccountDao accountDao = new AccountDao();
    private static final RoleDao roleDao = new RoleDao();

    public static String genAccount() {
        List<Account> accounts = accountDao.getAll();
        if (accounts.isEmpty()) {
            return "AC1";
        } else {
            List<Integer> nums = new ArrayList<>();
            accounts.forEach(element -> nums.add(Integer.valueOf(element.getIdAccount().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            nums.forEach(System.out::println);
            return "AC"+(nums.get(nums.size()-1)+1);
        }
    }

    public static String genRole() {
        List<Integer> ids = new ArrayList<>();
        roleDao.getAll().forEach(element -> ids.add(Integer.parseInt(element.getIdRole().replaceAll("[^\\\\d.]", ""))));
        if (ids.isEmpty()) {
            return "RL01";
        }
        if (ids.get(ids.size() - 1) < 9) {
            return "RL0" + (ids.get(ids.size() - 1) + 1);
        } else {
            return "RL" + (ids.get(ids.size() - 1) + 1);
        }
    }
}

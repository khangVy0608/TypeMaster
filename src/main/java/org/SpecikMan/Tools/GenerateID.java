package org.SpecikMan.Tools;

import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.DifficultyDao;
import org.SpecikMan.DAL.ModeDao;
import org.SpecikMan.DAL.RoleDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.Difficulty;
import org.SpecikMan.Entity.Mode;
import org.SpecikMan.Entity.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateID {
    private static final AccountDao accountDao = new AccountDao();
    private static final RoleDao roleDao = new RoleDao();
    private static final DifficultyDao difficultyDao = new DifficultyDao();
    private static final ModeDao modeDao = new ModeDao();

    public static String genAccount() {
        List<Account> accounts = accountDao.getAll();
        if (accounts.isEmpty()) {
            return "AC1";
        } else {
            List<Integer> nums = new ArrayList<>();
            accounts.forEach(element -> nums.add(Integer.valueOf(element.getIdAccount().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            return "AC"+(nums.get(nums.size()-1)+1);
        }
    }

    public static String genRole() {
        List<Role> roles = roleDao.getAll();
        if (roles.isEmpty()) {
            return "RL1";
        } else {
            List<Integer> nums = new ArrayList<>();
            roles.forEach(element -> nums.add(Integer.valueOf(element.getIdRole().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            return "RL"+(nums.get(nums.size()-1)+1);
        }
    }
    public static String genDifficulty() {
        List<Difficulty> difficulties = difficultyDao.getAll();
        if (difficulties.isEmpty()) {
            return "DF1";
        } else {
            List<Integer> nums = new ArrayList<>();
            difficulties.forEach(element -> nums.add(Integer.valueOf(element.getIdDifficulty().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            return "DF"+(nums.get(nums.size()-1)+1);
        }
    }
    public static String genMode() {
        List<Mode> modes = modeDao.getAll();
        if (modes.isEmpty()) {
            return "MD1";
        } else {
            List<Integer> nums = new ArrayList<>();
            modes.forEach(element -> nums.add(Integer.valueOf(element.getIdMode().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            return "MD"+(nums.get(nums.size()-1)+1);
        }
    }
}

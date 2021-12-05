package org.SpecikMan.Tools;

import org.SpecikMan.DAL.*;
import org.SpecikMan.Entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateID {
    private static final AccountDao accountDao = new AccountDao();
    private static final RoleDao roleDao = new RoleDao();
    private static final DifficultyDao difficultyDao = new DifficultyDao();
    private static final ModeDao modeDao = new ModeDao();
    private static final LevelDao levelDao = new LevelDao();
    private static final DetailsDao detailsDao = new DetailsDao();
    private static final DetailLogDao logDao = new DetailLogDao();
    private static final InventoryDao inventoryDao = new InventoryDao();
    private static final GroupDao grDao = new GroupDao();
    public static String genAccount() {
        List<Account> accounts = accountDao.getAll();
        if (accounts.isEmpty()) {
            return "AC11";
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
    public static String genLevel() {
        List<Level> levels = levelDao.getAll();
        if (levels.isEmpty()) {
            return "LV1";
        } else {
            List<Integer> nums = new ArrayList<>();
            levels.forEach(element -> nums.add(Integer.valueOf(element.getIdLevel().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            return "LV"+(nums.get(nums.size()-1)+1);
        }
    }
    public static String genDetails() {
        List<AccountLevelDetails> details = detailsDao.getAll();
        if (details.isEmpty()) {
            return "LD1";
        } else {
            List<Integer> nums = new ArrayList<>();
            details.forEach(element -> nums.add(Integer.valueOf(element.getIdLevelDetails().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            return "LD"+(nums.get(nums.size()-1)+1);
        }
    }
    public static String genLog() {
        List<DetailLog> logs = logDao.getAll();
        if (logs.isEmpty()) {
            return "LG1";
        } else {
            List<Integer> nums = new ArrayList<>();
            logs.forEach(element -> nums.add(Integer.valueOf(element.getIdLog().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            return "LG"+(nums.get(nums.size()-1)+1);
        }
    }
    public static String genInventory() {
        List<Inventory> inventories = inventoryDao.getAll();
        if (inventories.isEmpty()) {
            return "IV1";
        } else {
            List<Integer> nums = new ArrayList<>();
            inventories.forEach(element -> nums.add(Integer.valueOf(element.getIdInventory().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            return "IV"+(nums.get(nums.size()-1)+1);
        }
    }
    public static String genGroup() {
        List<Group> grs = grDao.getAll();
        if (grs.isEmpty()) {
            return "GR1";
        } else {
            List<Integer> nums = new ArrayList<>();
            grs.forEach(element -> nums.add(Integer.valueOf(element.getIdGroup().replaceAll("[^0-9]", ""))));
            Collections.sort(nums);
            return "GR"+(nums.get(nums.size()-1)+1);
        }
    }
}
package ClearCoding.Utils;

import ClearCoding.Entity.Skill;
import ClearCoding.Entity.Skill_Set;

public class Utilites {

    public static int getEmployeeRank(String position){
        int rank=0;
        switch (position) {
            case "JD":
                rank = 1;
                break;
            case "MD":
                rank = 2;
                break;
            case "AD":
                rank = 3;
                break;
            case "SD":
                rank = 4;
                break;
            default:
                rank = 0;
        }
        return rank;
    }

    public static boolean numberOrNot(String input){
        try
        {
            Integer.parseInt(input);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
        return true;
    }

    public static long toLong(String input){
        return Long.parseLong(input);
    }



}

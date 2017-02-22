package ClearCoding.Utils;


import ClearCoding.Entity.*;

public class FieldControler {

    public static long toLong(String input){
        return Long.parseLong(input);
    }

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

    public static Boolean isSkillLower(Skill skill, Skill_Set skill_set){
        boolean b = false;
        if (toLong(skill_set.getSkillBySkillId().getName()) > toLong(skill.getName()))
            b=true;

        return b;
    }


}

package cn.edu.mydotabuff.common;

import android.text.TextUtils;

import java.math.BigInteger;

import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.model.LobbyType;

public class Common {

    /**
     * userID转SteamID
     *
     * @param userID
     * @return
     */
    public static String getSteamID(String userID) {
        BigInteger big = new BigInteger("76561197960265728");
        big = big.add(new BigInteger(userID));
        return big.toString();
    }

    /**
     * SteamID转userID
     *
     * @param steamID
     * @return
     */
    public static String getUserID(String steamID) {
        BigInteger steamid = new BigInteger(steamID);
        BigInteger big = new BigInteger("76561197960265728");
        return steamid.subtract(big).toString();
    }

    // 获取在线状态
    public static String getPersonState(int id) {
        switch (id) {
            case 0:
                return "离线";
            case 1:
                return "在线";
            case 2:
                return "忙碌";
            case 3:
                return "离开";
            case 4:
                return "打嗑睡";
            case 5:
                return "想要交易";
            case 6:
                return "想玩游戏";
            default:
                return "unknown";
        }
    }

    public static String getGameMode(int type) {
        String str = "";
        switch (type) {
            case -1:
                str = "无效比赛";
                break;
            case 0:
                str = "公开匹配";
                break;
            case 1:
                str = "练习赛";
                break;
            case 2:
                str = "锦标赛";
                break;
            case 3:
                str = "辅导赛";
                break;
            case 4:
                str = "机器人对抗赛";
                break;
            case 5:
                str = "战队比赛";
                break;
            case 6:
                str = "单排天梯";
                break;
            case 7:
                str = "天梯匹配";
                break;
            case 8:
                str = "Solo模式";
                break;

            default:
                break;
        }
        return str;
    }

    // public static int getHeroDrawableId(int ID) {
    // switch (ID) {
    // case 1:
    // return R.drawable.hero_1;
    // case 2:
    // return R.drawable.hero_2;
    // case 3:
    // return R.drawable.hero_3;
    // case 4:
    // return R.drawable.hero_4;
    // case 5:
    // return R.drawable.hero_5;
    // case 6:
    // return R.drawable.hero_6;
    // case 7:
    // return R.drawable.hero_7;
    // case 8:
    // return R.drawable.hero_8;
    // case 9:
    // return R.drawable.hero_9;
    // case 10:
    // return R.drawable.hero_10;
    // case 11:
    // return R.drawable.hero_11;
    // case 12:
    // return R.drawable.hero_12;
    // case 13:
    // return R.drawable.hero_13;
    // case 14:
    // return R.drawable.hero_14;
    // case 15:
    // return R.drawable.hero_15;
    // case 16:
    // return R.drawable.hero_16;
    // case 17:
    // return R.drawable.hero_17;
    // case 18:
    // return R.drawable.hero_18;
    // case 19:
    // return R.drawable.hero_19;
    // case 20:
    // return R.drawable.hero_20;
    // case 21:
    // return R.drawable.hero_21;
    // case 22:
    // return R.drawable.hero_22;
    // case 23:
    // return R.drawable.hero_23;
    // case 25:
    // return R.drawable.hero_25;
    // case 26:
    // return R.drawable.hero_26;
    // case 27:
    // return R.drawable.hero_27;
    // case 28:
    // return R.drawable.hero_28;
    // case 29:
    // return R.drawable.hero_29;
    // case 30:
    // return R.drawable.hero_30;
    // case 31:
    // return R.drawable.hero_31;
    // case 32:
    // return R.drawable.hero_32;
    // case 33:
    // return R.drawable.hero_33;
    // case 34:
    // return R.drawable.hero_34;
    // case 35:
    // return R.drawable.hero_35;
    // case 36:
    // return R.drawable.hero_36;
    // case 37:
    // return R.drawable.hero_37;
    // case 38:
    // return R.drawable.hero_38;
    // case 39:
    // return R.drawable.hero_39;
    // case 40:
    // return R.drawable.hero_40;
    // case 41:
    // return R.drawable.hero_41;
    // case 42:
    // return R.drawable.hero_42;
    // case 43:
    // return R.drawable.hero_43;
    // case 44:
    // return R.drawable.hero_44;
    // case 45:
    // return R.drawable.hero_45;
    // case 46:
    // return R.drawable.hero_46;
    // case 47:
    // return R.drawable.hero_47;
    // case 48:
    // return R.drawable.hero_48;
    // case 49:
    // return R.drawable.hero_49;
    // case 50:
    // return R.drawable.hero_50;
    // case 51:
    // return R.drawable.hero_51;
    // case 52:
    // return R.drawable.hero_52;
    // case 53:
    // return R.drawable.hero_53;
    // case 54:
    // return R.drawable.hero_54;
    // case 55:
    // return R.drawable.hero_55;
    // case 56:
    // return R.drawable.hero_56;
    // case 57:
    // return R.drawable.hero_57;
    // case 58:
    // return R.drawable.hero_58;
    // case 59:
    // return R.drawable.hero_59;
    // case 60:
    // return R.drawable.hero_60;
    // case 61:
    // return R.drawable.hero_61;
    // case 62:
    // return R.drawable.hero_62;
    // case 63:
    // return R.drawable.hero_63;
    // case 64:
    // return R.drawable.hero_64;
    // case 65:
    // return R.drawable.hero_65;
    // case 66:
    // return R.drawable.hero_66;
    //
    // case 67:
    // return R.drawable.hero_67;
    //
    // case 68:
    // return R.drawable.hero_68;
    //
    // case 69:
    // return R.drawable.hero_69;
    //
    // case 70:
    // return R.drawable.hero_70;
    //
    // case 71:
    // return R.drawable.hero_71;
    //
    // case 72:
    // return R.drawable.hero_72;
    //
    // case 73:
    // return R.drawable.hero_73;
    //
    // case 74:
    // return R.drawable.hero_74;
    //
    // case 75:
    // return R.drawable.hero_75;
    //
    // case 76:
    // return R.drawable.hero_76;
    //
    // case 77:
    // return R.drawable.hero_77;
    //
    // case 78:
    // return R.drawable.hero_78;
    //
    // case 79:
    // return R.drawable.hero_79;
    //
    // case 80:
    // return R.drawable.hero_80;
    //
    // case 81:
    // return R.drawable.hero_81;
    //
    // case 82:
    // return R.drawable.hero_82;
    //
    // case 83:
    // return R.drawable.hero_83;
    //
    // case 84:
    // return R.drawable.hero_84;
    //
    // case 85:
    // return R.drawable.hero_85;
    //
    // case 86:
    // return R.drawable.hero_86;
    //
    // case 87:
    // return R.drawable.hero_87;
    //
    // case 88:
    // return R.drawable.hero_88;
    //
    // case 89:
    // return R.drawable.hero_89;
    //
    // case 90:
    // return R.drawable.hero_90;
    //
    // case 91:
    // return R.drawable.hero_91;
    //
    // case 92:
    // return R.drawable.hero_92;
    //
    // case 93:
    // return R.drawable.hero_93;
    //
    // case 94:
    // return R.drawable.hero_94;
    //
    // case 95:
    // return R.drawable.hero_95;
    //
    // case 96:
    // return R.drawable.hero_96;
    //
    // case 97:
    // return R.drawable.hero_97;
    //
    // case 98:
    // return R.drawable.hero_98;
    //
    // case 99:
    // return R.drawable.hero_99;
    //
    // case 100:
    // return R.drawable.hero_100;
    //
    // case 101:
    // return R.drawable.hero_101;
    //
    // case 102:
    // return R.drawable.hero_102;
    //
    // case 103:
    // return R.drawable.hero_103;
    // case 104:
    // return R.drawable.hero_104;
    // case 105:
    // return R.drawable.hero_105;
    // case 106:
    // return R.drawable.hero_106;
    // case 107:
    // return R.drawable.hero_107;
    // case 109:
    // return R.drawable.hero_109;
    // case 110:
    // return R.drawable.hero_110;
    // default:
    // return R.drawable.hero_103;
    //
    // }
    // }

    // public static void setHeroIcon(Context _caller, int ID, ImageView itemBg)
    // {
    // switch (ID) {
    // case 1:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_1));
    // break;
    // case 2:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_2));
    // break;
    // case 3:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_3));
    // break;
    // case 4:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_4));
    // break;
    // case 5:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_5));
    // break;
    // case 6:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_6));
    // break;
    // case 7:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_7));
    // break;
    // case 8:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_8));
    // break;
    // case 9:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_9));
    // break;
    // case 10:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_10));
    // break;
    // case 11:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_11));
    // break;
    // case 12:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_12));
    // break;
    // case 13:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_13));
    // break;
    // case 14:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_14));
    // break;
    // case 15:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_15));
    // break;
    // case 16:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_16));
    // break;
    // case 17:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_17));
    // break;
    // case 18:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_18));
    // break;
    // case 19:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_19));
    // break;
    // case 20:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_20));
    // break;
    // case 21:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_21));
    // break;
    // case 22:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_22));
    // break;
    // case 23:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_23));
    // break;
    // case 25:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_25));
    // break;
    // case 26:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_26));
    // break;
    // case 27:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_27));
    // break;
    // case 28:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_28));
    // break;
    // case 29:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_29));
    // break;
    // case 30:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_30));
    // break;
    // case 31:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_31));
    // break;
    // case 32:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_32));
    // break;
    // case 33:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_33));
    // break;
    // case 34:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_34));
    // break;
    // case 35:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_35));
    // break;
    // case 36:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_36));
    // break;
    // case 37:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_37));
    // break;
    // case 38:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_38));
    // break;
    // case 39:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_39));
    // break;
    // case 40:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_40));
    // break;
    // case 41:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_41));
    // break;
    // case 42:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_42));
    // break;
    // case 43:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_43));
    // break;
    // case 44:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_44));
    // break;
    // case 45:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_45));
    // break;
    // case 46:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_46));
    // break;
    // case 47:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_47));
    // break;
    // case 48:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_48));
    // break;
    // case 49:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_49));
    // break;
    // case 50:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_50));
    // break;
    // case 51:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_51));
    // break;
    // case 52:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_52));
    // break;
    // case 53:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_53));
    // break;
    // case 54:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_54));
    // break;
    // case 55:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_55));
    // break;
    // case 56:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_56));
    // break;
    // case 57:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_57));
    // break;
    // case 58:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_58));
    // break;
    // case 59:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_59));
    // break;
    // case 60:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_60));
    // break;
    // case 61:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_61));
    // break;
    // case 62:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_62));
    // break;
    // case 63:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_63));
    // break;
    // case 64:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_64));
    // break;
    // case 65:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_65));
    // break;
    // case 66:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_66));
    // break;
    // case 67:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_67));
    // break;
    // case 68:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_68));
    // break;
    // case 69:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_69));
    // break;
    // case 70:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_70));
    // break;
    // case 71:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_71));
    // break;
    // case 72:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_72));
    // break;
    // case 73:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_73));
    // break;
    // case 74:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_74));
    // break;
    // case 75:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_75));
    // break;
    // case 76:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_76));
    // break;
    // case 77:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_77));
    // break;
    // case 78:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_78));
    // break;
    // case 79:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_79));
    // break;
    // case 80:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_80));
    // break;
    // case 81:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_81));
    // break;
    // case 82:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_82));
    // break;
    // case 83:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_83));
    // break;
    // case 84:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_84));
    // break;
    // case 85:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_85));
    // break;
    // case 86:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_86));
    // break;
    // case 87:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_87));
    // break;
    // case 88:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_88));
    // break;
    // case 89:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_89));
    // break;
    // case 90:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_90));
    // break;
    // case 91:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_91));
    // break;
    // case 92:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_92));
    // break;
    // case 93:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_93));
    // break;
    // case 94:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_94));
    // break;
    // case 95:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_95));
    // break;
    // case 96:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_96));
    // break;
    // case 97:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_97));
    // break;
    // case 98:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_98));
    // break;
    // case 99:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_99));
    // break;
    // case 100:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_100));
    // break;
    // case 101:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_101));
    // break;
    // case 102:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_102));
    // break;
    // case 103:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_103));
    // break;
    // case 104:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_104));
    // break;
    // case 105:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_105));
    // break;
    // case 106:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_106));
    // break;
    // case 107:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_107));
    // break;
    // case 109:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_109));
    // break;
    // case 110:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hero_110));
    // break;
    // case 0:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.default_pic));
    // break;
    // default:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.default_pic));
    // break;
    // }
    // }

    // public static void setItemIcon(Context _caller, int ID, ImageView itemBg)
    // {
    // switch (ID) {
    // case 1:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.blink));
    // break;
    // case 2:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.blades_of_attack));
    // break;
    // case 3:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.broadsword));
    // break;
    // case 4:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.chainmail));
    // break;
    // case 5:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.claymore));
    // break;
    // case 6:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.helm_of_iron_will));
    // break;
    // case 7:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.javelin));
    // break;
    // case 8:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mithril_hammer));
    // break;
    // case 9:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.platemail));
    // break;
    // case 10:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.quarterstaff));
    // break;
    // case 11:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.quelling_blade));
    // break;
    // case 12:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ring_of_protection));
    // break;
    // case 13:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.gauntlets));
    // break;
    // case 14:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.slippers));
    // break;
    // case 15:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mantle));
    // break;
    // case 16:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.branches));
    // break;
    // case 17:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.belt_of_strength));
    // break;
    // case 18:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.boots_of_elves));
    // break;
    // case 19:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.robe));
    // break;
    // case 20:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.circlet));
    // break;
    // case 21:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ogre_axe));
    // break;
    // case 22:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.blade_of_alacrity));
    // break;
    // case 23:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.staff_of_wizardry));
    // break;
    // case 24:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ultimate_orb));
    // break;
    // case 25:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.gloves));
    // break;
    // case 26:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.lifesteal));
    // break;
    // case 27:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ring_of_regen));
    // break;
    // case 28:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.sobi_mask));
    // break;
    // case 29:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.boots));
    // break;
    // case 30:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.gem));
    // break;
    // case 31:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.cloak));
    // break;
    // case 32:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.talisman_of_evasion));
    // break;
    // case 33:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.cheese));
    // break;
    // case 34:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.magic_stick));
    // break;
    // case 35:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.magic_wand));
    // break;
    // case 36:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.magic_wand));
    // break;
    // case 37:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ghost));
    // break;
    // case 38:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.clarity));
    // break;
    // case 39:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.flask));
    // break;
    // case 40:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dust));
    // break;
    // case 41:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.bottle));
    // break;
    // case 42:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ward_observer));
    // break;
    // case 43:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ward_sentry));
    // break;
    // case 44:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.tango));
    // break;
    // case 45:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.courier));
    // break;
    // case 46:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.tpscroll));
    // break;
    // case 47:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.travel_boots));
    // break;
    // case 48:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.travel_boots));
    // break;
    // case 49:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.phase_boots));
    // break;
    // case 50:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.phase_boots));
    // break;
    // case 51:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.demon_edge));
    // break;
    // case 52:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.eagle));
    // break;
    // case 53:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.reaver));
    // break;
    // case 54:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.relic));
    // break;
    // case 55:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hyperstone));
    // break;
    // case 56:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ring_of_health));
    // break;
    // case 57:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.void_stone));
    // break;
    // case 58:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mystic_staff));
    // break;
    // case 59:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.energy_booster));
    // break;
    // case 60:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.point_booster));
    // break;
    // case 61:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.vitality_booster));
    // break;
    // case 62:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.power_treads));
    // break;
    // case 63:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.power_treads));
    // break;
    // case 64:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hand_of_midas));
    // break;
    // case 65:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hand_of_midas));
    // break;
    // case 66:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.oblivion_staff));
    // break;
    // case 67:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.oblivion_staff));
    // break;
    // case 68:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.pers));
    // break;
    // case 69:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.pers));
    // break;
    // case 70:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.poor_mans_shield));
    // break;
    // case 71:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.poor_mans_shield));
    // break;
    // case 72:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.bracer));
    // break;
    // case 73:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.bracer));
    // break;
    // case 74:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.wraith_band));
    // break;
    // case 75:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.wraith_band));
    // break;
    // case 76:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.null_talisman));
    // break;
    // case 77:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.null_talisman));
    // break;
    // case 78:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mekansm));
    // break;
    // case 79:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mekansm));
    // break;
    // case 80:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.vladmir));
    // break;
    // case 81:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.vladmir));
    // break;
    // case 84:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.flying_courier));
    // break;
    // case 85:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.buckler));
    // break;
    // case 86:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.buckler));
    // break;
    // case 87:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ring_of_basilius));
    // break;
    // case 88:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ring_of_basilius));
    // break;
    // case 89:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.pipe));
    // break;
    // case 90:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.pipe));
    // break;
    // case 91:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.urn_of_shadows));
    // break;
    // case 92:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.urn_of_shadows));
    // break;
    // case 93:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.headdress));
    // break;
    // case 94:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.headdress));
    // break;
    // case 95:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.sheepstick));
    // break;
    // case 96:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.sheepstick));
    // break;
    // case 97:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.orchid));
    // break;
    // case 98:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.orchid));
    // break;
    // case 99:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.cyclone));
    // break;
    // case 100:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.cyclone));
    // break;
    // case 101:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.force_staff));
    // break;
    // case 102:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.force_staff));
    // break;
    // case 103:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon));
    // break;
    // case 197:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon_2));
    // break;
    // case 198:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon_3));
    // break;
    // case 199:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon_4));
    // break;
    // case 200:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon_5));
    // break;
    // case 104:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon));
    // break;
    // case 201:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon_2));
    // break;
    // case 202:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon_3));
    // break;
    // case 203:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon_4));
    // break;
    // case 204:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.dagon_5));
    // break;
    // case 105:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.necronomicon));
    // break;
    // case 191:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.necronomicon_2));
    // break;
    // case 192:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.necronomicon_3));
    // break;
    // case 106:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.necronomicon));
    // break;
    // case 193:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.necronomicon_2));
    // break;
    // case 194:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.necronomicon_3));
    // break;
    // case 107:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ultimate_scepter));
    // break;
    // case 108:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ultimate_scepter));
    // break;
    // case 109:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.refresher));
    // break;
    // case 110:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.refresher));
    // break;
    // case 111:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.assault));
    // break;
    // case 112:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.assault));
    // break;
    // case 113:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.heart));
    // break;
    // case 114:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.heart));
    // break;
    // case 115:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.black_king_bar));
    // break;
    // case 116:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.black_king_bar));
    // break;
    // case 117:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.aegis));
    // break;
    // case 118:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.shivas_guard));
    // break;
    // case 119:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.shivas_guard));
    // break;
    // case 120:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.bloodstone));
    // break;
    // case 121:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.bloodstone));
    // break;
    // case 122:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.sphere));
    // break;
    // case 123:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.sphere));
    // break;
    // case 124:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.vanguard));
    // break;
    // case 125:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.vanguard));
    // break;
    // case 126:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.blade_mail));
    // break;
    // case 127:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.blade_mail));
    // break;
    // case 128:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.soul_booster));
    // break;
    // case 129:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.soul_booster));
    // break;
    // case 130:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hood_of_defiance));
    // break;
    // case 131:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.hood_of_defiance));
    // break;
    // case 132:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.rapier));
    // break;
    // case 133:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.rapier));
    // break;
    // case 134:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.monkey_king_bar));
    // break;
    // case 135:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.monkey_king_bar));
    // break;
    // case 136:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.radiance));
    // break;
    // case 137:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.radiance));
    // break;
    // case 138:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.butterfly));
    // break;
    // case 139:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.butterfly));
    // break;
    // case 140:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.greater_crit));
    // break;
    // case 141:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.greater_crit));
    // break;
    // case 142:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.basher));
    // break;
    // case 143:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.basher));
    // break;
    // case 144:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.bfury));
    // break;
    // case 145:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.bfury));
    // break;
    // case 146:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.manta));
    // break;
    // case 147:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.manta));
    // break;
    // case 148:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.lesser_crit));
    // break;
    // case 149:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.lesser_crit));
    // break;
    // case 150:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.armlet));
    // break;
    // case 151:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.armlet));
    // break;
    // case 183:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.invis_sword));
    // break;
    // case 152:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.invis_sword));
    // break;
    // case 153:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.sange_and_yasha));
    // break;
    // case 154:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.sange_and_yasha));
    // break;
    // case 155:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.satanic));
    // break;
    // case 156:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.satanic));
    // break;
    // case 157:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mjollnir));
    // break;
    // case 158:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mjollnir));
    // break;
    // case 159:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.skadi));
    // break;
    // case 160:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.skadi));
    // break;
    // case 161:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.sange));
    // break;
    // case 162:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.sange));
    // break;
    // case 163:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.helm_of_the_dominator));
    // break;
    // case 164:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.helm_of_the_dominator));
    // break;
    // case 165:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.maelstrom));
    // break;
    // case 166:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.maelstrom));
    // break;
    // case 167:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.desolator));
    // break;
    // case 168:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.desolator));
    // break;
    // case 169:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.yasha));
    // break;
    // case 170:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.yasha));
    // break;
    // case 171:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mask_of_madness));
    // break;
    // case 172:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mask_of_madness));
    // break;
    // case 173:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.diffusal_blade));
    // break;
    // case 174:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.diffusal_blade));
    // break;
    // case 195:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.diffusal_blade_2));
    // break;
    // case 196:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.diffusal_blade_2));
    // break;
    // case 175:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ethereal_blade));
    // break;
    // case 176:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ethereal_blade));
    // break;
    // case 177:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.soul_ring));
    // break;
    // case 178:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.soul_ring));
    // break;
    // case 179:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.arcane_boots));
    // break;
    // case 180:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.arcane_boots));
    // break;
    // case 181:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.orb_of_venom));
    // break;
    // case 184:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ancient_janggo));
    // break;
    // case 185:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ancient_janggo));
    // break;
    // case 186:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.medallion_of_courage));
    // break;
    // case 187:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.medallion_of_courage));
    // break;
    // case 188:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.smoke_of_deceit));
    // break;
    // case 189:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.veil_of_discord));
    // break;
    // case 190:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.veil_of_discord));
    // break;
    // case 205:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.rod_of_atos));
    // break;
    // case 206:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.rod_of_atos));
    // break;
    // case 207:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.abyssal_blade));
    // break;
    // case 208:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.abyssal_blade));
    // break;
    // case 209:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.heavens_halberd));
    // break;
    // case 210:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.heavens_halberd));
    // break;
    // case 211:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ring_of_aquila));
    // break;
    // case 212:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.ring_of_aquila));
    // break;
    // case 213:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.tranquil_boots));
    // break;
    // case 214:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.tranquil_boots));
    // break;
    // case 215:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.shadow_amulet));
    // break;
    // case 216:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.halloween_candy_corn));
    // break;
    // case 217:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mystery_hook));
    // break;
    // case 218:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mystery_arrow));
    // break;
    // case 219:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mystery_missile));
    // break;
    // case 220:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mystery_toss));
    // break;
    // case 221:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.mystery_vacuum));
    // break;
    // case 226:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.halloween_rapier));
    // break;
    // case 228:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.greevil_whistle));
    // break;
    // case 235:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.greevil_whistle_toggle));
    // break;
    // case 227:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.present));
    // break;
    // case 229:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_stocking));
    // break;
    // case 230:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_skates));
    // break;
    // case 231:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_cake));
    // break;
    // case 232:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_cookie));
    // break;
    // case 233:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_coco));
    // break;
    // case 234:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_ham));
    // break;
    // case 236:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_kringle));
    // break;
    // case 237:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_mushroom));
    // break;
    // case 238:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_greevil_treat));
    // break;
    // case 239:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_greevil_garbage));
    // break;
    // case 240:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.winter_greevil_chewy));
    // break;
    // case 182:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.stout_shield));
    // break;
    // case 0:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.default_item));
    // break;
    // default:
    // itemBg.setImageDrawable(_caller.getResources().getDrawable(
    // R.drawable.default_item));
    // break;
    // }
    // }

    public static String getItemName(int ID) {
        switch (ID) {
            case 1:
                return "blink";
            case 2:
                return "blades_of_attack";
            case 3:
                return "broadsword";
            case 4:
                return "chainmail";
            case 5:
                return "claymore";
            case 6:
                return "helm_of_iron_will";
            case 7:
                return "javelin";
            case 8:
                return "mithril_hammer";
            case 9:
                return "platemail";
            case 10:
                return "quarterstaff";
            case 11:
                return "quelling_blade";
            case 12:
                return "ring_of_protection";
            case 13:
                return "gauntlets";
            case 14:
                return "slippers";
            case 15:
                return "mantle";
            case 16:
                return "branches";
            case 17:
                return "belt_of_strength";
            case 18:
                return "boots_of_elves";
            case 19:
                return "robe";
            case 20:
                return "circlet";
            case 21:
                return "ogre_axe";
            case 22:
                return "blade_of_alacrity";
            case 23:
                return "staff_of_wizardry";
            case 24:
                return "ultimate_orb";
            case 25:
                return "gloves";
            case 26:
                return "lifesteal";
            case 27:
                return "ring_of_regen";
            case 28:
                return "sobi_mask";
            case 29:
                return "boots";
            case 30:
                return "gem";
            case 31:
                return "cloak";
            case 32:
                return "talisman_of_evasion";
            case 33:
                return "cheese";
            case 34:
                return "magic_stick";
            case 36:
                return "magic_wand";
            case 37:
                return "ghost";
            case 38:
                return "clarity";
            case 39:
                return "flask";
            case 40:
                return "dust";
            case 41:
                return "bottle";
            case 42:
                return "ward_observer";
            case 43:
                return "ward_sentry";
            case 44:
                return "tango";
            case 45:
                return "courier";
            case 46:
                return "tpscroll";
            case 48:
                return "travel_boots";
            case 50:
                return "phase_boots";
            case 51:
                return "demon_edge";
            case 52:
                return "eagle";
            case 53:
                return "reaver";
            case 54:
                return "relic";
            case 55:
                return "hyperstone";
            case 56:
                return "ring_of_health";
            case 57:
                return "void_stone";
            case 58:
                return "mystic_staff";
            case 59:
                return "energy_booster";
            case 60:
                return "point_booster";
            case 61:
                return "vitality_booster";
            case 63:
                return "power_treads";
            case 65:
                return "hand_of_midas";
            case 67:
                return "oblivion_staff";
            case 69:
                return "pers";
            case 71:
                return "poor_mans_shield";
            case 73:
                return "bracer";
            case 75:
                return "wraith_band";
            case 77:
                return "null_talisman";
            case 79:
                return "mekansm";
            case 81:
                return "vladmir";
            case 84:
                return "flying_courier";
            case 86:
                return "buckler";
            case 88:
                return "ring_of_basilius";
            case 90:
                return "pipe";
            case 92:
                return "urn_of_shadows";
            case 94:
                return "headdress";
            case 96:
                return "sheepstick";
            case 98:
                return "orchid";
            case 100:
                return "cyclone";
            case 102:
                return "force_staff";
            case 104:
                return "dagon";
            case 106:
                return "necronomicon";
            case 108:
                return "ultimate_scepter";
            case 110:
                return "refresher";
            case 112:
                return "assault";
            case 114:
                return "heart";
            case 116:
                return "black_king_bar";
            case 117:
                return "aegis";
            case 119:
                return "shivas_guard";
            case 121:
                return "bloodstone";
            case 123:
                return "sphere";
            case 125:
                return "vanguard";
            case 127:
                return "blade_mail";
            case 129:
                return "soul_booster";
            case 131:
                return "hood_of_defiance";
            case 133:
                return "rapier";
            case 135:
                return "monkey_king_bar";
            case 137:
                return "radiance";
            case 139:
                return "butterfly";
            case 141:
                return "greater_crit";
            case 143:
                return "basher";
            case 145:
                return "bfury";
            case 147:
                return "manta";
            case 149:
                return "lesser_crit";
            case 151:
                return "armlet";
            case 152:
                return "invis_sword";
            case 154:
                return "sange_and_yasha";
            case 156:
                return "satanic";
            case 158:
                return "mjollnir";
            case 160:
                return "skadi";
            case 162:
                return "sange";
            case 164:
                return "helm_of_the_dominator";
            case 166:
                return "maelstrom";
            case 168:
                return "desolator";
            case 170:
                return "yasha";
            case 172:
                return "mask_of_madness";
            case 174:
                return "diffusal_blade";
            case 176:
                return "ethereal_blade";
            case 178:
                return "soul_ring";
            case 180:
                return "arcane_boots";
            case 181:
                return "orb_of_venom";
            case 182:
                return "stout_shield";
            case 185:
                return "ancient_janggo";
            case 187:
                return "medallion_of_courage";
            case 188:
                return "smoke_of_deceit";
            case 190:
                return "veil_of_discord";
            case 193:
                return "necronomicon_2";
            case 194:
                return "necronomicon_3";
            case 196:
                return "diffusal_blade_2";
            case 201:
                return "dagon_2";
            case 202:
                return "dagon_3";
            case 203:
                return "dagon_4";
            case 204:
                return "dagon_5";
            case 206:
                return "rod_of_atos";
            case 208:
                return "abyssal_blade";
            case 210:
                return "heavens_halberd";
            case 212:
                return "ring_of_aquila";
            case 214:
                return "tranquil_boots";
            case 215:
                return "shadow_amulet";
            case 216:
                return "enchanted_mango";
            case 218:
                return "ward_dispenser";
            case 220:
                return "travel_boots_2";
            case 226:
                return "lotus_orb";
            case 229:
                return "solar_crest";
            case 231:
                return "guardian_greaves";
            case 232:
                return "aether_lens";
            case 235:
                return "octarine_core";
            case 236:
                return "dragon_lance";
            case 237:
                return "faerie_fire";
            case 239:
                return "iron_talon";
            case 240:
                return "blight_stone";
            case 241:
                return "tango_single";
            case 242:
                return "crimson_guard";
            case 244:
                return "wind_lace";
            case 247:
                return "moon_shard";
            case 249:
                return "silver_edge";
            case 250:
                return "bloodthorn";
            case 252:
                return "echo_sabre";
            case 254:
                return "glimmer_cape";
            case 257:
                return "tome_of_knowledge";
            case 263:
                return "hurricane_pike";
            case 265:
                return "infused_raindrop";
            case 1021:
                return "river_painter";
            case 1022:
                return "river_painter2";
            case 1023:
                return "river_painter3";
            case 1024:
                return "river_painter4";
            case 1025:
                return "river_painter5";
            case 1026:
                return "river_painter6";
            case 1027:
                return "river_painter7";
            default:
                return "default";
        }
    }

    /**
     * 获取英雄英文名（获取keyName）
     *
     * @param ID 英雄ID
     * @return
     */
    public static String getHeroName(int ID) {
        switch (ID) {
            case 1:
                return "antimage";
            case 2:
                return "axe";
            case 3:
                return "bane";
            case 4:
                return "bloodseeker";
            case 5:
                return "crystal_maiden";
            case 6:
                return "drow_ranger";
            case 7:
                return "earthshaker";
            case 8:
                return "juggernaut";
            case 9:
                return "mirana";
            case 10:
                return "morphling";
            case 11:
                return "nevermore";
            case 12:
                return "phantom_lancer";
            case 13:
                return "puck";
            case 14:
                return "pudge";
            case 15:
                return "razor";
            case 16:
                return "sand_king";
            case 17:
                return "storm_spirit";
            case 18:
                return "sven";
            case 19:
                return "tiny";
            case 20:
                return "vengefulspirit";
            case 21:
                return "windrunner";
            case 22:
                return "zuus";
            case 23:
                return "kunkka";
            case 25:
                return "lina";
            case 26:
                return "lion";
            case 27:
                return "shadow_shaman";
            case 28:
                return "slardar";
            case 29:
                return "tidehunter";
            case 30:
                return "witch_doctor";
            case 31:
                return "lich";
            case 32:
                return "riki";
            case 33:
                return "enigma";
            case 34:
                return "tinker";
            case 35:
                return "sniper";
            case 36:
                return "necrolyte";
            case 37:
                return "warlock";
            case 38:
                return "beastmaster";
            case 39:
                return "queenofpain";
            case 40:
                return "venomancer";
            case 41:
                return "faceless_void";
            case 42:
                return "skeleton_king";
            case 43:
                return "death_prophet";
            case 44:
                return "phantom_assassin";
            case 45:
                return "pugna";
            case 46:
                return "templar_assassin";
            case 47:
                return "viper";
            case 48:
                return "luna";
            case 49:
                return "dragon_knight";
            case 50:
                return "dazzle";
            case 51:
                return "rattletrap";
            case 52:
                return "leshrac";
            case 53:
                return "furion";
            case 54:
                return "life_stealer";
            case 55:
                return "dark_seer";
            case 56:
                return "clinkz";
            case 57:
                return "omniknight";
            case 58:
                return "enchantress";
            case 59:
                return "huskar";
            case 60:
                return "night_stalker";
            case 61:
                return "broodmother";
            case 62:
                return "bounty_hunter";
            case 63:
                return "weaver";
            case 64:
                return "jakiro";
            case 65:
                return "batrider";
            case 66:
                return "chen";
            case 67:
                return "spectre";
            case 68:
                return "ancient_apparition";
            case 69:
                return "doom_bringer";
            case 70:
                return "ursa";
            case 71:
                return "spirit_breaker";
            case 72:
                return "gyrocopter";
            case 73:
                return "alchemist";
            case 74:
                return "invoker";
            case 75:
                return "silencer";
            case 76:
                return "obsidian_destroyer";
            case 77:
                return "lycan";
            case 78:
                return "brewmaster";
            case 79:
                return "shadow_demon";
            case 80:
                return "lone_druid";
            case 81:
                return "chaos_knight";
            case 82:
                return "meepo";
            case 83:
                return "treant";
            case 84:
                return "ogre_magi";
            case 85:
                return "undying";
            case 86:
                return "rubick";
            case 87:
                return "disruptor";
            case 88:
                return "nyx_assassin";
            case 89:
                return "naga_siren";
            case 90:
                return "keeper_of_the_light";
            case 91:
                return "wisp";
            case 92:
                return "visage";
            case 93:
                return "slark";
            case 94:
                return "medusa";
            case 95:
                return "troll_warlord";
            case 96:
                return "centaur";
            case 97:
                return "magnataur";
            case 98:
                return "shredder";
            case 99:
                return "bristleback";
            case 100:
                return "tusk";
            case 101:
                return "skywrath_mage";
            case 102:
                return "abaddon";
            case 103:
                return "elder_titan";
            case 104:
                return "legion_commander";
            case 105:
                return "techies";
            case 106:
                return "ember_spirit";
            case 107:
                return "earth_spirit";
            case 108:
                return "abyssal_underlord";
            case 109:
                return "terrorblade";
            case 110:
                return "phoenix";
            case 111:
                return "oracle";
            case 112:
                return "winter_wyvern";
            case 113:
                return "arc_warden";
            case 114:
                return "monkey_king";
            default:
                return "default";
        }
    }

    /**
     * 获取英雄英文名（获取keyName）
     *
     * @param chineseName 英雄中文名
     * @return
     */
    public static String getHeroName(String chineseName) {
        switch (chineseName) {
            case "敌法师":
                return "antimage";
            case "斧王":
                return "axe";
            case "祸乱之源":
                return "bane";
            case "嗜血狂魔":
                return "bloodseeker";
            case "水晶室女":
                return "crystal_maiden";
            case "卓尔游侠":
                return "drow_ranger";
            case "撼地者":
                return "earthshaker";
            case "主宰":
                return "juggernaut";
            case "米拉娜":
                return "mirana";
            case "变体精灵":
                return "morphling";
            case "影魔":
                return "nevermore";
            case "幻影长矛手":
                return "phantom_lancer";
            case "帕克":
                return "puck";
            case "帕吉":
                return "pudge";
            case "剃刀":
                return "razor";
            case "沙王":
                return "sand_king";
            case "风暴之灵":
                return "storm_spirit";
            case "斯温":
                return "sven";
            case "小小":
                return "tiny";
            case "复仇之魂":
                return "vengefulspirit";
            case "风行者":
                return "windrunner";
            case "宙斯":
                return "zuus";
            case "昆卡":
                return "kunkka";
            case "莉娜":
                return "lina";
            case "莱恩":
                return "lion";
            case "暗影萨满":
                return "shadow_shaman";
            case "斯拉达":
                return "slardar";
            case "潮汐猎人":
                return "tidehunter";
            case "巫医":
                return "witch_doctor";
            case "巫妖":
                return "lich";
            case "力丸":
                return "riki";
            case "谜团":
                return "enigma";
            case "修补匠":
                return "tinker";
            case "狙击手":
                return "sniper";
            case "瘟疫法师":
                return "necrolyte";
            case "术士":
                return "warlock";
            case "兽王":
                return "beastmaster";
            case "痛苦女王":
                return "queenofpain";
            case "剧毒术士":
                return "venomancer";
            case "虚空假面":
                return "faceless_void";
            case "冥魂大帝":
                return "skeleton_king";
            case "死亡先知":
                return "death_prophet";
            case "幻影刺客":
                return "phantom_assassin";
            case "帕格纳":
                return "pugna";
            case "圣堂刺客":
                return "templar_assassin";
            case "冥界亚龙":
                return "viper";
            case "露娜":
                return "luna";
            case "龙骑士":
                return "dragon_knight";
            case "戴泽":
                return "dazzle";
            case "发条技师":
                return "rattletrap";
            case "拉席克":
                return "leshrac";
            case "先知":
                return "furion";
            case "噬魂鬼":
                return "life_stealer";
            case "黑暗贤者":
                return "dark_seer";
            case "克林克兹":
                return "clinkz";
            case "全能骑士":
                return "omniknight";
            case "魅惑魔女":
                return "enchantress";
            case "哈斯卡":
                return "huskar";
            case "暗夜魔王":
                return "night_stalker";
            case "育母蜘蛛":
                return "broodmother";
            case "赏金猎人":
                return "bounty_hunter";
            case "编织者":
                return "weaver";
            case "杰奇洛":
                return "jakiro";
            case "蝙蝠骑士":
                return "batrider";
            case "陈":
                return "chen";
            case "幽鬼":
                return "spectre";
            case "远古冰魄":
                return "ancient_apparition";
            case "末日使者":
                return "doom_bringer";
            case "熊战士":
                return "ursa";
            case "裂魂人":
                return "spirit_breaker";
            case "矮人直升机":
                return "gyrocopter";
            case "炼金术士":
                return "alchemist";
            case "祈求者":
                return "invoker";
            case "沉默术士":
                return "silencer";
            case "殁境神蚀者":
                return "obsidian_destroyer";
            case "狼人":
                return "lycan";
            case "酒仙":
                return "brewmaster";
            case "暗影恶魔":
                return "shadow_demon";
            case "德鲁伊":
                return "lone_druid";
            case "混沌骑士":
                return "chaos_knight";
            case "米波":
                return "meepo";
            case "树精卫士":
                return "treant";
            case "食人魔魔法师":
                return "ogre_magi";
            case "不朽尸王":
                return "undying";
            case "拉比克":
                return "rubick";
            case "干扰者":
                return "disruptor";
            case "司夜刺客":
                return "nyx_assassin";
            case "娜迦海妖":
                return "naga_siren";
            case "光之守卫":
                return "keeper_of_the_light";
            case "艾欧":
                return "wisp";
            case "维萨吉":
                return "visage";
            case "斯拉克":
                return "slark";
            case "美杜莎":
                return "medusa";
            case "巨魔战将":
                return "troll_warlord";
            case "半人马战行者":
                return "centaur";
            case "马格纳斯":
                return "magnataur";
            case "伐木机":
                return "shredder";
            case "钢背兽":
                return "bristleback";
            case "巨牙海民":
                return "tusk";
            case "天怒法师":
                return "skywrath_mage";
            case "亚巴顿":
                return "abaddon";
            case "上古巨神":
                return "elder_titan";
            case "军团指挥官":
                return "legion_commander";
            case "工程师":
                return "techies";
            case "灰烬之灵":
                return "ember_spirit";
            case "大地之灵":
                return "earth_spirit";
            case "孽主":
                return "abyssal_underlord";
            case "恐怖利刃":
                return "terrorblade";
            case "凤凰":
                return "phoenix";
            case "神谕者":
                return "oracle";
            case "寒冬飞龙":
                return "winter_wyvern";
            case "天穹守望者":
                return "arc_warden";
            case "齐天大圣":
                return "monkey_king";
            default:
                return "default";
        }
    }

    public static String getMmrLevel(String mmr) {
        if (TextUtils.isEmpty(mmr)) {
            return DotaApplication.getApplication().getString(R.string.rank_level_unknow);
        }
        int score = Integer.parseInt(mmr);
        if (score >= 0 && score <= 3200) {
            return DotaApplication.getApplication().getString(R.string.rank_level_n);
        } else if (score > 3200 && score <= 3800) {
            return DotaApplication.getApplication().getString(R.string.rank_level_h);
        } else {
            return DotaApplication.getApplication().getString(R.string.rank_level_vh);
        }
    }

    public static boolean getMatchResult(int playerSlot, boolean radiantWin) {
        if (playerSlot <= 4) {
            if (radiantWin) {
                return true;
            } else {
                return false;
            }
        } else {
            if (radiantWin) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static String getLobbyTypeName(int lobbyType) {
        switch (lobbyType) {
            case LobbyType.LOBBY_TYPE_NORMAL:
                return DotaApplication.getApplication().getString(R.string.lobby_type_normal);
            case LobbyType.LOBBY_TYPE_PRACTICE:
                return DotaApplication.getApplication().getString(R.string.lobby_type_practice);
            case LobbyType.LOBBY_TYPE_TOURNAMENT:
                return DotaApplication.getApplication().getString(R.string.lobby_type_tournament);
            case LobbyType.LOBBY_TYPE_TUTORIAL:
                return DotaApplication.getApplication().getString(R.string.lobby_type_tutorial);
            case LobbyType.LOBBY_TYPE_COOP_BOTS:
                return DotaApplication.getApplication().getString(R.string.lobby_type_coop_bots);
            case LobbyType.LOBBY_TYPE_RANKED_TEAM_MM:
                return DotaApplication.getApplication().getString(R.string.lobby_type_ranked_team_mm);
            case LobbyType.LOBBY_TYPE_RANKED_SOLO_MM:
                return DotaApplication.getApplication().getString(R.string.lobby_type_ranked_solo_mm);
            case LobbyType.LOBBY_TYPE_RANKED:
                return DotaApplication.getApplication().getString(R.string.lobby_type_ranked);
            case LobbyType.LOBBY_TYPE_1V1_MID:
                return DotaApplication.getApplication().getString(R.string.lobby_type_1v1_mid);
            case LobbyType.LOBBY_TYPE_BATTLE_CUP:
                return DotaApplication.getApplication().getString(R.string.lobby_type_battle_cup);
            default:
                return DotaApplication.getApplication().getString(R.string.unknow);
        }
    }
}

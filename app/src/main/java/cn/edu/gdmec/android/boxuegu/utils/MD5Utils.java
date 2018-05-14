package cn.edu.gdmec.android.boxuegu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by student on 17/12/27.
 */

public class MD5Utils {
    /**
     * md5 加密算法
     * @param text
     * @return
     */
    public static String md5(String text){
        try{
            //拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest digest = MessageDigest.getInstance("md5");
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] result = digest.digest(text.getBytes());
            //StringBuilder是一个类，可以用来处理字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : result){
                //0xFF (十进制1),转换成int计算
                int number = b & 0xff;
                //一个整数被转换为一个字符串.
                String hex = Integer.toHexString(number);
                if (hex.length() == 1){
                    sb.append("0" + hex);
                }else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            return "";
        }
    }
}

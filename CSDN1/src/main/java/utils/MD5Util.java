package utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: Starry.Teng
 * Email: tengxing7452@163.com
 * Date: 17-10-10
 * Time: 下午9:27
 * Describe: MD5加密工具类
 */
public class MD5Util {

    /**
     * 普通md5加密
     * @param text
     * @return
     */
    public static String getMD5(String text){
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] buffer = digest.digest(text.getBytes());
            // byte -128 ---- 127
            StringBuffer sb = new StringBuffer();
            for (byte b : buffer) {
                int a = b & 0xff;
                // Log.d(TAG, "" + a);
                String hex = Integer.toHexString(a);

                if (hex.length() == 1) {
                    hex = 0 + hex;
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * md5加密+salt
     * @param text
     * @return
     */
    public static String getMD5(String text,String salt){
        try {
            return getMD5(getMD5(text)+getMD5(salt)); //分别加密在加密
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 任意文件转换成Md5
     * @param in
     * @return
     */
    public static String getMD5(InputStream in) {
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] bytes = new byte[8192];
            int byteCount;
            while ((byteCount = in.read(bytes)) > 0) {
                digester.update(bytes, 0, byteCount);
            }
            byte[] digest = digester.digest();

            // byte -128 ---- 127
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                int a = b & 0xff;
                String hex = Integer.toHexString(a);

                if (hex.length() == 1) {
                    hex = 0 + hex;
                }

                sb.append(hex);
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
        }
        return null;
    }

    public static String decrypt(){
        return null;
    }

    // 测试主函数
    public static void main(String args[]) {
        String s = new String("tengxing");
        System.out.println(getMD5(s));
        System.out.println(getMD5("同学"));
        System.out.println(getMD5(s+"同学"));

    }


}

package net.sebinson.sample.web.mis.common.tools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.alibaba.fastjson.util.IOUtils;

public class CodeUtil {

    public String nextCode(String currCode, int unit) {

        return null;
    }

    public static void main1(String[] args) {

        String strDefalt = "";
        String strDefaltEncode = "";
        String strUTF8Encode = "";
        String strChangeEncode = "";
        try {
            strDefalt = "我心不乱";
            strDefaltEncode = new String("我心不乱".getBytes(), "GBK");
            strUTF8Encode = new String(strDefalt.getBytes("GBK"), "GBK");
            strChangeEncode = new String("我心不乱".getBytes("shift-jis"), "shift-jis");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Default charsetName=" + Charset.defaultCharset().name());
        System.out.println("strDefalt=" + strDefalt);
        System.out.println("strDefaltEncode=" + strDefaltEncode);
        System.out.println("strUTF8Encode=" + strUTF8Encode);
        System.out.println("strChangeEncode=" + strChangeEncode);
        System.out.println();
    }

    public static void main2(String[] args) {

        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File("d:\\test.txt");
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            osw = new OutputStreamWriter(bos, "UTF-8");
            osw.write("我心不乱");
        } catch (IOException ioe) {
            System.out.println(ioe.getStackTrace());
        } finally {
            if (osw != null) {
                IOUtils.close(osw);
            }
            if (bos != null) {
                IOUtils.close(bos);
            }
            if (fos != null) {
                IOUtils.close(fos);
            }
            System.out.println("i coming");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        try {
            File file = new File("d:\\test.txt");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "GBK");
            BufferedReader br = new BufferedReader(isr);
            System.out.println(br.readLine());
            br.close();
            isr.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getStackTrace());
        }
        System.out.println();
    }
}

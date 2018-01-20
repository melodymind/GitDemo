package com.example.demo;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sx on 2018/1/18.
 */
public class IsolationTest {

    /**
     * 求和
     * @param inputData
     * @return
     */
    public static double getSum(double[] inputData) {
        if (inputData == null || inputData.length == 0)
            return -1;
        int len = inputData.length;
        double sum = 0;
        for (int i = 0; i < len; i++) {
            sum = sum + inputData[i];
        }
        return sum;
    }

    /**
     * 计算长度
     * @param inputData
     * @return
     */
    public static int getCount(double[] inputData) {
        if (inputData == null)
            return -1;
        return inputData.length;
    }

    /**
     * 求平方和
     * @param inputData
     * @return
     */
    public static double getSquareSum(double[] inputData) {
        if(inputData==null||inputData.length==0)
            return -1;
        int len=inputData.length;
        double sqrsum = 0.0;
        for (int i = 0; i <len; i++) {
            sqrsum = sqrsum + inputData[i] * inputData[i];
        }
        return sqrsum;
    }

    /**
     * 求平均数
     * @param inputData
     * @return
     */
    public static double getAverage(double[] inputData) {
        if (inputData == null || inputData.length == 0)
            return -1;
        int len = inputData.length;
        double result;
        result = getSum(inputData) / len;

        return result;
    }

    /**
     * 求方差
     * @param inputData
     * @return
     */
    public static double getVariance(double[] inputData) {
        int count = getCount(inputData);
        double sqrsum = getSquareSum(inputData);
        double average = getAverage(inputData);
        double result;
        result = (sqrsum - count * average * average) / count;
        return result;
    }

    /**
     * 返回整型的标准差
     * @param inputData
     * @return
     */
    public static double getStandardDeviation(double[] inputData) {
        double result;
        //绝对值化很重要
        result = Math.sqrt(Math.abs(getVariance(inputData)));
        return result;
    }

    /**
     * 从文件中读取数据
     */
    public void readText(){
        String fileName="salesInfo.txt";
        String path="/Users/sx/Downloads/";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path+fileName));
            String line=null;
            int sku=-1;
            List<Integer> numList=new ArrayList<>();
            List<List<String>> sales=new LinkedList<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings=line.split(" ");
                List<String> list=new LinkedList<>();
                //list 索引位0：sku 1：日期 2：数量
                for (int i = 0; i <strings.length ; i++) {
                    if(!"".equals(strings[i])){
                        list.add(strings[i]);
                    }
                }
                //获取当前sku
                int skuTmp=Integer.parseInt(list.get(0));

                if(-1==sku){
                    sku=skuTmp;
                }else {
                    if(sku!=skuTmp){
                        findIsolation(numList,sales);
                        sku=skuTmp;
                        numList=new ArrayList<>();
                        sales=new LinkedList<>();
                    }
                }
                numList.add(Integer.parseInt(list.get(2)));
                sales.add(list);
            }
            findIsolation(numList,sales);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findIsolation(List<Integer> numList,List<List<String>> sales){
        int size=numList.size();
        double[] arr=new double[size];
        for (int i = 0; i <size ; i++)
            arr[i] = numList.get(i);
        double avg=getAverage(arr);
        double std=getStandardDeviation(arr);
        int maxPoint=(int)(avg+3*std);
        for (int i = 0; i <size ; i++){
            if(arr[i]>=maxPoint&&arr[i]>200){
                System.out.println(" sku: "+sales.get(i).get(0)+
                                    " date: "+sales.get(i).get(1)+
                                    " num: "+sales.get(i).get(2)+"-----avg: "+(int)avg+" std: "+(int)std+" maxPoint: "+maxPoint);
            }
        }
    }


    @Test
    public void test(){
        double[] arr={244, 161, 209, 117, 66, 7, 47, 74, 22, 45, 154, 41, 123, 52, 76, 60, 72, 67, 85, 69, 135, 76, 49, 70, 100, 60, 80, 73, 136, 102};
//        System.out.println(getAverage(arr));
        readText();

    }
}

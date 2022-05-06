package P1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;


public class MagicSquare {
    public static boolean generateMagicSquare(int n) {
        BufferedWriter br = null;
        try {
            if (n == 0) {
                System.out.println("n为0，不合法");
                return false;
            }
            br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src//P1//TXT//6.txt"), StandardCharsets.UTF_8));
            int magic[][] = new int[n][n];
            int row = 0, col = n / 2, i, j, square = n * n;
            for (i = 1; i <= square; i++) {
                magic[row][col] = i;
                if (i % n == 0)
                    row++;
                else {
                    if (row == 0)
                        row = n - 1;
                    else
                        row--;
                    if (col == (n - 1))
                        col = 0;
                    else
                        col++;
                }
            }
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    String s = String.valueOf(magic[i][j]);
                    br.write(s);
                    br.write('\t');
                    System.out.print(magic[i][j] + "\t");
                }
                br.newLine();
                System.out.println();
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("n为偶数，不合法");
            return false;
        } catch (NegativeArraySizeException e) {
            System.out.println("n为负数，不合法");
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MagicSquare ms = new MagicSquare();
        if (ms.isLegalMagicSquare("1.txt") == true) {
            System.out.println("1.txt是MagicSqure");
        } else {
            System.out.println("1.txt不是MagicSqure");
        }
        if (ms.isLegalMagicSquare("2.txt") == true) {
            System.out.println("2.txt是MagicSqure");
        } else {
            System.out.println("2.txt不是MagicSqure");
        }
        if (ms.isLegalMagicSquare("3.txt") == true) {
            System.out.println("3.txt是MagicSqure");
        } else {
            System.out.println("3.txt不是MagicSqure");
        }
        if (ms.isLegalMagicSquare("4.txt") == true) {
            System.out.println("4.txt是MagicSqure");
        } else {
            System.out.println("4.txt不是MagicSqure");
        }
        if (ms.isLegalMagicSquare("5.txt") == true) {
            System.out.println("5.txt是MagicSqure");
        } else {
            System.out.println("5.txt不是MagicSqure");
        }
        System.out.print("请输入n的值:");
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        MagicSquare.generateMagicSquare(n);
        if (ms.isLegalMagicSquare("6.txt") == true) {
            System.out.println("6.txt是MagicSqure");
        } else {
            System.out.println("6.txt不是MagicSqure");
        }
    }

    public boolean isLegalMagicSquare(String fileName) throws IOException {
        String filePath = "src//P1//TXT//" + fileName;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
        ArrayList<String> arr = new ArrayList<>();
        String s = null;
        int row = 0;
        int list = 0;
        int temp = 0;
        int sum1 = 0;//行
        int sum3 = 0;//正斜
        int sum4 = 0;//反斜
        while ((s = br.readLine()) != null) {
            arr.add(s);
            row++;
        }
        int[] sum2 = new int[row];//列
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        for (int i = 0; i < row; i++) {
            String[] Sarr = arr.get(i).split("\t");
            for (int j = 0; j < Sarr.length; j++) {
                if (!pattern.matcher(Sarr[j]).matches()) {
                    System.out.println(fileName + "数字格式错误");
                    br.close();
                    return false;
                }
                sum2[j] += Integer.parseInt(Sarr[j]);
                if (j == i) {
                    sum3 += Integer.parseInt(Sarr[j]);
                }
                if (i + j == row - 1) {
                    sum4 += Integer.parseInt(Sarr[j]);
                }
                if (i == 0) {
                    sum1 += Integer.parseInt(Sarr[j]);
                } else {
                    temp += Integer.parseInt(Sarr[j]);
                }
            }
            if (i == 0) {
                list = Sarr.length;
            } else {
                if (list != Sarr.length) {
                    System.out.println(fileName + "并非矩阵");
                    br.close();
                    return false;
                }
                if (sum1 != temp) {
                    br.close();
                    return false;
                }
                temp = 0;
            }
        }
        if (list != row) {
            System.out.println(fileName + "行列数不相等");
            br.close();
            return false;
        }
        for (int i = 0; i < sum2.length; i++) {
            if (sum2[i] != sum2[0]) {
                br.close();
                return false;
            }
        }
        if (sum1 != sum2[0]) {
            br.close();
            return false;
        } else if (sum1 != sum3) {
            br.close();
            return false;
        } else if (sum1 != sum4) {
            br.close();
            return false;
        }
        return true;
    }
}

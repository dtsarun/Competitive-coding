package com.projecteuler;

import java.math.BigInteger;

public class Euler416 {

    private static final int M = 10;

    private static final BigInteger N = new BigInteger("1000000000000");

    private static final int T = M*2;

    private static final int MATRIX_SIZE = (T+1)*(T+2);

    private static final long MOD_VAL = 1000000000;

    private static int cnt = 0;
    
    public static void main(String[] args) {
         long[][] A = new long[T+1][T+1];

         long[][] B = new long[T+1][T+1];

         long[][] TMatrix ;

         long[] resMat = new long[MATRIX_SIZE];


         resMat = initializeMatrix();
//         printArr(resMat);

         TMatrix = constructBaseMatrix();
//         System.out.println("**********");
//         printArr(TMatrix,MATRIX_SIZE,MATRIX_SIZE);
//         System.out.println();
//         printArr(constructShiftMatrix(),MATRIX_SIZE,MATRIX_SIZE);
//         System.out.println();
//         printArr(matrixMul2Dx2D(constructShiftMatrix(),TMatrix,
//                 MATRIX_SIZE,MATRIX_SIZE,MATRIX_SIZE,MATRIX_SIZE),
//                 MATRIX_SIZE,MATRIX_SIZE);
         TMatrix = matrixMul2Dx2D(constructShiftMatrix(),TMatrix,
                 MATRIX_SIZE,MATRIX_SIZE,MATRIX_SIZE,MATRIX_SIZE);
//         for(int i=4;i<N;i++)
//             resMat =
matrixMul2Dx1D(TMatrix,resMat,MATRIX_SIZE,MATRIX_SIZE,MATRIX_SIZE);
         System.out.println("calculating matpow");
         TMatrix =
matPow(TMatrix,MATRIX_SIZE,MATRIX_SIZE,N.subtract(new
BigInteger("4")));
         resMat =
matrixMul2Dx1D(TMatrix,resMat,MATRIX_SIZE,MATRIX_SIZE,MATRIX_SIZE);

//         System.out.print("F(" + M +"," + N + ") = ");

         long sum = 0;
         for(int i=0;i<MATRIX_SIZE;i++)
             sum = modSum(sum,resMat[i]);
         System.out.println("F("+M+","+N+") = "+sum);


    }

    private static long[][] matPow(long[][] a,int x,int y,BigInteger n) {
//        System.out.println("^^^^^^^^^^");
        long[][] res = a;
        long f = 1;
        BigInteger bf = new BigInteger("1");
        if(n.equals(new BigInteger("1")) ||
                bf.equals(new BigInteger("0"))) return a;

        while(true) {
            if(bf.multiply(new BigInteger("2")).compareTo(n)<0) {
                bf = bf.multiply(new BigInteger("2"));
                res = matrixMul2Dx2D(res,res,x,y,x,y);
//                System.out.println("bf "+bf);
            }
            else {
                res = matrixMul2Dx2D(res,matPow(a,x,y,n.subtract(bf)),x,y,x,y);
                break;
            }
            System.out.println(n+","+cnt++);

        }
        return res;
    }


    private void printArr(long a[]) {
        System.out.println();
        for(int i=0;i<MATRIX_SIZE;i++)
            System.out.println(a[i]);
    }

private static long[] initializeMatrix() {
    long[] resMat = new long[MATRIX_SIZE];
    for(int i=0;i<T+1;i++) {
        for(int j=0;j<T+1;j++) {
           if(j>=i) {
            resMat[getij(i,j,1)] = 0;
            resMat[getij(i,j,0)] = modProd(choose(T,i),choose(T-i,j-i));
            resMat[getij(i,j,0)] = modProd(resMat[getij(i,j,0)],modPow(2,i));
            if(i==0 || i==j) {
                resMat[getij(i,j,1)] = modProd(choose(T,i),choose(T-i,j-i));
                resMat[getij(i,j,0)]-=  resMat[getij(i,j,1)];
            }
            if(i==0 && j==0)
                    resMat[getij(i,j,1)] = 0;
//            System.out.println("("+i+","+j +"," + getij(i,j,0)+ ")"
//                    + " A " +resMat[getij(i,j,0)] + " B " +
//resMat[getij(i,j,1)]);
            }

        }
    }
//    System.out.println("&&&&&&&&");
//    printArr(resMat);
//    System.out.println("&&&&&&&&");
//    for(int i=0;i<T+1;i++)
//        for(int j=i;j<T+1;j++)
//            System.out.println("("+i+","+j +"," + getij(i,j,0)+ ","
//+ getij(i,j,1) + ")"
//                    + " A " +resMat[getij(i,j,0)] + " B " +
//resMat[getij(i,j,1)]);
//    System.out.println("&&&&&&&");
//    for(int i=0;i<MATRIX_SIZE;i++) {
//        System.out.println(resMat[i]);
//    }
//    System.out.println("0,2 " + getij(0,2,1)+"\n1,1 " + getij(1,1,1));
    return resMat;
    }

    private static int getij(int x,int y,int z) {
//        System.out.println("x = " + x + ",y = " + y + ",z = " +
//z+"("+MATRIX_SIZE+")" + "  " +
//                ((T*(T+1)/2-((T-x)*(T-x+1)/2)+y)+z*((T+1)*(T+2)/2)));
        return  ((T*(T+1)/2-((T-x)*(T-x+1)/2)+y)+z*((T+1)*(T+2)/2));
    }

    private static void initMat0(long a[][],int m,int n) {
        for(int i=0;i<m;i++)
            for(int j=0;j<n;j++)
                a[i][j] = 0;
    }



    private void printArr(long a[][],int m, int n) {
        for(int j=0;j<m;j++) {
            for(int i=0;i<n;i++) {
                System.out.print(""+a[i][j]+" ");
            }
            System.out.println();
        }
    }


    private static long choose(int n, int r) {
        long res = 1;
        for(int i=0;i<n-r;i++) {
            res  = res*(n-i);
        }
        for(int i=0;i<n-r;i++) {
            res  = res/(i+1);
        }
        return res;
    }

    private static long[][] constructBaseMatrix() {
        long[][] res = new long[MATRIX_SIZE][MATRIX_SIZE];
        initMat0(res,MATRIX_SIZE,MATRIX_SIZE);
        for(int m=0;m<T+1;m++)  {
            for(int n=m;n<T+1;n++) {
//                System.out.println("m,n = " + m + "," + n);
                for(int i= T-m; i<T+1;i++) {
                    for(int j= T-m; j<i+1; j++) {
                        int a = j+n-T;
                        int b = j-T+m;
                        int c = T-n+i-j;
                        int d = i-j;
                        int e = j-T+n;
                        int f = i;


res[getij(e,f,0)][getij(m,n,0)]=modProd(choose(a,b),choose(c,d));

res[getij(e,f,1)][getij(m,n,1)]=modProd(choose(a,b),choose(c,d));

                    }
                }

            }
        }

        return res;
    }

    private static long[][] constructShiftMatrix() {
        long[][] res = new long[MATRIX_SIZE][MATRIX_SIZE];
        initMat0(res,MATRIX_SIZE,MATRIX_SIZE);
        for(int i=0;i<MATRIX_SIZE;i++)
            res[i][i]=1;
        for(int i=0;i<T+1;i++) {
            for(int j=0;j<MATRIX_SIZE;j++) {
                res[j][(T+1)*(T+2)/2+i] = res[j][i];
                res[j][i]=0;
            }
        }
        return res;
    }



    private static long[][] matrixMul2Dx2D(long[][] a,long b[][],int m,int n,
            int o,int p) {
        long[][] res = new long[n][o];
        initMat0(res,n,o);
        for(int i=0;i<m;i++) {
            for(int j=0;j<p;j++) {
                for(int k=0;k<n;k++) {
                    res[j][i] = modSum(res[j][i],modProd(a[k][i],b[j][k]));
                }
            }
        }
        return res;
    }

    private static long[] matrixMul2Dx1D(long[][] a,long b[],int m,int n,int o) {
        long[] res = new long[n];
        for(int i=0;i<m;i++) {
            for(int k=0;k<n;k++) {
                res[i] = modSum(res[i],modProd(a[k][i],b[k])) ;
            }
        }
        return res;
    }



    private static long modSum(long a , long b) {
        return(a+b)%MOD_VAL;
    }

    private static long modProd(long a, long b) {
        return (a*b)%MOD_VAL;
    }

    private static long modPow(long a, long b) {
        if(b==0) return 1;
        return a*modPow(a,b-1)%MOD_VAL;
    }


}
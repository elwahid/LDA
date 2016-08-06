/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lda_classification;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author GITS
 */
public class LDA {
    void viewMatrik(double[][]matriks1)
    {
        for(int i=0;i<matriks1.length;i++)
        {
            for(int j=0;j<matriks1[0].length;j++)
            {
                System.out.print("  "+matriks1[i][j]);
            }
            System.out.println();
        }
    }
    
    double[][] hasilKaliMatriks(double [][]matriks1,double [][]matriks2){
        double hasil[][]=new double[matriks1.length][matriks2[0].length];
        for(int i=0;i<hasil.length;i++)
        {
            for(int j=0;j<hasil[0].length;j++)
            {
                for(int k=0;k<matriks1[0].length;k++)
                {
                    hasil[i][j]+=matriks1[i][k]*matriks2[k][j];
                }
            }
        }
        return (hasil);
    }
    double[][] hasilKaliMatriksKovarian(double [][]matriks1,double [][]matriks2,int jumlah){
        double hasil[][]=new double[matriks1.length][matriks2[0].length];
        for(int i=0;i<hasil.length;i++)
        {
            for(int j=0;j<hasil[0].length;j++)
            {
                for(int k=0;k<matriks1[0].length;k++)
                {
                    hasil[i][j]+=matriks1[i][k]*matriks2[k][j];
                }
                hasil[i][j]=hasil[i][j]/jumlah;
            }
        }
        return (hasil);
    }
    public double[] LDA_Classification(double [][]fitur,Integer []kelas,double [][]testing){
        double []hasilklasifikasi = new double[5];
        hasilklasifikasi[0]=0;
        if(fitur.length == kelas.length){
            //get unique class
            Set<Integer> uniqueClass = new HashSet<Integer>(Arrays.asList(kelas));
            DecimalFormat df = new DecimalFormat(".###");
            int []Class = new int[99]; //kelas
            int parameter=0;
            int baris1 = 0;
            int baris2 = 0;
            int baris3 = 0;
            int baris4 = 0;
            int kelas1 = 0;
            int kelas2 = 0;
            int kelas3 = 0;
            int kelas4 = 0;
            double P1 = 0; //prior probability vector
            double P2 = 0; //prior probability vector
            double P3 = 0; //prior probability vector
            double P4 = 0; //prior probability vector
            for(Integer x : uniqueClass) {
                Class[parameter] = x;
                parameter++;
            }
            for (int baris=0;baris<fitur.length;baris++){
                if(kelas[baris]==Class[0]){
                    kelas1++;
                }
                if(kelas[baris]==Class[1]){
                    kelas2++;
                }
                if(kelas[baris]==Class[2]){
                    kelas3++;
                }
                if(kelas[baris]==Class[3]){
                    kelas4++;
                }
            }
            double [][]x1 = new double[kelas1][fitur[0].length]; // kelompok data 1
            double [][]x2 = new double[kelas2][fitur[0].length]; // kelompok data 2
            double [][]x3 = new double[kelas3][fitur[0].length]; // kelompok data 3
            double [][]x4 = new double[kelas4][fitur[0].length]; // kelompok data 4
            double [][]xt = new double[testing[0].length][testing.length]; // transpose data
            double [][]xo1 = new double[kelas1][fitur[0].length]; // mean corrected kelompok data 1
            double [][]xo2 = new double[kelas2][fitur[0].length]; // mean corrected kelompok data 2
            double [][]xo3 = new double[kelas3][fitur[0].length]; // mean corrected kelompok data 3
            double [][]xo4 = new double[kelas4][fitur[0].length]; // mean corrected kelompok data 4
            double [][]xo1t = new double[fitur[0].length][kelas1]; // matrik transpose mean corrected kelompok data 1
            double [][]xo2t = new double[fitur[0].length][kelas2]; // matrik transpose mean corrected kelompok data 2
            double [][]xo3t = new double[fitur[0].length][kelas3]; // matrik transpose mean corrected kelompok data 3
            double [][]xo4t = new double[fitur[0].length][kelas4]; // matrik transpose mean corrected kelompok data 4
            double [][]u1 = new double[1][fitur[0].length]; //mean fitur kelompok 1
            double [][]u2 = new double[1][fitur[0].length]; //mean fitur kelompok 2
            double [][]u3 = new double[1][fitur[0].length]; //mean fitur kelompok 3
            double [][]u4 = new double[1][fitur[0].length]; //mean fitur kelompok 4
            double [][]u1t = new double[fitur[0].length][1]; //mean fitur transpose kelompok 1
            double [][]u2t = new double[fitur[0].length][1]; //mean fitur transpose kelompok 2
            double [][]u3t = new double[fitur[0].length][1]; //mean fitur transpose kelompok 3
            double [][]u4t = new double[fitur[0].length][1]; //mean fitur transpose kelompok 4
            double [][]u = new double[1][fitur[0].length]; ////mean keseluruhan
            double [][]C = new double[fitur[0].length][fitur[0].length]; // matrik kovarian
            double [][]CI = new double[fitur[0].length][fitur[0].length]; // matrik kovarian Inverse
            double [][]C1 = new double[fitur[0].length][fitur[0].length]; // matrik kovarian kelompok 1
            double [][]C2 = new double[fitur[0].length][fitur[0].length]; // matrik kovarian kelompok 2
            double [][]C3 = new double[fitur[0].length][fitur[0].length]; // matrik kovarian kelompok 1
            double [][]C4 = new double[fitur[0].length][fitur[0].length]; // matrik kovarian kelompok 2
            for (int baris=0;baris<fitur.length;baris++){
                if(kelas[baris]==Class[0]){
                    for(int kolom=0;kolom<fitur[baris].length;kolom++){
                        x1[baris1][kolom]=fitur[baris][kolom];
                        u1[0][kolom] = u1[0][kolom] + x1[baris1][kolom]; // penjumlahan data untuk mencari mean u1
                        u[0][kolom] = u[0][kolom] + fitur[baris][kolom]; // penjumlahan data untuk mencari mean u
                    }
                    baris1++;
                }
                if(kelas[baris]==Class[1]){
                    for(int kolom=0;kolom<fitur[baris].length;kolom++){
                        x2[baris2][kolom]=fitur[baris][kolom];
                        u2[0][kolom] = u2[0][kolom] + x2[baris2][kolom]; // penjumlahan data untuk mencari mean u2
                        u[0][kolom] = u[0][kolom] + fitur[baris][kolom]; // penjumlahan data untuk mencari mean u
                    }
                    baris2++;
                }
                if(kelas[baris]==Class[2]){
                    for(int kolom=0;kolom<fitur[baris].length;kolom++){
                        x3[baris3][kolom]=fitur[baris][kolom];
                        u3[0][kolom] = u3[0][kolom] + x3[baris3][kolom]; // penjumlahan data untuk mencari mean u3
                        u[0][kolom] = u[0][kolom] + fitur[baris][kolom]; // penjumlahan data untuk mencari mean u
                    }
                    baris3++;
                }
                if(kelas[baris]==Class[3]){
                    for(int kolom=0;kolom<fitur[baris].length;kolom++){
                        x4[baris4][kolom]=fitur[baris][kolom];
                        u4[0][kolom] = u4[0][kolom] + x4[baris4][kolom]; // penjumlahan data untuk mencari mean u4
                        u[0][kolom] = u[0][kolom] + fitur[baris][kolom]; // penjumlahan data untuk mencari mean u
                    }
                    baris4++;
                }
            }
            P1=Double.valueOf(df.format(Double.valueOf(baris1)/Double.valueOf(fitur.length)));
            P2=Double.valueOf(df.format(Double.valueOf(baris2)/Double.valueOf(fitur.length)));
            P3=Double.valueOf(df.format(Double.valueOf(baris3)/Double.valueOf(fitur.length)));
            P4=Double.valueOf(df.format(Double.valueOf(baris4)/Double.valueOf(fitur.length)));
            for(int kolom=0;kolom<fitur[0].length;kolom++){
                u[0][kolom]=Double.valueOf(df.format(u[0][kolom]/fitur.length)); // hasil penjumlahan u dibagi dengan jumlah seluruh data
                u1[0][kolom]=Double.valueOf(df.format(u1[0][kolom]/baris1)); // hasil penjumlahan u1 dibagi dengan jumlah kelompok data 1
                u2[0][kolom]=Double.valueOf(df.format(u2[0][kolom]/baris2)); // hasil penjumlahan u2 dibagi dengan jumlah kelompok data 2
                u3[0][kolom]=Double.valueOf(df.format(u3[0][kolom]/baris3)); // hasil penjumlahan u3 dibagi dengan jumlah kelompok data 3
                u4[0][kolom]=Double.valueOf(df.format(u4[0][kolom]/baris4)); // hasil penjumlahan u4 dibagi dengan jumlah kelompok data 4
            }
            System.out.println("DATA X3 = ");
            for (int baris=0;baris<baris3;baris++){
                for(int kolom=0;kolom<fitur[baris].length;kolom++)
                    System.out.print(x3[baris][kolom]+", ");
                System.out.println();
            }
            System.out.println("DATA X4 = ");
            for (int baris=0;baris<baris4;baris++){
                for(int kolom=0;kolom<fitur[baris].length;kolom++)
                    System.out.print(x4[baris][kolom]+", ");
                System.out.println();
            }
            
            //System.out.println("Mean X1 = "+u1[0][0]+","+u1[0][1]);
            //System.out.println("Mean X2 = "+u2[0][0]+","+u2[0][1]);
            //System.out.println("Mean X = "+u[0][0]+","+u[0][1]);
            
            //System.out.println("Mean Corrected X1 = ");
            for (int baris=0;baris<baris1;baris++){
                for(int kolom=0;kolom<fitur[baris].length;kolom++){
                    xo1[baris][kolom]=Double.valueOf(df.format(x1[baris][kolom]-u[0][kolom]));
                    //System.out.print(xo1[baris][kolom]+", ");
                }
                //System.out.println();
            }
            //System.out.println("Mean Corrected X2 = ");
            for (int baris=0;baris<baris2;baris++){
                for(int kolom=0;kolom<fitur[baris].length;kolom++){
                    xo2[baris][kolom]=Double.valueOf(df.format(x2[baris][kolom]-u[0][kolom]));
                    //System.out.print(xo2[baris][kolom]+", ");
                }
                //System.out.println();
            }
            System.out.println("Mean Corrected X3 = ");
            for (int baris=0;baris<baris3;baris++){
                for(int kolom=0;kolom<fitur[baris].length;kolom++){
                    xo3[baris][kolom]=Double.valueOf(df.format(x3[baris][kolom]-u[0][kolom]));
                    System.out.print(xo2[baris][kolom]+", ");
                }
                System.out.println();
            }
            System.out.println("Mean Corrected X4 = ");
            for (int baris=0;baris<baris4;baris++){
                for(int kolom=0;kolom<fitur[baris].length;kolom++){
                    xo4[baris][kolom]=Double.valueOf(df.format(x4[baris][kolom]-u[0][kolom]));
                    System.out.print(xo2[baris][kolom]+", ");
                }
                System.out.println();
            }
            //==================================================================
            //Hitung matriks transpose xo1
            //System.out.println("Transpose X1 = ");
            for (int j=0;j<fitur[0].length;j++){
                for (int i=0;i<baris1;i++){
                    xo1t[j][i]=xo1[i][j];
                    //System.out.print(xo1t[j][i]+", ");
                }
                //System.out.println();
            }
            //Hitung matriks transpose xo2
            //System.out.println("Transpose X2 = ");
            for (int j=0;j<fitur[0].length;j++){
                for (int i=0;i<baris2;i++){
                    xo2t[j][i]=xo2[i][j];
                    //System.out.print(xo2t[j][i]+", ");
                }
                //System.out.println();
            }
            //Hitung matriks transpose xo3
            System.out.println("Transpose X3 = ");
            for (int j=0;j<fitur[0].length;j++){
                for (int i=0;i<baris3;i++){
                    xo3t[j][i]=xo3[i][j];
                    System.out.print(xo2t[j][i]+", ");
                }
                System.out.println();
            }
            //Hitung matriks transpose xo4
            System.out.println("Transpose X4 = ");
            for (int j=0;j<fitur[0].length;j++){
                for (int i=0;i<baris4;i++){
                    xo4t[j][i]=xo4[i][j];
                    System.out.print(xo2t[j][i]+", ");
                }
                System.out.println();
            }
            //==================================================================
            //Hitung mean transpose u1
            for (int j=0;j<fitur[0].length;j++){
                for (int i=0;i<1;i++){
                    u1t[j][i]=u1[i][j];
                }
            }
            //Hitung mean transpose u2
            for (int j=0;j<fitur[0].length;j++){
                for (int i=0;i<1;i++){
                    u2t[j][i]=u2[i][j];
                }
            }
            //Hitung mean transpose u3
            for (int j=0;j<fitur[0].length;j++){
                for (int i=0;i<1;i++){
                    u3t[j][i]=u3[i][j];
                }
            }
            //Hitung mean transpose u4
            for (int j=0;j<fitur[0].length;j++){
                for (int i=0;i<1;i++){
                    u4t[j][i]=u4[i][j];
                }
            }
            //==================================================================
            //Hitung matriks transpose x
            /*for (int j=0;j<fitur[0].length;j++){
                for (int i=0;i<fitur.length;i++){
                    xt[j][i]=fitur[i][j];
                }
            }*/
            //Hitung matriks transpose data testing
            for (int j=0;j<testing[0].length;j++){
                for (int i=0;i<testing.length;i++){
                    xt[j][i]=testing[i][j];
                }
            }
            //==================================================================
            //Hitung matriks Kovarian kelompok 1
            System.out.println("Matriks Kovarian kelompok 1 = ");
            C1=hasilKaliMatriksKovarian(xo1t,xo1,baris1);
            //viewMatrik(C1);
            //Hitung matriks Kovarian kelompok 2
            System.out.println("Matriks Kovarian kelompok 2 = ");
            C2=hasilKaliMatriksKovarian(xo2t,xo2,baris2);
            //viewMatrik(C2);
            //Hitung matriks Kovarian kelompok 3
            System.out.println("Matriks Kovarian kelompok 3 = ");
            C3=hasilKaliMatriksKovarian(xo3t,xo3,baris3);
            viewMatrik(C3);
            //Hitung matriks Kovarian kelompok 4
            System.out.println("Matriks Kovarian kelompok 4 = ");
            C2=hasilKaliMatriksKovarian(xo4t,xo4,baris4);
            viewMatrik(C4);
            //==================================================================
            //Hitung matriks Kovarian keseluruhan
            System.out.println("Matriks Kovarian = ");
            for (int i=0;i<fitur[0].length;i++){
                for (int j=0;j<fitur[0].length;j++){
                    C[i][j] = Double.valueOf(df.format(((P1)*C1[i][j])+((P2)*C2[i][j])+((P3)*C3[i][j])+((P4)*C4[i][j])));
                    System.out.print(C[i][j]+", ");
                }
                System.out.println();
            }
            //==================================================================
            //Hitung determinan matriks kovarian
            double determinan;
            determinan = (C[0][0]*C[1][1])-(C[1][0]*C[0][1]);
            //System.out.println("Determinan = "+determinan);
            //==================================================================
            //Hitung invers matriks Kovarian
            //Proses membentuk adjoin
            CI = C;
            double temp = CI[0][0];
            CI[0][0]=CI[1][1];
            CI[1][1]=temp;
            CI[0][1]=CI[0][1]*-1;
            CI[1][0]=CI[1][0]*-1;
            System.out.println("Matriks Kovarian Inverse = ");
            for (int i=0;i<fitur[0].length;i++){
                for (int j=0;j<fitur[0].length;j++){
                    CI[i][j] = CI[i][j]/determinan;
                    System.out.print(CI[i][j]+", ");
                }
                System.out.println();
            }
            //==================================================================
            //Hitung fungsi diskriminan
            double []F1 = new double[fitur.length]; // Fungsi diskriminan
            double []F2 = new double[fitur.length]; // Fungsi diskriminan
            double []F3 = new double[fitur.length]; // Fungsi diskriminan
            double []F4 = new double[fitur.length]; // Fungsi diskriminan
            for (int i=0;i<testing.length;i++){
                //============================================
                double [][]hasil1 = new double[u1.length][CI[0].length];
                hasil1 = hasilKaliMatriks(u1,CI);
                
                double [][]xtemp = new double[baris1][1];
                for(int j=0;j<fitur[0].length;j++){
                    xtemp[j][0]=xt[j][i];
                }
                double [][]hasil2 = new double[hasil1.length][xtemp[0].length];
                hasil2 = hasilKaliMatriks(hasil1,xtemp);
                double [][]half=new double [1][1];
                half[0][0]=0.5;
                double [][]hasil3 = new double[half.length][u1[0].length];
                hasil3 = hasilKaliMatriks(half,u1);
                double [][]hasil4 = new double[hasil3.length][CI[0].length];
                hasil4 = hasilKaliMatriks(hasil3,CI);
                double [][]hasil5 = new double[hasil4.length][u1t[0].length];
                hasil5 = hasilKaliMatriks(hasil4,u1t);
                F1[i] = hasil2[0][0]-hasil5[0][0]+Math.log(P1);
                System.out.println("Hasil F1 data ke-"+(i+1)+" = "+F1[i]);
                //viewMatrik(hasil);
                //============================================
            }
            for (int i=0;i<testing.length;i++){
                //============================================
                double [][]hasil1 = new double[u2.length][CI[0].length];
                hasil1 = hasilKaliMatriks(u2,CI);
                
                double [][]xtemp = new double[baris2][1];
                for(int j=0;j<fitur[0].length;j++){
                    xtemp[j][0]=xt[j][i];
                }
                double [][]hasil2 = new double[hasil1.length][xtemp[0].length];
                hasil2 = hasilKaliMatriks(hasil1,xtemp);
                double [][]half=new double [1][1];
                half[0][0]=0.5;
                double [][]hasil3 = new double[half.length][u2[0].length];
                hasil3 = hasilKaliMatriks(half,u2);
                double [][]hasil4 = new double[hasil3.length][CI[0].length];
                hasil4 = hasilKaliMatriks(hasil3,CI);
                double [][]hasil5 = new double[hasil4.length][u2t[0].length];
                hasil5 = hasilKaliMatriks(hasil4,u2t);
                F2[i] = hasil2[0][0]-hasil5[0][0]+Math.log(P2);
                System.out.println("Hasil F2 data ke-"+(i+1)+" = "+F2[i]);
                //viewMatrik(hasil);
                //============================================
            }
            for (int i=0;i<testing.length;i++){
                //============================================
                double [][]hasil1 = new double[u3.length][CI[0].length];
                hasil1 = hasilKaliMatriks(u3,CI);
                
                double [][]xtemp = new double[baris3][1];
                for(int j=0;j<fitur[0].length;j++){
                    xtemp[j][0]=xt[j][i];
                }
                double [][]hasil2 = new double[hasil1.length][xtemp[0].length];
                hasil2 = hasilKaliMatriks(hasil1,xtemp);
                double [][]half=new double [1][1];
                half[0][0]=0.5;
                double [][]hasil3 = new double[half.length][u3[0].length];
                hasil3 = hasilKaliMatriks(half,u3);
                double [][]hasil4 = new double[hasil3.length][CI[0].length];
                hasil4 = hasilKaliMatriks(hasil3,CI);
                double [][]hasil5 = new double[hasil4.length][u3t[0].length];
                hasil5 = hasilKaliMatriks(hasil4,u3t);
                F3[i] = hasil2[0][0]-hasil5[0][0]+Math.log(P3);
                System.out.println("Hasil F3 data ke-"+(i+1)+" = "+F3[i]);
                //viewMatrik(hasil);
                //============================================
            }
            for (int i=0;i<testing.length;i++){
                //============================================
                double [][]hasil1 = new double[u4.length][CI[0].length];
                hasil1 = hasilKaliMatriks(u4,CI);
                
                double [][]xtemp = new double[baris4][1];
                for(int j=0;j<fitur[0].length;j++){
                    xtemp[j][0]=xt[j][i];
                }
                double [][]hasil2 = new double[hasil1.length][xtemp[0].length];
                hasil2 = hasilKaliMatriks(hasil1,xtemp);
                double [][]half=new double [1][1];
                half[0][0]=0.5;
                double [][]hasil3 = new double[half.length][u4[0].length];
                hasil3 = hasilKaliMatriks(half,u4);
                double [][]hasil4 = new double[hasil3.length][CI[0].length];
                hasil4 = hasilKaliMatriks(hasil3,CI);
                double [][]hasil5 = new double[hasil4.length][u4t[0].length];
                hasil5 = hasilKaliMatriks(hasil4,u4t);
                F4[i] = hasil2[0][0]-hasil5[0][0]+Math.log(P4);
                System.out.println("Hasil F2 data ke-"+(i+1)+" = "+F4[i]);
                //viewMatrik(hasil);
                //============================================
            }
            for (int i=0;i<testing.length;i++){
                if(F1[i]>F2[i]&&F1[i]>F3[i]&&F1[i]>F4[i])
                    hasilklasifikasi[0] = Class[0];
                if(F2[i]>F1[i]&&F2[i]>F3[i]&&F2[i]>F4[i])
                    hasilklasifikasi[0] = Class[1];
                if(F3[i]>F1[i]&&F3[i]>F2[i]&&F3[i]>F4[i])
                    hasilklasifikasi[0] = Class[2];
                if(F4[i]>F1[i]&&F4[i]>F3[i]&&F4[i]>F2[i])
                    hasilklasifikasi[0] = Class[3];
            }
            hasilklasifikasi[1] = F1[0];
            hasilklasifikasi[2] = F2[0];
            hasilklasifikasi[3] = F3[0];
            hasilklasifikasi[4] = F4[0];
            //==================================================================
        }else
        {
            JOptionPane.showMessageDialog(null, "Jumlah data Kelas dan Fitur tidak sama");
        }
        return hasilklasifikasi;
    }
}

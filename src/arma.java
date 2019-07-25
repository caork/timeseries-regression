class arma{
    // average  function
    public static double avgdata(double [] originaldata){
        return sumdata(originaldata) / originaldata.length;
    }


    // summation function
    public static double sumdata(double [] originaldata){
        double sum = 0.0;
        for (int i = 0; i < originaldata.length; ++i){
            sum += originaldata[i];
        }
        return sum;
    }


    // standard deviation function
    public static double stdvalue(double [] originaldata){
        return Math.sqrt(varvalue(originaldata));
    }


    // variance function
    public static double varvalue(double [] originaldata){
        if (originaldata.length <= 1)
            return 0.0;
        double var = 0.0;
        double mu = avgdata(originaldata);
        for (int i = 0; i < originaldata.length; ++i){
            var += (originaldata[i] - mu) * (originaldata[i] - mu);
        }
        var /= (originaldata.length - 1);
        return var;
    }


    // levinson method solve
    public static double [][] levinsonsolve(double [] garma){
        int order = garma.length - 1;
        double [][] result = new double[order + 1][order + 1];
        double [] sigmasq = new double[order + 1];

        sigmasq[0] = garma[0];
        result[1][1] = garma[1]/sigmasq[0];
        sigmasq[1] = sigmasq[0] * (1.0 - result[1][1] * result[1][1]);
        for (int k = 1; k < order; ++k){
            double sumtop = 0.0;
            double sumsub = 0.0;
            for (int j = 1; j <= k; ++j){
                sumtop += garma[k + 1 - j] * result[k][j];
                sumsub += garma[j] * result[k][j];
            }
            result[k + 1][k + 1] = (garma[k + 1] - sumtop) / (garma[0] - sumsub);
            for (int j = 1; j <= k; ++j){
                result[k + 1][j] = result[k][j] - result[k + 1][k + 1] * result[k][k + 1 - j];
            }
            sigmasq[k + 1 ] = sigmasq[k] * (1.0 - result[k + 1][k + 1] * result[k + 1][k + 1]);
        }
        return result;
    }


    public static double [] autocovdata(double [] originaldata, int order){
        double mu = avgdata(originaldata);
        double [] autocov = new double[order + 1];

        for (int i = 0; i <= order; ++i){
            autocov[i] = 0.0;
            for (int j = 0; j < originaldata.length - i; ++j){
                autocov[i] += (originaldata[i + j] - mu) * (originaldata[i] - mu);
            }
            autocov[i] /= (originaldata.length - i);
        }
        return autocov;
    }


    // AR
    public static double [] computeARcoe(double [] orignaldata, int p){
        double [] garma = autocovdata(orignaldata, p);

        double [][] result = levinsonsolve(garma);
        double [] arcoe = new double[p + 1];
        for (int i = 0; i < p; ++i){
            arcoe[i] = result[p][i + 1];
        }
        arcoe[p] = result[0][p];
        return arcoe;
    }


    //MA
    public static double [] computerMAcoe(double [] originaldata, int q){
        int p = (int)Math.log(originaldata.length);
        double [] bestgarma = autocovdata(originaldata, p);
        double [][]bestresult = levinsonsolve(bestgarma);
        double [] alpha = new double[p + 1];
        alpha[0] = -1;
        for (int i = 1; i <= p; ++i){
            alpha[i] = bestresult[p][i];
        }
        double [] paragarma = new double[q + 1];
        for (int k = 0; k <= q; ++k){
            double sum = 0.0;
            for (int j = 0; j <= p - k; ++j){
                sum += alpha[j] * alpha[k + j];
            }
            paragarma[k] = sum / bestresult[0][p];
        }
        double [][] tmp = levinsonsolve(paragarma);
        double [] macoe = new double[q + 1];
        for (int i = 1; i < macoe.length; ++i){
            macoe[i] = -tmp[q][i];
        }
        macoe[0] = 1 / tmp[0][q];
        return macoe;
    }


    //ARMA
    public static double [] computearmacoe(double [] originaldata,int p, int q){
        double [] allgarma = autocovdata(originaldata, p + q);
        double [] garma = new double[p + 1];
        for (int i = 0; i < garma.length; ++i){
            garma[i] = allgarma[q + i];
        }
        double [][] arresult = levinsonsolve(garma);

        double [] arcoe = new double[p + 1];
        for (int i = 0; i < p; ++ i){
            arcoe[i] = arresult[p][i + 1];
        }
        arcoe[p] = arresult[0][p];

        double [] alpha = new double[p + 1];
        alpha[0] = -1;
        for (int i = 1; i <= p; ++i){
            alpha[i] = arcoe[i - 1];
        }

        double [] paragarma = new double[q + 1];
        for (int k = 0; k <= q; ++k){
            double sum = 0.0;
            for (int i = 0; i <= p; ++i){
                for (int j = 0; j <= p; ++j){
                    sum += alpha[i] * alpha[j] * allgarma[Math.abs(k + i - j)];
                }
            }
            paragarma[k] = sum;
        }
        double [][] maresult = levinsonsolve(paragarma);
        double [] macoe = new double[q + 1];
        for (int i = 1; i <= q; ++i){
            macoe[i] = maresult[q][i];
        }
        macoe[0] = maresult[0][q];

        double [] armacoe = new double[p + 1 +2];
        for (int i = 0; i < armacoe.length; ++i){
            if (i < arcoe.length){
                armacoe[i] = arcoe[i];
            }
            else{
                armacoe[i] = macoe[i - arcoe.length];
            }
        }
        return armacoe;
    }

    // main function
    public static void main(String[] args){
        double [] a = {1, 2, 3, 4};
        double [] b = computearmacoe(a, 1, 1);
        System.out.println("ARMAcoe:");
        for (double num:b){
            System.out.print(num);
            System.out.print(", ");
        }
    }
}
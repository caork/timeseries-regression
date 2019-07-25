public class linearRefression {

    public static double [] fit(double [] x,double [] y){
        double sumX = 0.0;
        double sumX2 = 0.0;
        double sumY = 0.0;
        double XY = 0.0;
        for (int i = 0; i < x.length;++i){
            sumX += x[i];
            sumY += y[i];
            XY += x[i] * y[i];
            sumX2 += Math.pow(x[i],2);
        }
        double k = (sumX * sumY/x.length - XY)/(Math.pow(sumX,2)/x.length - sumX2);
        double b = (sumY - k*sumX)/x.length;
        double [] coe = {k,b};
        return coe;
    }

    public static double [] predict(double [] x,double [] coe){
        double [] yhat = new double[x.length];
        for (int i = 0; i < x.length; ++i){
            yhat[i] = coe[0]*x[i] + coe[1];
        }
        return yhat;
    }

    public static void main(String[] args) {
        double [] x = {1,2,3,4,5,6,7,8,9};
        double [] y = {3,6,5,1,3,7,9,7,5};
        double [] model = fit(x,y);
        double [] yhat = predict(x, model);

        for (double num:yhat){
            System.out.println(num);
        }
    }
}

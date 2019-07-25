public class BIC {

    public static double SSE(double [] original_data, double [] predict_data){
        // caculate SSE and return it
        double dfvalue = 0.0;
        double sumsse = 0.0;
        for (int i = 0; i < original_data.length; ++i){
            dfvalue = Math.pow(original_data[i] - predict_data[i], 2);
            sumsse += dfvalue;
        }
        return sumsse;
    }

    public static double BIC(double [] original_data, double [] predict_data,double p){

        double sse = SSE(original_data,predict_data);
        double AIC = original_data.length*Math.log(sse) + 2*p;
        return AIC;

    }

    public static void main(String[] args) {

        double [] original_data = {1,2,3,4,5,6,7,8,9};
        double [] predict_data = {2,5,4,2,5,6,7,8,7};

        System.out.println(BIC(original_data,predict_data,2));
    }
}

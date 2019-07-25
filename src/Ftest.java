public class Ftest {


    public static double SSR(double [] original_data, double [] predict_data) {
        // caculate SSR and return it
        // sum(yhat - mean(y))^2
        double sum = 0.0;

        for (int i = 0;i < original_data.length; ++i){
            sum += original_data[i];
        }

        double mean = sum/original_data.length;

        double dfvalue = 0.0;
        double sumssr = 0.0;
        for (int i = 0; i < original_data.length; ++i){
            dfvalue = Math.pow(predict_data[i] - mean, 2);
            sumssr += dfvalue;
        }

        return sumssr;
    }

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

    public static double ftest(double [] original_data, double [] predict_data) {
        //F = ( SSR / 1) / ( SSE / ( N - 2 ) )
        //and return F
        double ssr = SSR(original_data, predict_data);
        double sse = SSE(original_data, predict_data);
        double f = (ssr / 1) / (sse / (original_data.length - 2));

        return f;
    }

    public static double genaralized_ftest(double [] original_data,double [] predict_data, double [] further_predict_data,int p){

        double SSE1 = SSE(original_data,predict_data);
        double SSE0 = SSE(original_data,further_predict_data);

        double f = ((SSE0 - SSE1)/2)/(SSE1/(original_data.length - p - 1));

        return f;
    }

    public static void main(String [] args) {
        double [] a = {1,2,3,4,5,6};
        double [] b = {3,4,5,6,8,8};
        System.out.println("success");
        System.out.println(ftest(a,b));
        //print F value
    }
}

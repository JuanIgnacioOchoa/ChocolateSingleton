package mx.iteso.singleton;

/**
 * Created by Juan on 29/10/2015.
 */
public class ChocolateBoiler {
    private volatile boolean empty;
    private volatile boolean boiled;

    private static volatile ChocolateBoiler instance = null;

     //Otra manera de hacerlo sinchrono
/*  private static class SingletonHolder{
        public static final ChocolateBoiler instance = new ChocolateBoiler();
    }
    public static ChocolateBoiler getInstance(){
        return SingletonHolder.instance;
    }
*/
    private  ChocolateBoiler() {
        empty = true;
        boiled = false;
    }
    public static ChocolateBoiler getInstance(){
        if(instance== null){
            synchronized (ChocolateBoiler.class){
                if(instance == null){
                    instance = new ChocolateBoiler();
                }
            }
        }
        return instance;
    }

    public synchronized void fill() {
        if (isEmpty()) {
            empty = false;
            boiled = false;
            // fi ll the boiler with a milk/chocolate mixture
        }
    }
    public synchronized void drain() {
        if (!isEmpty() && isBoiled()) {
            // drain the boiled milk and chocolate
            empty = true;
        }
    }
    public synchronized void boil() {
        if (!isEmpty() && !isBoiled()) {
            // bring the contents to a boil
            boiled = true;
        }
    }
    public boolean isEmpty() {
        return empty;
    }
    public boolean isBoiled() {
        return boiled;
    }
}

package mx.iteso;

import mx.iteso.singleton.ChocolateBoiler;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Created by Juan on 29/10/2015.
 */
public class ChocolateBoilerTest {
    private ChocolateBoiler boiler;
    @Before
    public void setUp(){
        boiler = ChocolateBoiler.getInstance();
    }
    @Test
    public void fillTest(){
        boiler.fill();
        assertEquals(false, boiler.isEmpty());
    }
    @Test
    public void boilTest(){
        boiler.fill();
        boiler.boil();
        assertEquals(true, boiler.isBoiled());
    }
    @Test
    public void drainTest(){
        boiler.fill();
        boiler.boil();
        boiler.drain();
        assertEquals(true, boiler.isEmpty());
    }
    public class Boiler implements Callable<ChocolateBoiler>{
        public ChocolateBoiler call() throws Exception{
            System.out.println("start " + Thread.currentThread().getId());
            System.out.println("end " + Thread.currentThread().getId());
            return ChocolateBoiler.getInstance();
        }
    }
    @Test
    public void multiThreadTest() throws Exception{
        int TotalThreads = 20;
        final ExecutorService ex = Executors.newCachedThreadPool();
        HashSet<ChocolateBoiler> boilers = new HashSet<ChocolateBoiler>();
        HashSet<Callable<ChocolateBoiler>> boilerCall = new HashSet<Callable<ChocolateBoiler>>();

        for(int i = 0; i < TotalThreads; i++ ) ex.submit(new Boiler());

        ex.invokeAll(boilerCall);
        //ex.awaitTermination();
        for(Callable<ChocolateBoiler> call : boilerCall) boilers.add(call.call());
        for(ChocolateBoiler chocolateBoiler : boilers) assertEquals(ChocolateBoiler.getInstance().hashCode(),chocolateBoiler);
    }


}

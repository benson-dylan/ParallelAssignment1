import java.io.*;
import java.util.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.*;

public class AllPrimeNumbers
{
    static class Counter
    {
        private int counter;
        private Lock lock = new ReentrantLock();

        public Counter()
        {
            this.counter = 2;
        }

        public Counter(int start)
        {
            this.counter = start;
        }

        public int getAndIncrement()
        {
            int temp;
            lock.lock();
            try
            {
                temp = this.counter;
                this.counter = temp + 1;
            } 
            finally
            {
                lock.unlock();
            }
            return temp;
        }

        public int get()
        {
            return this.counter;
        }
    }

    static class MyThread extends Thread
    {
        private final List<Boolean> sharedList;
        private final Counter counter;
        private final int n;

        public MyThread(List<Boolean> sharedList, Counter counter, int n)
        {
            this.sharedList = sharedList;
            this.counter = counter;
            this.n = n;
        }

        public void run()
        {
            while (counter.get() <= Math.sqrt(n))
            {
                int num = counter.getAndIncrement();
                checkForPrime(num);
            }
        }

        public void checkForPrime(int num)
        {
            if (sharedList.get(num))
            {
                for (int i = num * num; i <= this.n; i += num)
                {
                    sharedList.set(i, false);
                }
            }
        }
    }

    public static void main(String [] args)
    {
        Instant startTime = Instant.now();
        int n = 100000000;
        long sumOfPrimes = 0;
        List<Integer> sharedPrimeList = Collections.synchronizedList(new ArrayList<Integer>());
        Counter counter = new Counter();

        List<Boolean> sharedList = Collections.synchronizedList(new ArrayList<Boolean>());
        for (int i = 0; i <= n; i++)
        {
            sharedList.add(true);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(8);

        for (int i = 0; i < 8; i++)
        {
            MyThread myThread = new MyThread(sharedList, counter, n);
            executorService.execute(myThread);
        }

        executorService.shutdown();

        try 
        {
          executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);  
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }

        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i <= n; i++)
        {
            if (sharedList.get(i))
            {
                primes.add(i);
                sumOfPrimes += i;
            }
        }

        Instant endTime = Instant.now();
        Duration elapsedTime = Duration.between(startTime, endTime);

        String fileName = "primes.txt";
        int primesLen = primes.size();

        try 
        {
            FileWriter writer = new FileWriter(fileName);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(elapsedTime.toMillis() + " " + primes.size() + " " + sumOfPrimes + "\n");
            for (int i = 0; i < 10; i++)
            {
                bufferWriter.write(primes.get(primesLen - 10 + i) + "\n");
            }
            bufferWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
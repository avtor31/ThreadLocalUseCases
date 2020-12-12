package com.avtor31;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    private static final int poolSize = 2;
    public static final String DATE_AS_STRING = "20201021";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
	    Main main = new Main();
        System.out.println("useDateFormatServiceAsThreadUnsafe:");
	    main.useDateFormatServiceAsThreadUnsafe();
        System.out.println("__________________________________________");
        System.out.println("useThreadLocalDateFormatServiceAsCallableTask:");
	    main.useThreadLocalDateFormatServiceAsCallableTask();
        System.out.println("__________________________________________");
        System.out.println("useNewDateFormatServiceAsCallableTask:");
	    main.useNewDateFormatServiceAsCallableTask();

        /**
         * C:\Java\jdk-12.0.2\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.1.2\lib\idea_rt.jar=51330:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020.1.2\bin" -Dfile.encoding=UTF-8 -classpath D:\projects\ThreadLocal\SimpleDateFormat\out\production\SimpleDateFormat com.company.Main
         * useDateFormatServiceAsThreadUnsafe:
         * It was created new SimpleDateFormat object in DateFormatService
         * For input string: ".22002E4.22002E4"
         * For input string: ".22002E4.22002"
         * null
         * null
         * Wed Oct 21 00:00:00 MSK 2020
         * Wed Oct 21 00:00:00 MSK 2020
         * Wed Oct 21 00:00:00 MSK 2020
         * __________________________________________
         * useThreadLocalDateFormatServiceAsCallableTask:
         * It was created new SimpleDateFormat object
         * Wed Oct 21 00:00:00 MSK 2020
         * It was created new SimpleDateFormat object
         * Wed Oct 21 00:00:00 MSK 2020
         * Wed Oct 21 00:00:00 MSK 2020
         * Wed Oct 21 00:00:00 MSK 2020
         * Wed Oct 21 00:00:00 MSK 2020
         * __________________________________________
         * useNewDateFormatServiceAsCallableTask:
         * It was created new SimpleDateFormat object in DateFormatService
         * It was created new SimpleDateFormat object in DateFormatService
         * Wed Oct 21 00:00:00 MSK 2020
         * It was created new SimpleDateFormat object in DateFormatService
         * It was created new SimpleDateFormat object in DateFormatService
         * Wed Oct 21 00:00:00 MSK 2020
         * Wed Oct 21 00:00:00 MSK 2020
         * It was created new SimpleDateFormat object in DateFormatService
         * Wed Oct 21 00:00:00 MSK 2020
         * Wed Oct 21 00:00:00 MSK 2020
         *
         * Process finished with exit code 0
         *
         * Как можно заметить, применение ThreadLocal сокращает количество уникальных объектов SimpleDateFormat
         * до количества потоков, использующих эти объекты.
         */
    }

    private void useNewDateFormatServiceAsCallableTask() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        List<Future<Date>> futures = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            futures.add(executorService.submit(() ->
                    new DateFormatService().convertToDate(DATE_AS_STRING)));
        }
        executorService.shutdown();

        for (Future<Date> future: futures) {
            System.out.println(future.get());
        }

    }

    private void useThreadLocalDateFormatServiceAsCallableTask() throws ExecutionException, InterruptedException {
        ThreadLocalDateFormatService formatService = new ThreadLocalDateFormatService();
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        Callable<Date> task = () -> formatService.convertToDate(DATE_AS_STRING);

        List<Future<Date>> futures = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            futures.add(executorService.submit(task));
        }
        executorService.shutdown();

        for (Future<Date> future: futures) {
            System.out.println(future.get());
        }
    }

    private void useDateFormatServiceAsThreadUnsafe() throws ExecutionException, InterruptedException {
        DateFormatService formatService = new DateFormatService();
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        Callable<Date> task = () -> formatService.convertToDate(DATE_AS_STRING);

        List<Future<Date>> futures = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            futures.add(executorService.submit(task));
        }
        executorService.shutdown();

        for (Future<Date> future: futures) {
            System.out.println(future.get());
        }
    }
}

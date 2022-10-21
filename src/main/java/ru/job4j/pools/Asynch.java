package ru.job4j.pools;

import java.lang.Thread;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;
import java.util.concurrent.CompletableFuture;

public class Asynch {

    public static void iWork() throws InterruptedException {
		int count = 0;
		while (count < 10) {
			System.out.println(Thread.currentThread().getName() + " Me: I'm working");
			TimeUnit.SECONDS.sleep(1);
			count++;
		}
	}
	
	public static CompletableFuture<Void> goToTrash() {
		return CompletableFuture.runAsync(
            () -> {
                System.out.println(Thread.currentThread().getName() + " Son: Mom / Dad, I went to take out the trash");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " Son: Mom / Dad, I'm back!");
            }
		);
	}
	
	public static void runAsyncExample() throws Exception {
		CompletableFuture<Void> gtt = Asynch.goToTrash();
		Asynch.iWork();
	}
	
	public static CompletableFuture<String> buyProduct(String product) {
		return CompletableFuture.supplyAsync(
            () -> {
                System.out.println(Thread.currentThread().getName() + " Son: Mom / Dad, I went to the store");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " Son: Mom / Dad, I bought " + product);
                return product;
            }
		);
	}
	
	public static String supplyAsyncExample() throws Exception {
		CompletableFuture<String> gtt = Asynch.buyProduct("Bread");
		Asynch.iWork();
		return gtt.get();
	}
	
	public static void thenRunExample() throws Exception {
		CompletableFuture<Void> gtt = goToTrash();
		gtt.thenRun(() -> {
			int count = 0;
			while (count < 3) {
				System.out.println(Thread.currentThread().getName() + " Son: I wash my hands");
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count++;
			}
			System.out.println(Thread.currentThread().getName() + " Son: I washed my hands");
		});
		iWork();
	}
	
	public static String supplyAsyncThenAsseptExample() throws Exception {
		CompletableFuture<String> bp = Asynch.buyProduct("bread");
		bp.thenAccept((product) -> System.out.println(Thread.currentThread().getName() + " Son: I put the " + product + " in the fridge"));
		Asynch.iWork();
		return bp.get();
	}
	
	public static void thenApplyExample() throws Exception {
		CompletableFuture<String> bm = buyProduct("Milk")
            .thenApply((product) -> Thread.currentThread().getName() + " Son: I poured you a mug " + product + ". Hold on.");
		iWork();
		System.out.println(Thread.currentThread().getName() + "  " + bm.get());
	}
	
	public static void thenComposeExample() throws Exception {
		CompletableFuture<String> result = goToTrash().thenCompose(a -> buyProduct("Milk"));
		System.out.println(Thread.currentThread().getName() + "  " + result.get());
	}
	
	public static void thenCombineExample() throws Exception {
		CompletableFuture<String> result = buyProduct("Milk")
            .thenCombine(buyProduct("Bread"), (r1, r2) -> Thread.currentThread().getName() + "  " + "Bought " + r1 + " and " + r2);
		iWork();
		System.out.println(Thread.currentThread().getName() + "  " + result.get());
	}

	public static CompletableFuture<Void> washHands(String name) {
		return CompletableFuture.runAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "  " + name + ", wash hands");
		});
	}

	public static void allOfExample() throws Exception {
		CompletableFuture<Void> all = CompletableFuture.allOf(
				washHands("Dad"), washHands("Mom"),
				washHands("Ivan"), washHands("Boris")
		);
		iWork();
		TimeUnit.SECONDS.sleep(3);
	}

	public static CompletableFuture<String> whoWashHands(String name) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Thread.currentThread().getName() + "  " + name + ", wash hands";
		});
	}

	public static void anyOfExample() throws Exception {
		CompletableFuture<Object> first = CompletableFuture.anyOf(
				whoWashHands("Dad"), whoWashHands("Mom"),
				whoWashHands("Ivan"), whoWashHands("Boris")
		);
		System.out.println(Thread.currentThread().getName() + "  " + "Who is washing hands now?");
		TimeUnit.SECONDS.sleep(1);
		System.out.println(Thread.currentThread().getName() + "  " + first.get());
	}

	public static void main(String[] args) throws Exception {
		runAsyncExample();
		supplyAsyncExample();
		System.out.println(Thread.currentThread().getName() + "  " + supplyAsyncThenAsseptExample());
		thenApplyExample();
		thenComposeExample();
		thenCombineExample();
		allOfExample();
		anyOfExample();
	}
}

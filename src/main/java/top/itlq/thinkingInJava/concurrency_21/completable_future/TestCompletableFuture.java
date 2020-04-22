package top.itlq.thinkingInJava.concurrency_21.completable_future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *
 */
class TestCompletableFuture {
    @Test
    void testNonBlockGet(){
        CompletableFuture<Machina> machinaCompletableFuture = CompletableFuture.completedFuture(new Machina(0));
        try {
            machinaCompletableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testThenApply(){
        long t = System.currentTimeMillis();
        CompletableFuture<Machina> machinaCompletableFuture =
                CompletableFuture.completedFuture(new Machina(0))
                        .thenApply(Machina::work)
                        .thenApply(Machina::work)
                        .thenApply(Machina::work)
                        .thenApply(Machina::work);
        System.out.println(System.currentTimeMillis() - t);
    }

    /**
     * 异步，立马返回，操作链被存为一组回调
     */
    @Test
    void testThenApplyAsync() {
        long t = System.currentTimeMillis();
        CompletableFuture<Machina> machinaCompletableFuture =
                CompletableFuture.completedFuture(new Machina(0))
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work);
        System.out.println(System.currentTimeMillis() - t);
        machinaCompletableFuture.join();
        System.out.println(System.currentTimeMillis() - t);
        try {
            System.out.println(machinaCompletableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - t);
    }
}

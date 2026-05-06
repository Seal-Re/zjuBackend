package com.hengtiansoft.fastop.service.context;

import com.hengtiansoft.fastop.base.common.context.UserContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserContextHolder 是 ThreadLocal 实现 — 必须保证：
 *   1. 默认值 "unknown" 防 NPE
 *   2. set + get 同线程一致
 *   3. clear 后 get 重置
 *   4. 跨线程隔离（线程池场景下不能串号）
 */
class UserContextHolderTest {

    @AfterEach
    void cleanup() {
        UserContextHolder.clear();
    }

    @Test
    void defaultIsUnknown() {
        assertEquals("unknown", UserContextHolder.getCurrentUser());
    }

    @Test
    void setAndGetWithinSameThread() {
        UserContextHolder.setCurrentUser("alice");
        assertEquals("alice", UserContextHolder.getCurrentUser());
    }

    @Test
    void clearResetsToUnknown() {
        UserContextHolder.setCurrentUser("bob");
        UserContextHolder.clear();
        assertEquals("unknown", UserContextHolder.getCurrentUser());
    }

    @Test
    void differentThreadsAreIsolated() throws InterruptedException {
        UserContextHolder.setCurrentUser("main-user");
        AtomicReference<String> otherThreadValue = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            otherThreadValue.set(UserContextHolder.getCurrentUser());
            latch.countDown();
        });
        t.start();
        latch.await();
        assertEquals("unknown", otherThreadValue.get(),
                "ThreadLocal 必须线程隔离，不应看到主线程的 main-user");
        assertEquals("main-user", UserContextHolder.getCurrentUser(),
                "主线程上下文不应被其他线程影响");
    }
}

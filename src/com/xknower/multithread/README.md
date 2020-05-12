# JAVA 进程线程研究

## > 《Java线程与并发编程实践 Jeff 焉倩 201702》

```
-- Chapter One & Thread & Runnable (线程机制的底层实现)

> 并行, 指的时同一个时刻多个任务在运行, 即多处理器和核心得一些组合中运行线程
> 并发, 是一种更为宽泛得并行, 并发中, 线程可以同时运行或者通过上下文切换实现看上去是同时运行, 即虚拟运行

JVM 虚拟机给每条线程分配独立的 JVM 栈空间, 避免彼此产生干扰。

栈空间为每条线程单独准备了一份 [方法参数、局部变量以及返回值(共享变量)的拷贝]

> java.lang.Thread
> java.lang.Runnable

Thread 类为底层操作系统的线程体系架构, 提供一套统一接口 (通常由操作系统负责创建和管理线程)
, 单个操作系统线程和一个Thread对象关联。

Runnable 接口, 为关联的Thread对象的线程, 提供执行代码。在 run 方法方法中执行, 不接收和返回数据, 但可能抛出异常

> 01 线程的创建和执行

> 02 线程状态
线程名称
线程存活标识
线程执行状态
线程优先级
线程是否为守护线程

> 线程执行状态 Thread.State
[NEW RUNNABLE BLOCKED WAITING TIMED_WAITING TERMINATED]
BLOCKED 阻塞, 并等待一个监听锁
WAITING 无限期等待, 另一个线程执行特定操作
TIMED_WAITING 特定时间等待,另一个线程执行特定操作

> 03 线程高级操作
中断线程 , 中断其他线程的机制
// void interrupt() 中断调用此方法的Thread对象所关联的线程
// static boolean interrupted() 验证当前线程是否已经中断, 调用会清除中断状态
// boolean isInterrupted() 验证当前线程是否已经中断, 不会影响到线程的中断状态
// -> java.lang.InterruptedExecption

等待线程, 允许调用线程等待执行方法的线程对象所关联的线程执行完毕
// void join() 无限期等待知道该线程死亡
// void join(long millis) 该线程死亡之前, 最多等待 millis 毫秒, 传入 0 则会无限期等待
// void join(long millis, int nanos)
// -> java.lang.InterruptedExecption

线程睡眠
// void sleep(long millis) 睡眠 millis 毫秒数
// void slepp(long millis, int nanos)

```

```
-- Chapter Two & synchronized (同步, 线程之间交互, 共享数据)

线程之间的同步, 通常是通过共享变量来完成的
// 共享变量, 实例变量或类变量
// 局部变量, 每个线程都有自己的局部变量拷贝

每个Java对象都和一个监听器相关联, 监听器是一个互斥的构造, 器阻止多条线程同时在临界区中并发执行
线程进入临界区前, 需要锁住监听器。
在多核多处理器的环境中, 锁住一个监听器时, 主存中的共享变量会被读取到对应的拷贝中,
这个动作能保证该线程使用该变量最近的值并且不会污染这些值, 称之为可见性
当离开临界区时, 线程会释放监听器, 共享变量的值会被回写到主存中

> 01 问题: 竞态条件、数据竞争、缓存变量

>  竞态条件 (当正确结果, 取决于, 相对时间或者调度器所控制的多线程交叉时, 竞态条件会发生)
// check-then-act
// read-modify-write

> 数据竞争 (指的是, 两个或以上的线程并发的访问同一块内存区域, 同时其中至少一条是为了写入)
// happens-before-ordering

> 缓存变量 (为提高效率, 将共享变量, 在主存中数据, 缓存到寄存器或处理器缓存中, 进行处理, 处理完成后从缓存中更新到主存)
//

> 02 同步临界区 : 同步方法, 同步块, 可见性

> 同步 synchronized
// 保证两个及以上线程, 并发执行时, 不会同时执行同一块临界区。临界区是需要以串行方式访问的一段代码块
// 同步属性 -> 互斥行, 线程获取临界区的锁, 称之为互斥锁
// 同步属性 -> 可见性, 保证了一个线程在临界区执行时, 总是烤蛋共享变量最新最近的修改

// 同步实现, 是通过监听器时间的, 监听器是针对临界区构建的并发访问控制, 并发必须以不可分割的形式执行
// 每个对象都和一个监听器相关联, 所以线程可以通过获取和释放监听器的锁(标识)来上锁和解锁
// ! Thread.sleep() 方法不会释放获取的锁

> 活跃性 问题 : 死锁、活锁、饿死

> 死锁 , 一个线程等待另一个线程互斥持有的资源, 且该线程也在等待本线程持有的互斥资源, 导致两个线程都无法执行
// 死锁, 会发生在 synchronized 带来的过多的同步上
// 避免死锁最简单的方式, 就是阻止同步方法或者同步块调用其他的同步方法和同步块

> 活锁 , 一个线程持续重试一个总是失败的操作, 以致于无法执行

> 饿死 , 线程一直被(调度器)延迟访问器赖以执行的资源, 而导致无限延迟

> volatile & final

> volatile 轻量级的仅包含可见性的同步形式 (解决只有可见性导致的问题)
// volatile 标识的变量, 线程访问时直接访问主存, 不会访问缓存中的拷贝

```

```
-- Chapter Three & (等待/通知, 实现线程间交互)

> 01 等待/通知机制, 利用了一个对象的条件队列, 存储哪些等待某个条件成立的线程, 称之为等待集合
> 该等待队列是和对象锁绑定在一起的
> 这些方法必须在同步的上下文中被调用, 否则抛出异常 java.lang.IllegalMonitorStateException

> java.lang.Object

> 等待
> void wait() 等待某个条件成立, 导致当前线程一直处于等待状态, 直到另外的线程调用该对象的通知方法或被中断
> void wait(long timeout) 等待被唤醒或者等待时间超时
> void wait(lang timeout, int nanos)
// wait 方法会导致当前线程对该对象关联监听器的所有权被释放

> void notify() 通知处于等待中的线程, 唤醒正等待该对象监听器的单条线程
> void notifyAll() 通知处于等待中的线程, 唤醒正等待该对象监听器的全部线程

> 02 消费者/生产者

```
-- Chapter Four & 线程组/线程局部变量/定时器框架

> 01 THreadGroup

> 02 线程局部变量 (设置线程私有数据 [局部变量/线程局部变量(没有定义局部变量情况下)])
> java.lang.ThreadLocal 代表一个线程局部变量, 为每个线程提供单独的存储槽
> java.langInheritableThreadLocal 子类, 可以将父线程的值传递给子线程

> 03 定时器框架
> java.util.Timer
> java.util.TimerTask

> Timer 适合大规模并发调度定时任务, 该类使用了一个二进制堆表示其定时任务队列, 时间复杂度为 O(log n)

```

```
-- Chapter Five & 并发工具类 (Executor、同步器(synchronizer) 、锁框架) (线程池、阻塞队列)

> Java 对底层线程操作的支持, 使得可以创建多线程应用。

> java.util.concurrent 并发编程工具框架包
> java.util.concurrent.atomic 支持单个变量上, 无锁且线程安全的操作工具
> java.utils.concurrent.locks

> 01 executor , 实现了接口 java.util.concurrent.Executor 接口, 将任务的提交操作从任务执行机制中解耦出来
> 解耦从而产生更易维护的、具备伸缩性的代码
> java.util.concurrent.ExecutorService 接口扩展 Executor 接口并实现了线程池
> java.util.concurrent.ScheduledExecutorService 能够让调度任务运行一次或者在指定延迟之后周期性执行
> java.util.concurrent.Executors 工具类, 可以用来返回多种ExectorService 和 ScheduledExecutorService

> Callable 任务执行定义
> java.util.concurrent.Future 接口, 代表异步计算的结果

java.util.concurrent.TimeUnit 时间枚举工具类

```

```
-- Chapter Six & 同步器

> 倒计时门闩(countdown latch)、同步屏障(cyclic barrier)、交换器(exchanger)、信号量(semaphore)、Phaser 同步器

> Java 提供了 synchronized 关键字对临界区进行线程同步访问

> 01 倒计时门闩(countdown latch) (保证多条线程几乎同时开始工作)
> 倒计时门闩, 会导致一个或多个线程等待, 直到另一个线程打开门闩, 等待线程才能继续执行
> 其由, 一个计数变量和两个操作组成, 其分别是 "导致一条线程等待直到计数变为0" 以及 "递减计数变量"
> java.utilconcurrent.CountDownLatch
> void await() 除非线程中断, 否则强制调用线程一直等到计数器倒数到 0
> boolean await(long timeout, TimeUnit unit)
> void countDown() 递减计数, 递减到0时, 释放所有等待线程

> 02 同步屏障(cyclic barrier) (允许一组线程彼此互相等待, 直到抵达某个公共屏障点)
// 同步屏障适用于在并行分解下的场合。将任务分解成单个小任务执行, 执行结果随后合并到整个任何结果之中
> 因为该屏障在等待线程释放之后可以重用, 所以称之为可循环使用屏障
> 适用于对于数量固定并且互相之间必须不时等待彼此的多线程应用
> java.util.concurrent.CyclicBarrier
> CyclicBarrier(int parties)
> CyclicBarrier(int parties, Runnable barrierAction)
// 跨越屏障时执行 barrierAction, 及 parties 从 -1 -> 0, 适用于在任意线程继续执行之前更新共享状态
// 如果未指定 barrierAction , 将抛出异常 IllegalArgumentExection
> int await() 强制调用线程一直等待直到所有 parties 都已经在在同步屏障调用了 await 方法
> int await(long timeout, TimeUnit unit)

> 03 交换器(exchanger) ()
> 提供了一个线程彼此之间能够交换对象的同步点
> java.util.concurrent.Exchange<V>
> V exchange(V x) 在交换点上等待其他线程到达, 之后将所有对象传入其中, 接收其他线程的对象作为返回
> V exchange(V x, long timeout, TimeUnit unit)

> 04 信号量(semaphore) (控制资源访问)
> 信号量维护了一组许可证(permit), 以约束访问被限制资源的线程数
> 当前值可以被递增1的信号量称作计数信号量, 当前值只能取0和1的信号量称谓二进制信号量或互斥量
> java.util.concurrent.Semaphore
// 信号量公平策略为false 时, 信号量不会保证线程获取许可证的顺序, 而抢占是允许的
> void acquire() 从信号量中获取一个许可证
> void acquire(int permits)
> void acquireUninterruptibly()
> void acquireUninterruptibly(int permits)
> boolean tryAcquire()
> boolean tryAcquire(int permits)
> boolean tryAcquire(long timeout, TimeUnit unit)
> boolean tryAcquire(int permits, long timeout, TimeUnit unit)
> void release() 释放一个许可证
> void release(int permits)

> 05 Phaser 同步器 (一个更加弹性的同步屏障)
> 一个phaser是的一组线程在屏障上等待, 在最后一条线程到达之后, 这些线程才得以继续执行
> 和同步屏障协调固定数目的线程不同, 一个phaser能够协调不定数目的线程
> 实现使用了 phase 和 phase 值, phase 表示 phaser 当前状态, 同时这已状态被一个整型的 phase 值确定
> java.util.concurrent.Phaser

```

```
-- Chapter Seven & 锁框架

> java.util.concurrent.locks
> 针对条件进行加锁和等待, 该机制不同于对象的内置加锁同步和Object的等待/通知机制

> 锁、重入锁、条件、读写锁、重入读写锁

> 01 锁
> Lock
> 锁提供了, 比监听器关联的锁更为弹性的锁操作
> 获取到的锁必须释放, 在同步方法和代码块上下文中, 隐式的监听锁和每个对象关联
> 锁提升了伸缩性, 到块状锁的缺失移除了发生在同步方法和代码块上自动释放锁的功能

> 02 重入锁 (实现了接口 Lock, 描述了一个可重入的互斥锁)
> java.util.concurrent.locks.ReentrantLock;

> 03 条件
> java.util.concurrent.locks.Condition
> 其将 Object 的 Wait 的 notification方法(wait、notify、notifyAll) 分解到不同的条件对象中
> 将这些条件和 Lock 实现的使用组合起来, 起到让每个对象具有多重等待集合的作用
> 这里 Lock 取代了 同步方法和代码块、Condition 取代了 Object 的 wait 和 notification 方法

> 04 读写锁
> 读写锁适用于对数据频繁读取而较少修改的场景, 锁框架针对这些场景提供了读写锁, 在读取时有更好的并发性, 写入时保证安全的互斥访问
// 维护了一对锁, 一个锁针对只读操作, 一个锁针对写操作, 没有写操作时读锁可能被多条线程同时持有, 写操作是互斥的
> ReadWriteLock
> Lock readLock()
> Lock writeLock()

> 05 重入读写锁
> ReentrantReadWriteLock

> 06 StampedLock

```

```
-- Chapter Eight & 高级并发工具类

> 并发集合、原子变量、Fork/Join框架、Completion Service

> 01 并发集合

> 集合
> 集合框架, 由 java.util 包下接口和类组成
> 接口包含 , List Set Map , 类包含 ArrayList TreeSet HashMap
> java.util.Collections.synchronizedList 同步包装方法, 可以使得上面非线程安全的实现类变得线程安全
> 并发集合是具有并发性能和高扩展性、面向集合的类型

> BlockingQueue , 接口 java.util.Queue 子接口, 其支持阻塞操作
> 实现类, ArrayBlockingQueue、DelayQueue、LinkedBlockingQueue、PriorityBlockingQueue、SynchronousQueue
> LikedBlockingDeque、LinkedTransferQueue

> ConcurrentMap 是 java.util.Map 的子接口
> ConcurrentHashMap , 提供 HashMap 的线程能力

> 02 原子变量
> 对象监听器关联的内置锁, 一直都有性能不佳问题。研究在同步上下文中创建非阻塞的算法, 是从根本上提高性能的方法。
> java.util.concurrent.atomic
> 原子变量, 用于计数器、序列生成器等
> 低级同步机制, 强制使用互斥(持有锁的线程保证一组变量被互斥的访问)以及可见性(被保护变量的更改对后续持续获取锁的其他线程可见)
> 低级同步机制问题 : 1、争用的同步, 非常昂贵的上下文切换代价 2、持有锁线程执行的延迟, 导致CPU时间的利用下降

> compare-and-swap (CAS)(java.util.concurrent.Semaphore)

> 03 Fork/Join框架
> 由特定的 ExecutorService和线程池构成, 将任务分解成较小得任务并发执行
> ForkJoinPool、ForkJoinTask、ForkJoinWorkerThread、RecursiveAction、RecursiveTask、CountedCompleter

> 04 Completion Service
> java.util.concurrent.CompletionService<V>

```

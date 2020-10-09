# JAVA 源码研究

> 使用版本 [jdk-8u211] [环境 IDEA]

> 构建步骤

## 1、创建项目结构

rt/        // rt.jar 反编译源码
rtsrc/     // JDK 源码
src/       // 项目源码

## 2、导入源码

> import jdk source of src.zip
> import jdk source of decompile rt.jar
> import jdk source of other source

> rtsrc copy(8204) and replace(7388) to rt , then rt copy (13964) and not replace (7718) to rtsrc
> new add (330) of rtsrc copy(8204) and replace(7388) to rt
> modified (7388) of rtsrc copy(8204) and replace(7388) to rt
> then new add (5240) rt copy (13964) and not replace (7718) to rtsrc

> openjdk/jdk/src/share/classes
> rtsrc copy(9858) and replace(6308) to rt
> modified (6308) of rtsrc copy(9858) and replace(6308) to rt

> fix decompile source and compile test

> 设置 rtsrc/ 、src/ 参与编译
> 设置 IDEA 编译缓存
> 设置 IDEA Sourcepath 为 rtsrc/ (修改JDK注释时, 行数不能更改)
> 规范: 在原注释行添加注释, 使用 #* 将注释区分开

## 3、配置构建 JDK -> [IDEA 、jdk-8u211]

> jre\lib
charsets.jar
deploy.jar
javaws.jar
jce.jar
jfr.jar
jfxswt.jar
jsse.jar
management-agent.jar
plugin.jar
resources.jar
rt.jar

> jre\lib\ext
access-bridge-64.jar
cldrdata.jar
dnsns.jar
jaccess.jar
jfxrt.jar
localedata.jar
nashorn.jar
sunec.jar
sunjce_provider.jar
sunmsca.pi.jar
sunpkcs11.jar
zipfs.jar

>
tools.jar 包 lib\tools.jar

>
sun.awt.UNIXToolkit
// http://www.docjar.com/html/api/sun/awt/UNIXToolkit.java.html

sun.font.FontConfigManager
// http://www.docjar.com/html/api/sun/font/FontConfigManager.java.html

## 4、src.zip 、rt.jar 包源码分析

> 更改源码并编译后, 解压 rt.jar 并替换修改的 class 文件, 重新打包并替换 jre/lib/rt.jar 文件
> !操作前请注意备份

### exploration : rtsrc/

-- 包结构
com
    com.sun   // sun的hotspot虚拟机中java.* 和javax.*的实现类。因为包含在rt中，所以我们也可以调用。
              // 不是sun对外公开承诺的接口，在不同版本的hotspot中可能是不同的。
java          // java SE的标准库, 是java标准的一部分, 是对外承诺的java开发接口, 通常要保持向后兼容, 一般不会轻易修改
              // 包括其他厂家的在内，所有jdk的实现，在java.*上都是一样的
javax         // java标准的一部分，但是没有包含在标准库中，一般属于标准库的扩展。通常属于某个特定领域，不是一般性的api。
org           // 是由企业或者组织提供的java类库。其中比较常用的是w3c提供的对XML、网页、服务器的类和接口。
launcher

### exploration: java

applet    // 不独立运行, 而嵌入到另一个程序中运行的的小程序
awt       // Abstract Window ToolKit (抽象窗口工具包), 提供图形化界面, 比如按钮、图片、颜色控制、样式等工具包
beans     // 核心在于封装了java 的反射, 提供JavaBean的各种操作, 基于JavaBean组件架构。
io        // 提供对数据流的输出、输出、读写。通过数据流、序列化和文件系统提供系统的输入和输出。
lang      // 语言基础包, 基本数据类型、包装类、运算、异常类等，以及根类Object, 提供常用的方法。
math      // 提供执行任意精度整数算术 和任意精度十进制算术的类。
net       // 网络通信, 比如http、cookie、socket等的实现。
nio       // 简称java new io 定义了缓冲区, 这是数据的容器, 并提供其他NIO包的概述。
rmi       // Java Remote Method Invocation 远程方法调用
security  // 安全框架提供类和接口
sql       // 数据库相关, 提供的API来访问和处理的数据存储在数据源
text      // 提供用于处理独立于自然语言的方式处理文本、日期、数字和消息的类和接口
time      // 日期、时间、时刻主要的API, 和持续时间
util      // 集合框架、遗留的集合类、并发包、事件模型、日期和时间的设施、国际化和各种实用工具类

-- 基础功能结构 (java.lang & java.util)

> 01 基础类和包

> 02 集合框架

> 03 多线程框架

> 04 事件框架

> 05 异常体系

> 06 注解体系

> 07 反射体系

> 08 Lambda & Stream API

> 09 日志框架

> 10 工具功能类

#### exploration: java.lang 语言基础包

--> annotation 注解包 (13)
Annotation
Documented
Inherited
Native
Repeatable
Retention
Target
ElementType
RetentionPolicy
AnnotationFormatError
AnnotationTypeMismatchException
IncompleteAnnotationException
package-info.java

--
> instrument (5)
ClassFileTransformer
Instrumentation
ClassDefinition
IllegalClassFormatException
UnmodifiableClassException

> invoke (38)
AbstractValidatingLambdaMetafactory
BoundMethodHandle
CallSite
ConstantCallSite
DelegatingMethodHandle
DirectMethodHandle
DontInline
ForceInline
InfoFromMemberName
InjectedProfile
InnerClassLambdaMetafactory
InvokeDynamic
InvokerBytecodeGenerator
Invokers
LambdaForm
LambdaFormBuffer
LambdaFormEditor
LambdaMetafactory
MemberName
MethodHandle
MethodHandleImpl
MethodHandleInfo
MethodHandleNatives
MethodHandleProxies
MethodHandles
MethodHandleStatics
MethodType
MethodTypeForm
MutableCallSite
ProxyClassesDumper
SerializedLambda
SimpleMethodHandle
Stable
SwitchPoint
TypeConvertingMethodAdapter
VolatileCallSite
LambdaConversionException
WrongMethodTypeException
package-info.java

> management (21)
BufferPoolMXBean
ClassLoadingMXBean
CompilationMXBean
GarbageCollectorMXBean
LockInfo
ManagementFactory
ManagementPermission
MemoryManagerMXBean
MemoryMXBean
MemoryNotificationInfo
MemoryPoolMXBean
MemoryType
MemoryUsage
MonitorInfo
OperatingSystemMXBean
PlatformComponent
PlatformLoggingMXBean
PlatformManagedObject
RuntimeMXBean
ThreadInfo
ThreadMXBean

> ref (8)
Finalizer
FinalizerHistogram
FinalReference
PhantomReference
Reference
ReferenceQueue
SoftReference
WeakReference

--> reflect 反射包 (32)
AccessibleObject
AnnotatedArrayType
AnnotatedElement
AnnotatedParameterizedType
AnnotatedType
AnnotatedTypeVariable
AnnotatedWildcardType
Array
Constructor
Executable
Field
GenericArrayType
GenericDeclaration
InvocationHandler
Member
Method
Modifier
Parameter
ParameterizedType
Proxy
ReflectAccess
ReflectPermission
Type
TypeVariable
WeakCache
WildcardType
GenericSignatureFormatError
InvocationTargetException
MalformedParameterizedTypeException
MalformedParametersException
UndeclaredThrowableException
package-info.java

-- > 基本支持类 (117)
> 11
Boolean
Byte
Double
Float
Integer
Short
Long
Number
String
Enum
Void

> 12
Object
Class
ClassLoader.java
ClassLoaderHelper
ClassValue
Package
Compiler
package-info.java

> 11
Appendable
Override
SuppressWarnings
SafeVarargs
Readable
FunctionalInterface
Deprecated
Comparable
Cloneable
AutoCloseable
Iterable                   // 迭代器
ApplicationShutdownHooks
AssertionStatusDirectives
RuntimePermission
SecurityManager

> 字符串工具类 16
AbstractStringBuilder
StringBuffer
StringBuilder
StringCoding
ConditionalSpecialCasing

Character
CharacterData
CharacterData0E
CharacterData00
CharacterData01
CharacterData02
CharacterDataLatin1
CharacterDataPrivateUse
CharacterDataUndefined
CharacterName
CharSequence

> 工具类 6
System
Terminator
Shutdown
Runtime
Math
StrictMath

> 线程进程 11
Runnable

Thread
ThreadDeath
ThreadGroup
ThreadLocal
InheritableThreadLocal
StackTraceElement

Process
ProcessBuilder
ProcessEnvironment
ProcessImpl

> 异常 & 错误  3 + 20 + 27
Error
Exception
Throwable

ExceptionInInitializerError
InstantiationError
AbstractMethodError
AssertionError
BootstrapMethodError
ClassCircularityError
ClassFormatError
InternalError
IllegalAccessError
NoSuchFieldError
NoSuchMethodError
NoClassDefFoundError
OutOfMemoryError
StackOverflowError
UnknownError
UnsatisfiedLinkError
UnsupportedClassVersionError
VerifyError
VirtualMachineError
LinkageError

ArrayIndexOutOfBoundsException
ArithmeticException
ArrayStoreException
ClassCastException
ClassNotFoundException
CloneNotSupportedException
EnumConstantNotPresentException
InstantiationException
InterruptedException
IllegalAccessException
IllegalArgumentException
IllegalMonitorStateException
IllegalStateException
IllegalThreadStateException
IncompatibleClassChangeError
IndexOutOfBoundsException
NegativeArraySizeException
NoSuchFieldException
NoSuchMethodException
NullPointerException
NumberFormatException
ReflectiveOperationException
RuntimeException
SecurityException
StringIndexOutOfBoundsException
TypeNotPresentException
UnsupportedOperationException


#### exploration: java.util 核心工具包

--> concurrent
>> atomic
AtomicBoolean
AtomicInteger
AtomicIntegerArray
AtomicIntegerFieldUpdater
AtomicLong
AtomicLongArray
AtomicLongFieldUpdater
AtomicMarkableReference
AtomicReference
AtomicReferenceArray
AtomicReferenceFieldUpdater
AtomicStampedReference
DoubleAccumulator
DoubleAdder
LongAccumulator
LongAdder
package-info.java
Striped64
>> locks
AbstractOwnableSynchronizer
AbstractQueuedLongSynchronizer
AbstractQueuedSynchronizer
Condition
Lock
LockSupport
package-info.java
ReadWriteLock
ReentrantLock
ReentrantReadWriteLock
StampedLock
>
AbstractExecutorService
ArrayBlockingQueue
BlockingDeque
BlockingQueue
BrokenBarrierException
Callable
CancellationException
CompletableFuture
CompletionException
CompletionService
CompletionStage
ConcurrentHashMap
ConcurrentLinkedDeque
ConcurrentLinkedQueue
ConcurrentMap
ConcurrentNavigableMap
ConcurrentSkipListMap
ConcurrentSkipListSet
CopyOnWriteArrayList
CopyOnWriteArraySet
CountDownLatch
CountedCompleter
CyclicBarrier
Delayed
DelayQueue
Exchanger
ExecutionException
Executor
ExecutorCompletionService
Executors
ExecutorService
ForkJoinPool
ForkJoinTask
ForkJoinWorkerThread
Future
FutureTask
LinkedBlockingDeque
LinkedBlockingQueue
LinkedTransferQueue
package-info.java
Phaser
PriorityBlockingQueue
RecursiveAction
RecursiveTask
RejectedExecutionException
RejectedExecutionHandler
RunnableFuture
RunnableScheduledFuture
ScheduledExecutorService
ScheduledFuture
ScheduledThreadPoolExecutor
Semaphore
SynchronousQueue
ThreadFactory
ThreadLocalRandom
ThreadPoolExecutor
TimeoutException
TimeUnit
TransferQueue

> function
BiConsumer
BiFunction
BinaryOperator
BiPredicate
BooleanSupplier
Consumer
DoubleBinaryOperator
DoubleConsumer
DoubleFunction
DoublePredicate
DoubleSupplier
DoubleToIntFunction
DoubleToLongFunction
DoubleUnaryOperator
Function
IntBinaryOperator
IntConsumer
IntFunction
IntPredicate
IntSupplier
IntToDoubleFunction
IntToLongFunction
IntUnaryOperator
LongBinaryOperator
LongConsumer
LongFunction
LongPredicate
LongSupplier
LongToDoubleFunction
LongToIntFunction
LongUnaryOperator
ObjDoubleConsumer
ObjIntConsumer
ObjLongConsumer
package-info.java
Predicate
Supplier
ToDoubleBiFunction
ToDoubleFunction
ToIntBiFunction
ToIntFunction
ToLongBiFunction
ToLongFunction
UnaryOperator

> jar
Attributes
JarEntry
JarException
JarFile
JarInputStream
JarOutputStream
JarVerifier
JavaUtilJarAccessImpl
Manifest
Pack200

> logging
ConsoleHandler
ErrorManager
FileHandler
Filter
Formatter
Handler
Level
Logger
Logging
LoggingMXBean
LoggingPermission
LoggingProxyImpl
LogManager
LogRecord
MemoryHandler
SimpleFormatter
SocketHandler
StreamHandler
XMLFormatter

> prefs
AbstractPreferences
BackingStoreException
Base64
InvalidPreferencesFormatException
NodeChangeEvent
NodeChangeListener
PreferenceChangeEvent
PreferenceChangeListener
Preferences
PreferencesFactory
WindowsPreferences
WindowsPreferencesFactory
XmlSupport

> regex
ASCII
Matcher
MatchResult
Pattern
PatternSyntaxException
UnicodeProp

> spi
CalendarDataProvider
CalendarNameProvider
CurrencyNameProvider
LocaleNameProvider
LocaleServiceProvider
ResourceBundleControlProvider
TimeZoneNameProvider

> stream
AbstractPipeline
AbstractShortCircuitTask
AbstractSpinedBuffer
AbstractTask
BaseStream
Collector
Collectors
DistinctOps
DoublePipeline
DoubleStream
FindOps
ForEachOps
IntPipeline
IntStream
LongPipeline
LongStream
MatchOps
Node
Nodes
package-info.java
PipelineHelper
ReduceOps
ReferencePipeline
Sink
SliceOps
SortedOps
SpinedBuffer
Stream
StreamOpFlag
Streams
StreamShape
StreamSpliterators
StreamSupport
TerminalOp
TerminalSink
Tripwire

> zip
Adler32
CheckedInputStream
CheckedOutputStream
Checksum
CRC32
DataFormatException
Deflater
DeflaterInputStream
DeflaterOutputStream
GZIPInputStream
GZIPOutputStream
Inflater
InflaterInputStream
InflaterOutputStream
ZipCoder
ZipConstants
ZipConstants64
ZipEntry
ZipError
ZipException
ZipFile
ZipInputStream
ZipOutputStream
ZipUtils
ZStreamRef

--> 基本支持类 (116)

> 集合框架 63

Collection
List
Map
Set
Queue
Deque
Vector
Stack

Iterator

AbstractCollection
AbstractList.java
AbstractSequentialList
ArrayList
LinkedHashMap
LinkedHashSet
LinkedList
ListIterator
ListResourceBundle
AbstractMap
EnumMap
HashMap
Hashtable
IdentityHashMap
NavigableMap
SortedMap
WeakHashMap
TreeMap
AbstractSet
BitSet
JumboEnumSet
NavigableSet
RegularEnumSet
SortedSet
TreeSet
EnumSet
HashSet
AbstractQueue
ArrayDeque
PriorityQueue

Enumeration
PrimitiveIterator

Collections
ArrayPrefixHelpers
Arrays
ArraysParallelSortHelpers
ComparableTimSort
Comparator
Comparators
Currency
Dictionary
DualPivotQuicksort
Formattable
FormattableFlags
Formatter
RandomAccess
ResourceBundle
ServiceLoader
Spliterator
Spliterators
SplittableRandom
StringJoiner
StringTokenizer
Tripwire

> 工具类 17
Base64
Random
Scanner
UUID
Objects
Observable
Observer

DoubleSummaryStatistics
IntSummaryStatistics
LongSummaryStatistics

Optional
OptionalDouble
OptionalInt
OptionalLong

Properties
PropertyPermission.java
PropertyResourceBundle

> 事件 3
EventObject
EventListener
EventListenerProxy

> 时间工具类 11
Date
Timer.java
TimerTask
TimeZone
TimSort
SimpleTimeZone
Locale
LocaleISOData
Calendar
GregorianCalendar
JapaneseImperialCalendar

> 错误 & 异常 22
ServiceConfigurationError
FormatterClosedException
ConcurrentModificationException
DuplicateFormatFlagsException
EmptyStackException
FormatFlagsConversionMismatchException
IllegalFormatCodePointException
IllegalFormatConversionException
IllegalFormatException
IllegalFormatFlagsException
IllegalFormatPrecisionException
IllegalFormatWidthException
IllformedLocaleException
InputMismatchException
InvalidPropertiesFormatException
MissingFormatArgumentException
MissingFormatWidthException
MissingResourceException
NoSuchElementException
TooManyListenersException
UnknownFormatConversionException
UnknownFormatFlagsException


#### exploration: java.io & java.nio & java.net (I/O网络体系)

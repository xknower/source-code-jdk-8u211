package com.xknower.utils;

/**
 * Buffer 缓存工具类实体
 *
 * @author xknower
 */
public class BufferEntity {

    /**
     * 缓存数据
     */
    private byte[] buff;

    /**
     * 数据容量标识 [真实数据偏移值, 写偏移]
     */
    private int offset;
    /**
     * 最大总容量 [分配容量]
     */
    private int capacity;
    /**
     * 限制最大容量值
     */
    private int limit = 0x00FFFFF;

    /**
     * 读指针 [改变指针可以反复读取, 读指针不能大于 数据容量标识]
     */
    private int pos;

    /**
     * 操作标识
     */
    private int mark;

    public BufferEntity() {
        allocate(255);
    }

    /**
     * 获取所有可读数据
     */
    public byte[] getAll() {
        byte[] data = new byte[offset];
        System.arraycopy(buff, 0, data, 0, offset);
        return data;
    }

    public boolean put(byte[] data) {
        if (data != null && residueAllocate(data.length)) {
            //
            return arraycopy(data);
        }
        return false;
    }

    //

    public int len() {
        // 数据长度
        return offset;
    }

    public int capacity() {
        // 缓存容量
        return capacity;
    }

    private int limit() {
        return limit;
    }

    /**
     * 判断容量并自动扩容
     *
     * @param len 待写入数据长度
     * @return true 数据可写入, false 数据不可写入
     */
    private boolean residueAllocate(int len) {
        if ((len + 0xFF) > capacity - offset) {
            return allocate(len);
        }
        return true;
    }

    /**
     * 分配缓存
     */
    private boolean allocate(int cap) {
        return allocateArray(cap) != null && this.buff != null;
    }

    private byte[] allocateArray(int cap) {
        if (cap < 0) {
            cap = 0xFF;
        }
        if (cap > limit) {
            // 超出最大可分配容量
            return this.buff;
        }
        //
        byte[] source = new byte[cap + capacity];
        if (buff != null) {
            // 复制旧数据
            System.arraycopy(buff, 0, source, 0, buff.length);
        }
        this.buff = source;
        if (capacity == 0) {
            // 初次分配, 参数重置
            this.offset = 0;
            this.pos = 0;
            this.mark = 0;
        }
        this.capacity = source.length;
        return this.buff;
    }

    // 写入数据

    private boolean arraycopy(byte[] source) {
        return arraycopy(source, 0, source.length);
    }

    private boolean arraycopyByPostion(byte[] source, int sourcePos, int endPos) {
        // 计算拷贝源数据长度
        int length = (endPos == 0 ? source.length : endPos) - sourcePos;
        return arraycopy(source, sourcePos, length);
    }

    /**
     * 将源数据拷贝搭目的数据
     *
     * @param source    the source array.
     * @param sourcePos starting position in the source array.
     * @param length    the number of array elements to be copied.
     */
    private boolean arraycopy(byte[] source, int sourcePos, int length) {
        if ((source.length - sourcePos < length) || (buff.length - offset < length)) {
            return false;
        }

        // the destination array.
        // starting position in the destination data.
        System.arraycopy(source, sourcePos, buff, offset, length);
        //
        offset = offset + length;

        return true;
    }

    //

//    public BufferEntity() {
//        buff = allocateArray(1536);
//        buff.mark();
//    }
//
//    public BufferEntity(int len) {
//        buff = allocateArray(len);
//        buff.mark();
//    }
//
//    public BufferEntity(byte[] bytes) {
//        if (bytes.length > 1024) {
//            buff = allocateArray(bytes.length + 100);
//        } else {
//            buff = allocateArray(1024);
//        }
//        buff.mark();
//        buff.put(bytes);
//        buff.limit(bytes.length);
//        buff.reset();
//    }
//
//    /**
//     * 清除
//     */
//    public void clear() {
//        // limit=capacity , position=0,重置mark
//        buff.clear();
//        // 取当前的position的快照标记mark
//        buff.mark();
//    }
//
//    /**
//     * 判断缓冲区是否还有数据
//     */
//    public boolean hasRemain() {
//        return buff.remaining() > 0;
//    }

    /********** ********** 将数据放入缓冲区 ********** **********/

//    public void put(byte a) {
//        buff.put(a);
//    }
//
//    public void put(short a) {
//        buff.putShort(a);
//    }
//
//    public void put(byte[] byteData) {
//        buff.put(byteData);
//    }
//
//    public void put(int intData) {
//        buff.putInt(intData);
//    }
//
//    public void putShort(int intShortData) {
//        buff.putShort((short) intShortData);
//    }
//
//    public void put(String str) {
//        try {
//            // US-ASCII
//            byte[] b = str.getBytes("gbk");
//            buff.put(b);
//        } catch (Exception e) {
//            log.error(String.format("gbk put"), e);
//        }
//    }
//
//    public void put(String str, int len) {
//        byte[] result = new byte[len];
//        try {
//            byte[] b = str.getBytes("gbk");
//            System.arraycopy(b, 0, result, 0, b.length);
//            for (int m = b.length; m < len; m++) {
//                result[m] = 0;
//            }
//            buff.put(result);
//        } catch (Exception e) {
////            log.error(String.format("gbk put"), e);
//        }
//    }

    /********** ********** 从缓冲区中取值 ********** **********/

//    /**
//     * 取一定字节长度的byte字节数组
//     */
//    public byte[] gets(int len) {
//        byte[] data = new byte[len];
//        buff.get(data);
//        return data;
//    }
//
//    /**
//     * byte - 1 字节
//     */
//    public byte get() {
//        return buff.get();
//    }
//
//    /**
//     * Short 长度 - 2字节
//     */
//    public short getShort() {
//        return buff.getShort();
//    }
//
//    /**
//     * Int 长度 - 4字节
//     */
//    public int getInt() {
//        return buff.getInt();
//    }
//
//    /**
//     * hex转浮点
//     */
//    public float getFloat() {
//        return buff.getFloat();
//    }
//
//    /**
//     * 1、字节
//     * 将data字节型数据转换为0~255 (0xFF 即BYTE)
//     */
//    public int getUnsignedByte() {
//        return buff.get() & 0x0FF;
//    }
//
//    /**
//     * 2、字
//     * 将data字节型数据转换为0~65535 (0xFFFF 即 WORD)
//     */
//    public int getUnsignedShort() {
//        short t = buff.getShort();
//        return t & 0xffff;
//    }
//
//    /**
//     * 3、双字 DWORD
//     */
//    public long getUnsignedInt() {
//        return buff.getInt() & 0x0FFFFFFFF;
//    }
//
//    /**
//     * 4、一定长度字节, 字节序转化为十六进制字符串
//     */
//    public String encodeHexString(int len) {
//        byte[] bytes = this.gets(len);
//        StringBuilder bcd = new StringBuilder();
//        for (int m = 0; m < len; m++) {
//            bcd.append(String.format("%02X", bytes[m]));
//        }
//        return bcd.toString();
//    }
//
//    /**
//     * 一定长度字节的GBK转码后字符串
//     */
//    public String getString(int len) {
//        return ToolBuff.decodeToGBKStr(this.gets(len), 0, len);
//    }
//
//    /**
//     * 字符串
//     */
//    public String getString() {
//        byte[] bytes = this.array();
//        return ToolBuff.decodeToGBKStr(bytes, 0, bytes.length);
//    }

//    public byte[] array() {
//        int pos = buff.position();
//        byte[] data = new byte[pos];
//        buff.reset();
//        buff.get(data);
//        return data;
//    }

//    /**
//     * 设置数据定位指针
//     *
//     * @param position
//     */
//    public void setPosition(int position) {
//        buff.position(position);
//    }

//    /**
//     * 获取一定长度字节数组转化的二进制位字符串 ,字符串长度为字节长度8倍
//     */
//    public String getByteArrToBinStr(int len) {
//        byte[] b = gets(len);
//        return ToolBuff.byteArrToBinStr(b);
//    }

    /**
     * 取一个值的前后四位的值
     *
     * @param b xu
     * @return 返回字节数组 0 为前四位值 、1 为后四位值
     */
    public static byte[] getPreAndAfter(byte b) {
        byte[] result = new byte[2];
        // 00001111用于取出后四位
        byte num = 0xF;
        // 将高位移到低位再取值
        byte behind = (byte) ((b >> 4) & num);
        // 与掉前四位
        byte front = (byte) (b & num);
        // 转化为字节字符
        // char[ ] arr=new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        // "前四位:"+arr[front]+" 后四位:"+arr[behind];
        result[0] = behind;
        result[1] = front;
        return result;
    }
}

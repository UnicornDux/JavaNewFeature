package com.wp.protocol.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 *
 */
public class ByteUtils {

    public static byte[] intToByteArray(int value) {
       byte[] result = new byte[4];
       result[0] = (byte)((value >> 24) & 0xFF);
       result[1] = (byte)((value >> 16) & 0xFF);
       result[2] = (byte)((value >> 8) & 0xFF);
       result[3] = (byte)(value & 0xFF);
       return result;
    }

    public static int bytesToInt(byte byte1, byte byte2) {
        return (byte1 & 0xFF) << 8 | (byte2 & 0xFF);
    }

    public static byte[] shortToByteArray(short number) {
        byte[] byteArray = new byte[2];
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(number);
        buffer.flip(); // 为读取做准备
        buffer.get(byteArray);
        return byteArray;
    }

    public static boolean compareBytes(byte[] first, byte[] second) {
        for(int i = 0; i < (first.length < second.length ? first.length : second.length); ++i) {
            if (first[i] != second[i]) {
                return false;
            }
        }

        return first.length == second.length;
    }

    public static byte[] longToByte4BigEndian(long l) {
        byte[] b = new byte[]{(byte)((int)(l >> 24 & 4278190080L)), (byte)((int)(l >> 16 & 16711680L)), (byte)((int)(l >> 8 & 65280L)), (byte)((int)(l & 255L))};
        return b;
    }

    public static long byte4ToLongBigEndian(byte[] b) {
        long l = (long)b[3] & 255L;
        l |= (long)b[2] << 8 & 65280L;
        l |= (long)b[1] << 16 & 16711680L;
        l |= (long)b[0] << 24 & 4278190080L;
        return l;
    }

    public static UUID byte16ToUuidBigEndian(byte[] b) {
        return new UUID(byte8ToLongBigEndian(subBytes(b, 0, 8)), byte8ToLongBigEndian(subBytes(b, 8, 8)));
    }

    public static byte[] uuidToByte16BigEndian(UUID uuid) {
        long l = uuid.getLeastSignificantBits();
        long l2 = uuid.getMostSignificantBits();
        return combineByteArray(longToByte8BigEndian(l2), longToByte8BigEndian(l));
    }

    public static UUID byte8ToUuidBigEndian(byte[] b) {
        return new UUID(0L, byte8ToLongBigEndian(b));
    }

    public static byte[] uuidToByte8BigEndian(UUID uuid) {
        long l = uuid.getLeastSignificantBits();
        return longToByte8BigEndian(l);
    }

    public static long byte8ToLongBigEndian(byte[] b) {
        long l = (long)b[7] & 255L;
        l |= (long)b[6] << 8 & 65280L;
        l |= (long)b[5] << 16 & 16711680L;
        l |= (long)b[4] << 24 & 4278190080L;
        l |= (long)b[3] << 32 & 1095216660480L;
        l |= (long)b[2] << 40 & 280375465082880L;
        l |= (long)b[1] << 48 & 71776119061217280L;
        l |= (long)b[0] << 56 & -72057594037927936L;
        return l;
    }

    public static byte[] longToByte8BigEndian(long l) {
        byte[] b = new byte[]{(byte)((int)(l >> 56 & 255L)), (byte)((int)(l >> 48 & 255L)), (byte)((int)(l >> 40 & 255L)), (byte)((int)(l >> 32 & 255L)), (byte)((int)(l >> 24 & 255L)), (byte)((int)(l >> 16 & 255L)), (byte)((int)(l >> 8 & 255L)), (byte)((int)(l & 255L))};
        return b;
    }

    public static int byte4ToIntBigEndian(byte[] b) {
        int i = b[3] & 255;
        i |= b[2] << 8 & '\uff00';
        i |= b[1] << 16 & 16711680;
        i |= b[0] << 24 & -16777216;
        return i;
    }

    public static byte[] intToByte4BigEndian(int i) {
        byte[] res = new byte[]{(byte)(i >> 24 & 255), (byte)(i >> 16 & 255), (byte)(i >> 8 & 255), (byte)(i & 255)};
        return res;
    }

    public static int byte3ToIntBigEndian(byte[] b) {
        int i = b[2] & 255;
        i |= b[1] << 8 & '\uff00';
        i |= b[1] << 16 & 16711680;
        return i;
    }

    public static byte[] intToByte3BigEndian(int i) {
        byte[] res = new byte[]{(byte)(i >> 16 & 255), (byte)(i >> 8 & 255), (byte)(i & 255)};
        return res;
    }

    public static String toString(byte[] data) {
        StringBuffer buffer = new StringBuffer();
        byte[] var2 = data;
        int var3 = data.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            String d = Integer.toHexString(b & 255);
            if (d.length() > 1) {
                buffer.append(d + " ");
            } else {
                buffer.append("0" + d + " ");
            }
        }

        return buffer.toString();
    }

    public static byte[] subBytes(byte[] data, int start, int len) {
        byte[] res = new byte[len];

        for(int i = 0; i < len; ++i) {
            res[i] = data[start + i];
        }

        return res;
    }

    public static byte[] subBytes(byte[] data, int start) {
        int len = data.length - start;
        return subBytes(data, start, len);
    }

    public static long bytesToLong(byte[] b) {
        long l = (long)b[0] << 56 & -72057594037927936L;
        l |= (long)b[1] << 48 & 71776119061217280L;
        l |= (long)b[2] << 40 & 280375465082880L;
        l |= (long)b[3] << 32 & 1095216660480L;
        l |= (long)b[4] << 24 & 4278190080L;
        l |= (long)b[5] << 16 & 16711680L;
        l |= (long)b[6] << 8 & 65280L;
        l |= (long)b[7] & 255L;
        return l;
    }

    public static byte[] longToBytes(long l) {
        byte[] b = new byte[]{(byte)((int)(l >>> 56)), (byte)((int)(l >>> 48)), (byte)((int)(l >>> 40)), (byte)((int)(l >>> 32)), (byte)((int)(l >>> 24)), (byte)((int)(l >>> 16)), (byte)((int)(l >>> 8)), (byte)((int)l)};
        return b;
    }

    public static byte[] combineByteArray(byte[]... bytes) {
        int len = 0;
        byte[][] var2 = bytes;
        int pos = bytes.length;

        for(int var4 = 0; var4 < pos; ++var4) {
            byte[] bs = var2[var4];
            if (bs != null) {
                len += bs.length;
            }
        }

        byte[] res = new byte[len];
        pos = 0;
        byte[][] var13 = bytes;
        int var14 = bytes.length;

        for(int var6 = 0; var6 < var14; ++var6) {
            byte[] bs = var13[var6];
            if (bs != null) {
                byte[] var8 = bs;
                int var9 = bs.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    byte b = var8[var10];
                    res[pos++] = b;
                }
            }
        }

        return res;
    }

    public static byte[] uuidToBytes(UUID uuid) {
        return combineByteArray(longToBytes(uuid.getMostSignificantBits()), longToBytes(uuid.getLeastSignificantBits()));
    }

    public static UUID bytesToUuid(byte[] data) {
        if (data != null && data.length >= 16) {
            byte[] bMost = subBytes(data, 0, 8);
            byte[] bLeast = subBytes(data, 8, 8);
            return new UUID(bytesToLong(bMost), bytesToLong(bLeast));
        } else {
            return null;
        }
    }

    public static byte[] intToBytes2(int i) {
        byte[] res = new byte[]{(byte)(i >> 8 & 255), (byte)(i & 255)};
        return res;
    }

    public static byte[] intToBytes2SmallDian(int i) {
        byte[] res = new byte[]{(byte)(i & 255), (byte)(i >> 8 & 255)};
        return res;
    }

    public static int bytesToIntBigEndian(byte[] b) {
        int res = (int)((long)(b[0] << 24) & 4278190080L);
        res = (int)((long)res | (long)(b[1] << 16) & 16711680L);
        res = (int)((long)res | (long)(b[2] << 8) & 65280L);
        res = (int)((long)res | (long)b[3] & 255L);
        return res;
    }

    public static int bytesToIntSmallEndian(byte[] b) {
        int res = (int)((long)(b[3] << 24) & 4278190080L);
        res = (int)((long)res | (long)(b[2] << 16) & 16711680L);
        res = (int)((long)res | (long)(b[1] << 8) & 65280L);
        res = (int)((long)res | (long)b[0] & 255L);
        return res;
    }

    public static byte[] intToBytesBigEndian(int i) {
        byte[] res = new byte[]{(byte)((int)((long)(i >> 24) & 255L)), (byte)((int)((long)(i >> 16) & 255L)), (byte)((int)((long)(i >> 8) & 255L)), (byte)((int)((long)i & 255L))};
        return res;
    }

    public static int bytesToInt(byte[] b) {
        int res = (int)((long)(b[3] << 24) & 4278190080L);
        res = (int)((long)res | (long)(b[2] << 16) & 16711680L);
        res = (int)((long)res | (long)(b[1] << 8) & 65280L);
        res = (int)((long)res | (long)b[0] & 255L);
        return res;
    }

    public static byte[] intToBytes(int i) {
        byte[] res = new byte[]{(byte)((int)((long)i & 255L)), (byte)((int)((long)(i >> 8) & 255L)), (byte)((int)((long)(i >> 16) & 255L)), (byte)((int)((long)(i >> 24) & 255L))};
        return res;
    }

    public static short bytesToShort(byte[] b) {
        short res = (short)((int)((long)(b[1] << 8) & 65280L));
        res = (short)((int)((long)res | (long)b[0] & 255L));
        return res;
    }

    public static byte[] shortToBytes(short i) {
        byte[] res = new byte[]{(byte)((int)((long)i & 255L)), (byte)((int)((long)(i >> 8) & 255L))};
        return res;
    }

    public static short bytesToShortBigEndian(byte[] b) {
        short res = (short)((int)((long)(b[0] << 8) & 65280L));
        res = (short)((int)((long)res | (long)b[1] & 255L));
        return res;
    }

    public static byte[] shortToBytesBigEndian(short i) {
        byte[] res = new byte[]{(byte)((int)((long)(i >> 8) & 255L)), (byte)((int)((long)i & 255L))};
        return res;
    }

    public static byte[] wrapData(byte[] data, int len) {
        byte[] res = new byte[len];

        for(int i = 0; i < len; ++i) {
            if (i < data.length) {
                res[i] = data[i];
            } else {
                res[i] = 0;
            }
        }

        return res;
    }

    public static byte[] getByteBits(byte data) {
        byte[] result = new byte[8];

        for(int i = 0; i < 8; ++i) {
            result[i] = (byte)(data >> i & 1);
        }

        return result;
    }

    public static byte[] getBytesWithBuffer(ByteBuffer buffer) {
        if (buffer == null) {
            return null;
        } else {
            byte[] bytes = new byte[buffer.position()];

            for(int i = 0; i < buffer.position(); ++i) {
                bytes[i] = buffer.array()[i];
            }

            return bytes;
        }
    }
}

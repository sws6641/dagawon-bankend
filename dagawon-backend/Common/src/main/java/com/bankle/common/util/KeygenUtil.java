package com.bankle.common.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.Date;

/**
 * KeygenUtil
 *
 * @author sh.lee
 * @date 2023-09-18
 **/
public class KeygenUtil {
    private static final Object _syncObj48BitTime = new Object();
    private static final Object _syncObjWorkerKey = new Object();
    private static final Object _syncObjGenDate = new Object();
    private static long _last48BitTime = 0;
    private static long _lastWorkerKey = 0;
    private static long __lastGenDate = 0;

    /**
     * 이 함수를 통해서 생성된 문자열은 항상 Unique 함을 보장함.<br/>
     * 길이는 8 character<br/>
     * millisecond 단위 시간값을 포함하기에, 동시에 다수의 요청이 있을 경우, lock 될 수 있음.
     */
    public static String get48BitTimeBase64StrSupportUnique() {
        long curTime;
        synchronized (_syncObj48BitTime) {
            curTime = System.currentTimeMillis();
            if (curTime <= _last48BitTime) {
                curTime = _last48BitTime + 1;
            }
            _last48BitTime = curTime;
        }

        byte[] bit48Buffer = new byte[6];
        {
            ByteBuffer buf = ByteBuffer.allocate(8);
            buf.order(ByteOrder.BIG_ENDIAN);
            buf.putLong(curTime);
            byte[] result = buf.array();

            // long 형의 하위 6byte만 복사.
            System.arraycopy(result, 2, bit48Buffer, 0, bit48Buffer.length);
        }
        return Base64.encodeBase64String(bit48Buffer);
    }

    /**
     * 이 함수를 통해서 생성된 문자열은 항상 Unique 함을 보장함.<br/>
     * 길이는 22 ~ 24 character<br/>
     * millisecond 단위 시간값을 포함하기에, 동시에 다수의 요청이 있을 경우, lock 될 수 있음.
     */
    public static String getWorkerKeySupportUnique() {
        long curTime;
        synchronized (_syncObjWorkerKey) {
            curTime = System.currentTimeMillis();
            if (curTime <= _lastWorkerKey) {
                curTime = _lastWorkerKey + 1;
            }
            _lastWorkerKey = curTime;
        }

        curTime = curTime * 17 + 1;
        byte[] timeBuffer;
        {
            ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
            buffer.putLong(curTime);
            timeBuffer = buffer.array();
        }

        byte[] randBuffer;
        {
            SecureRandom randomSecureRandom = new SecureRandom();
            randBuffer = new byte[Long.BYTES];
            randomSecureRandom.nextBytes(randBuffer);
        }

        byte[] tmpArr = new byte[Long.BYTES * 2];
        for (int ii = 0; ii < Long.BYTES; ++ii) {
            tmpArr[ii * 2] = (byte) randBuffer[ii];
            tmpArr[ii * 2 + 1] = (byte) timeBuffer[ii];
        }
        return Base64.encodeBase64URLSafeString(tmpArr);
    }

    /**
     * AES256 Secret key
     */
    public static String genAES256SecretKey() {
        return genKeyAsBase64(32);
    }

    /**
     * AES IV
     */
    public static String genAESIV() {
        return genKeyAsBase64(16);
    }

    /**
     * SHA256 SaltKey
     */
    public static String genSHA256SaltKey() {
        return genKeyAsBase64(32);
    }

    private static String genKeyAsBase64(int keySizeBytes) {
        SecureRandom randomSecureRandom = new SecureRandom();
        byte[] buffer = new byte[keySizeBytes];
        randomSecureRandom.nextBytes(buffer);
        return Base64.encodeBase64String(buffer);
    }

    public static String genOAuthCode() {
        return genKeyAsBase64URLSafe(15);
    }

    public static String genRandomFileName() {
        return genKeyAsBase64URLSafe(15);
    }

    public static String genNewToken() {
        return genKeyAsBase64URLSafe(30);
    }

    /**
     * PNV Hos Code
     */
    public static String genPnvHospitalCode() {
        return genKeyAsBase64URLSafe(6);
    }

    private static String genKeyAsBase64URLSafe(int keySizeBytes) {
        SecureRandom randomSecureRandom = new SecureRandom();
        byte[] buffer = new byte[keySizeBytes];
        randomSecureRandom.nextBytes(buffer);
        return Base64.encodeBase64URLSafeString(buffer);
    }

    public static Date getDateSupportUnique() {
        long curTime;
        synchronized (_syncObjGenDate) {
            curTime = System.currentTimeMillis();
            if (curTime <= __lastGenDate) {
                curTime = __lastGenDate + 1;
            }
            __lastGenDate = curTime;
        }
        return new Date(curTime);
    }
}


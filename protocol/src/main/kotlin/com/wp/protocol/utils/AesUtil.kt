package com.wp.protocol.utils

import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * author : kyle
 * e-mail : 1239878682@qq.com
 * date   : 2024/1/30
 * 看了我的代码，感动了吗?
 */
object AesUtil {

    //    var iv = byteArrayOf(
//        0xBA.toByte(), 0xF1.toByte(), 0x03, 0x09, 0x0A, 0xD7.toByte(), 0x0C, 0x2F,
//        0x9A.toByte(), 0xAD.toByte(), 0x6E, 0x88.toByte(), 0x01, 0x14, 0xC4.toByte(), 0x01
//    )
//    val key = byteArrayOf(
//        0xDA.toByte(), 0x87.toByte(), 0xDA.toByte(), 0xA6.toByte(), 0xDA.toByte(), 0x67, 0xE2.toByte(), 0x87.toByte(),
//        0x5A, 0x83.toByte(), 0xDA.toByte(), 0xB7.toByte(), 0xDA.toByte(), 0x87.toByte(), 0x29, 0x43,
//        0xDA.toByte(), 0x87.toByte(), 0x18, 0x54, 0xDA.toByte(), 0x07, 0xDA.toByte(), 0x87.toByte()
//    )
    private val key = "13src82g13src82g".toByteArray()
    private val iv = "31vrxt70xryu0qxe".toByteArray()


    fun encode(data: ByteArray): ByteArray? {
        return doCipher(data, Cipher.ENCRYPT_MODE)
    }

    fun decode(data: ByteArray): ByteArray? {
        return doCipher(data, Cipher.DECRYPT_MODE)
    }

    /**
     * 进行加密/解密
     *
     * @param data 原始数据
     * @param key 密钥
     * @param opmode 加密/解密[Cipher.ENCRYPT_MODE],[Cipher.DECRYPT_MODE]
     */
    fun doCipher(data: ByteArray, opmode: Int): ByteArray? {
        var bytes: ByteArray? = null
        try {
            val secretKey: SecretKey = SecretKeySpec(key, "AES")
            //AES 是加密方式、CBC 是工作模式、PKCS5Padding 填充方式
            val cipher = Cipher.getInstance("AES/CFB128/NoPadding")
            cipher.init(opmode, secretKey, IvParameterSpec(iv))
            bytes = cipher.doFinal(data)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        }
        return bytes
    }

    fun getVerify(bytes: List<Byte>): Byte {
        var j: Int
        var crc: Byte = 0x00
        var i = 0
        while (i < bytes.size) {
            crc = (crc.toInt() xor bytes[i].toInt()).toByte()
            j = 8
            while (j > 0) {
                if (crc.toInt() and 0x80 != 0) {
                    crc = (crc.toInt() shl 1).toByte()
                    crc = (crc.toInt() xor 0x31).toByte()
                } else {
                    crc = (crc.toInt() shl 1).toByte()
                }
                j--
            }
            i++
        }
        return crc
    }

}
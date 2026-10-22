package com.todo;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class CriptoUtil {
    private static final String ALGO = "AES/GCM/NoPadding";
    private static final int IV_LEN = 12;
    private static final int TAG_BITS = 128;
    private static final SecretKeySpec KEY;

    static {
        try {
            // Chave fixa para o projeto (em produção usaríamos variáveis de ambiente)
            String chaveSecreta = "chave_super_secreta_do_projeto_todo";
            byte[] kb = MessageDigest.getInstance("SHA-256").digest(chaveSecreta.getBytes(StandardCharsets.UTF_8));
            KEY = new SecretKeySpec(kb, "AES");
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String cifrar(String texto) {
        if (texto == null || texto.trim().isEmpty()) return null;
        try {
            byte[] iv = new byte[IV_LEN];
            new SecureRandom().nextBytes(iv);
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, KEY, new GCMParameterSpec(TAG_BITS, iv));
            byte[] ct = c.doFinal(texto.getBytes(StandardCharsets.UTF_8));
            byte[] out = new byte[IV_LEN + ct.length];
            System.arraycopy(iv, 0, out, 0, IV_LEN);
            System.arraycopy(ct, 0, out, IV_LEN, ct.length);
            return Base64.getEncoder().encodeToString(out);
        } catch (Exception e) { throw new RuntimeException("Erro ao cifrar", e); }
    }

    public static String decifrar(String b64) {
        if (b64 == null || b64.isEmpty()) return null;
        try {
            byte[] in = Base64.getDecoder().decode(b64);
            byte[] iv = Arrays.copyOfRange(in, 0, IV_LEN);
            byte[] ct = Arrays.copyOfRange(in, IV_LEN, in.length);
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, KEY, new GCMParameterSpec(TAG_BITS, iv));
            return new String(c.doFinal(ct), StandardCharsets.UTF_8);
        } catch (Exception e) { throw new RuntimeException("Erro ao decifrar", e); }
    }
}
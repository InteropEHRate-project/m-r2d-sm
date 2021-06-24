package eu.interopehrate.mr2dsm.util;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class SecurityUtil {
    private final static String KEYSTORE_NAME = "keystore.p12";
    private final static String KEYSTORE_PASSWORD = "interop";

    public static boolean isJWT(String jwt) {
        String[] jwtSplitted = jwt.split("\\.");
        if (jwtSplitted.length != 3) // The JWT is composed of three parts
            return false;
        try {
            String jsonFirstPart = new String(Base64.decode(jwtSplitted[0], Base64.DEFAULT));
            JSONObject firstPart = new JSONObject(jsonFirstPart); // The first part of the JWT is a JSON
            if (!firstPart.has("alg")) // The first part has the attribute "alg"
                return false;
            String jsonSecondPart = new String(Base64.decode(jwtSplitted[1], Base64.DEFAULT));
            JSONObject secondPart = new JSONObject(jsonSecondPart); // The first part of the JWT is a JSON
        }catch (JSONException err){
            return false;
        }
        return true;
    }

    public static Claims decode(String jwt, String key) throws InvalidKeySpecException {
        PublicKey publicKey = null;
        try {
            //Remove extra Strings
            key = key.replaceAll("\r", "");
            key = key.replace("-----BEGIN PUBLIC KEY-----\n","");
            key = key.replace("-----END PUBLIC KEY-----", "");

            //Decode the public key and convert it to bytes
            byte[] publicKeyBytes = Base64.decode(key,Base64.DEFAULT);

            // create a key object from the bytes
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            Log.e("jwt decode", e.getMessage());
        }

        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jwt).getBody();
    }

    public static boolean isKeystore(String keystoreStr) {
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            String base64string = new String(Base64.decode(keystoreStr, Base64.DEFAULT));
            byte[] decoded = Base64.decode(base64string, Base64.DEFAULT);
            keystore.load(new ByteArrayInputStream(decoded), KEYSTORE_PASSWORD.toCharArray());
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void storeKeystore(Context context, String keystoreStr) throws IOException {
        String keyStorePath = getKeystorePath(context);
        String base64string = new String(Base64.decode(keystoreStr, Base64.DEFAULT));
        FileOutputStream stream = new FileOutputStream(keyStorePath + ".p12");
        stream.write(Base64.decode(base64string, Base64.DEFAULT));
    }

    private static String getKeystorePath(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/"
                + KEYSTORE_NAME;
    }

}

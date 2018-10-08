package com.sida.xiruo.xframework.cert;

import com.sida.xiruo.common.components.encrypt.Base64;
import com.sida.xiruo.xframework.cache.redis.JedisClusterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

public final class JskAndCerFileGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(JskAndCerFileGenerator.class);
    private static final String JKS_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.jks";
    private static final String CER_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.cer";
    private static final String PASSWORD = "sidasida";
    private static final String ALIAS = "sida";
    private static final String KEYTOOL_CMD = "C:/Java/jdk1.8.0_121/bin/keytool";

    public static void main(String[] args) {
        try {
//            buildKeyAndSaveToJksFile();
//            Thread.sleep(3000);
//            exportCerFile();
//            Thread.sleep(3000);
            readJks();
            readCer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void executeCommand(String[] arstringCommand) {
        try {
            Runtime.getRuntime().exec(arstringCommand);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //生成密钥并保存到jks文件
    public static void buildKeyAndSaveToJksFile() {
        String[] command = new String[]{
                "cmd ",
                "/k",
                "start", // cmd Shell命令
                KEYTOOL_CMD,
                "-genkeypair", //表示生成密钥
                "-alias", //要处理的条目的别名（jks文件别名）
                ALIAS,
                "-keyalg", //密钥算法名称(如 RSA DSA（默认是DSA）)
                "RSA",
//                "-keysize",//密钥位大小(长度)
//                "2048",
//                "-sigalg", //签名算法名称
//                "SHA1withRSA",
                "-dname",// 唯一判别名,CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称),
                // ST=(州或省份名称), C=(单位的两字母国家代码)"
                "CN=蒋凌锋, OU=深圳市工业设计行业协会, O=设计云, L=深圳市, ST=广东省, C=中华人民共和国",
                "-validity", // 有效天数
                "36500",
                "-keypass",// 密钥口令(私钥的密码)
                PASSWORD,
                "-keystore", //密钥库名称(jks文件路径)
                JKS_FILE,
                "-storepass", // 密钥库口令(jks文件的密码)
                PASSWORD,
                "-v"// 详细输出（秘钥库中证书的详细信息）
        };
        executeCommand(command);
    }
    //从jks文件中导出证书文件
    public static void exportCerFile() {
        String[] command = new String[]{
                "cmd ", "/k",
                "start", // cmd Shell命令
                KEYTOOL_CMD,
                "-exportcert", // - export指定为导出操作
                "-alias", // -alias指定别名，这里是ss
                ALIAS,
                "-keystore", // -keystore指定keystore文件，这里是d:/demo.keystore
                JKS_FILE,
                "-rfc",
                "-file",//-file指向导出路径
                CER_FILE,
                "-storepass",// 指定密钥库的密码
                PASSWORD
        };
        executeCommand(command);
    }
    //读取jks文件获取公、私钥
    public static void readJks() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(JKS_FILE), PASSWORD.toCharArray());
        Enumeration<String> aliases = keyStore.aliases();
        String alias = null;
        while (aliases.hasMoreElements()) {
            alias = aliases.nextElement();
        }
        System.out.println("jks文件别名是：" + alias);
        PrivateKey key = (PrivateKey) keyStore.getKey(alias, PASSWORD.toCharArray());
        System.out.println("jks文件中的私钥是：" + new String(Base64.encode(key.getEncoded())));
        Certificate certificate = keyStore.getCertificate(alias);
        PublicKey publicKey = certificate.getPublicKey();
        System.out.println("jks文件中的公钥:" + new String(Base64.encode(publicKey.getEncoded())));
    }
    //读取证书文件获取公钥
    public static void readCer() throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate =
                certificateFactory.generateCertificate(new FileInputStream(CER_FILE));
        PublicKey publicKey = certificate.getPublicKey();
        System.out.println("证书中的公钥:" + new String(Base64.encode(publicKey.getEncoded())));
    }
}

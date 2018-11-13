package com.sida.xiruo.xframework.cert;

import com.sida.xiruo.common.components.encrypt.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
//import sun.security.pkcs10.PKCS10;
import sun.security.pkcs10.PKCS10;
import sun.security.x509.X500Name;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JskAndCerFileGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(JskAndCerFileGenerator.class);
    private static final String JKS_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.jks";
    private static final String CER_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.cer";
    private static final String CSR_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.csr";
    private static final String P12_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.p12";
    private static final String PEM_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.pem";
    private static final String CRT_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.crt";
    private static final String PUB_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.pub";
    private static final String KEY_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida.key";
    private static final String NP_KEY_FILE = "C:/project/dcloud/security/authorization/src/main/resources/sida-np.key";
    private static final String PASSWORD = "sidasida";
    private static final String ALIAS = "sida";
    private static final String KEYTOOL_CMD = "C:/Java/jdk1.8.0_121/bin/keytool";
    private static final String OPENSSL_CMD = "\"C:/Program Files/OpenSSL-Win64/bin/openssl\"";

    public static void main(String[] args) {
        try {
//            removeAllCert();
//            Thread.sleep(1000);
//            buildKeyAndSaveToJksFile();
//            Thread.sleep(3000);
//            exportCerFile();
////            Thread.sleep(3000);
//            exportCsrFile();
////            Thread.sleep(3000);
//            exportP12File();
//            Thread.sleep(3000);
//            exportPemFile();
//            Thread.sleep(3000);
//            exportCrtFile();
//            exportPubFile();
//            exportKeyFile();
//            Thread.sleep(3000);
//            exportNpKeyFile();
//            Thread.sleep(3000);
            readJks();
            readCer();
            readCsr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void removeAllCert() {
        try {
            File file = new File(JKS_FILE);
            file.delete();
            file = new File(CER_FILE);
            file.delete();
            file = new File(CSR_FILE);
            file.delete();
            file = new File(CRT_FILE);
            file.delete();
            file = new File(P12_FILE);
            file.delete();
            file = new File(PEM_FILE);
            file.delete();
            file = new File(PUB_FILE);
            file.delete();
            file = new File(KEY_FILE);
            file.delete();
            file = new File(NP_KEY_FILE);
            file.delete();
        } catch(Exception e) {
            System.out.println(e.getMessage());
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
                "/c",
                "start", // cmd Shell命令
                KEYTOOL_CMD,
                "-genkeypair", //表示生成密钥
                "-alias", //要处理的条目的别名（jks文件别名）
                ALIAS,
                "-keyalg", //密钥算法名称(如 RSA DSA（默认是DSA）)
                "RSA",
//                "-keysize",//密钥位大小(长度)
//                "4096",
//                "-sigalg", //签名算法名称
//                "SHA1withRSA",
                "-dname",// 唯一判别名,CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称),
                // ST=(州或省份名称), C=(单位的两字母国家代码)"
                "CN=dcloud.butongtech.com, OU=不同（深圳）科技有限公司, O=设计云, L=深圳市, ST=广东省, C=CN",
                "-validity", // 有效天数
                "3650",
                "-storetype",
                "JKS",
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
                "cmd ", "/c",
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
    public static void exportCsrFile() {
        String[] command = new String[]{
                "cmd ", "/c",
                "start", // cmd Shell命令
                KEYTOOL_CMD,
                "-certreq",
                "-keyalg",
                "RSA",
                "-alias", // -alias指定别名，这里是ss
                ALIAS,
                "-keystore", // -keystore指定keystore文件，这里是d:/demo.keystore
                JKS_FILE,
                "-storetype",
                "JKS",
                "-file",//-file指向导出路径
                CSR_FILE,
                "-storepass",// 指定密钥库的密码
                PASSWORD
        };
        executeCommand(command);
    }
//    public static void exportCrtFile() {
//        String[] command = new String[]{
//                "cmd ", "/c",
//                "start", // cmd Shell命令
//                KEYTOOL_CMD,
//                "-export", // - export指定为导出操作
//                "-alias", // -alias指定别名，这里是ss
//                ALIAS,
//                "-keystore", // -keystore指定keystore文件，这里是d:/demo.keystore
//                JKS_FILE,
//                "-file",//-file指向导出路径
//                CRT_FILE,
//                "-storepass",// 指定密钥库的密码
//                PASSWORD
//        };
//        executeCommand(command);
//    }
    public static void exportP12File() {
        String[] command = new String[]{
                "cmd ", "/c",
                "start", // cmd Shell命令
                KEYTOOL_CMD,
                "-importkeystore",
                "-srckeystore",
                JKS_FILE,
                "-destkeystore",
                P12_FILE,
                "-srcstoretype",
                "JKS",
                "-deststoretype",
                "PKCS12",
                "-srcalias",
                ALIAS,
                "-destalias",
                ALIAS,
                "-deststorepass",
                PASSWORD,
                "-srcstorepass",
                PASSWORD,
                "-srckeypass",
                PASSWORD,
                "-destkeypass",
                PASSWORD,
                "-noprompt"
        };
        executeCommand(command);
    }
    public static void exportPemFile() {
        String[] command = new String[]{
//                "cmd ", "/c",
//                "start", // cmd Shell命令
                OPENSSL_CMD,
                "pkcs12",
                "-in",
                P12_FILE,
                "-passin",
                "pass:" + PASSWORD,
                "-passout",
                "pass:" + PASSWORD,
                "-out",
                PEM_FILE
        };
        executeCommand(command);
    }
    public static void exportCrtFile() {
        String[] command = new String[]{
//                "cmd ", "/c",
//                "start", // cmd Shell命令
                OPENSSL_CMD,
                "x509",
                "-in",
                PEM_FILE,
                "-passin",
                "pass:" + PASSWORD,
//                "-passout",
//                "pass:" + PASSWORD,
                "-out",
                CRT_FILE
        };
        executeCommand(command);
    }
    public static void exportPubFile() {
        String[] command = new String[]{
//                "cmd ", "/c",
//                "start", // cmd Shell命令
                OPENSSL_CMD,
                "rsa",
                "-in",
                PEM_FILE,
                "-passin",
                "pass:" + PASSWORD,
                "-pubout",
                "-out",
                PUB_FILE
        };
        executeCommand(command);
    }
    public static void exportKeyFile() {
        String[] command = new String[]{
//                "cmd ", "/c",
//                "start", // cmd Shell命令
                OPENSSL_CMD,
                "rsa",
                "-in",
                PEM_FILE,
                "-passin",
                "pass:" + PASSWORD,
                "-passout",
                "pass:" + PASSWORD,
                "-out",
                KEY_FILE
        };
        executeCommand(command);
    }
    public static void exportNpKeyFile() {
        String[] command = new String[]{
//                "cmd ", "/c",
//                "start", // cmd Shell命令
                OPENSSL_CMD,
                "rsa",
                "-in",
                KEY_FILE,
                "-out",
                NP_KEY_FILE
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
        System.out.println("cr证书中的公钥:" + new String(Base64.encode(publicKey.getEncoded())));
    }

//    public static void readCsr() throws Exception {
//        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//        Certificate certificate =
//                certificateFactory.generateCertificate(new FileInputStream(CSR_FILE));
//        PublicKey publicKey = certificate.getPublicKey();
//        System.out.println("证书中的公钥:" + new String(Base64.encode(publicKey.getEncoded())));
//    }

    public static PKCS10 readCsr() throws Exception {
        File f = new File(CSR_FILE);
        InputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] bytes = new byte[(int) f.length()];
        in.read(bytes);
        String base64String = new String(bytes, "ISO-8859-1");
//        System.out.println(base64String);
        Pattern p = Pattern
                .compile("-----BEGIN NEW CERTIFICATE REQUEST-----([\\s\\S]*?)-----END NEW CERTIFICATE REQUEST-----([\\s\\S]*)");
        BASE64Decoder decoder = new BASE64Decoder();
        Matcher m = p.matcher(base64String);
        if (m.find()) {
            String s = m.group(1);
//            System.out.println(s.trim());
            byte[] bArray = decoder.decodeBuffer(s);
            PKCS10 csr = new PKCS10(bArray);
            PublicKey publicKey = csr.getSubjectPublicKeyInfo();
            X500Name subjectX500Name = csr.getSubjectName();
//            System.out.println(publicKey);
            System.out.println("csr证书中的公钥:" + new String(Base64.encode(publicKey.getEncoded())));
            System.out.println(subjectX500Name);
            return csr;
        }
        throw new Exception("文件错误 ，无法读取csr");
    }
}

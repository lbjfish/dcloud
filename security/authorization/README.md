证书公钥私钥的创建步骤:
1.keytool -genkeypair -alias chelizidev -keyalg RSA -dname "CN=clzxc L=xurui S=C C=GD" -keypass chelizi201723 -keystore clzdev.jks -storepass chelizi201723
2.keytool -list -rfc --keystore clzdev.jks | openssl x509 -inform pem -pubkey

clzdev.jks改文件为私钥 私钥放在授权服务器的配置文件当中
第二步骤生成的是公钥放资源服务器中的配置文件中
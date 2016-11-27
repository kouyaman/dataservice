package jp.iforcom.bs.util;

import org.mindrot.jbcrypt.CipherHelper;

public class Encryption {

      public static void main(String[] args) {

          //データ送信用の結合＆暗号化
          String retData = execute(args[0], args[1]);

          //データ出力
          System.out.println("暗号化文字列：" + retData);
      }

      public static String execute(String id, String pass) {

          final String key = "1234567890123456"; // 暗号化方式「AES」の場合、キーは16文字で
          final String algorithm = "AES"; // 暗号化方式「AES(Advanced Encryption Standard)」

          final String target = id + "\t" + pass;
          String encrypedResult = "";

          try {

              encrypedResult = CipherHelper.encrypt(target, key, algorithm);

              encrypedResult = encrypedResult.replace("+", "_");
              encrypedResult = encrypedResult.replace("/", "-");
              encrypedResult = encrypedResult.replace("=", ".");

          } catch (Exception e) {
              e.printStackTrace();
          }

          return encrypedResult;

      }
}

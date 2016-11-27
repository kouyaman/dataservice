package jp.iforcom.bs.util;

import org.mindrot.jbcrypt.CipherHelper;

public class Decription {

    public static void main(String[] args) {
        final String authId = args[0];
        String retData;
        retData = authId;

        String DecryptResult = execute(retData);
        String[] decryptData = DecryptResult.split("\t");
        String userId = decryptData[0];
        String password = decryptData[1];


        //データ出力
        System.out.println("復号後ID：" + userId);
        System.out.println("復号後Pass：" + password);
    }

      public static String execute(String target){

          //keyはEncryptionTestと同じ
          final String key = "1234567890123456"; // 暗号化方式「AES」の場合、キーは16文字で
          final String algorithm = "AES"; // 暗号化方式「AES(Advanced Encryption Standard)」

          String decryptResult = "";

          try {
              target = target.replace("_","+");
              target = target.replace("-","/");
              target = target.replace(".","=");

              decryptResult = CipherHelper.decrypt(target, key, algorithm);

          }catch(Exception e){
              e.printStackTrace();
          }

          return decryptResult;
      }

}

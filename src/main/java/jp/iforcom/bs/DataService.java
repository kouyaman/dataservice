package jp.iforcom.bs;

import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import jp.iforcom.bs.response.ResponsePut;
import jp.iforcom.bs.response.ResponsePut.Info;
import jp.iforcom.bs.util.Decription;
import jp.iforcom.bs.util.Encryption;

/**
 * パス：ルート
 */
@Path("/")
public class DataService {

    private Logger logger = Logger.getLogger(DataService.class);

    /**
     * 今回修正していくAPI
     *
     * パス：/{認証ID}/{インフォメーションID}
     *
     * @param authId 認証ID
     * @param infoId インフォメーションID
     * @param requestBody リクエストボディ
     * @return レスポンス情報（JSON）
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN + ";charset=UTF-8")
    @Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
    @Path("/{authId}/{infoId}")
    public Response putData(
            @PathParam("authId") String authId,
            @PathParam("infoId") String infoId,
            String requestBody) {

        // 設定ファイルを読み込む
        ClassLoader cl = this.getClass().getClassLoader();
        URL url = cl.getResource("log4j.properties");
        PropertyConfigurator.configure(url);

        logger.debug("AAAAA");

        //レスポンスデータ
        ResponsePut response = new ResponsePut();
        //レスポンスデータ（返却情報）
        Info info = new ResponsePut.Info();

        try {

            //--------------------------------------------------------------------------------
            //【八木くん】ここから
            //①暗号化されたユーザID＋パスワードが、authIdに入ってくるので、
            //　復号化して、ユーザID、パスワードに分ける。
            //　例：ユーザID="ike"、パスワード="ike"→"lCnf0zR21Z-nuXbq2xqQxA.."

            String decResult = Decription.execute(authId);
            String[] div = decResult.split("\t");
            String userId = div[0];
            String password = div[1];

            //データ出力
            System.out.println("復号後ID：" + userId);
            System.out.println("復号後Pass：" + password);

            //【八木くん】ここまで
            //--------------------------------------------------------------------------------
            //【池本さん】ここから

            //①作成したDBアクセスクラスのインスタンスを作成する。
            DBAccess dba = new DBAccess();

            //②ユーザID、パスワードをもとに、ユーザ情報チェックメソッドを呼ぶ。
            if (!dba.GetUserInfo(userId, password)) {
                // 存在しない
            } else {
                //③ユーザID、インフォメーションIDをもとに、データ蓄積情報登録メソッドを呼ぶ。
                dba.UpdateDataInfo(userId, infoId, requestBody);
            }


            //【池本さん】ここまで
            //--------------------------------------------------------------------------------

            //デバッグ情報
            //デバッグを出したい時に追記する
            StringBuffer sb = new StringBuffer();
            sb.append("authId=" + authId);
            sb.append(", infoId=" + infoId);
            response.setDebug(sb.toString());

            //返却情報
            info.setResultString("xxxxxxxxxxxxxxxxxxx");
            response.setInfo(info);

        } catch (Exception e) {

            //TODO
            //DBエラー等の場合、ここでエラー内容を返す
            response.setInfo(null);
            response.setError(e.getMessage(), e.getStackTrace().toString());
        }

        //レスポンス返却
        return Response.ok(response,
                MediaType.APPLICATION_JSON).build();

    }

    /**
     * エンコード
     *
     * パス：/enc
     *
     * @param requestBody リクエストボディ
     * @return レスポンス情報（JSON）
     */
    @POST
    @Consumes(MediaType.TEXT_PLAIN + ";charset=UTF-8")
    @Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
    @Path("/enc")
    public Response enc(String requestBody) {

        //レスポンスデータ
        ResponsePut response = new ResponsePut();
        //レスポンスデータ（返却情報）
        Info info = new ResponsePut.Info();

        try {

            String[] div = requestBody.split("\t");
            String userId = div[0];
            String password = div[1];

            //データ送信用の結合＆暗号化
            String encResult = Encryption.execute(userId, password);

            //データ出力
            System.out.println("暗号化文字列：" + encResult);

            //返却情報
            info.setResultString(encResult);
            response.setInfo(info);

        } catch (Exception e) {

            //TODO
            //DBエラー等の場合、ここでエラー内容を返す
            response.setInfo(null);
            response.setError("エラーコード", "エラーメッセージ");
        }

        //レスポンス返却
        return Response.ok(response,
                MediaType.APPLICATION_JSON).build();

    }

    /**
     * テスト用API
     *
     * パス：/hello
     *
     * @return "Hello World"
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String sayHello() {
        return "Hello World";
    }



    /**
     * データ返却API
     *
     * パス：/{ユーザーID}/{キーID}
     *
     * @param userId ユーザーID
     * @param keyId キーID
     * @return
     */
    @GET
    @Produces({MediaType.TEXT_PLAIN + ";charset=UTF-8"})
    @Path("/{userId}/{keyId}")
    public Response getTest(
            @PathParam("userId") String userId,
            @PathParam("keyId") String keyId) {

        // 設定ファイルを読み込む
        ClassLoader cl = this.getClass().getClassLoader();
        URL url = cl.getResource("log4j.properties");
        PropertyConfigurator.configure(url);

        //レスポンスデータ
        ResponsePut response = new ResponsePut();
        //レスポンスデータ（返却情報）
        Info info = new ResponsePut.Info();

        try {
            //データ出力
            System.out.println("ユーザーID：" + userId);
            System.out.println("キーID：" + keyId);

            //①作成したDBアクセスクラスのインスタンスを作成する。
            DBAccess dba = new DBAccess();

            //②ユーザID、パスワードをもとに、ユーザ情報チェックメソッドを呼ぶ。
            response = dba.GetData(userId, keyId);
        } catch (Exception e) {

            //TODO
            //DBエラー等の場合、ここでエラー内容を返す
            response.setInfo(null);
            response.setError(e.getMessage(), e.getStackTrace().toString());
        }

        //レスポンス返却
        return Response.ok(response,
                MediaType.APPLICATION_JSON).build();
    }

}

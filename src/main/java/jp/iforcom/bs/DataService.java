package jp.iforcom.bs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.iforcom.bs.response.ResponsePut;
import jp.iforcom.bs.response.ResponsePut.Info;

/**
 * パス：ルート
 */
@Path("/")
public class DataService {

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

        //レスポンスデータ
        ResponsePut response = new ResponsePut();
        //レスポンスデータ（返却情報）
        Info info = new ResponsePut.Info();

        try {

            //--------------------------------------------------------------------------------
            //【八木くん】ここから
            //①暗号化されたユーザID＋パスワードが、authIdに入ってくるので、
            //　復号化して、ユーザID、パスワードに分ける。
            String userId = "";
            String password = "";




            //【八木くん】ここまで
            //--------------------------------------------------------------------------------
            //【池本さん】ここから
            userId = "ike";
            password = "ike";
            
            //①作成したDBアクセスクラスのインスタンスを作成する。
            DBAccess dba = new DBAccess();

            //②ユーザID、パスワードをもとに、ユーザ情報チェックメソッドを呼ぶ。
            if (!dba.GetUserInfo(userId, password))
            {
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
     * テスト用API
     *
     * パス：/{認証ID}/{インフォメーションID}
     *
     * @param authId 認証ID
     * @param infoId インフォメーションID
     * @return "authId=" + 認証ID + ", infoId=" + インフォメーションID + " ですね？"
     */
    @GET
    @Produces({MediaType.TEXT_PLAIN + ";charset=UTF-8"})
    @Path("/{authId}/{infoId}")
    public String getTest(
            @PathParam("authId") String authId,
            @PathParam("infoId") String infoId) {

        return "authId=" + authId + ", infoId=" + infoId + " ですね？";
    }

}

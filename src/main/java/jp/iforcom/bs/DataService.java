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

    /**
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

            System.out.println(authId);
            System.out.println(infoId);

            //デバッグ情報
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

}

package jp.iforcom.bs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.iforcom.bs.response.ResponsePut;
import jp.iforcom.bs.response.ResponsePut.Info;

public class DBAccess {

    static Connection _con = null;


    /**
     * DB接続
     */
    private void GetConnection() {
        try {

            // ドライバクラスをロード
            Class.forName("com.mysql.jdbc.Driver");

            // データベースへ接続
//            _con = DriverManager.getConnection("jdbc:mysql://192.168.85.215/dservice_db?useSSL=false","dservice_admin","_iforcom1456");
            _con = DriverManager.getConnection("jdbc:mysql://dservice.caf3ytu9abel.ap-northeast-1.rds.amazonaws.com/dservice_db?useSSL=false","dservice_admin","_iforcom1456");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * UserInfoデータ存在チェック(認証)
     * @param userId
     * @param password
     * @param userName
     * @return
     */
    public boolean GetUserInfo(String userId, String password)
    {
        PreparedStatement ps = null;
        String sql = "SELECT UserId, Password, UserName FROM UserInfo WHERE UserId = '" + userId + "' AND Password = '" + password + "'";

        try
        {
            GetConnection();

            // ステートメントオブジェクトを生成
            ps = _con.prepareStatement(sql);

            // クエリーを実行して結果セットを取得
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                // close処理
                if(ps != null) {
                    ps.close();
                }
                // close処理
                if(_con != null) {
                    _con.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * UserIdチェック(登録時)
     * @param userId
     * @return
     */
    private boolean UserIdCheck(String userId)
    {
        PreparedStatement ps = null;
        String sql = "SELECT UserId FROM UserInfo WHERE UserId = '" + userId + "'";

        try
        {
            // ステートメントオブジェクトを生成
            ps = _con.prepareStatement(sql);

            // クエリーを実行して結果セットを取得
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {

        }
    }

    /**
     * UserInfoデータ追加
     * @param userId
     * @param password
     * @param userName
     * @return
     */
    public boolean AddUserInfo(String userId, String password)
    {
        PreparedStatement ps = null;

        String sql = "INSERT INTO UserInfo(UserId, Password, UserName) VALUES('" + userId + "', '" + password + "')";

        try
        {
            GetConnection();
            if (UserIdCheck(userId))
            {
                return false;
            }

            // ステートメントオブジェクトを生成
            ps = _con.prepareStatement(sql);
            // 実行
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                // close処理
                if(ps != null) {
                    ps.close();
                }
                // close処理
                if(_con != null) {
                    _con.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * DataInfoデータ存在チェック
     * @param userId
     * @param keyId
     * @param data
     * @return
     */
    public boolean GetDataInfo(String userId, String keyId)
    {
        PreparedStatement ps = null;
        String sql = "SELECT UserId, KeyId, Data FROM DataInfo WHERE UserId = '" + userId + "' AND KeyId = '" + keyId + "'";

        try
        {
            // ステートメントオブジェクトを生成
            ps = _con.prepareStatement(sql);

            // クエリーを実行して結果セットを取得
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {

        }
    }

    /**
     * DataInfoデータ追加/更新
     * @param userId
     * @param keyId
     * @param data
     * @return
     */
    public boolean UpdateDataInfo(String userId, String keyId, String data)
    {
        PreparedStatement ps = null;
        String sql = null;

        try
        {
            GetConnection();
            Date updateDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            if (!GetDataInfo(userId, keyId))
            {
                sql = "INSERT INTO DataInfo(UserId, KeyId, Data, UpdateDate) VALUES('" + userId + "', '" + keyId + "', '" + data + "', '" + sdf.format(updateDate) + "')";
            }
            else
            {
                sql = "UPDATE DataInfo SET Data = '" + data + "', UpdateDate = '" + sdf.format(updateDate) + "' WHERE UserId = '" + userId + "' AND KeyId = '" + keyId + "'";
            }

            // ステートメントオブジェクトを生成
            ps = _con.prepareStatement(sql);
            // 実行
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                // close処理
                if(ps != null) {
                    ps.close();
                }
                // close処理
                if(_con != null) {
                    _con.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * UserInfoデータ削除
     * @param userId
     * @param password
     * @return
     */
    public boolean DeleteUserInfo(String userId, String password)
    {
        PreparedStatement ps = null;
        String sql = "DELETE FROM UserInfo WHERE UserId = '" + userId + "', Password = '" + password + "'";

        try
        {
            GetConnection();

            // ステートメントオブジェクトを生成
            ps = _con.prepareStatement(sql);
            // 実行
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                // close処理
                if(ps != null) {
                    ps.close();
                }
                // close処理
                if(_con != null) {
                    _con.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * DataInfoデータ削除
     * @param userId
     * @param keyId
     * @return
     */
    public boolean DeleteDataInfo(String userId, String keyId)
    {
        PreparedStatement ps = null;
        String sql = "DELETE FROM DataInfo WHERE UserId = '" + userId + "', KeyId = '" + keyId + "'";

        try
        {
            GetConnection();

            // ステートメントオブジェクトを生成
            ps = _con.prepareStatement(sql);
            // 実行
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                // close処理
                if(ps != null) {
                    ps.close();
                }
                // close処理
                if(_con != null) {
                    _con.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * データ取得
     * @param userId
     * @param keyId
     * @return ResponsePut
     */
    public ResponsePut GetData(String userId, String keyId)
    {
        PreparedStatement ps = null;
        String sql = "SELECT UserId, KeyId, Data, UpdateDate FROM DataInfo WHERE UserId = '" + userId + "' AND KeyId LIKE '%" + keyId + "%' ORDER BY UserId, KeyId, UpdateDate";

        ResponsePut response = new ResponsePut();
        Info info = new ResponsePut.Info();
        ArrayList list = new ArrayList();

        try
        {
            GetConnection();


            // ステートメントオブジェクトを生成
            ps = _con.prepareStatement(sql);

            // クエリーを実行して結果セットを取得
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String key = rs.getString("KeyId");
                String user = rs.getString("UserId");
                String data = rs.getString("Data");
                Date updateDate = rs.getDate("UpdateDate");
                list.add("\n\"userId\":\"" + user + "\",\n"
                        + "\"keyId\":\"" + key + "\",\n"
                        + "\"updateDate\":\"" + updateDate + "\",\n"
                        + data);
            }
            info.setResultList(list);
            response.setInfo(info);
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                // close処理
                if(ps != null) {
                    ps.close();
                }
                // close処理
                if(_con != null) {
                    _con.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

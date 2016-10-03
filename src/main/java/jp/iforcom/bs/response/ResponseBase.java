package jp.iforcom.bs.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * レスポンス
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseBase implements Serializable {

    /**
     * デバッグ情報
     */
    @XmlElement(name = "debug")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String debug;

    /**
     * 処理結果
     */
    @XmlElement(name = "result")
    private String result = "Success";

    /**
     * エラー情報
     */
    @XmlElement(name = "error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorInfo errInfo;


    /**
     * コンストラクタ（正常終了時）
     */
    public ResponseBase() {

        //結果設定
        result = "Success";

        //デバッグ情報は設定しない
        debug = null;

        //エラー情報は設定しない
        errInfo = null;
    }

    /**
     * デバッグ情報を取得する
     *
     * @return デバッグ情報
     */
    public String getDebug() {
        return this.debug;
    }

    /**
     * デバッグ情報を設定する
     *
     * @param debug デバッグ情報
     */
    public void setDebug(String debug) {
        this.debug = debug;
    }

    /**
     * 処理結果を取得する
     *
     * @return 処理結果
     */
    public String getResult() {
        return this.result;
    }

    /**
     * 処理結果を設定する
     *
     * @param result 処理結果
     */
    public void setResult(String result) {
        this.result = result;
    }


    /**
     * エラー情報を取得する
     *
     * @return エラー情報
     */
    public ErrorInfo getErrorInfo() {
        return this.errInfo;
    }

    /**
     * エラー情報を設定する
     *
     * @param loErrInfo エラー情報
     */
    public void setErrorInfo(ErrorInfo loErrInfo) {
        this.errInfo = loErrInfo;
    }

    /**
     * エラー情報を設定する
     *
     * @param errorCode エラーコード
     * @param errorMessage エラーメッセージ
     */
    public void setError(String errorCode, String errorMessage) {

        //結果設定
        result = "Failure";

        //エラー情報設定
        errInfo = new ErrorInfo();
        errInfo.setCode(errorCode);
        errInfo.setMessage(errorMessage);
    }

    /**
     * 処理結果を提供する
     */
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ErrorInfo implements Serializable {

        //コード
        @XmlElement(name = "code")
        private String code;

        //メッセージ
        @XmlElement(name = "message")
        private String message;

        /**
         * コンストラクタ.
         */
        public ErrorInfo() {
            //該当処理なし
        }

        /**
         * コードを取得する
         *
         * @return 処理結果
         */
        public String getCode() {
            return this.code;
        }

        /**
         * コードを設定する
         *
         * @param code 処理結果
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * メッセージを取得する
         *
         * @return メッセージ
         */
        public String getMessage() {
            return this.message;
        }

        /**
         * メッセージを設定する
         *
         * @param message メッセージ
         */
        public void setMessage(String message) {
            this.message = message;
        }
    }

}

package jp.iforcom.bs.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * ルート
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponsePut extends ResponseBase {

    //返却情報
    @XmlElement(name = "result_data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Info information;

    /**
     * 返却情報を取得する
     *
     * @return 返却情報
     */
    public Info getInfo() {
        return this.information;
    }

    /**
     * 返却情報を設定する
     *
     * @param info 返却情報
     */
    public void setInfo(Info info) {
        this.information = info;
    }

    //返却情報
    @XmlElement(name = "data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String data;

    /**
     * 返却情報を取得する
     *
     * @return 返却情報
     */
    public String getData() {
        return this.data;
    }

    /**
     * 返却情報を設定する
     *
     * @param info 返却情報
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 返却情報を提供する
     */
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Info implements Serializable {

        //返却文字列
        @XmlElement(name = "result_string")
        private String resultString;

        //返却文字列
        @XmlElement(name = "result_list")
        private List resultList;

        /**
         * 返却文字列を取得する
         *
         * @return 返却文字列
         */
        public String getResultString() {
            return this.resultString;
        }

        /**
         * 返却文字列を設定する
         *
         * @param resultString 返却文字列
         */
        public void setResultString(String resultString) {
            this.resultString = resultString;
        }

        /**
         * 返却文字列を取得する
         *
         * @return 返却文字列
         */
        public List getResultList() {
            return this.resultList;
        }

        /**
         * 返却文字列を設定する
         *
         * @param resultString 返却文字列
         */
        public void setResultList(List resultList) {
            this.resultList = resultList;
        }
    }

}

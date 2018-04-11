package io.itit.androidlibrary.network.domain;

import java.io.Serializable;

public class UploadData extends BaseMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * attachment : {"associatedId":0,"createUserId":0,"id":0,"isDelete":false,"name":"head.png",
     * "size":0,"updateUserId":0,"uuid":"73e2a29d93804c0ea410ece4fe33e0bb.png"}
     * file_path : /p/file/get_file/73e2a29d93804c0ea410ece4fe33e0bb.png
     * success : true
     */

    public AttachmentBean attachment;
    public String file_path;
    public boolean success;

    public static class AttachmentBean {
        /**
         * associatedId : 0
         * createUserId : 0
         * id : 0
         * isDelete : false
         * name : head.png
         * size : 0
         * updateUserId : 0
         * uuid : 73e2a29d93804c0ea410ece4fe33e0bb.png
         */

        public int associatedId;
        public int createUserId;
        public int id;
        public boolean isDelete;
        public String name;
        public int size;
        public int updateUserId;
        public String uuid;
    }


}
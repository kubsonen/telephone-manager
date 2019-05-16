package pl.jj.app.model;

public class AjaxResponse {

    private boolean isAnServerResponse = true;

    private boolean error;

    private String msg;

    private Object returnedObject;

    public AjaxResponse(boolean error, String message) {
        this.error = error;
        this.msg = message;
    }

    public static AjaxResponse responseObject(String msg, Object o){
        AjaxResponse ar = new AjaxResponse(false, msg);
        ar.setReturnedObject(o);
        return ar;
    }

    public static AjaxResponse responseSuccess(String msg){
        return new AjaxResponse(false, msg);
    }

    public static AjaxResponse responseError(String msg){
        return new AjaxResponse(true, msg);
    }

    public boolean isAnServerResponse() {
        return isAnServerResponse;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getReturnedObject() {
        return returnedObject;
    }

    public void setReturnedObject(Object returnedObject) {
        this.returnedObject = returnedObject;
    }
}

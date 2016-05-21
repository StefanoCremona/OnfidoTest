package uk.co.stefanocremona.mycomiclibrary.utils;

/**
 * Created by stefanocremona on 21/05/16.
 */
import org.json.JSONException;
import org.json.JSONObject;

public class HTTPResponce {
    private int         responceCode;
    private JSONObject  responceBody;

    public HTTPResponce(int responceCode, String responceBody){
        this.responceCode = responceCode;

        try {
            if(responceBody!=null)
                this.responceBody = new JSONObject(responceBody);
            else
                this.responceBody = new JSONObject();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getResponceCode(){
        return responceCode;
    }
    public JSONObject getResponceBody(){
        return responceBody;
    }
}